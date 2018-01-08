package subsystems_auto;

import com.ctre.CANTalon;

import AutonomousControls.AutonomousControl;


public class Auto_Elevator extends AutonomousControl{
	private double elevatorSpeed = 0;
	
	private CANTalon elevatorMotor;
	
	public Auto_Elevator(double elevatorSpeed, CANTalon elevatorMotor){
		this.elevatorSpeed = elevatorSpeed;
		this.elevatorMotor = elevatorMotor;
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
		elevatorMotor.set(elevatorSpeed);
		
		System.out.println("Start Auto_Elevator at: " + elevatorSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
		elevatorMotor.set(0);
		
		System.out.println("Stop Auto_Elevator");
	}
}
