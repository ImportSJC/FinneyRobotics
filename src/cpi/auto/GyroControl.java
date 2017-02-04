package cpi.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;

public class GyroControl {
	private static GyroBase myGyro = new ADXRS450_Gyro();;
//	private GyroBase myGyro;
	
	public GyroControl(){
	}
	
    public void resetAll(){
    	try {
    		myGyro.reset();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public double getAngle()
    {
    	try {
    		return myGyro.getAngle();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return 0;
    }
    
    public double getRate(){
    	try {
    		return myGyro.getRate();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return 0;
    }
    
    public void free(){
    	try {
    		myGyro.free();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	myGyro = null;
    }
}
