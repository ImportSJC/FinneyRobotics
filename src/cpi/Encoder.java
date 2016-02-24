package cpi;

/**
 * We're using the 250 count encoders. TODO check if you need to set input device to quad encoder
 * @author ImportSJC
 *
 */
public class Encoder {
	private final double WHEEL_CIRCUMFERENCE = 7.75*Math.PI;//in inches
	CANTalonControl cant;
	boolean invert;
	
	private int cycleCounter = 0;//i timed the robot for 15 seconds and found that there are 50 cycles to a second use that to convert to rpms
	private double currentRotation = 0; //rotation of the wheel used for measuring rpms
	private double rpms = 0;
	
	private double oldPosition = 0;//used to calculate direction
	private double direction = 0;
	
	private boolean encoderLoaded = true;//make false when the encoder cant be loaded, ensures the robot stays functioning
	
	public Encoder(){
		encoderLoaded = false;
	}
	
	public Encoder(int CanID, boolean invert){
		try{
			cant = new CANTalonControl(CanID);
			this.invert = invert;
			cant.getPosition();
		}catch(Exception e){//TODO fix this catch statement, it doens't catch the can talon transmit error since its not being thrown
			System.out.println("Encoder " + CanID + " failed to load!");
			encoderLoaded = false;
		}
	}
	
	public void robotInit(){
		if(encoderLoaded){
			cant.setPosition(0);
		}
	}
	
	public void autoInit(){
		if(encoderLoaded){
			cycleCounter = 0;
			cant.setPosition(0);
		}
	}
	
	public void autoPeriodic(){
		//calculate rpms
//		if(cycleCounter<10){
//			cycleCounter++;
//		}else{
//			cycleCounter = 0;
//			rpms = (Math.max(Math.abs(currentRotation), Math.abs(getRotation()))-Math.min(Math.abs(currentRotation), Math.abs(getRotation())))*60*5;
//			currentRotation = getRotation();
//		}
	}
	
	public void TeleopInit(){
		if(encoderLoaded){
			cycleCounter = 0;
			cant.setPosition(0);
		}
	}
	
	public void TeleopPeriodic(){
		if(encoderLoaded){
			//calculate rpms
			if(cycleCounter<10){
				cycleCounter++;
			}else{
				cycleCounter = 0;
				rpms = (Math.max(Math.abs(currentRotation), Math.abs(getRotation()))-Math.min(Math.abs(currentRotation), Math.abs(getRotation())))*60*5;
				currentRotation = getRotation();
				direction = cant.getPosition() - oldPosition;
				oldPosition = cant.getPosition();
			}
//			System.out.println("RPMs: " + rpms);
			System.out.println("Current direction: " + getDirection());
		}
	}
	
	public void reset(){
		if(encoderLoaded){
			cant.setPosition(0);
		}
	}
	
	public double getRotation(){
		if(encoderLoaded){
			if(invert){
				return(-cant.getPosition()/1024)*0.54;
			}else{
				return(cant.getPosition()/1024)*0.54;
			}
		}
		return 0;
	}
	
	public double getDirection(){
		if(invert){
			return -direction;
		}else{
			return direction;
		}
	}
	
	/**
	 * Return the distance the robot has travelled in inches (assuming circumference was measured in inches)
	 * @return
	 */
	public double getDistance(){
		if(encoderLoaded){
			return(getRotation()*WHEEL_CIRCUMFERENCE);
		}
		return 0;
	}
	
	public double getAverageRotation(Encoder enc2){
		return((getRotation()+enc2.getRotation())/2);
	}
	
	public double getAverageDistance(Encoder enc2){
		return((getDistance()+enc2.getDistance())/2);
	}
	
	/**
	 * Subtracts the absolute value of enc and enc2.
	 * @param enc2 Other encoder used in calculation.
	 * @return How much more enc has traveled than enc2 in counts.
	 */
	public double getEncoderError(Encoder enc2){
		return(Math.abs(getRotation())-Math.abs(enc2.getRotation()));
	}
	
	public double getRPMs(){
		return rpms;
	}
	
	public double getAverageRPMs(Encoder enc2){
		return(getRPMs()+enc2.getRPMs())/2;
	}
}
