package auto_modes;

import AutonomousControls.AutonomousControl;

public class AutonomousMode {
	private String description;
	private AutonomousControl[] states;
	
	public AutonomousMode(String description, AutonomousControl[] states) {
		this.description = description;
		this.states = states;
	}
	
	/**
	 * @return the autonomous modes description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * @return the autonomous steps 
	 */
	public AutonomousControl[] getSteps(){
		return states;
	}
}
