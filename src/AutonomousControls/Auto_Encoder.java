package AutonomousControls;

import sensors.CustomEncoder;

public class Auto_Encoder extends AutonomousControl{
	private double startPosition;
	private double targetPosition;
	
	private CustomEncoder encoder;
	
	public Auto_Encoder(double targetPosition, CustomEncoder encoder){
		this.targetPosition = targetPosition;
		this.encoder = encoder;
	}
	
	@Override
	public void start(){
		encoder.reset();
		startPosition = encoder.getPos();
		System.out.println("Encoder start position: " + startPosition);
	}
	
	@Override 
	public boolean check(){
//		System.out.println("Encoder Position: " + AutoInputs.getEncoder());
		if(targetPosition>0 && encoder.getPos() >= targetPosition){return true;}
		else if(targetPosition<0 && encoder.getPos() <= targetPosition){return true;}
		return false;
	}
}
