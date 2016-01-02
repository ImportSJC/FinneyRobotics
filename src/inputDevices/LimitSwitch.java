package inputDevices;

import edu.wpi.first.wpilibj.CANTalon;
import auto.AutoOutputs;
import auto.SuperClass;

public class LimitSwitch extends SuperClass{
	private boolean targetPosition = true;
	
	CANTalon myTalon;
	
	public LimitSwitch(String limitSwitchLocation){
		if(limitSwitchLocation.equalsIgnoreCase("bottom")){
			myTalon = AutoOutputs.elevatorMotor1;
		}
	}
	
	public LimitSwitch(String limitSwitchLocation, boolean targetPosition){
		if(limitSwitchLocation.equalsIgnoreCase("bottom")){
			myTalon = AutoOutputs.elevatorMotor1;
		}
		
		this.targetPosition = targetPosition;
	}
	
	@Override 
	public boolean check(){
		//TODO: change this so it looks at only fwd or only rev depending on the limit switch location
		if(myTalon.isFwdLimitSwitchClosed() == targetPosition /*|| myTalon.isRevLimitSwitchClosed() == targetPosition*/){return true;}
		return false;
	}
}
