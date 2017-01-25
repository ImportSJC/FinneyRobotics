package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;

public class AutoInputs {
	
	//Encoders
	private static EncoderControl leftEnc;
	private static EncoderControl rightEnc;
	
	//Gyros
	private static GyroBase myGyro = new ADXRS450_Gyro();
	
	public static void robotInit(){
	}
	
	public static void AutoInit(){
		freeEncoders();
		initEncoders();
		resetEncoders();
	}
	
	public static void TeleInit(){
		freeEncoders();
		initEncoders();
		resetEncoders();
	}
	
	public static void freeEncoders(){
		if(leftEnc != null){
			leftEnc.free();
			leftEnc = null;
		}
		if(rightEnc != null){
			rightEnc.free();
			rightEnc = null;
		}
	}
	
	public static void initEncoders(){
		if(leftEnc == null)
			leftEnc = new EncoderControl(0, 1);
		if(rightEnc == null)
			rightEnc = new EncoderControl(2, 3, true);
	}
	
	public static void resetEncoders(){
		if(leftEnc != null){
			leftEnc.resetAll();
		}
		if(rightEnc != null){
			rightEnc.resetAll();
		}
	}
	
//	public static double getLeftEncoderCount(){
//		return leftEnc.getCount();
//	}
	
	public static double getSummedEncoderCount(){
		return Math.abs(leftEnc.getCount())+Math.abs(rightEnc.getCount());
	}
	
	public static double getLeftEncoderCount(){
		return leftEnc.getCount();
	}
	
	public static double getRightEncoderCount(){
		return rightEnc.getCount();
	}
	
	public static double getLeftEncoderDistance(){
		return leftEnc.getDistance();
	}
	
	public static double getRightEncoderDistance(){
		return rightEnc.getDistance();
	}
	
	public static double getEncoderCountAvg(){
		System.out.println("Left Encoder position: " + getSummedEncoderCount());
		System.out.println("Right Encoder position: " + getRightEncoderCount());
		return (leftEnc.getCount() + rightEnc.getCount())/2;
	}
	
	public static double getEncoderDistanceAvg(){
		return (leftEnc.getDistance() + rightEnc.getDistance())/2;
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
