package cpi.auto.inputDevices;

import cpi.auto.SuperClass;

public class Time extends SuperClass{
	//check if time is elasped
	private long startTime;
	private double secondsElapsed = 0.0;
	
	private double endTimeValue;
	
	//Timer functionality
	private double stopTimeValue;
	
	public Time(double value){
		endTimeValue = value;
	}
	
	@Override
	public void start(){
		secondsElapsed = 0;
		startTime = System.nanoTime();
	}
	
	public double getTime(){
		return (double)(System.nanoTime()-startTime) / 1000000000.0;
	}
	
	public void stopTime(){
		stopTimeValue = getTime();
	}
	
	public double getStopTime(){
		return stopTimeValue;
	}
	
	@Override 
	public boolean check(){
		secondsElapsed = getTime();
		if(secondsElapsed >= endTimeValue){
			return true;
		}
		return false;
	}
}
