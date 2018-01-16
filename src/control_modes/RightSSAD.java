package control_modes;

import general.CustomXBox;

/**
 * Right Single Stick Arcade Drive
 * @author ImportSJC
 *
 */
public class RightSSAD extends ControlMode{
	
	public RightSSAD() {
		super(2, "Right Single Stick Arcade Drive");
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
		double right = controller.rightStickYaxis() + controller.rightStickXaxis();
		double left = -(controller.rightStickYaxis() - controller.rightStickXaxis() );
		
		axisValues[0] = left;
		axisValues[1] = right;
		return axisValues;
	}
}
