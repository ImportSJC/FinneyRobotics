package cpi.auto;


public class AutoInputs {
	
	private static final boolean useMagEncoders = true;
	
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
		if(useMagEncoders){
			if(leftEnc == null)
				leftEnc = new EncoderControl(4, true);
			if(rightEnc == null)
				rightEnc = new EncoderControl(1);
		}else{
			if(leftEnc == null)
				leftEnc = new EncoderControl(0, 1);
			if(rightEnc == null)
				rightEnc = new EncoderControl(2, 3, true);
		}
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
	
	public static void updateEncoderRates(){
		leftEnc.updateRate();
		rightEnc.updateRate();
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
//		System.out.println("Avg Dist - left: " + leftEnc.getDistance() + " right: " + rightEnc.getDistance() + " total: " + (leftEnc.getDistance() + rightEnc.getDistance())/2);
		return (leftEnc.getDistance() + rightEnc.getDistance())/2;
	}
	
	/**
	 * 
	 * @return -1 for CCW turn, 0 for no turn, and 1 for CW turn
	 */
	public static double getEncoderTurnDirection(){
		if(leftEnc.getRate() > 0 && rightEnc.getRate() < 0){
			return 1;
		}else if(leftEnc.getRate() < 0 && rightEnc.getRate() > 0){
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * 
	 * @return -1 for backwards, 0 for no direction, and 1 for forwards
	 */
	public static double getEncoderDriveDirection(){
		if(leftEnc.getRate() > 0 && -rightEnc.getRate() < 0){
			return 1;
		}else if(leftEnc.getRate() < 0 && -rightEnc.getRate() > 0){
			return -1;
		}
		
		return 0;
	}
	
	public static double getLeftEncoderRate(){
		System.out.println("get left rate called");
		double tmp = leftEnc.getRate();
		return Math.abs(tmp);
	}
	
	public static double getSummedEncoderRate(){
		return Math.abs(leftEnc.getRate())+Math.abs(rightEnc.getRate());
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
