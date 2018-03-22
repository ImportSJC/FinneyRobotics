package control_modes;

import general.CustomXBox;
import logging.SimpleLogger;

/**
 * Arcade Drive
 * @author ImportSJC
 *
 */
public class ArcadeDrive extends ControlMode{
	
	public ArcadeDrive() {
		super(2, "Arcade Drive");
	}

	/**
	 * 
	 * @param controller
	 * @return double array with two axis values
	 * 			the first index contains the left motor speeds
	 * 			the second index contains the right motor speeds
	 */
	@Override
	public double[] getAxisValues(CustomXBox controller){
		double right = controller.leftStickYaxis() + controller.rightStickXaxis();
		double left = controller.leftStickYaxis() - controller.rightStickXaxis();
		
		SimpleLogger.log("Joystick right: " + right + " left: " + left);
		
		// (MECANUM BASE)
//		Drive.xAxis = controller.leftStickXaxis();
//		Drive.yAxis = -controller.leftStickYaxis();
//		Drive.zAxis = controller.rightStickXaxis();
//		if(Drive.xAxis == -0){
//			Drive.xAxis = 0;
//		}
//		if(Drive.yAxis == -0){
//			Drive.yAxis = 0;
//		}
//		if(Drive.zAxis == -0){
//			Drive.zAxis = 0;
//		}
		
		if(controller.leftBumper()){
			left = 0.2 * left;
			right = 0.2 * right;
		}
		
		axisValues[0] = left;
		axisValues[1] = right;
		return axisValues;
	}
}
