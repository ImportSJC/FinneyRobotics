package cpi.auto.inputDevices;

import org.usfirst.frc.team1405.robot.Robot;
import cpi.auto.GyroControl;
import cpi.auto.SuperClass;

public class Gyroscope extends SuperClass{
	private double targetAngle;
	private int channel;
	
	private double targetRate;//the rate the gyro must be under to have reached its target angle
	private double marginOfError;//the degrees of acceptable error
	
	GyroControl myGyro;
	
	public Gyroscope(double targetAngle, int channel){
		this.targetAngle = targetAngle;
		this.channel = channel;
		this.targetRate = 10;
		this.marginOfError = 5;
	}
	
	public Gyroscope(double targetAngle, double targetRate, double marginOfError, int channel){
		this.targetAngle = targetAngle;
		this.channel = channel;
		this.targetRate = targetRate;
		this.marginOfError = marginOfError;
	}
	
	@Override
	public void start(){
		System.out.println("GYRO INSTANTIATED");
		myGyro = new GyroControl(channel);
		myGyro.Init();
		myGyro.resetAll();
	}
	
	@Override 
	public boolean check(){
		Robot.targetAngleDistance = Math.abs(targetAngle - myGyro.getAngle());
		System.out.println("Gyro Angle: " + myGyro.getAngle());
		
//		if (targetAngle/2<=myGyro.getAngle()){
//		AutoOutputs.rampTurn(targetAngle-myGyro.getAngle(), targetAngle);
//		}
		
		//stop once it hits the target angle and its not moving fast
		if(targetAngle>0 && (myGyro.getAngle()>targetAngle-marginOfError && myGyro.getAngle()<targetAngle+marginOfError) && Math.abs(myGyro.getRate()) < targetRate){return true;}
		else if(targetAngle<0 && (myGyro.getAngle()>targetAngle-marginOfError && myGyro.getAngle()<targetAngle+marginOfError) && Math.abs(myGyro.getRate()) < targetRate){return true;}
		return false;
	}
}
