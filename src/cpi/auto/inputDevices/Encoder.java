package cpi.auto.inputDevices;

import cpi.auto.AutoInputs;
import cpi.auto.SuperClass;

public class Encoder extends SuperClass{
	private double startPosition;
	private double targetPosition;
	
	public Encoder(double value){
		targetPosition = value;
	}
	
	@Override
	public void start(){
		AutoInputs.resetEncoders();
		startPosition = AutoInputs.getEncoder();
		System.out.println("Encoder start position: " + startPosition);
	}
	
	@Override 
	public boolean check(){
//		System.out.println("Encoder Position: " + AutoInputs.getEncoder());
		if(targetPosition>0 && AutoInputs.getEncoder() >= targetPosition){return true;}
		else if(targetPosition<0 && AutoInputs.getEncoder() <= targetPosition){return true;}
		return false;
	}
}
