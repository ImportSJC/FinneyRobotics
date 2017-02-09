package cpi;

import cpi.auto.SuperClass;
import cpi.autoSupportClasses.AutonomousBase;

public class AutoMode {
	private SuperClass[][] matrix;
	private String         desc;
	
	public AutoMode(SuperClass[][] matrix, String desc) {
		this.matrix = matrix;
		this.desc   = desc;
	}
	
	public void assignAutoMode(){
		AutonomousBase.autoStates = matrix;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public SuperClass[][] getMatrix() {
		return this.matrix;
	}
}
