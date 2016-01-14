package cpi.auto.inputDevices;

import cpi.auto.SuperClass;

public class Time extends SuperClass{
	private long startTime;
	private double secondsElapsed = 0.0;
	
	private double endTimeValue;
	
	public Time(double value){
		endTimeValue = value;
	}
	
	@Override
	public void start(){
		secondsElapsed = 0;
		startTime = System.nanoTime();
	}
	
	@Override 
	public boolean check(){
		secondsElapsed = (double)(System.nanoTime()-startTime) / 1000000000.0;
		if(secondsElapsed >= endTimeValue){
			return true;
		}
		return false;
	}
}
