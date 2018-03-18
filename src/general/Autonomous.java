package general;

import AutonomousControls.AutonomousControl;
import auto_modes.AutonomousMode;
import logging.SimpleLogger;

public class Autonomous {
	public int columnIndex = 0;
	public int rowIndex = 0;
	public boolean columnInit = false; //has all the modes in the column been started yet?
	
	private String autoDescription = "";
	public AutonomousControl[] autoStates = null;
	
	/**
	 * Instantiate a new autonomous class by providing a list of all encoders and gyros.
	 * This is required since autonomous mode will be resetting them between steps in order to
	 * reduce drift and start fresh every step.
	 * @param encoders a list of encoders on the robot
	 * @param gyros a list of gyros on the robot
	 */
	public Autonomous(AutonomousMode mode){
		setAutoMode(mode);
	}
	
	/**
	 * Set the autonomous mode
	 * @param states the autonomous mode to set as the current mode
	 */
	public void setAutoMode(AutonomousMode mode){
		this.autoDescription = mode.getDescription();
		this.autoStates = mode.getSteps();
//		SimpleLogger.log("Auto mode set to: " + this.autoDescription);
	}
	
	public void AutonomousInit(){
		columnIndex = 0;
		rowIndex = 0;
		columnInit = false;
	}
	
	public void AutonomousPeriodic() {
		if (columnIndex<autoStates.length){
			if (!columnInit){
				// start the autonomous control object at the current index of the autoStates array.
				autoStates[columnIndex].start();
				columnInit = true; //TODO remove the column init bool by instead asking the autonomous control object if it has been started yet
			} else if(autoStates[columnIndex].check()){
				autoStates[columnIndex].stop();
				SimpleLogger.log("All checks passed - NEXT STATE");
				columnIndex++;
				columnInit = false;
			}
		} else {
			SimpleLogger.log("End of Autonomous Loop");
		}
	}
}
