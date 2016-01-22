package cpi;
import Interface.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class BallHandler {
	BooleanInput ballGrabber;
	BooleanInput shoot;
	CANTalon motor;
	Solenoid pneumatic;
	static final int MOTOR_DEVICE_NUMBER = 10;
	static final int PNEUMATICS_DEVICE_NUMBER = 3;
	static final double MOTOR_SPEED = 0;
	static final String BALL_GRABBER_INTERFACE = "<none>";
	static final boolean EXTEND = true;
	static final boolean RETRACT = false;

	public BallHandler()
	{
		ballGrabber = new BooleanInput("/Ball Handler", "Ball Grabber", BALL_GRABBER_INTERFACE, false);
		shoot = new BooleanInput("/Ball Handler", "Shoot", false);
		motor = new CANTalon(MOTOR_DEVICE_NUMBER);
		pneumatic = new Solenoid(PNEUMATICS_DEVICE_NUMBER);
	}
	
	public void TeleopPeriodic()
	{
		if(ballGrabber.Value())
		{
			motor.set(MOTOR_SPEED);
			pneumatic.set(EXTEND);
		}
		else
		{
			motor.set(0);
			pneumatic.set(RETRACT);
		}
	}
	
	public void Autonomous()
	{
		TeleopPeriodic();
	}
}
