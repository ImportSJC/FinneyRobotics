package cpi;

import java.util.ArrayList;

public class MySet {
	private static ArrayList<AutoMode> autoArrayList = new ArrayList<AutoMode>();
	private static ArrayList<ControlMode> controlArrayList = new ArrayList<ControlMode>();
	private static int currentAutoIndex = 0;
	private static int currentControlIndex = 0;
	
	public static void addAutoMode(AutoMode automode){
		autoArrayList.add(automode);
	}
	
   public static void assignAutoMode(int index){
	   autoArrayList.get(index).assignAutoMode();
	   System.out.println("Current Auto Mode: " + autoArrayList.get(index).getDesc());
	   currentAutoIndex = index;
   }
   
   public static void assignNextAutoMode(){
	   currentAutoIndex++;
	   if(currentAutoIndex >= autoArrayList.size()){
		   //loop the index to the beginning of the array
		   currentAutoIndex = 0;
	   }
	   
	   assignAutoMode(currentAutoIndex);
   }
   
   
	
	public static void addControlMode(ControlMode controlmode){
		controlArrayList.add(controlmode);
	}
	
  public static void assignControlMode(int index){
	  controlArrayList.get(index).assignControlMode();
	   System.out.println("Current Control Mode: " + controlArrayList.get(index).getDesc());
	   currentControlIndex = index;
  }
  
  public static void assignNextControlMode(){
	  currentControlIndex++;
	   if(currentControlIndex >= controlArrayList.size()){
		   //loop the index to the beginning of the array
		   currentControlIndex = 0;
	   }
	   
	   assignControlMode(currentControlIndex);
  }
}
