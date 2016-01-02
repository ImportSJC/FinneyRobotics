package auto;

import tele.Drive;
import tele.Elevator;
import edu.wpi.first.wpilibj.CANTalon;

public class AutoOutputs {
//	public static double leftMotor1 = 0.0;
//	public static double rightMotor1 = 0.0;
//	public static double leftMotor2 = 0.0;
//	public static double rightMotor2 = 0.0;
	
	public static CANTalon leftMotor1;
	public static CANTalon leftMotor2;
	public static CANTalon rightMotor1;
	public static CANTalon rightMotor2;
	public static CANTalon elevatorMotor1;
	public static CANTalon elevatorMotor2;
	
	private static boolean gyroAssist = false;
	private static double driveSpeed = 0.0;
	private static double turnSpeed = 0.0;
	
	public void robotInit(){
		leftMotor1 = Drive.leftMotor1;
		leftMotor2 = Drive.leftMotor2;
		rightMotor1 = Drive.rightMotor1;
		rightMotor2 = Drive.rightMotor2;
		elevatorMotor1 = Elevator.elevatorMotor1;
		elevatorMotor2 = Elevator.elevatorMotor2;
	}
	
	public void AutonomousPeriodic(){
		
		if(gyroAssist){
			if (driveSpeed>=0){
				//Moderate gyro assist (not as fast of correction)
//				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(200*driveSpeed))*AutoInputs.myGyro.getAngle());
//				setDrive(driveSpeed, -(1.0/(200*driveSpeed))*AutoInputs.myGyro.getAngle());
				
				//Drastic gyro assist (a faster correction)
				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(50*driveSpeed))*AutoInputs.myGyro.getAngle()
						 + " Gyro angle: " + AutoInputs.myGyro.getAngle());
				setDrive(driveSpeed, -(1.0/(50*driveSpeed))*AutoInputs.myGyro.getAngle());
			}
			else{
				//Moderate gyro assist (not as fast of correction)
//				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(200*driveSpeed))*AutoInputs.myGyro.getAngle());
//				setDrive(driveSpeed, (1.0/(200*driveSpeed))*AutoInputs.myGyro.getAngle());
				
				//Drastic gyro assist (a faster correction)
				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(50*driveSpeed))*AutoInputs.myGyro.getAngle()
						 + " Gyro angle: " + AutoInputs.myGyro.getAngle());
				setDrive(driveSpeed, (1.0/(50*driveSpeed))*AutoInputs.myGyro.getAngle());
			}
		}
	}
	
	public static void setDriveBrake(boolean value){
		leftMotor1.enableBrakeMode(value);
		leftMotor2.enableBrakeMode(value);
		rightMotor1.enableBrakeMode(value);
		rightMotor2.enableBrakeMode(value);
	}
	
	public static void setElevatorBrake(boolean value){
		elevatorMotor1.enableBrakeMode(value);
		elevatorMotor2.enableBrakeMode(value);
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
	
	public static void reset_Elevator(){
		elevatorMotor1.set(0);
		elevatorMotor2.set(0);
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
	
	public static void setElevator(double speed){
		System.out.println("Elevator motors are assigned the speed: " + speed);
		elevatorMotor1.set(-speed);
		elevatorMotor2.set(speed);
	}
	
	public static void rampTurn(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
		System.out.println("Gyro Rate: " + AutoInputs.myGyro.getRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + turnSpeed);
		if(Math.abs(AutoInputs.myGyro.getRate())<=20){
			setDrive(driveSpeed, turnSpeed * 1.25);
		}
		
		if(Math.abs(AutoInputs.myGyro.getRate())>=Math.abs(remainingAngle)){
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
