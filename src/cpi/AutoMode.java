package cpi;

import cpi.auto.SuperClass;

public class AutoMode {
	private SuperClass[][] matrix;
	private String         ID;
	private String         desc;
	public AutoMode(SuperClass[][] matrix, String ID, String desc) {
		this.matrix = matrix;
		this.ID     = ID;
		this.desc   = desc;
	}
	public String getID() {
		return this.ID;
	}
	public String getDesc() {
		return this.desc;
	}
	public SuperClass[][] getMatrix() {
		return this.matrix;
	}
}
