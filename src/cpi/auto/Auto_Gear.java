package cpi.auto;

import org.usfirst.frc.team1405.robot.GearControl;

public class Auto_Gear extends SuperClass{
	private boolean dropGear = false;
	
	public Auto_Gear(boolean dropGear){
		this.dropGear = dropGear;
	}
	
	@Override
	public void start(){
		if(dropGear){
			GearControl.TeleopPeriodic(false, false, true, false);
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
