package cpi.outputDevices;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Jaguar;

//import edu.wpi.first.wpilibj.CANJaguar;

public class MotorController {
	
	private boolean useTalon = false;
	
	private CANTalon talon;
	private Jaguar jaguar;
	
	public MotorController(int id){
		if(useTalon){
			talon = new CANTalon(id);
		}else{
			jaguar = new Jaguar(id);
		}
	}
	
	public void set(double speed){
		if(useTalon){
			talon.set(speed);
		}else{
			jaguar.set(speed);
		}
	}
	
	public double getPosition(){
		if(useTalon){
			return talon.getPosition();
		}else{
			return jaguar.getPosition();
		}
	}
	
	public void setPosition(double position){
		if(useTalon){
			talon.setPosition(position);
		}else{
			jaguar.setPosition(position);
		}
	}
	
	public void enableBrakeMode(boolean value){
		if(useTalon){
			talon.enableBrakeMode(value);
		}
	}
}
