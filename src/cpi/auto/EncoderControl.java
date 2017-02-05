package cpi.auto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderControl {
	//old base
	private static final double WHEEL_DIAMETER = 4; // wheel diameter in inches
	
	//new base
	//	private static final double WHEEL_DIAMETER = 3.875; // wheel diameter in inches
	
	private static final double COUNTS_PER_ROTATION = 360; //encoder counts per rotation of the wheel
	private static final double fullTurnCount = 5784; // (should be 3243) the number of encoder counts it takes to turn the robot a full rotation
	private static final double countOvershoot = 0; // the number of counts that the robot overshoots by (at .5 speed)
	
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
        resetAll();
    }
    
    public void resetAll(){
    	if(myEncoder != null){
    		myEncoder.reset();
    	}
    }
    
    public double getCount()
    {
    	if(myEncoder != null){
    		return myEncoder.get();
    	}
    	
    	return 0;
    }
    
    private static double getCircumference(double diameter){
    	return diameter*Math.PI;
    }
    
    private static double convertCountToDistance(double counts){
//    	System.out.println("Circ: " + getCircumference(WHEEL_DIAMETER) + "counts/cpr: " + (counts/COUNTS_PER_ROTATION));
    	return (getCircumference(WHEEL_DIAMETER)*(counts/COUNTS_PER_ROTATION))/(22.0/12.0);
    }
    
    public double getDistance(){
//    	System.out.println("Count: " + getCount() + " distance: " + convertCountToDistance(getCount()));
    	return convertCountToDistance(getCount());
    }
    
    public double getRate(){
    	if(myEncoder != null){
    		return myEncoder.getRate();
    	}
    	
    	return 0;
    }
    
    public static double convertAngleToCount(double angle){
    	return (fullTurnCount/(360/angle));
    }
    
    public void free(){
    	if(myEncoder != null){
    		myEncoder.free();
    	}
    	input1.free();
    	input2.free();
    }
}
