package cpi;

import java.util.ArrayList;

public class MySet {
	private static ArrayList<AutoMode> arraylist = new ArrayList<AutoMode>();
	public static void addAutoMode(AutoMode automode){
		arraylist.add(automode);
	}
   public static void assignAutoMode(int index){
	   arraylist.get(index).assignAutoMode();
   }
}
