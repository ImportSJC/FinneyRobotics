package cpi;

import java.util.ArrayList;

public class MySet {
	private static ArrayList<AutoMode> arraylist = new ArrayList<AutoMode>();
	private static int currentIndex = 0;
	
	public static void addAutoMode(AutoMode automode){
		arraylist.add(automode);
	}
	
   public static void assignAutoMode(int index){
	   arraylist.get(index).assignAutoMode();
	   System.out.println("Current Auto Mode: " + arraylist.get(index).getDesc());
	   currentIndex = index;
   }
   
   public static void assignNextAutoMode(){
	   currentIndex++;
	   if(currentIndex >= arraylist.size()){
		   //loop the index to the beginning of the array
		   currentIndex = 0;
	   }
	   
	   assignAutoMode(currentIndex);
   }
}
