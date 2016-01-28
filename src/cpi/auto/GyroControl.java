package cpi.auto;

import edu.wpi.first.wpilibj.AnalogGyro;

public class GyroControl {
	private AnalogGyro myGyro;
	public GyroControl(int myChannel)
	{	
		if (myChannel == 0){ myGyro = AutoInputs.myGyro; }
	}
	
    public void Init(){
    	System.out.println("Gyro Init");
        myGyro.reset();
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
    
    public void free(){
    	myGyro.free();
    }
}
