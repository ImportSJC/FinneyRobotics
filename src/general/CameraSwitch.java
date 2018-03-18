package general;

import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraSwitch {

	static Thread m_visionThread;
	static int  cameraSwitch=0;
	static int  size;
	static CvSink []  cvSink;
	static CvSource outputStream;
	static int device;
	static boolean state;
	
	public   CameraSwitch(UsbCamera [] camera) {m_visionThread = new Thread(() -> {
		// Get a CvSink. This will capture Mats from the camera
		size=camera.length;
		cvSink=new CvSink[size];
		
		for (int i=0;i<size;i++) {
		cvSink[i] = CameraServer.getInstance().getVideo(camera[i]);
		}
		// Setup a CvSource. This will send images back to the Dashboard
		outputStream = CameraServer.getInstance().putVideo("Selected Camera", 640, 480);

		// Mats are very memory expensive. Lets reuse this Mat.
		Mat mat = new Mat();

		// This cannot be 'true'. The program will never exit if it is. This
		// lets the robot stop this thread when restarting robot code or
		// deploying.
		while (!Thread.interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.

				
					if (cvSink[cameraSwitch].grabFrame(mat) == 0) {
						// Send the output the error.
						outputStream.notifyError(cvSink[cameraSwitch].getError());
						// skip the rest of the current iteration
						continue;
					}


			outputStream.putFrame(mat);
		}
	});
	m_visionThread.setDaemon(true);
	m_visionThread.start();
		
	}

	public void showCamera(int dev) {
		if (dev<0)dev=0;
		if (dev>=size)dev=size-1;
		cameraSwitch=dev;
	}
	public void incrementCamera(boolean btn) {
		if((state ==false) && (btn==true) ) {
			device++;
		if (device<0)device=0;
		if (device>=size)device=0;
		cameraSwitch=device;
		}
		state=btn;
	}
}
