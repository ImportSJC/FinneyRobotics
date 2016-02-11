package cpi;
import cpi.Interface.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class BallHandler {
	DoubleInput intake;
	DoubleInput shoot;
	BooleanInput intakeSolenoid;
	CANTalon motor;
	Solenoid pneumatic1;
	Solenoid pneumatic2;
	static final int MOTOR_DEVICE_NUMBER = 11;
	static final int PNEUMATICS_DEVICE_NUMBER1 = 2;
	static final int PNEUMATICS_DEVICE_NUMBER2 = 3;
	static final double MOTOR_SPEED = 0.5;
	
	static final double SHOOTING_TIMER = 3; //not in seconds
	double currentTimer = 0;
	
	static final String BALL_GRABBER_INTERFACE = "XBox360-Pilot: Left Trigger";
	static final String BALL_SHOOTER_INTERFACE = "XBox360-Pilot: Right Trigger";
	static final boolean EXTEND = false;
	static final boolean RETRACT = true;
	BooleanInput allRollersIn;
	BooleanInput allRollersOut;
	
	boolean solenoidExtended = false;
	boolean motorShooting = false;
	boolean motorIntaking = false;
	
	boolean intakeButtonPressed = false;
	boolean shootButtonPressed = false;
	
	double debug = 0.0;

	public BallHandler(String name){
		intake = new DoubleInput(name, "Ball Grabber", BALL_GRABBER_INTERFACE);
		shoot = new DoubleInput(name, "Shoot", BALL_SHOOTER_INTERFACE);
		intakeSolenoid = new BooleanInput(name, "Intake Solenoid", "XBox360-Pilot:Right Bumper");
		
		motor = new CANTalon(MOTOR_DEVICE_NUMBER);
		pneumatic1 = new Solenoid(PNEUMATICS_DEVICE_NUMBER1);
		pneumatic2 = new Solenoid(PNEUMATICS_DEVICE_NUMBER2);
		allRollersIn = new BooleanInput(name, "All Rollers In", "XBox360-Pilot:A Button");
		allRollersOut = new BooleanInput(name, "All Rollers Out", "XBox360-Pilot:Y Button");
	}
	
	public void robotInit()
	{
		
	}
	
	public void TeleopPeriodic()
	{
		intakeButtonPressed = intake.Value() > 0.5;
		shootButtonPressed = shoot.Value() > 0.5;
		if(intakeButtonPressed){
			motor.set(MOTOR_SPEED);
			debug = MOTOR_SPEED;
			pneumatic1.set(EXTEND);
			pneumatic2.set(RETRACT);
		}else if(shootButtonPressed){
			motor.set(-MOTOR_SPEED);
			debug = -MOTOR_SPEED;
			pneumatic1.set(RETRACT);
			pneumatic2.set(EXTEND);
			currentTimer = SHOOTING_TIMER;
		}else if(currentTimer > 0){
			//dont stop the motors
			motor.set(-MOTOR_SPEED);
			debug = -MOTOR_SPEED;
		}else{
			motor.set(0);
			debug = 0;
			pneumatic1.set(RETRACT);
			pneumatic2.set(EXTEND);
		}
		
		if(currentTimer>0){
			currentTimer-=0.1;
		}else if(currentTimer < 0){
			currentTimer = 0;
		}
		
		System.out.println("debug: " + debug);
		System.out.println("pneumatic: " + pneumatic1.get());
		System.out.println("current timer: " + currentTimer);
	}
	
	public void Autonomous()
	{
		TeleopPeriodic();
	}
}
