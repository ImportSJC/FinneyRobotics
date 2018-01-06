package inputDevices;

import com.ctre.CANTalon;

import auto.SuperClass;

public class LimitSwitch extends SuperClass{
	private boolean targetPosition = true;
	
	CANTalon switchController;
	
	public LimitSwitch(String limitSwitchLocation, CANTalon switchController){
		if(limitSwitchLocation.equalsIgnoreCase("bottom")){
			this.switchController = switchController;
		}
	}
	
	public LimitSwitch(String limitSwitchLocation, boolean targetPosition, CANTalon switchController){
		if(limitSwitchLocation.equalsIgnoreCase("bottom")){
			this.switchController = switchController;
		}
		
		this.targetPosition = targetPosition;
	}
	
	@Override 
	public boolean check(){
		//TODO: change this so it looks at only fwd or only rev depending on the limit switch location
		if(switchController.isFwdLimitSwitchClosed() == targetPosition /*|| myTalon.isRevLimitSwitchClosed() == targetPosition*/){return true;}
		return false;
	}
}
