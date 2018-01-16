package control_modes;

import general.CustomXBox;

public abstract class ControlMode {
	protected double axisValues[]; 	//store the axis values in this array and return it when the getAxisValues function is called
	private String description;		//the description of the control mode, used to print out to the console
	
	
	public ControlMode(int axis, String desc) {
		this.description   = desc;
		this.axisValues = new double[axis];
	}
	
	/**
	 * @return the number of axis supported in this control mode
	 */
	public int getAxis(){
		return axisValues.length;
	}
	
	/**
	 * @return the description of this control mode, used for printing to the console
	 */
	public String getDesc() {
		return this.description;
	}
	
	/**
	 * return the values for each axis in this control mode
	 * @param controller the controller to retreive the axis values from
	 * @return an array with the axis values
	 */
	public double[] getAxisValues(CustomXBox controller){
		return axisValues;
	}
}
