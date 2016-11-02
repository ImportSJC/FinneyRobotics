package cpi.autoSupportClasses;

import java.util.Vector;

import edu.wpi.first.wpilibj.DriverStation;

public class Set {
	static final String NONE="<none>";
	static boolean changeMode = false;
	static boolean oldChangeMode=false;
	static Vector<String> modeNames= new Vector<String>();
	static int modeNumber=0;
	static int defaultModeNumber=0;
	static String currentAutoMode = "";
	
	static boolean isFirst=true;

	public static void addName(String modeName){
		modeNames.addElement(modeName);
	}
	
	public static void setDefault(String defaultModeName){
		currentAutoMode=defaultModeName;
	}
	
	
	public static void disabledPeriodic(){
		if(isFirst){
			if(modeNames.size()==0){
				currentAutoMode=NONE;
//				DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
				isFirst=false;
				return;
			}else{
				if(currentAutoMode==NONE)currentAutoMode=modeNames.get(0);
			}
			isFirst=false;
//				DriverStation.reportWarning("Testing", false);
//				DriverStation.getInstance().reportError("Current Autonomous Mode - "+currentAutoMode, false);
				modeNumber=modeNames.indexOf(currentAutoMode);
				  AutonomousBase.selectAutoMode(modeNames.get(modeNumber));
		}
		else{

			if(modeNames.size()==0)return;
		}
		if(changeMode==oldChangeMode)return;
		oldChangeMode=changeMode;
		if(!oldChangeMode)return;
		if(modeNames.size()==0){
			currentAutoMode=NONE;
		}else{
			if(currentAutoMode==NONE)currentAutoMode=modeNames.get(0);
		}
		modeNumber++;
		if(modeNumber>=modeNames.size())modeNumber=0;
//		DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
		currentAutoMode=modeNames.get(modeNumber);
	  AutonomousBase.selectAutoMode(modeNames.get(modeNumber));
	}
}
