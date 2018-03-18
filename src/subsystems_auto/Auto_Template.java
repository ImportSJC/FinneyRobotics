package subsystems_auto;

import AutonomousControls.AutonomousControl;
import logging.SimpleLogger;

/*
 * Test Template Description
 */

public class Auto_Template extends AutonomousControl{
	private double templateSpeed = 0;
	
	public Auto_Template(double mySpeed){
		this.templateSpeed = mySpeed;
	}
	
	@Override
	public void start(){
		startMotors(templateSpeed);
	}
	
	private void startMotors(double mySpeed){
		//Set motor speeds
//		AutoOutputs.setTemplate(mySpeed);
		
		SimpleLogger.log("Start Auto_Template at: " + mySpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
//		AutoOutputs.reset_Template();
		
		SimpleLogger.log("Stop Auto_Template");
	}
}
