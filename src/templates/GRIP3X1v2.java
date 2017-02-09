package templates;



import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import cpi.tools.grip.GRIP3X1SwitchPipeline;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIP3X1v2 {
	static int RES_WIDTH=320;
	static int RES_HEIGHT=240;
	static int RES_DIVISOR=2;
	static String SWITCH_KEY="Camera # (0-2)";
	
	
	static Thread visionThread;
	static GRIP3X1SwitchPipeline pipeline=new GRIP3X1SwitchPipeline();
	static String CAMERA1="camera1";
	static String CAMERA2="camera2";
	static NetworkTable table;
	
	static public void robotInit(){
		visionThread = new Thread(() -> {
			// Set up network table
			table =NetworkTable.getTable("Robot/Test Beds/Vision");
			table.putNumber(SWITCH_KEY, 0.0);
			// Get the UsbCamera from CameraServer
			UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture(0);
			camera0.setResolution(RES_WIDTH, RES_HEIGHT);
			CvSink cvSinks[]=new CvSink[3];
			cvSinks[0] = CameraServer.getInstance().getVideo(camera0);
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(1);
			camera1.setResolution(RES_WIDTH, RES_HEIGHT);
			cvSinks[1] = CameraServer.getInstance().getVideo(camera1);
			UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(2);
			camera2.setResolution(RES_WIDTH, RES_HEIGHT);
			cvSinks[2] = CameraServer.getInstance().getVideo(camera2);
			// Set the resolution
			CvSource outputStream = CameraServer.getInstance().putVideo("GRIP 3X1", RES_WIDTH, RES_HEIGHT);
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
				if (cvSinks[0].grabFrame(mat[0]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[0].getError());
					// skip the rest of the current iteration
					System.out.println("cvSinks[0]");
					continue;
				}
				if (cvSinks[1].grabFrame(mat[1]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[1].getError());
					// skip the rest of the current iteration
					System.out.println("cvSinks[1]");
					continue;
				}
				if (cvSinks[2].grabFrame(mat[2]) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSinks[2].getError());
					// skip the rest of the current iteration
					System.out.println("cvSinks[2]");
					continue;
				}
				//Place process here
				int sw=(int)table.getNumber(SWITCH_KEY, 0.0);
				System.out.println(sw);
				switch(sw){
				case 0:
					System.out.println("case 0:");
					pipeline.setswitch0(true);
					pipeline.setswitch1(true);
					outputStream.putFrame(mat[0]);
					break;
				case 1:
					System.out.println("case 1:");
					pipeline.setswitch0(false);
					pipeline.setswitch1(true);
					outputStream.putFrame(mat[1]);
					break;
				case 2:
					System.out.println("case 2:");
					pipeline.setswitch0(true);
					pipeline.setswitch1(false);
					outputStream.putFrame(mat[2]);
					break;
					
				default:
					System.out.println("default:");
					pipeline.setswitch0(true);
					pipeline.setswitch1(true);
					outputStream.putFrame(mat[0]);
					break;
				}
	//			pipeline.process(mat[0],mat[1],mat[2]);
//				mat[0]=pipeline.switch3X1Output();
				// Give the output stream a new image to display
//				outputStream.putFrame(mat[0]);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
		
	}

}
