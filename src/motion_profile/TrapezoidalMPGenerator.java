package motion_profile;

import logging.SimpleLogger;

/**
 * Generate a customized MP to drive straight.
 * @author team1405
 *
 */
public class TrapezoidalMPGenerator {
	
	private final int TIME_PER_CYCLE = 10; //in ms
	private final double VELOCITY = 5.0;//in ft/s
	private final double ACCELERATION = 10.0;//in ft/s^2
	
	private double time;
	private double distance;
	
	double[][] mp;
	
	//remove these once you figure it all out
	private final double accelerationIncrement = 0.015; //ft to increment while accelerating
	private final double velocityIncrement = 0.05; //ft to increment while at max velocity
	
	public TrapezoidalMPGenerator(double distance, double time) {
		this.time = time;
		this.distance = distance;
		this.mp = new double[totalCycles()][3];
		
		if(timeToMaxVelocity() > time){
			SimpleLogger.log("ERROR: Time to short to reach distance specified.");//increase time or max velocity
		}
		
		SimpleLogger.log("timeToMaxVel: " + timeToMaxVelocity());
		SimpleLogger.log("accelCyclDist: " + accelerationCycleDistance() + " velCyclDist: " + velocityCycleDistance());
		SimpleLogger.log("velCycles: " + cyclesAtMaxVelocity() + " accelCycles: " + cyclesToMaxVelocity() + " total: " + totalCycles());
		
	}
	
	public double[][] generate(){
		double currentPosition = 0;
		System.out.println("total cycles: " + totalCycles());
		for(int i = 0; i < totalCycles(); i++){
			if(i < cyclesToMaxVelocity()){
				currentPosition = currentPosition + accelerationCycleDistance();
			}else if(i >= cyclesToMaxVelocity() && i < cyclesToMaxVelocity()+cyclesAtMaxVelocity()){
				currentPosition = currentPosition + velocityCycleDistance();
			}else if(i >= cyclesToMaxVelocity()+cyclesAtMaxVelocity()){
				currentPosition = currentPosition + accelerationCycleDistance();
			}
			mp[i] = createDataPoint(currentPosition);
		}
		
		return mp;
	}
	
	public void print(){
		System.out.println("mp.length: " + mp.length);
		for(int i = 0; i < mp.length; i++){
			System.out.println("mp position: " + mp[i][0]);
		}
	}
	
	private double timeToMaxVelocity(){
		return VELOCITY / ACCELERATION;
	}
	
	private int cyclesToMaxVelocity(){
//		System.out.println("6: " + timeToMaxVelocity() / (double)(TIME_PER_CYCLE*0.001));
		return (int)Math.round(timeToMaxVelocity() / (double)(TIME_PER_CYCLE*0.001));
	}
	
	private double distanceToMaxVelocity(){
//		System.out.println("1: " + (VELOCITY / 2.0));
//		System.out.println("2: " + timeToMaxVelocity());
//		System.out.println("3: " + (VELOCITY / 2.0) * timeToMaxVelocity());
		return (VELOCITY / 2.0) * timeToMaxVelocity();
	}
	
	private double accelerationCycleDistance(){
//		System.out.println("4: " + (double)cyclesToMaxVelocity());
//		System.out.println("5: " + (distanceToMaxVelocity() / (double)cyclesToMaxVelocity()));
		return distanceToMaxVelocity() / (double)cyclesToMaxVelocity();
//		return accelerationIncrement;
	}
	
	private int cyclesAtMaxVelocity(){
		return totalCycles() - (cyclesToMaxVelocity() * 2);
	}
	
	private double distanceAtMaxVelocity(){
		return distance - (distanceToMaxVelocity() * 2);
	}
	
	private double velocityCycleDistance(){
		return distanceAtMaxVelocity() / (double)cyclesAtMaxVelocity();
//		return velocityIncrement;
	}
	
	private int totalCycles(){
		return (int)Math.round(time/(double)(TIME_PER_CYCLE*0.001));
	}
	
	//TODO add velocity calculations
	private double[] createDataPoint(double position){
		double[] dataPoint = new double[3];
		dataPoint[0] = position;
		dataPoint[1] = 0; //velocity
		dataPoint[2] = TIME_PER_CYCLE;
		
		return dataPoint;
	}
}
