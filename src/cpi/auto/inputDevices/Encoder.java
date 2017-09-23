package cpi.auto.inputDevices;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.EncoderControl;
import cpi.auto.SuperClass;

public class Encoder extends SuperClass{
	private boolean useCount = true;
	private boolean useAverage = true;
	private boolean useRate = true;
	private boolean accurateDriving = true;
	
	private static final double MARGIN_OF_ERROR = 0.3;
	private static final double END_RATE = 200; // the rate the robot must be under to end the turn
	
	private double startPosition;
	private double targetPosition;
	private double targetAngle;
	
	public Encoder(double value, boolean useCount, boolean accurateDriving){
		//use average of the two encoders
		this.useCount = useCount;
		targetPosition = value;
		this.accurateDriving = accurateDriving;
	}
	
	public Encoder(double turnAngle, double ignoredValue, boolean useCount, boolean useRate){//TODO make encoders sense turning better than this....this is aweful
		useAverage = false;
		this.useCount = useCount;
		this.useRate = useRate;
		this.targetAngle = turnAngle;
		targetPosition = EncoderControl.convertAngleToCount(turnAngle);
	}
	
	@Override
	public void start(){
		AutoInputs.resetEncoders();
		AutoOutputs.startPID_Drive(targetPosition, accurateDriving);
//		if(useCount){
//			if(useAverage){
//				startPosition = AutoInputs.getEncoderCountAvg();
//			}else{
//				startPosition = AutoInputs.getSummedEncoderCount();
//			}
//		}else{
//			if(useAverage){
//				startPosition = AutoInputs.getEncoderDistanceAvg();
//			}else{
//				startPosition = AutoInputs.getLeftEncoderDistance();
//			}
//		}
		
		
	}
	
	private boolean check(double input){
//		if(atTargetAngle(input) && checkRate()){return true;}
		if( AutoOutputs.checkPID_Drive() ){return true;}
		
//		if(useAverage){
//			AutoOutputs.rampDrive_Encoder(targetPosition-input, targetPosition);
//		}else{
//			AutoOutputs.rampTurn_Encoder(targetPosition-input, targetPosition);
//		}
		
		return false;
	}
	
	private boolean atTargetAngle(double currentAngle){
		if(currentAngle>targetPosition-MARGIN_OF_ERROR && currentAngle<targetPosition+MARGIN_OF_ERROR){
			return true;
		}
		return false;
	}
	
	private boolean checkRate(){
		if((useRate && AutoInputs.getSummedEncoderRate()<END_RATE) || !useRate){return true;}
		return false;
	}
	
	@Override 
	public boolean check(){//TODO delete this and clean up class
//		System.out.print("Encoder target position: " + targetPosition);
		if(useCount){
			if(useAverage){
//				System.out.println(" - C:Y, A:Y - Current Position: " + AutoInputs.getEncoderCountAvg());
				return check(AutoInputs.getEncoderCountAvg());
			}else{
//				System.out.println(" - C:Y, A:N - Current Position: " + AutoInputs.getSummedEncoderCount());
				return check(AutoInputs.getSummedEncoderCount());
			}
		}else{
			if(useAverage){
//				System.out.println(" - C:N, A:Y - Current Position: " + AutoInputs.getEncoderDistanceAvg());
				return check(AutoInputs.getEncoderDistanceAvg());
			}else{
//				System.out.println(" - C:N, A:N - Current Position: " + AutoInputs.getLeftEncoderDistance() + " - Right: " + AutoInputs.getRightEncoderDistance());
				return check(AutoInputs.getLeftEncoderDistance());
			}
		}
	}
	
	@Override
	public void stop(){
		AutoOutputs.stopPID_Drive();
	}
}
