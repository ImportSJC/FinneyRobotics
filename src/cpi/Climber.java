package cpi;

import cpi.Interface.BooleanInput;

public class Climber {
	
	CANTalonControl climberMotor;
	BooleanInput climberInput;
	
	private double CLIMBING_SPEED = -1.0;
	
	public Climber(String name){
		climberInput = new BooleanInput(name,"climber input", "XBox360-Pilot:Left Bumper");
		climberMotor = new CANTalonControl(8);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
		if(climberInput.Value()){
			climberMotor.set(CLIMBING_SPEED);
		}else{
			climberMotor.set(0);
		}
	}
}
