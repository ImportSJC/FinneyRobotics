package cpi;

import java.util.concurrent.Callable;

import org.usfirst.frc.team1405.robot.Robot;

public class CallableControlModes implements Callable<Void> {
	
	public static Callable<Void> tankDrive(){
		Drive.rightMotor = -(Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED);
		return null;
	}
	
	public static void arcadeDrive(){
		//old base
//		rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
//		leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		
		//new base
		Drive.rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = -Drive.leftMotor;
	}
	
	public void rightSingleStickArcadeDrive(){
		Drive.rightMotor = (-Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.rightStickXaxis() * Drive.MAX_SPEED);
		
		Drive.leftMotor= -Drive.leftMotor;
	}
	
	public void leftSingleStickArcadeDrive(){
		Drive.rightMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) - (Robot.pilot.leftStickXaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = (-Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED) + (Robot.pilot.leftStickXaxis() * Drive.MAX_SPEED);
		
		Drive.leftMotor= -Drive.leftMotor;	
	}
	
	public Void call(){
		return null;
	}
	
}
