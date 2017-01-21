package cpi.auto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderControl {
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
    
    public double getPos()
    {
    	return myEncoder.get();
    }
    
    public void free(){
    	myEncoder.free();
    	input1.free();
    	input2.free();
    }
}
