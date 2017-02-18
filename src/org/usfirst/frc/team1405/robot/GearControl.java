package org.usfirst.frc.team1405.robot;

import cpi.outputDevices.MotorController;
import cpi.Arduino_LightControl;

public class GearControl {
	static MotorController gearFeedMotor;
	static double  INTAKE_SPEED= 1.0;
	static double  EJECT_SPEED= 1.0;
	

	
	//light control
	static final int LIGHT_CONTROL_OFF_STATE=0;
	static final int THIS_LIGHT_CONTROL_INDICATION=2;
	//end light control
	 
	
	private static int intakeState = 0; //0=below threshold, 1=initial high current, 2=running constant current, 3=high current from gear loading
	
	private static double GEAR_IN_BOT_CURRENT_THRESHOLD = 15;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(7);
	}
	
	public static void teleopInit(){
		
	}
	
	public static void TeleopPeriodic(boolean feedControl){
		System.out.println("Gear Motor Current: " + gearFeedMotor.getOutputCurrent());
		
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
		
		if(feedControl && intakeState != 3){
			gearFeedMotor.set(INTAKE_SPEED);
			Arduino_LightControl.Periodic(THIS_LIGHT_CONTROL_INDICATION);
		}else{
			gearFeedMotor.set(0.0);
			Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
		}
		
	}

}
