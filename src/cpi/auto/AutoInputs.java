package cpi.auto;


public class AutoInputs {
	
	//Encoders
	private static EncoderControl leftEnc;
	private static EncoderControl rightEnc;
	
	//Gyros
	private static GyroControl onboardGyro;
	
	public static void robotInit(){
		initGyros();
	}
	
	public static void AutoInit(){
		resetGyros();
		
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
	
	public static void initGyros(){
		System.out.println("start init");
		onboardGyro = new GyroControl();
		System.out.println("end init");
	}
	
	public static void resetEncoders(){
		if(leftEnc != null){
			leftEnc.resetAll();
		}
		if(rightEnc != null){
			rightEnc.resetAll();
		}
	}
	
	public static void resetGyros(){
		System.out.println("start reset");
		if(onboardGyro != null){
			System.out.println("reset gyros");
			onboardGyro.resetAll();
		}
		System.out.println("end reset");
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
	//TODO figure out if the wrapping below is even necessary, and if it is can we move that code into the GyroControl class
	public static double getGyroRate(){
		double myDouble = 0;
		
		try{
			myDouble = onboardGyro.getRate();
		}catch(NullPointerException e){
			System.out.println("ERROR: Onboard Gyro is not connected");
		}
		
		return myDouble;
	}
	
	public static double getGyroAngle(){
		double myDouble = 0;
		
		try{
			myDouble = onboardGyro.getAngle();
		}catch(NullPointerException e){
			System.out.println("ERROR: Onboard Gyro is not connected");
		}
		
		return myDouble;
	}
}
