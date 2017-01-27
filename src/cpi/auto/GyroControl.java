package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;

public class GyroControl {
	private static GyroBase myGyro = new ADXRS450_Gyro();;
//	private GyroBase myGyro;
	
	public GyroControl(){
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
