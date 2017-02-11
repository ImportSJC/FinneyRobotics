package cpi.ConrolModes;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.ControlMode;
import cpi.Drive;

/**
 * Arcade Drive
 * @author ImportSJC
 *
 */
public class ArcadeDrive extends ControlMode{
	
	public ArcadeDrive() {
		super("Arcade Drive");
	}

	@Override
	public void run(){
		//old base
//		rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
//		leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		
		//new base
		Drive.rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = -Drive.leftMotor;
	}
}
