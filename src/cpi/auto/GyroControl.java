package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;

public class GyroControl {
	private static GyroBase myGyro;
//	private GyroBase myGyro;
	
	public GyroControl(){
		Init();
	}
	
    public void Init(){
    	System.out.println("Gyro Init");
    	myGyro = new ADXRS450_Gyro();
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
    	myGyro = null;
    }
}
