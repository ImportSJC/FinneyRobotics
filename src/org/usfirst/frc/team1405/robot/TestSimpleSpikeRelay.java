package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class TestSimpleSpikeRelay {
	static NetworkTable settings;
	static boolean isNotFirstInit;
	static boolean enteredTestMode=false;
	static Relay relay;
	static String ENABLE="Enable";
	static String DIRECTION="Direction (true=CW : false=CCW)";
	static String TOGGLE="Toggle";
	static String CW_TIME="CW Time (sec)";
	static String CCW_TIME="CCW Time (sec)";
	static Timer timer=new Timer();
	static Relay.Value value;
    
	public static void robotInit(){
		
    	settings =NetworkTable.getTable("Test Beds/Relay");
    	settings.putBoolean(ENABLE, false);
    	settings.putBoolean(DIRECTION, true);

    	settings.putBoolean(TOGGLE, false);
    	settings.putNumber(CW_TIME, 5);
    	settings.putNumber(CCW_TIME, 5);
		
	}
	
    public static void testInit(){
    	LiveWindow.setEnabled(false);
    	enteredTestMode=true;
		relay=new Relay(0); 
		once=true;
		timer.start();
		value=Relay.Value.kForward;
  
    }
    static boolean once=true;

    public static void testPeriodic() {
    	if(!settings.getBoolean(ENABLE, false)){
        	relay.set(Relay.Value.kOff);
    		return;
    	}
    	if(settings.getBoolean(TOGGLE, true)){
    		
    		if(value==Relay.Value.kForward){
    			if(timer.hasPeriodPassed(settings.getNumber(CW_TIME, 5))){
    				value=Relay.Value .kReverse;
    			}
    			
    		}else{
    			if(timer.hasPeriodPassed(settings.getNumber(CCW_TIME, 5)))value=Relay.Value .kForward;
    			
    		}
	    	relay.set(value);
    		
    		
    	}else{
    	
    	if(settings.getBoolean(DIRECTION, true)){
        	relay.set(Relay.Value.kForward);
    		
    	}else{
    	relay.set(Relay.Value.kReverse);
    	}
    		
    	}
    }
    public static void disabledInit(){
		System.out.println("RC-disabledInit 0");
    	if(!enteredTestMode)return;
		System.out.println("RC-disabledInit 1");
    	enteredTestMode=false;
    	settings.putBoolean(ENABLE, false);
    	relay.set(Relay.Value.kOff);
    	relay.free();
    	timer.stop();
    }
    
    
}
