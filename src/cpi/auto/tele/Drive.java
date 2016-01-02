package cpi.auto.tele;

import cpi.CANTalon;
import cpi.auto.AutoInputs;
//import edu.wpi.first.wpilibj.CANTalon;

public class Drive {
	/*
	public static final CANTalon leftMotor1 = new CANTalon(3);//left encoder
	public static final CANTalon leftMotor2 = new CANTalon(4);
	public static final CANTalon rightMotor1 = new CANTalon(5);//right encoder
	public static final CANTalon rightMotor2 = new CANTalon(6);
	*/
	public static final CANTalon leftMotor1 = CANTalon.getInstance("/Autonomous/dRIVE", "Left Motor #1", 3);//left encoder
	public static final CANTalon leftMotor2 = CANTalon.getInstance("/Autonomous/Drive", "Left Motor #2", 4);;
	public static final CANTalon rightMotor1 = CANTalon.getInstance("/Autonomous/Drive", "Right Motor #1", 5);;//right encoder
	public static final CANTalon rightMotor2 = CANTalon.getInstance("/Autonomous/Drive", "Right Motor #2", 6);;
	public void robotInit(){
		
	}
	
	public void autoInit(){
		AutoInputs.myGyro.reset();
		AutoInputs.resetEncoders();
	}
	
	public void teleopPeriodic(){
		System.out.println("Gyro Angle: " + AutoInputs.myGyro.getAngle());
		System.out.println("Encoder Count: " + AutoInputs.getEncoder());
	}
}
