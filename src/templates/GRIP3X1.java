package templates;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import cpi.tools.grip.GRIP3X1SwitchPipeline;

public class GRIP3X1 {

	static Thread visionThread;
	static GRIP3X1SwitchPipeline pipeline=new GRIP3X1SwitchPipeline();
	static String CAMERA1="camera1";
	static String CAMERA2="camera2";
	
	static public void robotInit(){
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture();
			// Set the resolution
			camera0.setResolution(320, 240);
			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			VideoSink videoSinks[]=CvSink.enumerateSinks();
			CvSink cvSinks[]=new CvSink[3];
			cvSinks[0]=cvSink;
			cvSinks[1]=cvSink;
			cvSinks[2]=cvSink;
			if(videoSinks.length>=2)cvSinks[1]=CameraServer.getInstance().getVideo(videoSinks[1].getSource());
			if(videoSinks.length>=3)cvSinks[2]=CameraServer.getInstance().getVideo(videoSinks[2].getSource());
			System.out.println("VideoSink length = "+videoSinks.length);
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("GRIP 3X1", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat[] = new Mat[3];
			mat[0]=mat[1]=mat[2]=new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSinks[0].grabFrame(mat[0]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[0].getError());
					// skip the rest of the current iteration
					continue;
				}
				if (cvSinks[0].grabFrame(mat[1]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[1].getError());
					// skip the rest of the current iteration
					continue;
				}
				if (cvSinks[0].grabFrame(mat[2]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[2].getError());
					// skip the rest of the current iteration
					continue;
				}
				//Place process here
				pipeline.process(mat[0],mat[1],mat[2]);
				// Give the output stream a new image to display
				mat[0]=pipeline.grip3X1Output();
				outputStream.putFrame(mat[0]);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
