package cpi;

import cpi.Interface.*;

public class Elevator {
	public Elevator(String name){
		this.name=name;
		input=new DoubleInput(name,"Elevator control",0.0);
		motor1=cpi. CANTalon.getInstance(name,"Motor #1",9);
		motor2=cpi. CANTalon.getInstance(name,"Motor #2",10);
		enableSecondMotor= new SetBoolean(name,"#2 motor is enabled",true);
	}
	
	public void robotInit(){
		
	}
	
	public void teleopPeriodic(){
		motor1.set(input.Value());	
		if(enableSecondMotor.Value())motor2.set(input.Value());	
	}

	public void autonomousPeriodic(){
		teleopPeriodic();
	}
	public void testPeriodic(){
		teleopPeriodic();
	}

	CANTalon motor1;
	CANTalon motor2;
	DoubleInput input;
	String name;
	SetBoolean enableSecondMotor;
}
