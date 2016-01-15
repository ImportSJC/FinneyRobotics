package cpi.auto;


public class Auto_Drive extends SuperClass{
	private double driveSpeed = 0;
	private double turnSpeed = 0;
	private boolean gyroAssistEnabled = false;
	
	public Auto_Drive(double driveSpeed){
		this.driveSpeed = driveSpeed;
		this.gyroAssistEnabled = true;
	}
	
	public Auto_Drive(double driveSpeed, double turnSpeed){
		this.driveSpeed = driveSpeed;
		this.turnSpeed = turnSpeed;
		gyroAssistEnabled = false;
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
		AutoOutputs.setDriveFwd(driveSpeed);
		
		System.out.println("Start Auto_Drive at - " + driveSpeed);
	}
	
	private void startMotors(double driveSpeed, double turnSpeed){
		//Set motor speeds
		AutoOutputs.setDrive(driveSpeed, turnSpeed);
		
		System.out.println("Start Auto_Drive at: " + driveSpeed + " - " + turnSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
		AutoOutputs.reset_Drive();
	}
}
