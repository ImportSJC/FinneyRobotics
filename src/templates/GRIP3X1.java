package templates;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import cpi.tools.grip.GRIP3X1SwitchPipeline;

public class GRIP3X1 {

	static Thread visionThread;
	static GRIPIntermediate2Pipeline pipeline=new GRIPIntermediate2Pipeline();
	
	static public void robotInit(){
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture(0);
			// Set the resolution
			camera0.setResolution(320, 240);
			// Get the UsbCamera from CameraServer
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(1);
			// Set the resolution
			camera1.setResolution(320, 240);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("GRIP Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Put a rectangle on the image
				//Place process here
				
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
