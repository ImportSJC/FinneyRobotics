package AutonomousControls;

import java.util.Arrays;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

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
	
	private double[][] copyMatrix(double[][] test){
		double [][] myStuff = new double[test.length][];
		for(int i = 0; i < test.length; i++)
		{
		  double[] aMatrix = test[i];
		  int   aLength = aMatrix.length;
		  myStuff[i] = new double[aLength];
		  System.arraycopy(aMatrix, 0, myStuff[i], 0, aLength);
		}
		
		return myStuff;
	}
	
	@Override
	public void start(){
		double[][] temp1 = copyMatrix(leftMotionProfile);
		double[][] temp2 = copyMatrix(rightMotionProfile);
		mp = new MotionProfileExample(drive.getLeftMotorController(), drive.getRightMotorController(), temp1, temp2);
		mp.reset();
		
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
