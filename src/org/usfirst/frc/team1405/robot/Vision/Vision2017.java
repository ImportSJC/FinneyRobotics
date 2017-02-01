package org.usfirst.frc.team1405.robot.Vision;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1405.robot.Vision.pipelines.*;

public class Vision2017 {

	static final int RES_WIDTH=320;
	static final int RES_HEIGHT=240;
	static UsbCamera camera[];
	static CvSink cvSink[];
	static GeneralDetectionPipeline pipeline;
	
	static Thread visionThread;
	
	static public void robotInit(){
		camera=new UsbCamera[3];
		visionThread = new Thread(() -> {
			// Get three UsbCamera from CameraServer
			camera[0] = CameraServer.getInstance().startAutomaticCapture(0);
			// Set the resolution
			camera[0].setResolution(RES_WIDTH, RES_HEIGHT);
			
			// Get three UsbCamera from CameraServer
			camera[1] = CameraServer.getInstance().startAutomaticCapture(1);
			// Set the resolution
			camera[1].setResolution(RES_WIDTH, RES_HEIGHT);

			// Get three UsbCamera from CameraServer
			camera[2] = CameraServer.getInstance().startAutomaticCapture(0);
			// Set the resolution
			camera[2].setResolution(RES_WIDTH, RES_HEIGHT);

			// Get a CvSink. This will capture Mats from the camera
			cvSink[0] = CameraServer.getInstance().getVideo(camera[0]);
			cvSink[1] = CameraServer.getInstance().getVideo(camera[1]);
			cvSink[2] = CameraServer.getInstance().getVideo(camera[2]);
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Selected Video", RES_WIDTH, RES_HEIGHT);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat[] = new Mat[3];
			mat[0]=new Mat();
			mat[1]=new Mat();
			mat[2]=new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink[0].grabFrame(mat[0]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink[0].getError());
					// skip the rest of the current iteration
					continue;
				}
				pipeline.process(mat[0]);
				if (cvSink[1].grabFrame(mat[1]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink[1].getError());
					// skip the rest of the current iteration
					continue;
				}
				if (cvSink[2].grabFrame(mat[2]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink[2].getError());
					// skip the rest of the current iteration
					continue;
				}
				pipeline.process(mat[0]);
				outputStream.putFrame(pipeline.);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
