package org.usfirst.frc.team1405.robot;

import cpi.outputDevices.MotorController;

public class GearControl {
	static MotorController gearFeedMotor;
	static double  INTAKE_SPEED= -1.0;
	static double  EJECT_SPEED= 1.0;
	
	private static int intakeState = 0; //0=below threshold, 1=initial high current, 2=running constant current, 3=high current from gear loading
	
	private static double GEAR_IN_BOT_CURRENT_THRESHOLD = 15;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(1);
	}
	
	public static void teleopInit(){
		
	}
	
	public static void TeleopPeriodic(boolean feedControl){
//		System.out.println("Gear Motor Current: " + gearFeedMotor.getOutputCurrent());
		
		if(!feedControl){
			intakeState = 0;
		}
		
		if(gearFeedMotor.getOutputCurrent() > GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 0){
			intakeState++;
		}else if(gearFeedMotor.getOutputCurrent() < GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 1){
			intakeState++;
		}else if(gearFeedMotor.getOutputCurrent() > GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 2){
			intakeState++;
		}
		
		if(feedControl){
			gearFeedMotor.set(INTAKE_SPEED);
		}
		
//		if(feedControl && intakeState != 3){
//			gearFeedMotor.set(INTAKE_SPEED);
//		}else{
//			gearFeedMotor.set(0.0);
//		}
		
	}

}
