package control_modes;

import general.CustomXBox;

/**
 * Left Single Stick Arcade Drive
 * @author ImportSJC
 *
 */
public class LeftSSAD extends ControlMode{
	
	public LeftSSAD() {
		super(2, "Left Single Stick Arcade Drive");
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
		double right = controller.leftStickYaxis() + controller.leftStickXaxis();
		double left = -( controller.leftStickYaxis() - controller.leftStickXaxis() );
		
		axisValues[0] = left;
		axisValues[1] = right;
		return axisValues;
	}
}
