package cpi.auto;

import edu.wpi.first.wpilibj.AnalogGyro;

public class GyroControl {
	private AnalogGyro myGyro;
	public GyroControl(int myChannel){
		myGyro = new AnalogGyro(myChannel);
	}
	
    public void Init(){
    	System.out.println("Gyro Init");
        
    }
    
    public void resetAll(){
		myGyro.reset();
    }
    
    public double getAngle()
    {
    	return myGyro.getAngle();
    }
    
    public double getRate(){
    	return myGyro.getRate();
    }
}
