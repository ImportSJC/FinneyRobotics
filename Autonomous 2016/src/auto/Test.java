package auto;

import auto.Autonomous.Modes;
import auto.Mode;

public class Test {
	public static Mode mode0;
	
	public static void main(String[] args){
		Mode mode1 = new Mode("Test");
		Mode mode2 = new Mode("Finney");
		Mode mode3 = new Mode("Robotics");
		
		//////////////////////////////
		
		Modes.add(mode1);
		Modes.add(mode2);
		Modes.add(mode3);
		
		//////////////////////////////
		
		Modes.get("Test");
		RobotInit();
	}
	
	public static void RobotInit(){
		Autonomous.currentMode.RobotInit();
		
		AutoInit();
	}
	
	public static void AutoInit(){
		Autonomous.currentMode.AutoInit();
		
		AutoPeriodic();
	}
	
	public static void AutoPeriodic(){
		while(true){
			Autonomous.currentMode.AutoPeriodic();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
