package cpi.ConrolModes;

import java.util.concurrent.Callable;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.Drive;

/**
 * Tank Drive
 * @author ImportSJC
 *
 */
public class TankDrive implements Callable<Void> {
	
	public TankDrive(){
		Drive.rightMotor = -(Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED);
	}
	
	public Void call(){
		return null;
	}
	
}
