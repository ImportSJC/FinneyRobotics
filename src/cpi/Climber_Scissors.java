package cpi;

import cpi.Interface.BooleanInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber_Scissors {

	CANTalonControl leftClimberMotor;
	CANTalonControl rightClimberMotor;
	Solenoid extendAngle;
	Solenoid retractAngle;
	Solenoid deployScissors;
	Solenoid stowScissors;
	BooleanInput climberInput;
	BooleanInput angleInput;
	BooleanInput extenderInput;
	static final int LEFT_MOTOR_DEVICE_NUMBER = 5;
	static final int RIGHT_MOTOR_DEVICE_NUMBER = 6;
	static final int PNEUMATICS_DEPLOY_SCISSORS = 3;// double action 3,4
	static final int PNEUMATICS_STOW_SCISSORS = 4;// double action 3,4
	static final int PNEUMATICS_EXTEND_ANGLE = 5;// double action 5,6
	static final int PNEUMATICS_RETRACT_ANGLE = 6;// double action 5,6
	static final boolean POSITION_SCISSORS=true;
	boolean positionScissors=!POSITION_SCISSORS;
	
	private static final double CLIMBING_SPEED = -1.0;
	
	public Climber_Scissors(String name){
		climberInput = new BooleanInput(name,"climber input", "<none>");
		angleInput = new BooleanInput(name,"climber input", "<none>");
		extenderInput = new BooleanInput(name,"climber input", "<none>");
//		extenderInput = new BooleanInput(name,"climber input", "XBox360-Pilot:Left Bumper");
		leftClimberMotor = new CANTalonControl(LEFT_MOTOR_DEVICE_NUMBER);
		rightClimberMotor = new CANTalonControl(RIGHT_MOTOR_DEVICE_NUMBER);
		extendAngle = new Solenoid(PNEUMATICS_EXTEND_ANGLE);
		retractAngle = new Solenoid(PNEUMATICS_RETRACT_ANGLE);
		deployScissors = new Solenoid(PNEUMATICS_STOW_SCISSORS);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
		if(angleInput.Value())positionScissors=POSITION_SCISSORS;
		extendAngle.set(positionScissors);
		deployScissors.set(positionScissors&&extenderInput.Value());
		stowScissors.set(positionScissors&&extenderInput.Value());
		if(climberInput.Value()){
			leftClimberMotor.set(CLIMBING_SPEED);
			rightClimberMotor.set(-CLIMBING_SPEED);
		}else{
			leftClimberMotor.set(0);
			rightClimberMotor.set(0);
		}
	}
}
