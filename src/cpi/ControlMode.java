package cpi;

import java.util.concurrent.Callable;

public class ControlMode {
	private Callable<Void> 	   func;
	private String         desc;
	
	public ControlMode(Callable<Void> func, String desc) {
		this.func   = func;
		this.desc   = desc;
	}
	
	public void assignControlMode(){
		Drive.controlStates = func;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public Callable<Void> getFunc() {
		return this.func;
	}
}
