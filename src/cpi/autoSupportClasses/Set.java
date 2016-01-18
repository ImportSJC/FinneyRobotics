package cpi.autoSupportClasses;

import java.util.Vector;

import edu.wpi.first.wpilibj.DriverStation;

public class Set {
	static final String NONE="<none>";
	static cpi.Interface.BooleanInput ChangeMode =new cpi.Interface.BooleanInput("/Autonomous", "Autonomous Mode Control","XBox360-Pilot:A Button",false);
	static boolean oldChangeMode=false;
	static cpi.NetString currentAutoMode=new cpi.NetString("","Current Autonomous Mode",NONE);
	static Vector<String> modeNames= new Vector<String>();
	static int modeNumber=0;
	static int defaultModeNumber=0;

	static boolean isFirst=true;
	static boolean isFirstPress=true;

	public static void addName(String modeName){
		modeNames.addElement(modeName);
	}
	
	public static void setDefault(String defaultModeName){
		currentAutoMode.Value(defaultModeName);
	}
	
	
	public static void disabledPeriodic(){
		if(isFirst){
			if(modeNames.size()==0){
				currentAutoMode.Value(NONE);
				DriverStation.getInstance().reportError("\nCurrent Autonomous Mode - <none>", false);
				isFirst=false;
				return;
			}else{
				if(currentAutoMode.Value()==NONE)currentAutoMode.Value(modeNames.get(0));
			}
			isFirst=false;
				DriverStation.getInstance().reportError("\nCurrent Autonomous Mode - "+currentAutoMode.Value(), false);
				modeNumber=modeNames.indexOf(currentAutoMode.Value());
				  AutonomousBase.selectAutoMode(modeNames.get(modeNumber));
		}
		else{

			if(modeNames.size()==0)return;
		}
		if(ChangeMode.Value()==oldChangeMode)return;
		oldChangeMode=ChangeMode.Value();
		if(!oldChangeMode)return;
		if(modeNames.size()==0){
			currentAutoMode.Value(NONE);
		}else{
			if(currentAutoMode.Value()==NONE)currentAutoMode.Value(modeNames.get(0));
		}
		if(isFirstPress){
			isFirstPress=false;
			DriverStation.getInstance().reportError(cpi.Preferences.globalPrefix()+"\n\n", false);
			DriverStation.getInstance().reportError("\nCurrent Autonomous Mode - "+modeNames.get(modeNumber), false);
			return;
		}
		modeNumber++;
		if(modeNumber>=modeNames.size())modeNumber=0;
		DriverStation.getInstance().reportError("\nCurrent Autonomous Mode (Default) - "+modeNames.get(modeNumber), false);
		currentAutoMode.Value(modeNames.get(modeNumber));
	  AutonomousBase.selectAutoMode(modeNames.get(modeNumber));
	}
}
