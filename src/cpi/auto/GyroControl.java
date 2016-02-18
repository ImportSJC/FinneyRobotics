package cpi.auto;

import edu.wpi.first.wpilibj.AnalogGyro;

public class GyroControl {
	private AnalogGyro myGyro;
	
	private boolean gyroLoaded = true;
	public GyroControl(int myChannel){
		try{
			System.out.println("GYRO CONTROL CONSTRUCTOR");
			myGyro = new AnalogGyro(myChannel);
		}catch(Exception e){
			System.out.println("Gyro " + myChannel + " failed to load!");
			gyroLoaded = false;
		}
	}
	
    public void Init(){
    	System.out.println("Gyro Init");
        
    }
    
    public void resetAll(){
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
}
