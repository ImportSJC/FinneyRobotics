package autoModes;

import auto.AutoOutputs;
import auto.AutoInputs;
import auto.SuperClass;


public class Auto_Elevator extends SuperClass{
	private double elevatorSpeed = 0;
	
	public Auto_Elevator(double elevatorSpeed){
		this.elevatorSpeed = elevatorSpeed;
	}
	
	@Override
	public void start(){
		System.out.println("Starting Auto Elevator");
		startMotors(elevatorSpeed);
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void startMotors(double elevatorSpeed){
		//Set motor speeds
		AutoOutputs.setElevator(elevatorSpeed);
		
		System.out.println("Start Auto_Elevator at: " + elevatorSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
		AutoOutputs.reset_Elevator();
		
		System.out.println("Stop Auto_Elevator");
	}
}
