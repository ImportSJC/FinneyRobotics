package org.usfirst.frc.team1405.robot;

import cpi.outputDevices.MotorController;
import cpi.Arduino_LightControl;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearControl {
	static MotorController gearFeedMotor;
	static double  INTAKE_SPEED= -1.0;
	static double  EJECT_SPEED= 1.0;
	static final boolean REVERSE_PNEUMATIC_1_STATE=false;
	static DoubleSolenoid solenoid1;
	static final boolean REVERSE_PNEUMATIC_2_STATE=false;
	static DoubleSolenoid solenoid2;
	

	
	//light control
	static final int LIGHT_CONTROL_OFF_STATE=0;
	static final int THIS_LIGHT_CONTROL_INDICATION=2;
	//end light control
	 
	
	private static int intakeState = 0; //0=below threshold, 1=initial high current, 2=running constant current, 3=high current from gear loading
	
	private static double GEAR_IN_BOT_CURRENT_THRESHOLD = 15;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(ID_Assignments.GEAR_TALON_MOTOR,true);
		if(REVERSE_PNEUMATIC_1_STATE){
			solenoid1=new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_1B,ID_Assignments.GEAR_CYLINDER_1A);
		}else{
			solenoid1=new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_1A,ID_Assignments.GEAR_CYLINDER_1B);
		}
		if(REVERSE_PNEUMATIC_2_STATE){
			solenoid1=new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_2B,ID_Assignments.GEAR_CYLINDER_2A);
		}else{
			solenoid2=new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_2A,ID_Assignments.GEAR_CYLINDER_2B);
		}
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
			Arduino_LightControl.Periodic(THIS_LIGHT_CONTROL_INDICATION);
			solenoid1.set(DoubleSolenoid.Value.kForward);
			solenoid2.set(DoubleSolenoid.Value.kForward);
		}else{
			gearFeedMotor.set(0.0);
			Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
			solenoid1.set(DoubleSolenoid.Value.kReverse);
			solenoid2.set(DoubleSolenoid.Value.kReverse);
		}
		
//		if(feedControl && intakeState != 3){
//			gearFeedMotor.set(INTAKE_SPEED);
//		}else{
//			gearFeedMotor.set(0.0);
//		}
		
	}

}
