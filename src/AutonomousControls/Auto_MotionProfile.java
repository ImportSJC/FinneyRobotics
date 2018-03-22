package AutonomousControls;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import logging.SimpleLogger;
import logging.SimpleLogger.LogLevel;
import logging.SimpleLogger.LogSubsystem;
import motion_profile.MotionProfileExample;
import subsystems_tele.Drive;

public class Auto_MotionProfile extends AutonomousControl{
	private double[][] leftMotionProfile;
	private double[][] rightMotionProfile;
	
	private Drive drive;
	
	private MotionProfileExample mp;
	
	public Auto_MotionProfile(Drive drive, double[][] leftMotionProfile, double[][] rightMotionProfile){
		this.drive = drive;
		this.leftMotionProfile = leftMotionProfile;
		this.rightMotionProfile = rightMotionProfile;
	}
	
	@Override
	public void start(){
		mp = new MotionProfileExample(drive.getLeftMotorController(), drive.getRightMotorController(), leftMotionProfile, rightMotionProfile);
		
		mp.startMotionProfile();
	}
	
	@Override 
	public boolean check(){
		mp.control();
		
		SetValueMotionProfile setOutput = mp.getSetValue();
		drive.getRightMotorController().getTalon().set(ControlMode.MotionProfile, setOutput.value);
		drive.getLeftMotorController().getTalon().set(ControlMode.MotionProfile, setOutput.value);
		return mp.getSetValue() == SetValueMotionProfile.Hold;
	}
	
	@Override
	public void stop(){
		mp.reset();
		drive.setTankDrive(0, 0);
	}
}
