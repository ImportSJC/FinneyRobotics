package cpi.auto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderControl {
	private static final double WHEEL_DIAMETER = 4; // wheel diameter in inches
	private static final int COUNTS_PER_ROTATION = 360; //encoder counts per rotation of the wheel
	private static final double ROBOT_DIAMETER = 20.25; // the distance between the two sets of wheels on the robot
	private static final double fullTurnCount = 5600; // (should be 3243) the number of encoder counts it takes to turn the robot a full rotation
	private static final double countOvershoot = 45; // the number of counts that the robot overshoots by (at .5 speed)
	
	
	private Encoder myEncoder;
	
	private DigitalInput input1;
	private DigitalInput input2;
	
	public EncoderControl(int aChannel, int bChannel)
	{
		input1 = new DigitalInput(aChannel);
		input2 = new DigitalInput(bChannel);
		myEncoder = new Encoder(input1, input2);
	}
	
	public EncoderControl(int aChannel, int bChannel, boolean reverseEncoder)
	{
		input1 = new DigitalInput(aChannel);
		input2 = new DigitalInput(bChannel);
		myEncoder = new Encoder(input1, input2, reverseEncoder);
	}
	
    public void Init(){
    	System.out.println("Encoder Init");
        myEncoder.reset();
    }
    
    public void resetAll(){
		myEncoder.reset();
    }
    
    public double getCount()
    {
    	return myEncoder.get();
    }
    
    private static double getCircumference(double diameter){
    	return diameter*Math.PI;
    }
    
    private static double convertCountToDistance(double counts){
    	return (counts/COUNTS_PER_ROTATION)*getCircumference(WHEEL_DIAMETER);
    }
    
    public double getDistance(){
    	return convertCountToDistance(getCount());
    }
    
    public static double convertAngleToCount(double angle){
    	return (fullTurnCount/(360/angle))-countOvershoot;
    }
    
    public void free(){
    	myEncoder.free();
    	input1.free();
    	input2.free();
    }
}
