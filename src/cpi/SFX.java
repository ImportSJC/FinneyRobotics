package cpi;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SFX {
	static NetworkTable settings;
	private static boolean allowInit = true;
	
	public static void testInit(){
    	if(allowInit){
    		allowInit = false;
	    	settings =NetworkTable.getTable("Test Beds");
	    	if(!settings.containsKey("Motor speed PWM 8"))settings.putNumber("Motor speed PWM 8", 0.25);
	    	if(!settings.containsKey("Motor speed PWM 9"))settings.putNumber("Motor speed PWM 9", 0.25);
	    	if(!settings.containsKey("Enable"))settings.putBoolean("Enable", false);
    	}
    }

}
