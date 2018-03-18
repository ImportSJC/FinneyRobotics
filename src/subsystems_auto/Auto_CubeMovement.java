package subsystems_auto;

import java.util.concurrent.TimeUnit;

import AutonomousControls.AutonomousControl;
import logging.SimpleLogger;
import subsystems_tele.CubeMovement;

public class Auto_CubeMovement extends AutonomousControl {
	private CubeMovement forklift;
	private boolean inputCube;
	private int msTime = 1000;
	private long stopTime;

	public Auto_CubeMovement(CubeMovement forklift, boolean inputCube, int msTime){
		this.forklift = forklift;
		this.inputCube = inputCube;
		this.msTime = msTime;
	}
	
	@Override
	public void start(){
		SimpleLogger.log("Starting Auto Cube Mechanism");
		stopTime = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(msTime);
		if(inputCube){
			forklift.setIntake(1);
		} else{
			forklift.setIntake(-1);
		}
		
	}
	
	@Override
	public boolean check(){
		if(System.nanoTime() >= stopTime){
			return true;
		} else{
			return false;
		}
	}
	
	
	@Override
	public void stop(){
		forklift.CubeIntake_Reset();
	}
}
