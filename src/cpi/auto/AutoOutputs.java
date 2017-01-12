package cpi.auto;

import cpi.Drive;
import cpi.outputDevices.MotorController;

public class AutoOutputs {
//	public static double leftMotor1 = 0.0;
//	public static double rightMotor1 = 0.0;
//	public static double leftMotor2 = 0.0;
//	public static double rightMotor2 = 0.0;
	
	public static MotorController leftMotor1;
	public static MotorController leftMotor2;
	public static MotorController rightMotor1;
	public static MotorController rightMotor2;
	
	private static boolean gyroAssist = false;
	private static double driveSpeed = 0.0;
	private static double turnSpeed = 0.0;
	
	public static void robotInit(){
		leftMotor1 = Drive.left1;
		leftMotor2 = Drive.left2;
		rightMotor1 = Drive.right1;
		rightMotor2 = Drive.right2;
	}
	
	public void AutonomousPeriodic(){
		
		if(gyroAssist){
			if (driveSpeed>=0){
				//Moderate gyro assist (not as fast of correction)
//				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
//				setDrive(driveSpeed, -(1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
				
				//Drastic gyro assist (a faster correction)
				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(50*driveSpeed))*AutoInputs.getGyroAngle()
						 + " Gyro angle: " + AutoInputs.getGyroAngle());
				setDrive(driveSpeed, -(1.0/(50*driveSpeed))*AutoInputs.getGyroAngle());
			}
			else{
				//Moderate gyro assist (not as fast of correction)
//				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
//				setDrive(driveSpeed, (1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
				
				//Drastic gyro assist (a faster correction)
				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(50*driveSpeed))*AutoInputs.getGyroAngle()
						 + " Gyro angle: " + AutoInputs.getGyroAngle());
				setDrive(driveSpeed, (1.0/(50*driveSpeed))*AutoInputs.getGyroAngle());
			}
		}
	}
	
	public static void setDriveBrake(boolean value){
		leftMotor1.enableBrakeMode(value);
		leftMotor2.enableBrakeMode(value);
		rightMotor1.enableBrakeMode(value);
		rightMotor2.enableBrakeMode(value);
	}
	
	public static void reset_Drive(){
		leftMotor1.set(0);
		leftMotor2.set(0);
		rightMotor1.set(0);
		rightMotor2.set(0);
		gyroAssist = false;
		driveSpeed = 0.0;
		turnSpeed = 0.0;
	}
	
	public static void setDrive(double drivingSpeed, double turningSpeed){
		System.out.println("Drive Motors are assigned the drivespeed: " + drivingSpeed + " turnSpeed: " + turningSpeed);
		leftMotor1.set(drivingSpeed+turningSpeed);
		leftMotor2.set(drivingSpeed+turningSpeed);
		rightMotor1.set(-drivingSpeed+turningSpeed);
		rightMotor2.set(-drivingSpeed+turningSpeed);
		turnSpeed = turningSpeed;
		driveSpeed = drivingSpeed;
	}
	
	public static void setDriveFwd(double speed){
//		leftMotor1 = speed;
//		leftMotor2 = speed;
//		rightMotor1 = -speed;
//		rightMotor1 = -speed;
		driveSpeed = speed;
		gyroAssist = true;
		System.out.println("Drive Motors are assigned the speed: " + speed);
		leftMotor1.set(speed);
		leftMotor2.set(speed);
		rightMotor1.set(-speed);
		rightMotor2.set(-speed);
	}
	
	public static void setDriveTurn(double speed){
		System.out.println("Drive Motors are assigned the speed: " + speed);
		leftMotor1.set(speed);
		leftMotor2.set(speed);
		rightMotor1.set(speed);
		rightMotor2.set(speed);
	}
	
	public static void rampTurn(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
		System.out.println("Gyro Rate: " + AutoInputs.getGyroRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + turnSpeed);
		if(Math.abs(AutoInputs.getGyroRate())<=20){
			setDrive(driveSpeed, turnSpeed * 1.25);
		}
		
		if(Math.abs(AutoInputs.getGyroRate())>=Math.abs(remainingAngle)){
			setDrive(driveSpeed, turnSpeed * 0.75);
		}
		
		//make sure the turnspeed never drops below a certain value
		if(turnSpeed<=0 && turnSpeed>-0.085){setDrive(driveSpeed, -0.085);}
		else if(turnSpeed>0 && turnSpeed<0.085){setDrive(driveSpeed, 0.085);}
		
		//make sure the robot doesnt over shoot the targetAngle
		if( (targetAngle>0 && remainingAngle<0) ||
			 targetAngle<0 && remainingAngle>0){setDrive(driveSpeed,-turnSpeed);}
	}
}
