package cpi.ConrolModes;

import java.util.concurrent.Callable;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.Drive;

/**
 * Arcade Drive
 * @author ImportSJC
 *
 */
public class ArcadeDrive implements Callable<Void>{
	public ArcadeDrive(){
		//old base
//		rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
//		leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		
		//new base
		Drive.rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = -Drive.leftMotor;
	}
	
	public Void call(){
		return null;
	}
}
