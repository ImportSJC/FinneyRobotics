package cpi.auto;

import edu.wpi.first.wpilibj.GyroBase;

public class GyroControl {
	private GyroBase myGyro;
	public GyroControl(int myChannel)
	{	
		if (myChannel == 0){ myGyro = AutoInputs.getGyro(); }
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
