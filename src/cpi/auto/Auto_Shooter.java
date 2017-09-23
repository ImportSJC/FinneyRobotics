package cpi.auto;

import org.usfirst.frc.team1405.robot.ShooterControl;

public class Auto_Shooter extends SuperClass{
	private boolean shoot = false;
	private double percentage = 0.5;
	
	public Auto_Shooter(boolean shoot, double percentage){
		this.shoot = shoot;
		this.percentage = percentage;
	}
	
	@Override
	public void start(){
		if(shoot){
			ShooterControl.teleopPeriodic(true, false, false, false);
			ShooterControl.toggleRunShooterAuto(percentage);
		}
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	private void stopMotors(){
		//reset motor speeds
		ShooterControl.toggleRunShooterAuto(0);
		AutoOutputs.reset_Drive();
	}
}
