package subsystems_auto;

import AutonomousControls.AutonomousControl;
import logging.SimpleLogger;
import subsystems_tele.Drive;

public class Auto_Drive extends AutonomousControl{
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
