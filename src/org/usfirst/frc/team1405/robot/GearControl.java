package org.usfirst.frc.team1405.robot;

import cpi.outputDevices.MotorController;

public class GearControl {
	
	static MotorController gearFeedMotor;
	static double  INTAKE_SPEED= 1.0;
	static double  EJECT_SPEED= 1.0;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(7);
	}
	
	public static void teleopInit(){
		
	}
	
	public static void TeleopPeriodic(boolean feedControl){
		
		if(feedControl){
			gearFeedMotor.set(INTAKE_SPEED);
		}else{
			gearFeedMotor.set(0.0);
		}
		
	}

}
