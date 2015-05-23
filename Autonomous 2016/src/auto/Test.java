package auto;

import auto.Autonomous;
import auto.Autonomous.Modes;

public class Test {
	public static Autonomous.Mode mode0;
	
	public static void main(String[] args){
		Autonomous.Mode mode1 = new Autonomous().new Mode("Test");
		Autonomous.Mode mode2 = new Autonomous().new Mode("Finney");
//		Autonomous.Mode mode3 = new Autonomous().new Mode("Robotics");
		
		//////////////////////////////
		
		Modes.add(mode1);
		Modes.add(mode2);
//		Modes.add(mode3);
		
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
