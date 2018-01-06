package auto;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroControl implements Gyro{
	private Gyro myGyro;
	public GyroControl(int myChannel)
	{	
		if (myChannel == 0){ myGyro = AutoInputs.myGyro; }
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

    /**
     * Init the gyro
     */
	@Override
	public void calibrate() {
		System.out.println("Gyro Init");
        myGyro.reset();
	}

	@Override
	public void reset() {
		myGyro.reset();
	}
}
