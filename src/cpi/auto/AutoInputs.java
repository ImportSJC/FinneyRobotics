package cpi.auto;

import cpi.auto.tele.Drive;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.AnalogGyro;;

public class AutoInputs {
	//Encoders
	public static CANTalon leftMotor1;
	public static CANTalon rightMotor1;
	
	//Gyros
	public static AnalogGyro myGyro;
	
	public static void robotInit(){
		leftMotor1 = Drive.leftMotor1;
		rightMotor1 = Drive.rightMotor1;
		
		leftMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		myGyro = new AnalogGyro(new AnalogInput(0));
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
}
