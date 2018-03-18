package subsystems_auto;

import java.util.concurrent.TimeUnit;

import AutonomousControls.AutonomousControl;
import logging.SimpleLogger;
import subsystems_tele.CubeMovement;


public class Auto_Elevator extends AutonomousControl{
	private double elevatorSpeed = 0;
	private final int MSTIME;
	private long stopTime;
	private boolean rampMotors = true;
	
	private CubeMovement cubeMovement;
	
	public Auto_Elevator(CubeMovement cubeMovement, double elevatorSpeed, int mstime){
		this.elevatorSpeed = elevatorSpeed;
		this.cubeMovement = cubeMovement;
		this.MSTIME = mstime;
	}
	
	public Auto_Elevator(CubeMovement cubeMovement, double elevatorSpeed, int mstime, boolean ramp){
		this.elevatorSpeed = elevatorSpeed;
		this.cubeMovement = cubeMovement;
		this.MSTIME = mstime;
		this.rampMotors = ramp;
	}
	
	@Override
	public void start(){
		SimpleLogger.log("Starting Auto Elevator");
		startMotors(elevatorSpeed);
		stopTime = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(MSTIME);
		
	}
	
	@Override
	public boolean check(){
		SimpleLogger.log("Time: " + System.nanoTime() + " stopTime: " + stopTime + " check: " + (System.nanoTime() >= stopTime) );
		return System.nanoTime() >= stopTime;
	}
	
	
	@Override
	public void stop(){
		SimpleLogger.log("Stopping ELEVATOR MOTOR!!!!!");
		stopMotors();
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void startMotors(double elevatorSpeed){
		//Set motor speeds
		if(rampMotors){
			cubeMovement.setForkliftRamp(elevatorSpeed);
		}else{
			cubeMovement.setForklift(elevatorSpeed);
		}
		
		SimpleLogger.log("Start Auto_Elevator at: " + elevatorSpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
		if(rampMotors){
			cubeMovement.setForkliftRamp(0);
		}else{
			cubeMovement.setForklift(0);
		}
		
		SimpleLogger.log("Stop Auto_Elevator");
	}
}
