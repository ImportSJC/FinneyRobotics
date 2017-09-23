package cpi.auto;

import org.usfirst.frc.team1405.robot.GearControl;

public class Auto_Gear extends SuperClass{
	private boolean ejectGear = false;
	private boolean dropGear = false;
	
	public Auto_Gear(boolean ejectGear, boolean dropGear){
		this.ejectGear = ejectGear;
		this.dropGear = dropGear;
	}
	
	@Override
	public void start(){
		if(ejectGear){
			GearControl.TeleopPeriodic(false, true, false, false, false, false);
		}else if(dropGear){
			GearControl.TeleopPeriodic(false, false, true, false, false, false);
		}
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	private void stopMotors(){
		//reset motor speeds
		GearControl.TeleopPeriodic(false, false, false, false, false, false);
		GearControl.stopMotors();
		AutoOutputs.reset_Drive();
	}
}
