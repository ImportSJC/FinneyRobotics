package cpi.auto.inputDevices;

import org.usfirst.frc.team1405.robot.Robot;
import cpi.auto.SuperClass;

public class Auto_Encoder extends SuperClass{
	private double startPosition;
	private double targetPosition;
	
	public Auto_Encoder(double value){
		targetPosition = value;
	}
	
	@Override
	public void start(){
		Robot.enc1.reset();
		Robot.enc3.reset();
		startPosition = Robot.enc1.getAverageDistance(Robot.enc3);
		System.out.println("Encoder start position: " + startPosition);
	}
	
	@Override 
	public boolean check(){
//		System.out.println("Encoder Position: " + AutoInputs.getEncoder());
		if(targetPosition>0 && Robot.enc1.getAverageDistance(Robot.enc3) >= targetPosition){return true;}
		else if(targetPosition<0 && Robot.enc1.getAverageDistance(Robot.enc3) <= targetPosition){return true;}
		return false;
	}
}
