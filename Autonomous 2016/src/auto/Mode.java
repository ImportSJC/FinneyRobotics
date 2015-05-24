package auto;

public class Mode {
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
