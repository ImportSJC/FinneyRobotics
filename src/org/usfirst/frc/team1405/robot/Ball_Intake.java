package org.usfirst.frc.team1405.robot;


import cpi.outputDevices.MotorController;
import cpi.Arduino_LightControl;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Ball_Intake {
;
	static final boolean REVESE_SOLENOID=false;
	static final double motorVoltage=1.0;
	
	static MotorController motor;
	static DoubleSolenoid solenoid;
	
	public static void robotInit(){
		motor=new MotorController(ID_Assignments.BALL_INTAKE_MOTOR);
		if(REVESE_SOLENOID){
			solenoid=new DoubleSolenoid(ID_Assignments.BALL_INTAKE_CYLINDER_1B,ID_Assignments.BALL_INTAKE_CYLINDER_1A);
		}else{
			solenoid=new DoubleSolenoid(ID_Assignments.BALL_INTAKE_CYLINDER_1A,ID_Assignments.BALL_INTAKE_CYLINDER_1B);
		}
	}
	
	public static void teleopPeriodic(Boolean on){
		if(on){
			motor.set(motorVoltage);
			solenoid.set(DoubleSolenoid.Value.kForward);
			Arduino_LightControl.Periodic(3);
		}else{
			motor.set(0.0);
			solenoid.set(DoubleSolenoid.Value.kReverse);
			Arduino_LightControl.Periodic(0);
			
		}
	}

}
