package templates;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIPIntermediate3 {

	static Thread visionThread;
	static GRIPIntermediate2Pipeline pipeline=new GRIPIntermediate2Pipeline();
	static NetworkTable table;
	static String ENABLE_CHANNEL0="Enable channel 0";
	
	static public void robotInit(){
		table=NetworkTable.getTable("Robot/Vision");
		table.putBoolean(ENABLE_CHANNEL0,true);
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
			camera.setResolution(320, 240);
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(1);
			// Set the resolution
			camera1.setResolution(320, 240);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo(camera);
			CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 320, 240);
			
			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if(table.getBoolean(ENABLE_CHANNEL0,true)){
		//			System.out.println("cvSink");
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				
				} else{
		//			System.out.println("cvSink1");
				if (cvSink1.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				}
				// Put a rectangle on the image
				pipeline.process(mat);
		//		System.out.println("mat size = "+mat.size());
				outputStream.putFrame(mat);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
