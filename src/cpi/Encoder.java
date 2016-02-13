package cpi;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * We're using the 250 count encoders. TODO check if you need to set input device to quad encoder
 * @author ImportSJC
 *
 */
public class Encoder {
	private final double WHEEL_CIRCUMFERENCE = 7.75*Math.PI;//in inches
	CANTalon cant;
	boolean invert;
	
	private int cycleCounter = 0;//i timed the robot for 15 seconds and found that there are 50 cycles to a second use that to convert to rpms
	private double currentRotation = 0; //rotation of the wheel used for measuring rpms
	private double rpms = 0;
	private int direction = 0;
	
	public Encoder(int CanID, boolean invert){
		cant = new CANTalon(CanID);
		this.invert = invert;
	}
	
	public void robotInit(){
		cant.setPosition(0);
	}
	
	public void autoInit(){
		cycleCounter = 0;
		cant.setPosition(0);
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
		cycleCounter = 0;
		cant.setPosition(0);
	}
	
	public void TeleopPeriodic(){
//		System.out.println("encoder(" + canID + ") rotation: " + getRotation());
		
		//calculate rpms
		if(cycleCounter<10){
			cycleCounter++;
		}else{
			cycleCounter = 0;
			rpms = (Math.max(Math.abs(currentRotation), Math.abs(getRotation()))-Math.min(Math.abs(currentRotation), Math.abs(getRotation())))*60*5;
			currentRotation = getRotation();
		}
		direction = (int)(getRotation()-currentRotation);
//		System.out.println("RPMs: " + rpms);
	}
	
	public void reset(){
		cant.setPosition(0);
	}
	
	public double getRotation(){
		if(invert){
			return(-cant.getPosition()/1024)*0.54;
		}else{
			return(cant.getPosition()/1024)*0.54;
		}
	}
	
	/**
	 * Return the distance the robot has travelled in inches (assuming circumference was measured in inches)
	 * @return
	 */
	public double getDistance(){
		return(getRotation()*WHEEL_CIRCUMFERENCE);
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
	
	/**
	 * Return the direction the encoder is turning. Used to not shift while turning
	 * @return 1 when rotating forward, -1 when rotating backwards (invert is applied)
	 */
	public int getDirection(){
		if(direction>0 && !invert){
			return 1;
		}else if(direction>0 && invert){
			return -1;
		}else if(direction<0 && !invert){
			return -1;
		}else if(direction<0 && invert){
			return 1;
		}
		return 0;
	}
}
