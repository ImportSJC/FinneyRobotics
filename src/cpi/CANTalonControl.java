package cpi;

import org.usfirst.frc.team1405.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;

public class CANTalonControl {
	private CANTalon myCan;
	
	private boolean canTalonLoaded = true;
	
	public CANTalonControl(int myID){
		canTalonLoaded = !Robot.disableCANTalons;
		System.out.println("CanTalonLoaded: " + canTalonLoaded);
		if(canTalonLoaded){
			System.out.println("Loaded CanTalon: " + myID);
			myCan = new CANTalon(myID);
		}
	}
	
	public void set(double value){
		if(canTalonLoaded){
			myCan.set(value);
		}
	}
	
	public double getPosition(){
		if(canTalonLoaded){
			return myCan.getPosition();
		}
		return 0;
	}
	
	public void setPosition(double value){
		if(canTalonLoaded){
			myCan.setPosition(value);
		}
	}
	
	public void enableBrakeMode(boolean value){
		if(canTalonLoaded){
			myCan.enableBrakeMode(value);
		}
	}

}
