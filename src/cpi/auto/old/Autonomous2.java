package cpi.auto.old;

import java.util.Hashtable;
import java.util.Vector;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class Autonomous2 {
	public static Mode currentMode;
	public static Hashtable<String, Mode> modeTable= new Hashtable<String, Mode>();
	
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
	}
	
	public static class Modes
	{
		public static void add(Mode newMode){
			modeTable.put(newMode.modeName, newMode);
			modeNames.addElement(newMode.modeName);
		}
		
		public static void setCurrentMode(String modeName){
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
			if(modeNumber<0){
				modeNumber=0;
				return;
			}
			currentMode=modeTable.get(currentAutoMode.Value());
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
			
			
			DriverStation.getInstance().reportError("Current Autonomous Mode - "+modeNames.get(modeNumber), false);
			currentAutoMode.Value(modeNames.get(defaultModeNumber));
			currentMode=modeTable.get(currentAutoMode.Value());
			modeNumber=defaultModeNumber;
			if(modeNumber<0){
				modeNumber=0;
				return;
			}
			currentMode=modeTable.get(currentAutoMode.Value());
			isFirst=false;
		}
		private class SuperElement{
			private double value;
			private double testValue;
			private double outputLink;//TODO Change this to an appropriate object
			private double inputLink;//TODO Change this to an appropriate object
			private String condition;
			private String nextState;
			private boolean isTrue(){
				switch(condition){
				case "<":
					if(testValue<inputLink)return true;
					break;
				case ">":
					if(testValue>inputLink)return true;
					break;
				case "<=":
					if(testValue<=inputLink)return true;
					break;
				case ">=":
					if(testValue>=inputLink)return true;
					break;
				case "=":
					if(testValue==inputLink)return true;
					break;
				case "true":
					return true;
				}
				return false;
			}
			
		}
		
		public class Element extends SuperElement { // Elements added to mode to create the state form
			public Element(double value, double output, double input,String condition,String nextState){//TODO output and input must match SuperElement - class needs work
				super.value=value;
				super.inputLink=input;
				super.outputLink=output;
				// ...
			}
			
		}

		// Control conditions
		public static final String LT="<";
		public static final String GT=">";
		public static final String LTE="<=";
		public static final String GTE=">=";
		public static final String EQUALS="==";
		public static final String TRUE="true";
		
		public void AutoInit(){
			System.out.println(this.modeName + " - AutoInit");
		}
		
		public void AutoPeriodic(){
			System.out.println(this.modeName + " - AutoPeriodic");
		}
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
		currentMode=modeTable.get(modeNames.get(modeNumber));
	}
	static cpi.Interface.BooleanInput ChangeMode =new cpi.Interface.BooleanInput("/Autonomous", "Autonomous Mode Control",false);
	static boolean oldChangeMode=false;
	static cpi.NetString currentAutoMode=new cpi.NetString("","Current Autonomous Mode","<none>");
	static Vector<String> modeNames= new Vector<String>();
	static int modeNumber=0;
	static int defaultModeNumber=0;
	
	static boolean isFirst=true;
}
