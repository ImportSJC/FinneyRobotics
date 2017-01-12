package cpi.auto.inputDevices;

import com.ctre.CANTalon;

import cpi.auto.SuperClass;

public class LimitSwitch extends SuperClass{
	private boolean targetPosition = true;
	
	CANTalon myTalon;
	
	public LimitSwitch(){
		//Assign your Talon to the myTalon variable
	}
	
	@Override 
	public boolean check(){
		//TODO: change this so it looks at only fwd or only rev depending on the limit switch location
		if(myTalon.isFwdLimitSwitchClosed() == targetPosition /*|| myTalon.isRevLimitSwitchClosed() == targetPosition*/){return true;}
		return false;
	}
}
