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
}
