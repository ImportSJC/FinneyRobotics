package cpi;

import edu.wpi.first.wpilibj.CameraServer;


/**
 * 
 * @author Thomas Wulff
 *
 *<br>The SimpleCamera class provides a simple way for initializing the SimpleCamera process and activating the camera server.
 */

public class SimpleCamera {
	static final int WIDTH=320;
	static final int HEIGHT=240;
	/**
	 * 
	 * @param channel - The camera channel you wish to display on the default Dashboard or the SmartDashboard.
	 */
    public static void init(int channel){
    CameraServer.getInstance().startAutomaticCapture();
    }

}
