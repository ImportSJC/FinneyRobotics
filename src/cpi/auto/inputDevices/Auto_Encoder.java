package cpi.auto.inputDevices;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.auto.AutoValues;
import cpi.auto.SuperClass;

public class Auto_Encoder extends SuperClass{
	private double startPosition;
	private double targetPosition;
	
	private boolean isTurn = false;
	
	public Auto_Encoder(double value){
		targetPosition = value;
	}
	
	public Auto_Encoder(double value, boolean isTurn){
		//isTurn value doesn't even matter, just that its passed.
		this.isTurn = true;
		targetPosition = value*AutoValues.distance_degreeTurn;
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
		if(isTurn){
			System.out.println("Encoder Position: " + Robot.enc1.getDistance());
			System.out.println("Target Position: " + targetPosition);
			System.out.println("Encoder difference: " + (Math.abs(Robot.enc1.getDistance())-Math.abs(Robot.enc3.getDistance())));
			
			double avg = (Math.abs(Robot.enc1.getDistance()) + Math.abs(Robot.enc3.getDistance()))/2;
			if(avg >= targetPosition){return true;}
		}else{
			if(targetPosition>0 && Robot.enc1.getAverageDistance(Robot.enc3) >= targetPosition){return true;}
			else if(targetPosition<0 && Robot.enc1.getAverageDistance(Robot.enc3) <= targetPosition){return true;}
		}
		return false;
	}
}
