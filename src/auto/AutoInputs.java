package auto;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import tele.Drive;

public class AutoInputs {
	//Encoders
	public static CANTalon leftMotor1;
	public static CANTalon rightMotor1;
	
	//Gyros
	public static Gyro myGyro;
	
	public void RobotInit(){
		leftMotor1 = Drive.leftMotor1;
		rightMotor1 = Drive.rightMotor1;
		
		leftMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
//		myGyro = new GyroControl(new AnalogInput(0));
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
