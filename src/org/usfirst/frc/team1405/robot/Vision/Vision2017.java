package org.usfirst.frc.team1405.robot.Vision;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import org.opencv.core.Mat;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import templates.GRIPIntermediate2Pipeline;
import org.usfirst.frc.team1405.robot.Vision.pipelines.GeneralDetectionPipeline;

public class Vision2017 {

	static Thread visionThread;
	static GeneralDetectionPipeline boilerPipeline=new GeneralDetectionPipeline("Robot/Vision/Pipelines/Boiler");
	static NetworkTable table;
	static String CAMERA_ID_KEY="Select boiler camera ID (0, 1, 2)";
	static final int VERT_RES=120;
	static final int HOR_RES=160;

	static CameraServer cameraServer;
	static MjpegServer outputStreamServer;
	static UsbCamera camera[]=new UsbCamera[3];
	static UsbCamera camera0;
	static UsbCamera camera1;
	static UsbCamera camera2;

	static CvSink cvSink[]=new CvSink[3];
	static CvSink cvSink0;
	static CvSink cvSink1;
	static CvSink cvSink2;
	
	static CvSource outputStream;

	static boolean setupCamera1=true;
	static boolean setuoCamera2=false;
	static boolean settingsEnable=false;
	static String cameraID="0";
	
	static public void robotInit(){
		table=NetworkTable.getTable("Robot/Vision");
		cameraID=table.getString(CAMERA_ID_KEY,"0");
		table.putString(CAMERA_ID_KEY, cameraID);
		table.setPersistent(CAMERA_ID_KEY);
		visionThread = new Thread(() -> {
			
			camera[0]=CameraServer.getInstance().startAutomaticCapture(0);
			camera[0].setResolution(HOR_RES, VERT_RES);
			camera[0].setFPS(15);
			cvSink[0] = CameraServer.getInstance().getVideo(camera[0]);
			camera[1]=CameraServer.getInstance().startAutomaticCapture(1);
			camera[1].setResolution(HOR_RES, VERT_RES);	
			camera[1].setFPS(15);		
			cvSink[1] = CameraServer.getInstance().getVideo(camera[1]);
			camera[2]=CameraServer.getInstance().startAutomaticCapture(2);
			camera[2].setResolution(HOR_RES, VERT_RES);
			camera[2].setFPS(15);
			cvSink[2] = CameraServer.getInstance().getVideo(camera[2]);

			outputStream = CameraServer.getInstance().putVideo("Vew selected feed", VERT_RES, HOR_RES);
			
			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
//				System.out.println("!Thread.interrupted");
				
				
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if(DriverStation.getInstance().isDisabled())cameraID=table.getString(CAMERA_ID_KEY,"0");
				switch(cameraID){
				case "0":
				default:
				if (cvSink[0].grabFrame(mat) == 0 ) {
					// Send the output the error.
				outputStream.notifyError(cvSink[0].getError());
					// skip the rest of the current iteration
					continue;
					}
				break;
				
				case"1":
					if (cvSink[1].grabFrame(mat) == 0 ) {
					// Send the output the error.
						outputStream.notifyError(cvSink[1].getError());
					// skip the rest of the current iteration
					continue;
					}
				break;
				
				case"2":
				if (cvSink[2].grabFrame(mat) == 0 ) {
					// Send the output the error.
				outputStream.notifyError(cvSink[2].getError());
					// skip the rest of the current iteration
					continue;
					}
				break;
				}
				
				
				// Put a rectangle on the image
//				boilerPipeline.process(mat);
				boilerPipeline.process(mat);
				mat=boilerPipeline.hsvThresholdOutput();
				outputStream.putFrame(mat);
//				
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
