package cpi.outputDevices;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

//import edu.wpi.first.wpilibj.CANJaguar;

public class MotorController {
	private boolean useTalon = true;
	
	private CANTalon talon;
	private Jaguar jaguar;
	
	//Current logging
	NetworkTable settings;
	private int driveMotorCurrentArrayIndex = 0;
	private double[] driveMotorCurrentArray = new double[1000];
	private boolean writeToFile = false;
	private int myID = 0;

	public MotorController(int ID){
		myID = ID;
		
		if(this.useTalon){
			talon = new CANTalon(ID);
		}else{
			jaguar = new Jaguar(ID);
		}
		
		settings = NetworkTable.getTable("Motor_Current_Output");
		settings.putString("MotorCurrent"+myID, "");
		driveMotorCurrentArrayIndex = 0;
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
	
	public void driveMotorCurrentUpdate(){
		System.out.println("index: " + driveMotorCurrentArrayIndex + " length: "  + driveMotorCurrentArray.length);
		if(driveMotorCurrentArrayIndex < driveMotorCurrentArray.length){
			driveMotorCurrentArray[driveMotorCurrentArrayIndex] = getOutputCurrent();
			driveMotorCurrentArrayIndex++;
		}else if (!writeToFile){
			String tmp = "";
			for(int i = 0; i<driveMotorCurrentArray.length; i++){
				tmp+=driveMotorCurrentArray[i]+",";
			}
			settings.putString("MotorCurrent"+myID, tmp);
			writeToFile = true;
		}
		
		//output to a file, .csv
	}
	    
}
