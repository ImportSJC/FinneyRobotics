package cpi;

import java.io.Console;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;

public class MySet {
	private static ArrayList<AutoMode> autoArrayList = new ArrayList<AutoMode>();
	private static ArrayList<ControlMode> controlArrayList = new ArrayList<ControlMode>();
	private static int currentAutoIndex = 0;
	private static int currentControlIndex = 0;
	
	//
	// Auto Mode
	//
	
	public static void addAutoMode(AutoMode automode){
		autoArrayList.add(automode);
	}
	
   public static void assignAutoMode(int index){
	   autoArrayList.get(index).assignAutoMode();
	   DriverStation.reportError("Current Auto Mode: " + autoArrayList.get(index).getDesc(), false);
	   System.out.println("Current Auto Mode: " + autoArrayList.get(index).getDesc());
	   currentAutoIndex = index;
   }
   
   public static int getCurrentAutoModeIndex(){
		  return currentAutoIndex;
	  }
	  
	  public static String getCurrentAutoModeDescription(){
		  return autoArrayList.get(currentAutoIndex).getDesc();
	  }
	  
	  public static int getAutoModeSize(){
		  return autoArrayList.size();
	  }
   
   public static void assignNextAutoMode(){
	   currentAutoIndex++;
	   if(currentAutoIndex >= autoArrayList.size()){
		   //loop the index to the beginning of the array
		   currentAutoIndex = 0;
	   }
	   
	   assignAutoMode(currentAutoIndex);
   }
   
   //
   // Control Mode
   //
	
	public static void addControlMode(ControlMode controlmode){
		controlArrayList.add(controlmode);
	}
	
  public static void assignControlMode(int index){
	  controlArrayList.get(index).assignControlMode();
//	   System.out.println("Current Control Mode: " + controlArrayList.get(index).getDesc());
	   currentControlIndex = index;
  }
  
  public static int getCurrentControlModeIndex(){
	  return currentControlIndex;
  }
  
  public static String getCurrentControlModeDescription(){
	  return controlArrayList.get(currentControlIndex).getDesc();
  }
  
  public static int getControlModeSize(){
	  return controlArrayList.size();
  }
  
  public static void assignNextControlMode(){
	  currentControlIndex++;
	   if(currentControlIndex >= controlArrayList.size()){
		   //loop the index to the beginning of the array
		   currentControlIndex = 0;
	   }
	   
	   assignControlMode(currentControlIndex);
  }
  
  public static void printAutoMode(){
	  DriverStation.reportError("Current Auto Mode: " + autoArrayList.get(currentAutoIndex).getDesc(), false);
	  System.out.println("Current Auto Mode: " + autoArrayList.get(currentAutoIndex).getDesc());
  }
}
