package org.usfirst.frc.team1405.robot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.*;

public class TestSimpleMultiMotorPWM {
	static NetworkTable settings;
	static Jaguar motor8 ;
	static Jaguar motor9 ;
	static double speed8;
	static double speed9;
	static boolean isNotFirstInit;
	static Timer timer=new Timer();
	
	static  String SHOOTER_KEY="Motor speed PWM 8 (Shooter)";		
	static  String GATE__KEY="Motor speed PWM 9 (Gate)";
	static  String GATE__ON_TIME="Motor speed PWM 9 (Gate) On Time";
	static  String GATE__OFF_TIME="Motor speed PWM 9 (Gate) Off Time";
	static String ENABLE="Enable";
	static String ENABLE_TIMED_GATE="Enable Timed Gate";
	static boolean isGateOn;
	
    
	public static void robotInit(){
		timer=new Timer();
		timer.start();
    	settings =NetworkTable.getTable("Test Beds/Motor PWM Controls 8 & 9 ( set values between -1 and 1)");
    	if(!settings.containsKey(SHOOTER_KEY))settings.putNumber(SHOOTER_KEY, 0.25);
    	if(!settings.containsKey(GATE__KEY))settings.putNumber(GATE__KEY, 0.25);
    	
    	if(!settings.containsKey(GATE__ON_TIME))settings.putNumber(GATE__ON_TIME, 0.25);    	
    	if(!settings.containsKey(GATE__OFF_TIME))settings.putNumber(GATE__OFF_TIME, 0.25);

    	settings.putBoolean(ENABLE, false);
    	settings.putBoolean(ENABLE_TIMED_GATE, false);
		
	}
	
    public static void testInit(){
    	if(isNotFirstInit)return;
    	isNotFirstInit=true;
    	motor8 = new Jaguar(8);
    	motor9 = new Jaguar(9);
    	System.out.println("testInit");
    }
    public static void testPeriodic() {
    	if(!settings.getBoolean(ENABLE, false)){
        	motor8.set(0);
        	motor9.set(0);
    		return;
    	}
    	motor8.set(settings.getNumber(SHOOTER_KEY, 0.25));
    	if(settings.getBoolean(ENABLE_TIMED_GATE, false)){
    		if((!isGateOn)&&timer.hasPeriodPassed(settings.getNumber(GATE__ON_TIME, 0.25))){
    	    	motor9.set(0.0);
    	    	isGateOn=true;
    		}
    		
    		if((isGateOn)&&timer.hasPeriodPassed(settings.getNumber(GATE__OFF_TIME, 0.25))){
    	    	motor9.set(settings.getNumber(GATE__KEY, 0.25));
    	    	isGateOn=false;
    			
    		}
    		
    	}else{
        	motor9.set(settings.getNumber(GATE__KEY, 0.25));
    	}
    }
    
    public static void disabledInit(){
    	testInit();
    	settings.putBoolean(ENABLE, false);
    	motor8.set(0);
    	motor9.set(0);
    }
}
