package cpi.autoSupportClasses;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.SuperClass;
import cpi.auto.inputDevices.Time;

public class AutonomousBase {
	public static int columnIndex = 0;
	public static int rowIndex = 0;
	public static boolean columnInit = false; //has all the modes in the column been started yet?
//	public static boolean[] checks; //an array storing the boolean values of all mode checks in the current row
	public static SuperClass[][] autoStates = null;
	public static String autoMode = "";//this should be set to "" or "default"
	
	private static Time autoTimer = new Time(15);
	
	public static void selectAutoMode(String modeName){
		System.out.println("ModelName: " + modeName);
		autoMode=modeName;
	}
	
	public static boolean allChecksPassed(){
		//has all the checks passed?
		for (int i=0; i<autoStates[columnIndex].length; i++){
			if (!autoStates[columnIndex][i].check()){return false;}
		}
		for (int i=0; i<autoStates[columnIndex].length; i++){autoStates[columnIndex][i].stop();}
		return true;
	}	
	
	public static final void AutonomousInit(){
		columnIndex = 0;
		rowIndex = 0;
		columnInit = false;
		AutoOutputs.setDriveBrake(true);
		AutoInputs.resetGyros();
		
		autoTimer.start();
	}
	public static final void autonomousPeriodic() {
		if(autoStates==null)return;
		if (columnIndex<autoStates.length){
			if (!columnInit){
				AutoInputs.resetGyros();
				AutoInputs.resetEncoders();
				AutoOutputs.ResetValues();
				for (int i=0; i<autoStates[columnIndex].length; i++){
					autoStates[columnIndex][i].start();
				}
				columnInit = true;
			}
			else if(allChecksPassed()){
				System.out.println("All checks passed - NEXT STATE");
//				AutoInputs.resetGyro();
				columnIndex++;
				columnInit = false;
				
				autoTimer.stopTime();
			}
		}
		else{
			System.out.println("End of Autonomous Loop, Time: " + autoTimer.getStopTime());
			
			//TODO: remove this debug code for competitions
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			AutoOutputs.setDriveBrake(false);
		}
	}



}
