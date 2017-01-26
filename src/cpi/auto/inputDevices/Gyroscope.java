package cpi.auto.inputDevices;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.SuperClass;

public class Gyroscope extends SuperClass{
	private double targetAngle;
	
	public Gyroscope(double value){
		targetAngle = value;
	}
	
	@Override
	public void start(){
		AutoInputs.resetGyros();
	}
	
	@Override 
	public boolean check(){
		System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle());
		
//		if (targetAngle/2<=AutoInputs.getAngle()){
		AutoOutputs.rampTurn(targetAngle-AutoInputs.getGyroAngle(), targetAngle);
//		}
		
		//stop once it hits the target angle and its not moving fast
		if(targetAngle>0 && AutoInputs.getGyroAngle() >= targetAngle && AutoInputs.getGyroRate() < 10){return true;}
		else if(targetAngle<0 && AutoInputs.getGyroAngle() <= targetAngle){return true;}
		return false;
	}
}
