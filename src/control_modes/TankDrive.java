package control_modes;

import general.CustomXBox;

/**
 * Tank Drive
 * @author ImportSJC
 *
 */
public class TankDrive extends ControlMode {
	
	public TankDrive() {
		super(2, "Tank Drive");
	}

	/**
	 * 
	 * @param controller
	 * @return double array with two axis values
	 * 			the first index contains the left motors speed
	 * 			the second index contains the right motors speed
	 */
	@Override
	public double[] getAxisValues(CustomXBox controller){
		double right = controller.rightStickYaxis();
		double left = -controller.leftStickYaxis();
		
		axisValues[0] = left;
		axisValues[1] = right;
		return axisValues;
	}
}
