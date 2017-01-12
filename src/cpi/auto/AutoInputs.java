package cpi.auto;

import cpi.Drive;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;

public class AutoInputs {
	//Encoders
	public static MotorController leftMotor1;
	public static MotorController rightMotor1;
	
	//Gyros
	private static GyroBase myGyro = new ADXRS450_Gyro();
	
	public static void robotInit(){
		leftMotor1 = Drive.left1;
		rightMotor1 = Drive.right1;
//		leftMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
//		rightMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
//		myGyro = new GyroBase(new AnalogInput(0));
	}
	
	public void AutoInit(){
		leftMotor1.setPosition(0);
		rightMotor1.setPosition(0);
	}
	
	public static void resetEncoders(){
		leftMotor1.setPosition(0);
		rightMotor1.setPosition(0);
	}
	
	public static double getLeftEncoder(){
		return leftMotor1.getPosition();
	}
	
	public static double getRightEncoder(){
		return -rightMotor1.getPosition();
	}
	
	public static double getEncoder(){
		System.out.println("Left Encoder position: " + getLeftEncoder());
		System.out.println("Right Encoder position: " + getRightEncoder());
//		return (getLeftEncoder() + getRightEncoder())/2;
		return getLeftEncoder();
	}
	
	
	//Wrapping all gyro access code in try catch so that no exceptions go unchecked if no gyro is connected
	public static void resetGyro(){
		try{
			myGyro.reset();
		}catch(NullPointerException e){
			System.out.println("ERROR: Onboard Gyro is not connected");
		}
	}
	
	public static double getGyroRate(){
		double myDouble = 0;
		
		try{
			myDouble = myGyro.getRate();
		}catch(NullPointerException e){
			System.out.println("ERROR: Onboard Gyro is not connected");
		}
		
		return myDouble;
	}
	
	public static double getGyroAngle(){
		double myDouble = 0;
		
		try{
			myDouble = myGyro.getAngle();
		}catch(NullPointerException e){
			System.out.println("ERROR: Onboard Gyro is not connected");
		}
		
		return myDouble;
	}
	
	public static GyroBase getGyro(){
		return myGyro;
	}
}
