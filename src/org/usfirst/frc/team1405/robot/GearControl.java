package org.usfirst.frc.team1405.robot;

import cpi.Arduino_LightControl;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearControl {
	static MotorController gearFeedMotor;
	static MotorController climberMotor;
	static double  INTAKE_SPEED= 1.0;
	static double  EJECT_SPEED= -1.0;
	static final boolean REVERSE_PNEUMATIC=false;
	static DoubleSolenoid solenoid;
	

	
	//light control
	static final int LIGHT_CONTROL_OFF_STATE=0;
	static final int THIS_LIGHT_CONTROL_INDICATION=2;
	//end light control
	 
	
	private static int intakeState = 0; //0=below threshold, 1=initial high current, 2=running constant current, 3=high current from gear loading
	
	private static double GEAR_IN_BOT_CURRENT_THRESHOLD = .6;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(ID_Assignments.GEAR_TALON_MOTOR);
		climberMotor = new MotorController(ID_Assignments.CLIMB_TALON_MOTOR);
		solenoid = new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_1, ID_Assignments.GEAR_CYLINDER_2);
	}
	
	public static void teleopInit(){
		
	}
	
	public static void TeleopPeriodic(boolean feedControl, boolean ejectControl, boolean dropGear, boolean climb){
//		System.out.println("state: " + intakeState + "Gear Motor Current: " + gearFeedMotor.getOutputCurrent());
		
		if(ejectControl){
			gearFeedMotor.set(EJECT_SPEED);
		}else if(dropGear){
			gearFeedMotor.set(EJECT_SPEED);
			solenoid.set(DoubleSolenoid.Value.kForward);
		}else if(climb){
			gearFeedMotor.set(1.0);
			climberMotor.set(1.0);
		}else{
			climberMotor.set(0);
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
				solenoid.set(DoubleSolenoid.Value.kForward);
			}else{
				gearFeedMotor.set(0.0);
				Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
				solenoid.set(DoubleSolenoid.Value.kReverse);
			}
			
	//		if(feedControl && intakeState != 3){
	//			gearFeedMotor.set(INTAKE_SPEED);
	//		}else{
	//			gearFeedMotor.set(0.0);
	//			solenoid.set(DoubleSolenoid.Value.kReverse);
	//		}
		}
		
	}

}
