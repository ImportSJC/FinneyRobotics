package subsystems_tele;

import control_modes.ControlMode;
import general.CustomXBox;
import general.MotorController;


public class Drive {
	
	//Tank Drive with two motors on each side
	private MotorController left1;
	private MotorController left2;
	private MotorController right1;
	private MotorController right2;
	
	//Tank Drive with three motors on each side
	private MotorController left3;
	private MotorController right3;
	
	//Mecanum Drive with two motors on each wheel
	private MotorController frontLeft1;
	private MotorController frontLeft2;
	private MotorController frontRight1;
	private MotorController frontRight2;
	private MotorController backLeft1;
	private MotorController backLeft2;
	private MotorController backRight1;
	private MotorController backRight2;
	
	private CustomXBox controller;
	private ControlMode currentControlMode;
	
	/**
	 * Instantiate a new drive base with four motors, one in each corner of the bot
	 * @param defaultControlMode
	 * @param frontLeft
	 * @param frontRight
	 * @param backLeft
	 * @param backRight
	 */
	public Drive(CustomXBox controller, ControlMode defaultControlMode,
			MotorController left1, MotorController left2, MotorController right1, MotorController right2){
		this.controller = controller;
		this.currentControlMode = defaultControlMode;
		
		this.left1 = left1;
		this.left2 = left2;
		this.right1 = right1;
		this.right2 = right2;
	}
	
	/**
	 * Instantiate a new drive base with 6 motors, 3 on each side
	 * @param controller
	 * @param defaultControlMode
	 * @param left1
	 * @param left2
	 * @param left3
	 * @param right1
	 * @param right2
	 * @param right3
	 */
	public Drive(CustomXBox controller, ControlMode defaultControlMode,
			MotorController left1, MotorController left2,  MotorController left3,
			MotorController right1, MotorController right2, MotorController right3){
		
		this.controller = controller;
		this.currentControlMode = defaultControlMode;
		
		this.left1 = left1;
		this.left2 = left2;
		this.left3 = left3;
		this.right1 = right1;
		this.right2 = right2;
		this.right3 = right3;
		
	}

	public void setControlMode(ControlMode controlMode){
		this.currentControlMode = controlMode;
	}
	
	public void tank(){
		double[] axisValues = currentControlMode.getAxisValues(controller);
		if(controlModeCheck(2, axisValues.length, currentControlMode.getDesc())){
			double left = axisValues[0];
			double right = axisValues[1];
			
			if(left3 != null && right3 != null){
				right1.set(right);
				right2.set(right);
				right3.set(right);
				left1.set(left);
				left2.set(left);
				left3.set(left);
			}else{
				right1.set(right);
				right2.set(right);
				left1.set(left);
				left2.set(left);
			}
		}
	}
	
	public void mecanum(){
		double[] axisValues = currentControlMode.getAxisValues(controller);
		if(controlModeCheck(4, axisValues.length, currentControlMode.getDesc())){
			double frontLeft = axisValues[0];
			double backLeft = axisValues[1];
			double frontRight = axisValues[2];
			double backRight = axisValues[3];
			
			frontLeft1.set(frontLeft);
			frontLeft2.set(frontLeft);
			
			backLeft1.set(backLeft);
			backLeft2.set(backLeft);
			
			frontRight1.set(frontRight);
			frontRight2.set(frontRight);
			
			backRight1.set(backRight);
			backRight2.set(backRight);
		}
	}
	
	/**
	 * Check if the control mode is correct for the drive base attempting to be run
	 * @param targetAxisNumber the correct number of axis for the given drive base
	 * @param axisNumber the number of axis received from the current control mode
	 * @param description the description of the current control mode
	 * @return true iff the targetAxisNumber == the axisNumber
	 */
	private boolean controlModeCheck(int targetAxisNumber, int axisNumber, String description){
		if(targetAxisNumber != axisNumber){
			System.err.println("\nERROR: the current control mode is incorrect (" + description + ") it has " +
					axisNumber + " axis, target was " + targetAxisNumber + " axis.");
			return false;
		}
		
		return true;
	}
}
