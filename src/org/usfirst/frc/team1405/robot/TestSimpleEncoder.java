package org.usfirst.frc.team1405.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.*;

public class TestSimpleEncoder {
	static NetworkTable settings;
	static Encoder Encoder_0_1 ;
	static Encoder Encoder_2_3 ;
	static double speed8;
	static double speed9;
	static boolean isNotFirstInit;
	public static void robotInit(){

    	settings =NetworkTable.getTable("Test Beds/Monitor two encoders (channels 0,1 and 2,3");
    	settings.putBoolean("Enable", false);
    	settings.putNumber("Encoder_0_1",  0.0);	
    	settings.putNumber("Encoder_2_3",  0.0);
		
	}
    public static void testInit(){
    	Encoder_0_1.free();
    	Encoder_2_3.free();
    	if(isNotFirstInit)return;
    	isNotFirstInit=true;
    	Encoder_0_1 = new Encoder(0,1);
    	Encoder_2_3 = new Encoder(2,3);
    }
    public static void testPeriodic() {
    	if(!settings.getBoolean("Enable", false))return;
    	
    	settings.putNumber("Encoder_0_1",  Encoder_0_1.getRaw());	
    	settings.putNumber("Encoder_2_3",  Encoder_2_3.getRaw());
    }
    
    public static void disabledInit(){
    	Encoder_2_3.free();
    	Encoder_0_1.free();
    	settings.putBoolean("Enable", false);
    }
}
