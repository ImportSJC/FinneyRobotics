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
		AutoOutputs.startPID_Turn(targetAngle);
	}
	
	@Override 
	public boolean check(){
//		System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle());
		return AutoOutputs.checkPID_Turn();
	}
	
	@Override
	public void stop(){
		AutoOutputs.stopPID_Turn();
	}
}
