package org.usfirst.frc.team1405.robot;

import cpi.Arduino_LightControl;
import cpi.auto.AutoOutputs;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearControl {
	static MotorController gearFeedMotor;
	static MotorController climberMotor;
	static double  INTAKE_SPEED= 1.0;
	static double  EJECT_SPEED= -1.0;
	static final boolean REVERSE_PNEUMATIC=false;
	static DoubleSolenoid gearSolenoid;
	
	static DoubleSolenoid climberRatchetSolenoid;
	public static boolean ratchetEngaged = false;
	
	public static int bumpUpGearNum = 0;
	public static boolean bumpUpGearPressed = false;

	
	//light control
	static final int LIGHT_CONTROL_OFF_STATE=0;
	static final int THIS_LIGHT_CONTROL_INDICATION=2;
	//end light control
	 
	
	private static int intakeState = 0; //0=below threshold, 1=initial high current, 2=running constant current, 3=high current from gear loading
	
	private static double GEAR_IN_BOT_CURRENT_THRESHOLD = .6;
	
	public static void robotInit(){
		gearFeedMotor=new MotorController(ID_Assignments.GEAR_TALON_MOTOR, true);
		climberMotor = new MotorController(ID_Assignments.CLIMB_TALON_MOTOR);
		gearSolenoid = new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_1A, ID_Assignments.GEAR_CYLINDER_1B);
		climberRatchetSolenoid = new DoubleSolenoid(ID_Assignments.GEAR_CYLINDER_2A, ID_Assignments.GEAR_CYLINDER_2B);
	}
	
	public static void teleopInit(){
		gearFeedMotor.enableBrakeMode(false);
		climberMotor.enableBrakeMode(false);
		ratchetEngaged = false;
	}
	
	public static void TeleopPeriodic(boolean feedControl, boolean ejectControl, boolean dropGear, boolean climb, boolean climberRatchet, boolean bumpUpGear){
//		System.out.println("state: " + intakeState + "Gear Motor Current: " + gearFeedMotor.getOutputCurrent());
//		
//		System.out.println("Climber voltage: " + climberMotor.getOutputCurrent());
//		System.out.println("Gear voltage: " + gearFeedMotor.getOutputCurrent());
		
		if(ejectControl && !ratchetEngaged){
			gearFeedMotor.set(EJECT_SPEED);
			climberMotor.set(EJECT_SPEED);
		}else if(dropGear){
			AutoOutputs.setDriveFwd(-0.3);
			gearFeedMotor.set(EJECT_SPEED);
			climberMotor.set(EJECT_SPEED);
			gearSolenoid.set(DoubleSolenoid.Value.kForward);
		}else if(climb){
			gearFeedMotor.set(1.0);
			climberMotor.set(1.0);
		}else if(climberRatchet && feedControl){
			ratchetEngaged = true;
		}else if(bumpUpGear && !bumpUpGearPressed){
			bumpUpGearNum = 2;
		}else{
//			climberMotor.set(0);
//			if(!feedControl){
//				intakeState = 0;
//			}
			
//			if(gearFeedMotor.getOutputCurrent() > GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 0){
//				intakeState++;
//			}else if(gearFeedMotor.getOutputCurrent() < GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 1){
//				intakeState++;
//			}else if(gearFeedMotor.getOutputCurrent() > GEAR_IN_BOT_CURRENT_THRESHOLD && intakeState == 2){
//				intakeState++;
//			}
			
			if(feedControl && !ratchetEngaged){
				gearFeedMotor.set(INTAKE_SPEED);
				climberMotor.set(INTAKE_SPEED);
				Arduino_LightControl.Periodic(THIS_LIGHT_CONTROL_INDICATION);
				gearSolenoid.set(DoubleSolenoid.Value.kForward);
			}else{
				gearFeedMotor.set(0.0);
				climberMotor.set(0.0);
				Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
				gearSolenoid.set(DoubleSolenoid.Value.kReverse);
			}
			
	//		if(feedControl && intakeState != 3){
	//			gearFeedMotor.set(INTAKE_SPEED);
	//		}else{
	//			gearFeedMotor.set(0.0);
	//			solenoid.set(DoubleSolenoid.Value.kReverse);
	//		}
		}
		
		if(ratchetEngaged){
//			System.out.println("Ratchet engaged");
//			if (climberRatchetSolenoid.get() == DoubleSolenoid.Value.kForward){
//				System.out.println("Solenoid Reverse");
//				gearSolenoid.set(DoubleSolenoid.Value.kForward);
				if(gearSolenoid.get().equals(DoubleSolenoid.Value.kForward)){
					System.out.println("gear solenoid forward");
					climberRatchetSolenoid.set(DoubleSolenoid.Value.kReverse);
				}
//			}
		}else{
//			System.out.println("Ratchet disengaged");
//			if (climberRatchetSolenoid.get() == DoubleSolenoid.Value.kReverse){
//				System.out.println("Solenoid Forward");
				climberRatchetSolenoid.set(DoubleSolenoid.Value.kForward);
//			}
		}
		
		if(bumpUpGearNum > 0){
			gearFeedMotor.set(EJECT_SPEED);
			climberMotor.set(EJECT_SPEED);
			bumpUpGearNum--;
		}
		
		bumpUpGearPressed = bumpUpGear;
	}
	
	public static void stopMotors(){
		gearFeedMotor.set(0);
		climberMotor.set(0);
	}

}
