package cpi;

import cpi.Interface.BooleanInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber_Scissors {
	
	CANTalonControl climberMotor;
	Solenoid angle;
	Solenoid extender;
	BooleanInput climberInput;
	BooleanInput angleInput;
	BooleanInput extenderInput;
	static final int MOTOR_DEVICE_NUMBER = 8;
	static final int PNEUMATICS_EXTEND = 4;
	static final int PNEUMATICS_ANGLE = 5;
	static final boolean POSITION_SCISSORS=true;
	boolean positionScissors=!POSITION_SCISSORS;
	
	private static final double CLIMBING_SPEED = -1.0;
	
	public Climber_Scissors(String name){
		climberInput = new BooleanInput(name,"climber input", "XBox360-Pilot:Left Bumper");
		angleInput = new BooleanInput(name,"climber input", "XBox360-Pilot:Left Bumper");
		extenderInput = new BooleanInput(name,"climber input", "XBox360-Pilot:Left Bumper");
		climberMotor = new CANTalonControl(MOTOR_DEVICE_NUMBER);
		angle = new Solenoid(PNEUMATICS_ANGLE);
		extender = new Solenoid(PNEUMATICS_EXTEND);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
		if(angleInput.Value())positionScissors=POSITION_SCISSORS;
		angle.set(positionScissors);
		extender.set(positionScissors&&extenderInput.Value());
		if(climberInput.Value()){
			climberMotor.set(CLIMBING_SPEED);
		}else{
			climberMotor.set(0);
		}
	}
}
