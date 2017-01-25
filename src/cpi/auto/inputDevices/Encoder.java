package cpi.auto.inputDevices;

import cpi.auto.AutoInputs;
import cpi.auto.EncoderControl;
import cpi.auto.SuperClass;

public class Encoder extends SuperClass{
	private boolean useCount = true;
	private boolean useAverage = true;
	
	private double startPosition;
	private double targetPosition;
	
	public Encoder(double value, boolean useCount){
		//use average of the two encoders
		this.useCount = useCount;
		targetPosition = value;
	}
	
	public Encoder(double turnAngle, double ignoredValue, boolean useCount){//TODO make encoders sense turning better than this....this is aweful
		useAverage = false;
		this.useCount = true;
		targetPosition = EncoderControl.convertAngleToCount(turnAngle);
	}
	
	@Override
	public void start(){
		AutoInputs.resetEncoders();
		if(useCount){
			if(useAverage){
				startPosition = AutoInputs.getEncoderCountAvg();
			}else{
				startPosition = AutoInputs.getSummedEncoderCount();
			}
		}else{
			if(useAverage){
				startPosition = AutoInputs.getEncoderDistanceAvg();
			}else{
				startPosition = AutoInputs.getLeftEncoderDistance();
			}
		}
		System.out.println("Encoder start position: " + startPosition);
	}
	
	@Override 
	public boolean check(){
		System.out.print("Encoder target position: " + targetPosition);
		if(useCount){
			if(useAverage){
				System.out.println(" - C:Y, A:Y - Current Position: " + AutoInputs.getEncoderCountAvg());
				if(targetPosition>0 && AutoInputs.getEncoderCountAvg() >= targetPosition){return true;}
				else if(targetPosition<0 && AutoInputs.getEncoderCountAvg() <= targetPosition){return true;}
				return false;
			}else{
				System.out.println(" - C:Y, A:N - Current Position: " + AutoInputs.getSummedEncoderCount());
				if(targetPosition>0 && AutoInputs.getSummedEncoderCount() >= targetPosition){return true;}
				else if(targetPosition<0 && AutoInputs.getSummedEncoderCount() <= targetPosition){return true;}
				return false;
			}
		}else{
			if(useAverage){
				System.out.println(" - C:N, A:Y - Current Position: " + AutoInputs.getEncoderDistanceAvg());
				if(targetPosition>0 && AutoInputs.getEncoderDistanceAvg() >= targetPosition){return true;}
				else if(targetPosition<0 && AutoInputs.getEncoderDistanceAvg() <= targetPosition){return true;}
				return false;
			}else{
				System.out.println(" - C:N, A:N - Current Position: " + AutoInputs.getLeftEncoderDistance() + " - Right: " + AutoInputs.getRightEncoderDistance());
				if(targetPosition>0 && AutoInputs.getLeftEncoderDistance() >= targetPosition){return true;}
				else if(targetPosition<0 && AutoInputs.getLeftEncoderDistance() <= targetPosition){return true;}
				return false;
			}
		}
	}
}
