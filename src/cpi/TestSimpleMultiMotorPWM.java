package cpi;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.networktables.*;

public class TestSimpleMultiMotorPWM {
	static NetworkTable settings;
	static Jaguar motor8 ;
	static Jaguar motor9 ;
	static double speed8;
	static double speed9;
	static boolean isNotFirstInit;
    
	
    public static void testInit(){
    	if(isNotFirstInit)return;
    	isNotFirstInit=true;
    	settings =NetworkTable.getTable("Test Beds/Motor PWM Controls 8 & 9");
    	motor8 = new Jaguar(8);
    	motor9 = new Jaguar(9);
    	if(!settings.containsKey("Motor speed PWM 8"))settings.putNumber("Motor speed PWM 8", 0.25);
    	if(!settings.containsKey("Motor speed PWM 9"))settings.putNumber("Motor speed PWM 9", 0.25);
    	if(!settings.containsKey("Enable"))settings.putBoolean("Enable", false);
    }
    public static void testPeriodic() {
    	if(!settings.getBoolean("Enable", false)){
        	motor8.set(0);
        	motor9.set(0);
    		return;
    	}
    	motor8.set(settings.getNumber("Motor speed PWM 8", 0.25));
    	motor9.set(settings.getNumber("Motor speed PWM 9", 0.25));
    }
    
    public static void disabledInit(){
    	testInit();
    	settings.putBoolean("Enable", false);
    	motor8.set(0);
    	motor9.set(0);
    }
}
