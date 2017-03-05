package cpi.auto;

import cpi.Drive;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderControl {
	//old base
//	private static final double WHEEL_DIAMETER = 4; // wheel diameter in inches
//	private static final double COUNTS_PER_ROTATION = 360; //encoder counts per rotation of the wheel
	
	//new base
		private static final double WHEEL_DIAMETER = 3.93; // wheel diameter in inches, measured to be 3.875, 3.93 seems ideal when tested
		private static final double COUNTS_PER_ROTATION = 20; //encoder counts per rotation of the wheel
	

	private static final double fullTurnCount = 5784; // (should be 3243) the number of encoder counts it takes to turn the robot a full rotation
	private static final double countOvershoot = 0; // the number of counts that the robot overshoots by (at .5 speed)
	
	private Encoder myEncoder = null;
	private MotorController myTalon = null;
	
	private DigitalInput input1;
	private DigitalInput input2;
	
	private boolean reversed = false;
	
	//variables for my own rate implementation
	private double myRate;
	private double currentCount;
	
	public EncoderControl(int talonID, MotorController myTalon)
	{
		this.myTalon = myTalon;
	}
	
	public EncoderControl(int talonID, boolean reversed, MotorController myTalon)//TODO make this work
	{
		this.reversed = reversed;
		this.myTalon = myTalon;
	}
	
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
//    	System.out.println("Encoder Init");
        resetAll();
    }
    
    public void resetAll(){
    	if(myEncoder != null){
    		myEncoder.reset();
    	}else if(myTalon != null){
    		myTalon.setPosition(0);
    	}
    }

    public double getSpeed(){
    	return myEncoder.getRate();
    }
 
    public double getCount()
    {
    	if(myEncoder != null){
    		return myEncoder.get();
    	}else if(myTalon != null){
    		if(reversed){
    			return -myTalon.getPosition();
    		}
    		return myTalon.getPosition();
    	}
    	
    	return 0;
    }
    
    private static double getCircumference(double diameter){
    	return diameter*Math.PI;
    }
    
    private static double convertCountToDistance(double counts){
//    	System.out.println("Circ: " + getCircumference(WHEEL_DIAMETER) + "counts/cpr: " + (counts/COUNTS_PER_ROTATION) + " ratio: " + ((12/50)*(24/50)*(24/15)));
    	return (getCircumference(WHEEL_DIAMETER)*(counts/COUNTS_PER_ROTATION))*((12.0/50.0)*(24.0/50.0)*(24.0/15.0));
    }
    
    public double getDistance(){
//    	System.out.println("Count: " + getCount() + " distance: " + convertCountToDistance(getCount()));
    	return convertCountToDistance(getCount());
    }
    
    public void updateRate(){
//    	myRate = getCount() - currentCount;
//    	currentCount = getCount();
    }
    
    public double getRate(){
//    	System.out.println("Get Rate Called");
    	if(myEncoder != null){
    		return myEncoder.getRate();
    	}else if (myTalon != null){
//    		System.out.println("Get Rate using Velocity!");
    		if(reversed){
    			return -myTalon.getVelocity();
    		}
    		return myTalon.getVelocity();
    	}
    	return 0;
//    	return myRate;
    }
    
    public static double convertAngleToCount(double angle){
    	return (fullTurnCount/(360/angle));
    }
    
    public void free(){
    	if(myEncoder != null){
    		myEncoder.free();
    		input1.free();
        	input2.free();
    	}
    }
}
