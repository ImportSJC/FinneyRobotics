package cpi.auto.conditions;

import cpi.auto.SuperClass;

public class Not extends SuperClass{
	private SuperClass myObject;
	
	public Not (SuperClass myObject) {
		this.myObject = myObject;
	}
	
	@Override
	public void start(){
		myObject.start();
	}
	
	@Override
	public boolean check(){
		return !myObject.check();
	}
	
	@Override
	public void stop(){
		myObject.stop();
	}
}
