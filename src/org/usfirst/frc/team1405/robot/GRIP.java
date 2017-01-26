package org.usfirst.frc.team1405.robot;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import cpi.tools.grip.GRIP3X1SwitchPipeline;

import cpi.Net;
/**
 * 
 * @author Thomas Wulff
 *
 */
public class GRIP {

	Thread visionThread;
	GRIP3X1SwitchPipeline pipeline;
	CvSource outputStream;
	Net<Double[]> contours;
	GRIP(int channel0,int channel1,int channel2){
		pipeline = new GRIP3X1SwitchPipeline();
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture(channel1);
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(channel1);
			UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(channel2);
			// Set the resolution
			camera1.setResolution(320,280);
			camera2.setResolution(320, 280);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
			CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
			// Setup a CvSource. This will send images back to the Dashboard
			outputStream = CameraServer.getInstance().putVideo("GRIP", 320, 240);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat0 = new Mat();
			Mat mat1 = new Mat();
			Mat mat2 = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink1.grabFrame(mat1) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink1.getError());
					// skip the rest of the current iteration
					continue;
				}
				if (cvSink2.grabFrame(mat2) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink1.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Run your pipeline
				pipeline.process(mat0,mat1,mat2);
				// Give the output stream a new image to display
				outputStream.putFrame(pipeline.switch1Output());
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
	}
}
