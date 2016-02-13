package cpi.autoSupportClasses;

import org.usfirst.frc.team1405.robot.Robot;
import cpi.auto.AutoOutputs;
import cpi.auto.SuperClass;

public class AutonomousBase {//TODO look into columnIndex vs rowIndex. should they be refactored to the other (it seems like it should go matrix[row][col] but we have matrix[col][row])
	public static int columnIndex = 0;
	public static int rowIndex = 0;
	public static boolean columnInit = false; //has all the modes in the column been started yet?
//	public static boolean[] checks; //an array storing the boolean values of all mode checks in the current row
	public static SuperClass[][] autoStates = null;
	public static String autoMode = "simple_test";//this should be set to "" or "default"
	
	
	
	public static void selectAutoMode(String modeName){
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
		AutoOutputs.robotInit();
		AutoOutputs.setDriveBrake(true);
	}
	public static final void autonomousPeriodic() {
		if(autoStates==null)return;
		System.out.println("AutonomousPeriodic: " + autoMode);
		if (columnIndex<autoStates.length){
			if (!columnInit){
				//AutoInputs.myGyro.reset();
				Robot.enc1.reset();
				Robot.enc3.reset();
				for (int i=0; i<autoStates[columnIndex].length; i++){
					autoStates[columnIndex][i].start();
				}
				columnInit = true;
			}
			else if(allChecksPassed()){
				System.out.println("All checks passed - NEXT STATE");
//				for(int i=0; i<autoStates[columnIndex].length; i++){
//					autoStates[columnIndex][i].stop();
//				}
//				AutoInputs.myGyro.reset();
				columnIndex++;
				columnInit = false;
			}
		}
		else{
			System.out.println("End of Autonomous Loop");
			
			//TODO: remove this debug code for competitions
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			AutoOutputs.setDriveBrake(false);
		}
	}



}
