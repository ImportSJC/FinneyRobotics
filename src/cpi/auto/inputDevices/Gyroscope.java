package cpi.auto.inputDevices;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.SuperClass;

public class Gyroscope extends SuperClass{
	private static final double MARGIN_OF_ERROR = 0.5;
	private static final double END_RATE = 15; // the rate the robot must be under to end the turn
	
	private double targetAngle;
	
	public Gyroscope(double value){
		targetAngle = value;
	}
	
	@Override
	public void start(){
		AutoInputs.resetGyros();
	}
	
	private boolean atTargetAngle(double currentAngle){
		if(currentAngle>targetAngle-MARGIN_OF_ERROR && currentAngle<targetAngle+MARGIN_OF_ERROR){
			return true;
		}
		return false;
	}
	
	@Override 
	public boolean check(){
		System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle());

		//stop once it hits the target angle and its not moving fast
		if(atTargetAngle(AutoInputs.getGyroAngle()) && Math.abs(AutoInputs.getGyroRate()) < END_RATE){return true;}
		
//		if (targetAngle/2<=AutoInputs.getAngle()){
		AutoOutputs.rampTurn_Gyro(targetAngle-AutoInputs.getGyroAngle(), targetAngle);
//		}
		
		return false;
	}
}
