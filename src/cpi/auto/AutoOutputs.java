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
	
	private static double adjustment;
	private static double perfectRateTurn_Gyro;
	private static double perfectRateTurn_Encoder;
	private static double perfectRateDrive_Encoder;
	
	private static double leftEnc_driveFwd_zero = 0;
	private static double rightEnc_driveFwd_zero = 0;
	private static double driveFwd_margin = 1; //number of counts the right encoder can be off the left encoder while driving fwd
	private static double driveFwd_adjustment = .15;
	
	public static void robotInit(){
		leftMotor1 = Drive.left1;
		leftMotor2 = Drive.left2;
		rightMotor1 = Drive.right1;
		rightMotor2 = Drive.right2;
	}
	
	public static void ResetValues(){
		adjustment = .05;
		perfectRateTurn_Gyro = 70;
		perfectRateTurn_Encoder = 1200;
//		perfectRateDrive_Encoder = 2500; //this is a fast option
		perfectRateDrive_Encoder = 1500;
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
//		System.out.println("Drive Motors are assigned the drivespeed: " + drivingSpeed + " turnSpeed: " + turningSpeed);
		leftMotor1.set(-drivingSpeed-turningSpeed);
		leftMotor2.set(-drivingSpeed-turningSpeed);
		rightMotor1.set(drivingSpeed-turningSpeed);
		rightMotor2.set(drivingSpeed-turningSpeed);
		turnSpeed = turningSpeed;
		driveSpeed = drivingSpeed;
	}
	
	public static void setDriveFwd(double speed){
//		leftMotor1 = speed;
//		leftMotor2 = speed;
//		rightMotor1 = -speed;
//		rightMotor1 = -speed;
		
		double leftChange = 0;
		double rightChange = 0;
		
		if(leftEnc_driveFwd_zero-driveFwd_margin>rightEnc_driveFwd_zero){
			leftChange=driveFwd_adjustment;
		}else if(rightEnc_driveFwd_zero-driveFwd_margin>leftEnc_driveFwd_zero) {
			rightChange=driveFwd_adjustment;
		}
		
//		rightChange+=driveFwd_adjustment;
		
		leftMotor1.set(-speed+leftChange);
		leftMotor2.set(-speed+leftChange);
		rightMotor1.set(speed-rightChange);
		rightMotor2.set(speed-rightChange);
		
		driveSpeed = speed;
//		gyroAssist = true;
//		System.out.println("Drive Motors are assigned the speed: " + speed);
		System.out.println("Left Enc: " + AutoInputs.getLeftEncoderCount() + " Right enc: " + AutoInputs.getRightEncoderCount());
		System.out.println("left change: " + leftChange + " right change: " + rightChange);
		leftEnc_driveFwd_zero = AutoInputs.getLeftEncoderCount();
		rightEnc_driveFwd_zero = AutoInputs.getRightEncoderCount();
	}
	
	public static void setDriveTurn(double speed){
		System.out.println("Drive Motors are assigned the speed: " + speed);
		leftMotor1.set(speed);
		leftMotor2.set(speed);
		rightMotor1.set(speed);
		rightMotor2.set(speed);
	}
	
	public static void rampTurn_Gyro(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		double tmpTurn = turnSpeed;
		
		if(remainingAngle < 0 && perfectRateTurn_Gyro > 0){
			perfectRateTurn_Gyro/=2;
			perfectRateTurn_Gyro = -perfectRateTurn_Gyro;
		}else if (remainingAngle > 0 && perfectRateTurn_Gyro < 0){
			perfectRateTurn_Gyro = -perfectRateTurn_Gyro;
			perfectRateTurn_Gyro/=2;
		}
		
		if(AutoInputs.getGyroRate() > perfectRateTurn_Gyro){
			tmpTurn = tmpTurn - adjustment;
		}else if(AutoInputs.getGyroRate() < perfectRateTurn_Gyro){
			tmpTurn = tmpTurn + adjustment;
		}
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
		System.out.println("Gyro Rate: " + AutoInputs.getGyroRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + tmpTurn);
		
		setDrive(tmpDrive, tmpTurn);
	}
	
	public static void rampDrive_Encoder(double remainingDistance, double targetDistance){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		
		if(remainingDistance < 0 && perfectRateDrive_Encoder > 0){
			System.out.print("Case 1 - ");
			perfectRateDrive_Encoder/=2;
			perfectRateDrive_Encoder = -perfectRateDrive_Encoder;
		}else if (remainingDistance > 0 && perfectRateDrive_Encoder < 0){
			System.out.print("Case 2 - ");
			perfectRateDrive_Encoder = -perfectRateDrive_Encoder;
			perfectRateDrive_Encoder/=2;
		}
		
		if(AutoInputs.getEncoderDriveDirection() == -1 && perfectRateDrive_Encoder > 0){
			tmpDrive = tmpDrive + adjustment;
		}else if(AutoInputs.getEncoderDriveDirection() == 1 && perfectRateDrive_Encoder < 0){
			tmpDrive = tmpDrive - adjustment;
		}else{
			if(remainingDistance > 0){
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateDrive_Encoder)){
					System.out.print("Case 3 - ");
					tmpDrive = tmpDrive - adjustment;
				}else{
					System.out.print("Case 4 - ");
					tmpDrive = tmpDrive + adjustment;
				}
			}else{
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateDrive_Encoder)){
					System.out.print("Case 5 - ");
					tmpDrive = tmpDrive + adjustment;
				}else{
					System.out.print("Case 6 - ");
					tmpDrive = tmpDrive - adjustment;
				}
			}
		}
		
		System.out.println("Perfect Rate: " + perfectRateDrive_Encoder);
		System.out.println("Encoder Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Remaining distance: " + remainingDistance + " drive Speed: " + tmpDrive);
		setDriveFwd(tmpDrive);
	}
	
	public static void rampTurn_Encoder(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		double tmpTurn = turnSpeed;
		
		if(remainingAngle < 0 && perfectRateTurn_Encoder > 0){
			System.out.print("Case 1 - ");
			perfectRateTurn_Encoder/=2;
			perfectRateTurn_Encoder = -perfectRateTurn_Encoder;
		}else if (remainingAngle > 0 && perfectRateTurn_Encoder < 0){
			System.out.print("Case 2 - ");
			perfectRateTurn_Encoder = -perfectRateTurn_Encoder;
			perfectRateTurn_Encoder/=2;
		}
		
		if(AutoInputs.getEncoderTurnDirection() == -1 && perfectRateTurn_Encoder > 0){
			tmpTurn = tmpTurn + adjustment;
		}else if(AutoInputs.getEncoderTurnDirection() == 1 && perfectRateTurn_Encoder < 0){
			tmpTurn = tmpTurn - adjustment;
		}else{
			if(remainingAngle > 0){
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateTurn_Encoder)){
					System.out.print("Case 3 - ");
					tmpTurn = tmpTurn - adjustment;
				}else{
					System.out.print("Case 4 - ");
					tmpTurn = tmpTurn + adjustment;
				}
			}else{
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateTurn_Encoder)){
					System.out.print("Case 5 - ");
					tmpTurn = tmpTurn + adjustment;
				}else{
					System.out.print("Case 6 - ");
					tmpTurn = tmpTurn - adjustment;
				}
			}
		}
		
//		if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRate_Encoder)){
//			if(remainingAngle > 0){
//				System.out.print("Case 3 - ");
//				tmpTurn = tmpTurn - adjustment;
//			}else{
//				System.out.print("Case 4 - ");
//				tmpTurn = tmpTurn + adjustment;
//			}
//		}else if(AutoInputs.getSummedEncoderRate() < Math.abs(perfectRate_Encoder)){
//			if(remainingAngle > 0){
//				System.out.print("Case 5 - ");
//				tmpTurn = tmpTurn + adjustment;
//			}else{
//				System.out.print("Case 6 - ");
//				tmpTurn = tmpTurn - adjustment;
//			}
//		}
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
		System.out.println("Perfect Rate: " + perfectRateTurn_Encoder);
		System.out.println("Encoder Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + tmpTurn);
		setDrive(tmpDrive, tmpTurn);
	}
}
