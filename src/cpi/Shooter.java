package cpi;

import cpi.Interface.BooleanInput;

/**
 * The subsystem that contains two motors to operate the ball shooting functionality
 * 
 * @author Stephen Cerbone
 *
 */
public class Shooter {
	private BooleanInput shooting;
	private BooleanInput intaking;
	private String name;
	
	private CANTalon shooterTalon1;
	private CANTalon shooterTalon2;
	
	private final double MOTOR_SPEED_FULL = 1.0;
	
	private final String SHOOTER_MOTOR = "Shooter Motor";
	
	public Shooter(String name){
		this.name = name;
		
		shooting = new BooleanInput(this.name, "Shooting Motors");
		intaking = new BooleanInput(this.name, "Reverse Shooting Motors");
		
		shooterTalon1 = CANTalon.getInstance(name + "/" + SHOOTER_MOTOR, "Shooter Motor #1", 9);
		shooterTalon2 = CANTalon.getInstance(name + "/" + SHOOTER_MOTOR, "Shooter Motor #2", 10);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
		if(shooting.Value()){
			//turn motors
			shooterTalon1.set(MOTOR_SPEED_FULL);
			shooterTalon2.set(-MOTOR_SPEED_FULL);
		}
		else if(intaking.Value()){
			//reverse motors
			shooterTalon1.set(-MOTOR_SPEED_FULL);
			shooterTalon2.set(MOTOR_SPEED_FULL);
		}
		else{
			//stop motors
			shooterTalon1.set(0);
			shooterTalon2.set(0);
		}
		
	}
}
