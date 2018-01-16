package AutonomousControls;

import com.ctre.CANTalon;

public class Auto_LimitSwitch extends AutonomousControl{
	private boolean targetPosition = true;
	
	CANTalon switchController;
	
	public Auto_LimitSwitch(String limitSwitchLocation, CANTalon switchController){
		if(limitSwitchLocation.equalsIgnoreCase("bottom")){
			this.switchController = switchController;
		}
	}
	
	public Auto_LimitSwitch(String limitSwitchLocation, boolean targetPosition, CANTalon switchController){
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
