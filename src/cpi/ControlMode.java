package cpi;

public class ControlMode {
	private String         desc;
	
	public ControlMode(String desc) {
		this.desc   = desc;
	}
	
	public void assignControlMode(){
		Drive.controlStates = this;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void run(){
		
	}
}
