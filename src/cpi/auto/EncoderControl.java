package cpi.auto;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderControl {
	private Encoder myEncoder;
	public EncoderControl(int aChannel, int bChannel)
	{
		myEncoder = new Encoder(aChannel,bChannel);//TODO: set this up properly
//		myEncoder = new Encoder();//TODO: set this up properly
	}
	
    public void Init(){
    	System.out.println("Encoder Init");
        myEncoder.reset();
    }
    
    public void resetAll(){
		myEncoder.reset();
    }
    
    public double getSpeed(){
    	return myEncoder.getRate();
    }
    
    public double getPos()
    {
    	return myEncoder.get();
    }
    
    public void free(){
    	myEncoder.free();
    }
}
