package subsystems_tele;

import com.kauailabs.navx.frc.AHRS;

import MotorController.MotorController;
import control_modes.ControlMode;
import general.CustomXBox;
import logging.SimpleLogger;


public class Drive {
	
	//Tank Drive with two motors on each side
	private MotorController left;
	private MotorController right;
	
	//Mecanum Drive with two motors on each wheel
	private MotorController frontLeft1;
	private MotorController frontLeft2;
	private MotorController frontRight1;
	private MotorController frontRight2;
	private MotorController backLeft1;
	private MotorController backLeft2;
	private MotorController backRight1;
	private MotorController backRight2;
	
	private AHRS gyro;
	
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
	public Drive(CustomXBox controller, ControlMode defaultControlMode, AHRS gyro,
			MotorController left, MotorController right){
		this.controller = controller;
		this.currentControlMode = defaultControlMode;
		
		this.left = left;
		this.right = right;
		
		this.gyro = gyro;
	}
	
	public void setControlMode(ControlMode controlMode){
		this.currentControlMode = controlMode;
	}
	
	public void tank(){
		double[] axisValues = currentControlMode.getAxisValues(controller);
		if(controlModeCheck(2, axisValues.length, currentControlMode.getDesc())){
			double leftSpeed = axisValues[0];
			double rightSpeed = axisValues[1];
			
			leftSpeed = leftSpeed * (2.0/3.0);
			rightSpeed = rightSpeed * (2.0/3.0);
			
//				SimpleLogger.log("Right: " + right.get() + "Left: " + left.get());
				right.set(rightSpeed);
				left.set(leftSpeed);
//				right.rampMotor(right);
//				left.rampMotor(left);
		}
		
	}
	
	public void setTankDrive(double leftSpeed, double rightSpeed){
//		SimpleLogger.log("Setting drive: " + leftSpeed + ", " + rightSpeed);
			right.set(rightSpeed);
			left.set(leftSpeed);
	}
	
	public void setTankDrive_left(double leftSpeed){
		left.set(leftSpeed);
	}
	
	public void setTankDrive_right(double rightSpeed){
		right.set(rightSpeed);
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
	 * @return the motor controller with the specified encoder connected
	 */
	public MotorController getLeftMotorController(){
		return left;
	}
	
	/**
	 * @return the motor controller with the specified encoder connected
	 */
	public MotorController getRightMotorController(){
		return right;
	}

	public double getLeftDistance(){
		return left.getDriveDistance();
	}
	
	public double getRightDistance(){
		return right.getDriveDistance();
	}
	
	public void resetGyro(){
		gyro.reset();
	}
	
	public double getGyroAngle(){
		return gyro.getAngle();
	}
	
	public double getGyroRate(){
		return gyro.getRate();
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
