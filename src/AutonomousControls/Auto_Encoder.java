package AutonomousControls;

import MotorController.MotorController;
import sensors.CustomEncoder;

public class Auto_Encoder extends AutonomousControl{
	private final double TOLERANCE = 0.1;
	
	private double startDistance = 0;
	private double targetDistance;
	
	private MotorController controller;
	
	public Auto_Encoder(double targetDistance, MotorController controller){
		this.targetDistance = targetDistance;
		this.controller = controller;
	}
	
	@Override
	public void start(){
		controller.setPosition(0);
//		SimpleLogger.log("Encoder start position: " + startPosition);
	}
	
	@Override 
	public boolean check(){
//		SimpleLogger.log("Encoder Position: " + AutoInputs.getEncoder());
		if(controller.getDriveDistance() < targetDistance+TOLERANCE && controller.getDriveDistance() > targetDistance-TOLERANCE){
			return true;
		}
		return false;
	}
}
