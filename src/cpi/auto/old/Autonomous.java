package cpi.auto.old;

import java.util.Hashtable;
import java.util.Vector;

import cpi.Preferences;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class Autonomous {
	public static Mode currentMode;
	public static Hashtable<String, Mode> modeTable= new Hashtable<String, Mode>();
	
	public static void setCurrentMode(Mode mode){
		
	}
	public void Autonomous(){

		if(modeNames.size()==0){
			currentAutoMode.Value("<none>");
			DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
			return;
		}
		DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
		currentAutoMode.Value(modeNames.get(defaultModeNumber));
		modeNumber=defaultModeNumber;
		if(modeNumber<0)modeNumber=0;
		
		cpi.Preferences.addHardCodeListener(new ITableListener(){
    	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
    			if(modeNames.size()==0){
    				currentAutoMode.Value("<none>");
    				DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
    				return;
    			}
    			DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
    			currentAutoMode.Value(modeNames.get(defaultModeNumber));
    			modeNumber=defaultModeNumber;
    			if(modeNumber<0)modeNumber=0;
    	    }
      });
	}
	
	public static class Modes
	{
		public static void add(Mode newMode){
			modeTable.put(newMode.modeName, newMode);
			modeNames.addElement(newMode.modeName);
		}
		
		public static void get(String modeName){
			currentMode = modeTable.get(modeName);
		}
		
		public static void setDefaultMode(Mode mode){
			if(modeNames.size()==0){
				currentAutoMode.Value("<none>");
				//DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
				return;
			}
			defaultModeNumber=modeNames.indexOf(mode.modeName);
			//DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(defaultModeNumber), false);
			currentAutoMode.Value(modeNames.get(defaultModeNumber));
			modeNumber=defaultModeNumber;
			if(modeNumber<0)modeNumber=0;
		}
	}
	
	public class Mode
	{
		public String modeName;
		
		//Constructor
		public Mode(String name){
			this.modeName = name;
		}
		
		public void RobotInit(){	
			if(!isFirst)return;
			cpi.Preferences.addHardCodeListener(new ITableListener(){
	    	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
	    			if(modeNames.size()==0){
	    				currentAutoMode.Value("<none>");
	    				DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
	    				return;
	    			}
	    			DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
	    			currentAutoMode.Value(modeNames.get(defaultModeNumber));
	    			modeNumber=defaultModeNumber;
	    			if(modeNumber<0)modeNumber=0;
	    	    }
	      });
		}
		
		public void AutoInit(){
			System.out.println(this.modeName + " - AutoInit");
		}
		
		public void AutoPeriodic(){
			System.out.println(this.modeName + " - AutoPeriodic");
		}
	}
	
	public void disabledInit(){
		
	}
	public static void disabledPeriodic(){
		
		if(ChangeMode.Value()==oldChangeMode)return;
		oldChangeMode=ChangeMode.Value();
		if(!oldChangeMode)return;
		if(modeNames.size()==0){
			currentAutoMode.Value("<none>");
			DriverStation.getInstance().reportError("Current Autonomous Mode - <none>", false);
		}
		modeNumber++;
		if(modeNumber>=modeNames.size())modeNumber=0;
		DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
		currentAutoMode.Value(modeNames.get(modeNumber));
	}
	static cpi.Interface.BooleanInput ChangeMode =new cpi.Interface.BooleanInput("/Autonomous", "Autonomous Mode Control",false);
	static boolean oldChangeMode=false;
	static cpi.NetString currentAutoMode=new cpi.NetString("","Current Autonomous Mode","<none>");
	static Vector<String> modeNames= new Vector<String>();
	static int modeNumber=0;
	static int defaultModeNumber=0;
	
	static boolean isFirst=true;
}
