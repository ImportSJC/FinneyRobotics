package cpi.auto;

import org.usfirst.frc.team1405.robot.GearControl;

public class Auto_Gear extends SuperClass{
	private boolean ejectGear = false;
	
	public Auto_Gear(boolean ejectGear){
		this.ejectGear = ejectGear;
	}
	
	@Override
	public void start(){
		if(ejectGear){
			GearControl.TeleopPeriodic(false, true, false, false);
		}
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	private void stopMotors(){
		//reset motor speeds
		AutoOutputs.reset_Drive();
	}
}
