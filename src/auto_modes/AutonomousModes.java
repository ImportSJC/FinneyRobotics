package auto_modes;

import java.util.ArrayList;

public class AutonomousModes {
	public static ArrayList<AutonomousMode> autoModes = new ArrayList<>();
	
	private static int currentAutoMode = 0;
	
	public static void addAutoMode(AutonomousMode mode){
		autoModes.add(mode);
	}
	
	/**
	 * Change the current auto mode to the next one
	 */
	public static void nextAutoMode(){
		currentAutoMode++;
		currentAutoMode=currentAutoMode % autoModes.size();
		
		System.out.println("Current auto mode: " + autoModes.get(currentAutoMode).getDescription());
	}
	
	/**
	 * @return the current autonomous mode
	 */
	public static AutonomousMode getCurrentAutoMode(){
		return autoModes.get(currentAutoMode);
	}
	
}
