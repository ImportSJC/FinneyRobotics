package subsystems_auto;

import AutonomousControls.AutonomousControl;
import logging.SimpleLogger;
import logging.SimpleLogger.LogLevel;
import logging.SimpleLogger.LogSubsystem;
import subsystems_tele.Drive;

public class Auto_Drive extends AutonomousControl{
	private final boolean DRIVE_STRAIGHT = true; //drive straight using encoders
	
	private double driveSpeed = 0;
	private double turnSpeed = 0;
	private boolean isTurning = false;
	
	private Drive drive;
	
	public Auto_Drive(double driveSpeed, Drive drive){
		this.driveSpeed = driveSpeed;
		this.drive = drive;
	}
	
	public Auto_Drive(double driveSpeed, double turnSpeed, Drive drive){
		this.driveSpeed = driveSpeed;
		this.turnSpeed = turnSpeed;
		isTurning = true;
		this.drive = drive;
	}
	
	@Override
	public void start(){
		if (isTurning){
			SimpleLogger.log("Starting Auto Drive - WITH Turn");
			startMotors(driveSpeed, turnSpeed);
		}else{
			SimpleLogger.log("Starting Auto Drive - No Turn");
			startMotors(driveSpeed);
		}
	}
	
	@Override
	public boolean check(){
		double threshold = 0.2; //distance of difference between encoder sides to enable drive correction
		double correction = 0.1; //amount to correct
		if(DRIVE_STRAIGHT && turnSpeed == 0){
			SimpleLogger.log("Drive error: " + (drive.getLeftDistance()-drive.getRightDistance()), LogLevel.DEBUG, LogSubsystem.AUTO);
			if(drive.getLeftDistance() > drive.getRightDistance()+threshold){
				drive.setTankDrive(driveSpeed, driveSpeed+correction);
			}else if(drive.getRightDistance() > drive.getLeftDistance()+threshold){
				drive.setTankDrive(driveSpeed+correction, driveSpeed);
			}else{
				drive.setTankDrive(driveSpeed, driveSpeed);
			}
		}
		return true;
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void startMotors(double driveSpeed){
		//Set motor speeds
		drive.setTankDrive(driveSpeed, driveSpeed);
		
		SimpleLogger.log("Start Auto_Drive at - " + driveSpeed);
	}
	
	private void startMotors(double driveSpeed, double turnSpeed){
		//Set motor speeds
		drive.setTankDrive(-turnSpeed, turnSpeed);
		
		SimpleLogger.log("Start Auto_Drive at: " + driveSpeed + " - " + turnSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
		drive.setTankDrive(0, 0);
	}
}
