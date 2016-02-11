package cpi;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * We're using the 250 count encoders.
 * @author ImportSJC
 *
 */
public class Encoder {
	private final double WHEEL_CIRCUMFERENCE = 6*Math.PI;
	CANTalon cant;
	boolean invert;
	public Encoder(int CanID, boolean invert){
		cant = new CANTalon(CanID);
		this.invert = invert;
	}
	
	public void robotInit(){
		cant.setPosition(0);
	}
	
	public void TeleopPeriodic(){
		getRotation();
	}
	
	public void TeleopInit(){
		cant.setPosition(0);
	}
	
	public double getRotation(){
		if(invert){
			return(-cant.getPosition()/250);
		}else{
			return(cant.getPosition()/250);
		}
	}
	
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
}
