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
	
	private boolean gyroLoaded = true;
	
	public Gyroscope(){
		gyroLoaded = false;
	}
	
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
		if(gyroLoaded){
			try{
				System.out.println("GYRO INSTANTIATED");
				myGyro = Robot.gyro;
//				myGyro = new GyroControl(channel);
				myGyro.Init();
				myGyro.reset();
			}catch(Exception e){
				System.out.println("Gyro " + channel + " failed to load!");
				gyroLoaded = false;
			}
		}
	}
	
	@Override
	public boolean check(){
		if(gyroLoaded){
			Robot.targetAngleDistance = Math.abs(targetAngle - myGyro.getAngle());
			System.out.println("Gyro Angle: " + myGyro.getAngle());
			
			//stop once it hits the target angle and its not moving fast
			if(targetAngle>0 && (myGyro.getAngle()>targetAngle-marginOfError && myGyro.getAngle()<targetAngle+marginOfError) && Math.abs(myGyro.getRate()) < targetRate){return true;}
			else if(targetAngle<0 && (myGyro.getAngle()>targetAngle-marginOfError && myGyro.getAngle()<targetAngle+marginOfError) && Math.abs(myGyro.getRate()) < targetRate){return true;}
		}
		return false;
	}
}
