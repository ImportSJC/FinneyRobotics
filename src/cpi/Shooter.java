package cpi;

import cpi.Interface.BooleanInput;
import cpi.Interface.DoubleInput;
import edu.wpi.first.wpilibj.CANTalon;

/**
 * The subsystem that contains two motors to operate the ball shooting functionality
 * 
 * @author Stephen Cerbone
 *
 */
public class Shooter {
	private DoubleInput shooting;
	private DoubleInput intaking;
	private BooleanInput allRollersIn;
	private BooleanInput allRollersOut;
	private String name;
	
	private CANTalon shooterTalon1;
	private CANTalon shooterTalon2;
	
	private final double SHOOTING_SPEED = 1.0;
	private final double INTAKE_SPEED = 1.0;
//	private final String SHOOTER_MOTOR = "Shooter Motor";
	
	public Shooter(String name){
		this.name = name;
		
		shooting = new DoubleInput(this.name, "Shooting Motors", "XBox360-Pilot: Right Trigger");
		intaking = new DoubleInput(this.name, "Reverse Shooting Motors", "XBox360-Pilot: Left Trigger");
		allRollersIn = new BooleanInput(this.name, "All Roller Motors In", "XBox360-Pilot:A Button");
		allRollersOut = new BooleanInput(this.name, "All Roller Motors Out", "XBox360-Pilot:Y Button");
		
//		shooterTalon1 = CANTalon.getInstance(name + "/" + SHOOTER_MOTOR, "Shooter Motor #1", 1);
//		shooterTalon2 = CANTalon.getInstance(name + "/" + SHOOTER_MOTOR, "Shooter Motor #2", 2);
		
		shooterTalon1 = new CANTalon(9);
		shooterTalon2 = new CANTalon(10);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
//		System.out.println("shooting: " + shooting.Value());
		if(shooting.Value()>0.5){
			//turn motors
			shooterTalon1.set(SHOOTING_SPEED);
			shooterTalon2.set(-SHOOTING_SPEED);
		}
		else if(intaking.Value()>0.5){
			//reverse motors
			shooterTalon1.set(-INTAKE_SPEED);
			shooterTalon2.set(INTAKE_SPEED);
		}
		else if(allRollersIn.Value()){
			shooterTalon1.set(-INTAKE_SPEED);
			shooterTalon2.set(INTAKE_SPEED);
		}
		else if(allRollersOut.Value()){
			shooterTalon1.set(SHOOTING_SPEED);
			shooterTalon2.set(-SHOOTING_SPEED);
		}
		else{
			//stop motors
			shooterTalon1.set(0);
			shooterTalon2.set(0);
		}
		
	}
}
