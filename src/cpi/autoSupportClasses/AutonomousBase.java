package cpi.autoSupportClasses;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.Interface.BooleanInput;
import cpi.auto.AutoOutputs;
import cpi.auto.Autonomous;
import cpi.auto.SuperClass;

public class AutonomousBase {//TODO look into columnIndex vs rowIndex. should they be refactored to the other (it seems like it should go matrix[row][col] but we have matrix[col][row])
	public static int columnIndex = 0;
	public static int rowIndex = 0;
	public static boolean columnInit = false; //has all the modes in the column been started yet?
//	public static boolean[] checks; //an array storing the boolean values of all mode checks in the current row
	public static SuperClass[][] autoStates = null;
	public static String autoMode = "cross_basic_defense";//this should be set to "" or "default"
	
	private static BooleanInput auto1;
	private static BooleanInput auto2;
	private static BooleanInput auto3;
	private static BooleanInput auto4;
//	private static BooleanInput auto5;
	
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
	
	public static void RobotInit(){
		auto1 = new BooleanInput("/Disabled Auto Picker", "Auto 1", "XBox360-Pilot:A Button");
		auto2 = new BooleanInput("/Disabled Auto Picker", "Auto 2", "XBox360-Pilot:B Button");
		auto3 = new BooleanInput("/Disabled Auto Picker", "Auto 3", "XBox360-Pilot:X Button");
		auto4 = new BooleanInput("/Disabled Auto Picker", "Auto 4", "XBox360-Pilot:Y Button");
//		auto5 = new BooleanInput("/Disabled Auto Picker", "Auto 5", "XBox360-Pilot:Left Bumper");
	}
	
	public static void DisabledPeriodic(){
		if(auto1.Value()){
			autoMode = Autonomous.CROSS_BASIC_DEFENSE;
			System.out.println("Cross basic defense, go to allignment line (low bar)");
		}else if(auto2.Value()){
//			autoMode = Autonomous.DO_NOTHING;
//			System.out.println("DO NOTHING");
			autoMode = Autonomous.APPROACH_DEFENSE;
			System.out.println("Approach outerworks but don't cross");
		}else if(auto3.Value()){
//			autoMode = Autonomous.DOUBLE_BASIC_DEFENSE;
//			System.out.println("Two robots under the low bar");
			autoMode = Autonomous.CROSS_HARD_DEFENSE;
			System.out.println("Cross hard defense, at full speed, go to allignment line (rockwall ... etc)");
		}else if(auto4.Value()){
//			autoMode = Autonomous.LOWBAR_SCORE;
//			System.out.println("Under low bar and score");
			autoMode = Autonomous.JUST_SHOOT;
			System.out.println("Drive and shoot (in spy position)");
		}/*else if(auto5.Value()){
			autoMode = Autonomous.JUST_SHOOT;
			System.out.println("Just shoot (in spy position)");
		}*/
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
				System.out.println("RESETTING ENCODERS");
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
