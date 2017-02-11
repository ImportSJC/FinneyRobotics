package cpi.ConrolModes;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.ControlMode;
import cpi.Drive;

/**
 * Left Single Stick Arcade Drive
 * @author ImportSJC
 *
 */
public class LeftSSAD extends ControlMode{
	
	public LeftSSAD() {
		super("Left Single Stick Arcade Drive");
	}

	@Override
	public void run(){
		Drive.rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.leftStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.leftStickXaxis() * Drive.MAX_SPEED);
		
		Drive.leftMotor= -Drive.leftMotor;	
	}
}
