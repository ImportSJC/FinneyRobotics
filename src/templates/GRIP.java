package templates;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

//import cpi.tools.grip.SwitchPipeline; // uncomment this during customization

//import cpi.Net;
/**
 * 
 * @author Thomas Wulff
 *
 */
public class GRIP {

	Thread visionThread;
	GripPipeline pipeline;
	CvSource outputStream;
	//Net<Double[]> contours;
	GRIP(int channel){
		pipeline = new GripPipeline();
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(channel);
			// Set the resolution
			camera.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			outputStream = CameraServer.getInstance().putVideo("GRIP", 640, 480);

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
				// Run your pipeline
				pipeline.process(mat);
				// Give the output stream a new image to display
				outputStream.putFrame(pipeline.hslThresholdOutput());
//				contours=new Net <Double[]> ("Grip","Contours",(Double[])pipeline.filterContoursOutput().toArray());
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
	}
}
