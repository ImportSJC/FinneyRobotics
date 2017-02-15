package cpi.ConrolModes;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.ControlMode;
import cpi.Drive;

/**
 * Tank Drive
 * @author ImportSJC
 *
 */
public class TankDrive extends ControlMode {
	
	public TankDrive() {
		super("Tank Drive");
	}

	@Override
	public void run(){
		Drive.rightMotor = (Robot.pilot.rightStickYaxis() * Drive.MAX_SPEED);
		Drive.leftMotor = -(Robot.pilot.leftStickYaxis() * Drive.MAX_SPEED);
	}
}
