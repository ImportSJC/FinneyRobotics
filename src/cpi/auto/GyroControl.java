package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroControl {
	private static Gyro myGyro;
	
	public static boolean gyroLoaded = true;
	public GyroControl(int myChannel){
		try{
//			myGyro = new ADXRS450_Gyro();
			System.out.println("Initing gyro " + myChannel);
			myGyro = new AnalogGyro(myChannel);
			myGyro.reset();
			System.out.println("GYRO CONTROL CONSTRUCTOR");
		}catch(Exception e){
			System.out.println("Onboard Gyro failed to load!");
			gyroLoaded = false;
		}
	}
	
    public void Init(){
    	System.out.println("Gyro Init");
        
    }
    
    public void reset(){
    	if(gyroLoaded){
    		System.out.println("RESET GYRO");
    		myGyro.reset();
    	}
    }
    
    public double getAngle(){
    	if(gyroLoaded){
    		return myGyro.getAngle();
    	}
    	return 0;
    }
    
    public double getRate(){
    	if(gyroLoaded){
    		return myGyro.getRate();
    	}
    	return 0;
    }
}
