package cpi.outputDevices;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Jaguar;

//import edu.wpi.first.wpilibj.CANJaguar;

public class MotorController {
	private boolean useTalon = true;
	
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
//			return talon.getPosition();
			return talon.getEncPosition();
		}else{
			return jaguar.getPosition();
		}
	}
	
	public void setPosition(int position){
		if(useTalon){
			talon.setEncPosition(position);
		}else{
			jaguar.setPosition(position);
		}
	}
	
	public double  getVelocity(){
		System.out.println("Get Velocity called");
		if(useTalon){
			return talon.getEncVelocity();
		}
		
		return 0;
	}
	
	public double getOutputCurrent(){
		if(useTalon){
			return talon.getOutputCurrent();
		}
		
		return 0;
	}
	
	public void enableBrakeMode(boolean value){
		if(useTalon){
			talon.enableBrakeMode(value);
		}
	}
	
	public void EnableCurrentLimit(boolean enable){
	  if(useTalon){
        	talon.EnableCurrentLimit(enable);
        }
	}
	
	public void setCurrentLimit(int amps){
	  if(useTalon){
			talon.setCurrentLimit(amps);
		}
	}
	    
}
