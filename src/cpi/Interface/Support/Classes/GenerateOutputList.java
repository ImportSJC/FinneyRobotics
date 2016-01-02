package cpi.Interface.Support.Classes;

import cpi.SetString;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

public class GenerateOutputList {
	
	public GenerateOutputList(String pTable,SetString pInputLink, Vector<OutputDiscriptor> pVector){
		timer=new Timer();
	}
	public static void generateAll(){
		timer.cancel();
		timer.schedule(new TimerTask(){
			public void run(){
				
			}
	},1000);
		
	}
	void generateOneList(){
		
	}
	String table;
	SetString inputLink;
	Vector<OutputDiscriptor> outputDescriptors;
	static Timer timer;
	static Vector<GenerateOutputList> allLists;
}
