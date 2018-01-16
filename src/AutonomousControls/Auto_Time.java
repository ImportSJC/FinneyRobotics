package AutonomousControls;

/**
 * A timer that you can start and check to see if the time has expired.
 * Fixes issues involving periodic loop timing not working nicely with standard timing classes.
 * @author importsjc
 *
 */
public class Auto_Time extends AutonomousControl{
	private long startTime;
	private double secondsElapsed = 0.0;
	
	private double endTimeValue;
	
	public Auto_Time(double value){
		endTimeValue = value;
	}
	
	@Override
	public void start(){
		secondsElapsed = 0;
		startTime = System.nanoTime();
	}
	
	@Override 
	public boolean check(){
		secondsElapsed = (double)(System.nanoTime()-startTime) / 1000000000.0;
		if(secondsElapsed >= endTimeValue){
			return true;
		}
		return false;
	}
}
