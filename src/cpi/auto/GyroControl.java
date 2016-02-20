package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class GyroControl {
	private ADXRS450_Gyro myGyro;
	
	private boolean gyroLoaded = true;
	public GyroControl(){
		try{
			myGyro = new ADXRS450_Gyro();
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
    
    public void free(){
    	myGyro.free();
    }
}
