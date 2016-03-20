package cpi;

import cpi.Interface.BooleanInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber_Scissors {

	CANTalonControl leftClimberMotor;
	CANTalonControl rightClimberMotor;
	static Solenoid extendAngle;
	static Solenoid retractAngle;
	static Solenoid deployScissors;
	static Solenoid stowScissors;
	BooleanInput climberInput;
	BooleanInput angleRetract;
	BooleanInput angleExtend;
	BooleanInput extenderInput;
	static final int LEFT_MOTOR_DEVICE_NUMBER = 5;
	static final int RIGHT_MOTOR_DEVICE_NUMBER = 6;
	static final int PNEUMATICS_DEPLOY_SCISSORS = 3;// double action 3,4
	static final int PNEUMATICS_STOW_SCISSORS = 2;// double action 3,4
	static final int PNEUMATICS_EXTEND_ANGLE = 4;// double action 5,6
	static final int PNEUMATICS_RETRACT_ANGLE = 5;// double action 5,6
	static final boolean POSITION_SCISSORS=true;
	boolean positionScissors;
	boolean extendedScissors;
	
	static final boolean SCISSOR_RETRACT = false;
	static final boolean SCISSOR_EXTEND = true;
	boolean extenderToggle = false;//button toggle
	
	private static final double CLIMBING_SPEED = -1.0;
	
	public Climber_Scissors(String name){
		positionScissors = !POSITION_SCISSORS;
		System.out.println("CONSTRUCTING CLIMBER");
		
		climberInput = new BooleanInput(name, "climber input", "XBox360-Operator:Left Bumper");
		
		angleRetract = new BooleanInput(name,"climber input", "XBox360-Operator:A Button");
		angleExtend = new BooleanInput(name, "climber input", "XBox360-Operator:Y Button");
		
		extenderInput = new BooleanInput(name,"climber input", "XBox360-Operator:Right Bumper");
		
		leftClimberMotor = new CANTalonControl(LEFT_MOTOR_DEVICE_NUMBER);
		rightClimberMotor = new CANTalonControl(RIGHT_MOTOR_DEVICE_NUMBER);
		extendAngle = new Solenoid(PNEUMATICS_EXTEND_ANGLE);
		retractAngle = new Solenoid(PNEUMATICS_RETRACT_ANGLE);
		deployScissors = new Solenoid(PNEUMATICS_DEPLOY_SCISSORS);
		stowScissors = new Solenoid(PNEUMATICS_STOW_SCISSORS);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopInit(){
		extenderToggle = false;
		
		extendedScissors = false;
		positionScissors = !POSITION_SCISSORS;
	}
	
	public void teleopPeriodic(){
		//control angle
		if(angleRetract.Value()){
			positionScissors = !POSITION_SCISSORS;
		}else if (angleExtend.Value()){
			positionScissors = POSITION_SCISSORS;
		}
		
		//toggle scissors
		if(!extenderInput.Value() && extenderToggle){
			extendedScissors = !extendedScissors;
		}
		extenderToggle = extenderInput.Value();
		
		extendAngle.set(positionScissors);
		retractAngle.set(!positionScissors);
		deployScissors.set(!extendedScissors);
		stowScissors.set(extendedScissors);
		
		//winch
		if(climberInput.Value()){
			leftClimberMotor.set(CLIMBING_SPEED);
			rightClimberMotor.set(-CLIMBING_SPEED);
		}else{
			leftClimberMotor.set(0);
			rightClimberMotor.set(0);
		}
		
//		System.out.println("position scissors: " + positionScissors);
//		System.out.println("extenderInput (joystick): " + extenderInput.Value());
//		System.out.println("both: " + (positionScissors&&extenderInput.Value()));
	}
}
