package AutonomousControls;

import sensors.CustomGyro;

public class Auto_Gyroscope extends AutonomousControl{
	private double targetAngle;
	private double targetRate;
	
	CustomGyro myGyro;
	
	public Auto_Gyroscope(double targetAngle, double targetRate, int channel){
		this.targetAngle = targetAngle;
		this.targetRate = targetRate;
		myGyro = new CustomGyro(channel);
	}
	
	@Override
	public void start(){
		myGyro.calibrate();
	}
	
	@Override 
	public boolean check(){
		System.out.println("Gyro Angle: " + myGyro.getAngle());
		
		//TODO was this code necessary? what did it do?
//		if (targetAngle/2<=myGyro.getAngle()){
//		AutoOutputs.rampTurn(targetAngle-myGyro.getAngle(), targetAngle);
//		}
		
		//stop once it hits the target angle and its not moving fast
		if(myGyro.getRate() < targetRate){
			if(targetAngle>0 && myGyro.getAngle() >= targetAngle){return true;}
			else if(targetAngle<0 && myGyro.getAngle() <= targetAngle){return true;}
		}
		return false;
	}
}
