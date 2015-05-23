package auto;

import java.util.Hashtable;

public class Autonomous {
	public static Mode currentMode;
	public static Hashtable<String, Mode> modeTable = new Hashtable<String, Mode>();
	
	public static class Modes
	{
		public static void add(Mode newMode){
			modeTable.put(newMode.modeName, newMode);
			//currentMode = newMode;
		}
		
		public static void get(String findName){
			currentMode = modeTable.get(findName);
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
			System.out.println(this.modeName + " - RobotInit");
		}
		
		public void AutoInit(){
			System.out.println(this.modeName + " - AutoInit");
		}
		
		public void AutoPeriodic(){
			System.out.println(this.modeName + " - AutoPeriodic");
		}
	}
}
