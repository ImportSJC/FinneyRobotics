package MotorController;

import logging.SimpleLogger;

public class MotorControllerRamped extends MotorController{

	/**
	 * rate goes from 0 to 1, 1 being immediately setting the motor value and 0 means never changing the motor value
	 */
	private double rampRate = 1;
	
	public MotorControllerRamped(int id, double rampRate){
		super(id);
		
		this.rampRate = rampRate;
	}
	
	public MotorControllerRamped(int id, double rampRate, boolean reverse){
		super(id, reverse, null);
		
		setReversed(reverse);
		this.rampRate = rampRate;
	}
	
	public MotorControllerRamped(int id, double rampRate, double gearRatio, MotorController_Encoder encoder){
		super(id, gearRatio, encoder);
		
		this.rampRate = rampRate;
	}
	
	public MotorControllerRamped(int id, double rampRate, boolean reverse, double gearRatio, MotorController_Encoder encoder){
		super(id, reverse, gearRatio, encoder);
		
		setReversed(reverse);
		this.rampRate = rampRate;
	}
	
	//TODO add a range constructor

	/**
	 * Ramp "up" to given value, the value can be a negative.
	 * @param targetSpeed the value, positive or negative to ramp towards
	 */
	public void rampMotor(double targetSpeed){ //FIXME doesn't work when target speed is 0
		double currentSpeed = get();
		
		SimpleLogger.log("target: " + targetSpeed + " current: " + currentSpeed);
		
		double nextValue = 0;
		if(targetSpeed > currentSpeed){
			//we are less than the desired speed
			
			if(targetSpeed > 0){
				nextValue = currentSpeed + (targetSpeed * rampRate);
			}else{
				nextValue = currentSpeed - (targetSpeed * rampRate);
			}
			SimpleLogger.log("nextValue (belowTarget): " + nextValue);
			
			//ensure the ramp never allows the value to be set above the desired speed
			if(nextValue > targetSpeed){
				SimpleLogger.log("restricting ramp to: " + targetSpeed);
				nextValue = targetSpeed;
			}
		}else{
			//we are greater than the desired speed
			
			if(targetSpeed > 0){
				nextValue = currentSpeed - (targetSpeed * rampRate);
			}else{
				nextValue = currentSpeed + (targetSpeed * rampRate);
			}
			SimpleLogger.log("nextValue (aboveTarget): " + nextValue);
			
			//ensure the ramp never allows the value to be set below the desired speed
			if(nextValue < targetSpeed){
				SimpleLogger.log("restricting ramp to: " + targetSpeed);
				nextValue = targetSpeed;
			}
		}
		
		set(nextValue);
//		SimpleLogger.log("Right after setting please work: " + get());
	}
	
	/**
	 * Reset motor controller value to 0.
	 */
	public void resetRamp(){
		set(0);
	}
}
