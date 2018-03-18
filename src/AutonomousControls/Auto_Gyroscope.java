package AutonomousControls;

import subsystems_tele.Drive;

public class Auto_Gyroscope extends AutonomousControl{
	private final double TOLERANCE = 1.0;
	
	private double targetAngle;
	private double targetRate;
	
	private boolean isRateLimited = false;
	
	private Drive drive;
	
	public Auto_Gyroscope(Drive drive, double targetAngle, double targetRate){
		this.targetAngle = targetAngle;
		this.targetRate = targetRate;
		this.drive = drive;
		
		isRateLimited = true;
	}
	
	public Auto_Gyroscope(Drive drive, double targetAngle){
		this.targetAngle = targetAngle;
		this.drive = drive;
	}
	
	@Override
	public void start(){
		drive.resetGyro();
	}
	
	@Override 
	public boolean check(){
//		SimpleLogger.log("Gyro Angle: " + myGyro.getAngle());
		
		//TODO was this code necessary? what did it do?
//		if (targetAngle/2<=myGyro.getAngle()){
//		AutoOutputs.rampTurn(targetAngle-myGyro.getAngle(), targetAngle);
//		}
		
		//stop once it hits the target angle and its not moving fast
		if(isRateLimited){
			if(drive.getGyroRate() <= targetRate){
				if(drive.getGyroAngle() < targetAngle+TOLERANCE && drive.getGyroAngle() > targetAngle-TOLERANCE){return true;}
			}
			return false;
		}else{
			if(drive.getGyroAngle() < targetAngle+TOLERANCE && drive.getGyroAngle() > targetAngle-TOLERANCE){return true;}
			return false;
		}
	}
}
