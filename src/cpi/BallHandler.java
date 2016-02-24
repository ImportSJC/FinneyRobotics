package cpi;
import cpi.Interface.*;
import edu.wpi.first.wpilibj.Solenoid;

public class BallHandler {
	DoubleInput intake;
	DoubleInput shoot;
	BooleanInput intakeSolenoid;
	CANTalonControl motor;
	Solenoid pneumatic1;
	Solenoid pneumatic2;
	static final int MOTOR_DEVICE_NUMBER = 11;
	static final int PNEUMATICS_DEVICE_NUMBER1 = 2;
	static final int PNEUMATICS_DEVICE_NUMBER2 = 3;
	static final double MOTOR_SPEED = 1.0;
	
	static final double SHOOTING_TIMER = 0.2*50; //format (x*50) where x = number of seconds
	double currentTimer = -100;//the -100 is a magic number indicating that none of the shoot/intake buttons are being pushed
	
//	static final double SHOOTING_TIMER_2 = 1*50; //format (x*50) where x = number of seconds
//	double currentTimer2 = 0;
	
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
		
		motor = new CANTalonControl(MOTOR_DEVICE_NUMBER);
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
		
		if(allRollersIn.Value()){
			currentTimer = -100;
			motor.set(MOTOR_SPEED);
			debug = MOTOR_SPEED;
		}else if(allRollersOut.Value()){
			currentTimer = -100;
			motor.set(-MOTOR_SPEED);
			debug = -MOTOR_SPEED;
		}else if(intakeButtonPressed){
			currentTimer = -100;
			motor.set(MOTOR_SPEED);
			debug = MOTOR_SPEED;
			pneumatic1.set(EXTEND);
			pneumatic2.set(RETRACT);
		}else if(shootButtonPressed){
//			motor.set(-MOTOR_SPEED);
//			debug = -MOTOR_SPEED;
			if(currentTimer == -100){
				currentTimer = SHOOTING_TIMER;
			}
			pneumatic1.set(RETRACT);
			pneumatic2.set(EXTEND);
		}else if(BallRetain.getBallRetentionStage() != 0){
			motor.set(BallRetain.motorValues);
			if(BallRetain.getBallRetentionStage() == 1){
				pneumatic1.set(RETRACT);
				pneumatic2.set(EXTEND);
			}
		}else{
			currentTimer = -100;
			motor.set(0);
			debug = 0;
			
			pneumatic1.set(RETRACT);
			pneumatic2.set(EXTEND);
		}
		
		if(currentTimer <= 0 && currentTimer > -100){
//			currentTimer2 = SHOOTING_TIMER_2;
			motor.set(-MOTOR_SPEED);
			debug = -MOTOR_SPEED;
		}
		
		if(currentTimer>0){
			currentTimer-=1;
		}
		
//		if(currentTimer2 > 0){
//			motor.set(-MOTOR_SPEED);
//			debug = -MOTOR_SPEED;
//		}else{
//			motor.set(0);
//			debug = 0;
//		}
		
//		if(currentTimer2>0){
//			currentTimer2-=1;
//		}else if(currentTimer2 < 0){
//			currentTimer2 = 0;
//		}
		
//		System.out.println("debug: " + debug);
//		System.out.println("pneumatic: " + pneumatic1.get());
//		System.out.println("current timer: " + currentTimer);
	}
	
	public void Autonomous()
	{
		TeleopPeriodic();
	}
}
