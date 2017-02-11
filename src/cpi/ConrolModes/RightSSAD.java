package cpi.ConrolModes;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.ControlMode;
import cpi.Drive;

/**
 * Right Single Stick Arcade Drive
 * @author ImportSJC
 *
 */
public class RightSSAD extends ControlMode{
	
	public RightSSAD() {
		super("Right Single Stick Arcade Drive");
	}

	@Override
	public void run(){
		Drive.rightMotor = (-Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		
		Drive.leftMotor= -Drive.leftMotor;
	}
}
