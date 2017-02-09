package templates;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIP3Cameras2Switched {

	static Thread visionThread;
	static GRIPIntermediate2Pipeline pipeline=new GRIPIntermediate2Pipeline();
	static NetworkTable table;
	static String ENABLE_CHANNEL1="Enable channel 1";
	static String CAMERA_ID_KEY="Camera ID (0, 1, 2)";
	static final int VERT_RES=120;
	static final int HOR_RES=160;

	static CameraServer cameraServer;
	static MjpegServer outputStreamServer;
	static UsbCamera camera0;
	static UsbCamera camera1;
	static UsbCamera camera2;

	static CvSink cvSink0;
	static CvSink cvSink1;
	static CvSink cvSink2;
	
	static CvSource outputStream;

	static boolean setupCamera1=true;
	static boolean setuoCamera2=false;
	
	static public void robotInit(){
		table=NetworkTable.getTable("Robot/Vision");
		table.putNumber(CAMERA_ID_KEY, 0);
		visionThread = new Thread(() -> {
			
			camera0=CameraServer.getInstance().startAutomaticCapture(0);
			camera0.setResolution(HOR_RES, VERT_RES);
			camera0.setFPS(15);
			cvSink0 = CameraServer.getInstance().getVideo(camera0);
			camera1=CameraServer.getInstance().startAutomaticCapture(1);
			camera1.setResolution(HOR_RES, VERT_RES);	
			camera1.setFPS(15);		
			cvSink1 = CameraServer.getInstance().getVideo(camera1);
			camera2=CameraServer.getInstance().startAutomaticCapture(2);
			camera2.setResolution(HOR_RES, VERT_RES);
			camera2.setFPS(15);
			cvSink2 = CameraServer.getInstance().getVideo(camera2);


			/*
		cameraServer=new MjpegServer("Serve_USB Camera0",1180);
		outputStreamServer=new MjpegServer("Serve_USB outputStream",1181);
			// Get the UsbCamera from CameraServer
			camera0 = new UsbCamera("camera--0",0);
			camera0.setResolution(VERT_RES, HOR_RES);
			cameraServer.setSource(camera0);
			System.out.println("new Thread");
			*/
			/*
			camera1 = new UsbCamera("camera1",1);
			camera1.setResolution(VERT_RES, HOR_RES);
			camera2 = new UsbCamera("camera2",2);
			camera2.setResolution(VERT_RES, HOR_RES);
			cvSink0.setSource(camera0);
			*/
			
			// Set the resolution

			// Get a CvSink. This will capture Mats from the camera
			/*cvSink0=new CvSink("cvSink0");
			// Setup a CvSource. This will send images back to the Dashboard
			outputStream=new CvSource("outputStream",VideoMode.PixelFormat.kMJPEG,160,120,30);
			outputStreamServer.setSource(outputStream);
			*/
			outputStream = CameraServer.getInstance().putVideo("Rectangle", VERT_RES, HOR_RES);
			
			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
//				System.out.println("!Thread.interrupted");
				
				
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if(table.getNumber(CAMERA_ID_KEY,0)==0){
				if (cvSink0.grabFrame(mat) == 0 ) {
					// Send the output the error.
				outputStream.notifyError(cvSink0.getError());
					// skip the rest of the current iteration
					continue;
					}
				}
				
				if(table.getNumber(CAMERA_ID_KEY,0)==1){
					if (cvSink1.grabFrame(mat) == 0 ) {
					// Send the output the error.
						outputStream.notifyError(cvSink1.getError());
					// skip the rest of the current iteration
					continue;
					}
				}
				if(table.getNumber(CAMERA_ID_KEY,0)==2){
				if (cvSink2.grabFrame(mat) == 0 ) {
					// Send the output the error.
				outputStream.notifyError(cvSink2.getError());
					// skip the rest of the current iteration
					continue;
					}
				}
				
				
				// Put a rectangle on the image
				pipeline.process(mat);
//				System.out.println("mat size = "+mat.size());
				outputStream.putFrame(mat);
//				
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
