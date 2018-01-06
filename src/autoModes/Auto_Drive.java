package autoModes;

import auto.SuperClass;
import tele.Drive;

public class Auto_Drive extends SuperClass{
	private double driveSpeed = 0;
	private double turnSpeed = 0;
	private boolean gyroAssistEnabled = false;
	
	private Drive drive;
	
	public Auto_Drive(double driveSpeed, Drive drive){
		this.driveSpeed = driveSpeed;
		this.gyroAssistEnabled = true;
		this.drive = drive;
	}
	
	public Auto_Drive(double driveSpeed, double turnSpeed, Drive drive){
		this.driveSpeed = driveSpeed;
		this.turnSpeed = turnSpeed;
		gyroAssistEnabled = false;
		this.drive = drive;
	}
	
	@Override
	public void start(){
		if (gyroAssistEnabled){
			System.out.println("Starting Auto Drive - Gyro Assist Enabled");
			startMotors(driveSpeed);
		}else{
			System.out.println("Starting Auto Drive");
			startMotors(driveSpeed, turnSpeed);
		}
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void startMotors(double driveSpeed){
		//Set motor speeds
//		drive.drivefwd(driveSpeed) TODO get this working
		
		System.out.println("Start Auto_Drive at - " + driveSpeed);
	}
	
	private void startMotors(double driveSpeed, double turnSpeed){
		//Set motor speeds
//		drive.driveandturn(drivespeed, turnspeed) TODO get this working
		
		System.out.println("Start Auto_Drive at: " + driveSpeed + " - " + turnSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
//		drive.stop TODO get this working
	}
}
