package cpi.testBeds;
import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.networktables.*;

public class TestSimpleEncoder {
	static NetworkTable settings;
	static EncoderControl Encoder_0_1 ;
	static EncoderControl Encoder_2_3 ;
	static double speed8;
	static double speed9;
	static boolean isNotFirstInit;
	public static void robotInit(){

    	settings =NetworkTable.getTable("Test Beds/Monitor two encoders (channels 0,1 and 2,3");
    	settings.putBoolean("Enable", false);
    	settings.putNumber("Encoder_0_1 speed",  0.0);	
    	settings.putNumber("Encoder_2_3 speed",  0.0);
    	settings.putNumber("Encoder_0_1 position",  0.0);	
    	settings.putNumber("Encoder_2_3 position",  0.0);
		
	}
    public static void testInit(){
    	if(isNotFirstInit)return;
    	isNotFirstInit=true;
    	Encoder_0_1 = new EncoderControl(0,1);
    	Encoder_0_1.Init();
    	Encoder_2_3 = new EncoderControl(2,3);
    	Encoder_2_3.Init();
    }
    public static void testPeriodic() {
    	if(!settings.getBoolean("Enable", false))return;
    	settings.putNumber("Encoder_0_1 position",  Encoder_0_1.getPos());	
    	settings.putNumber("Encoder_2_3 position",  Encoder_2_3.getPos());
    	settings.putNumber("Encoder_0_1 speed",  Encoder_0_1.getSpeed());	
    	settings.putNumber("Encoder_2_3 position",  Encoder_2_3.getSpeed());
    }
    
    public static void disabledInit(){
    	settings.putBoolean("Enable", false);
    }
}
