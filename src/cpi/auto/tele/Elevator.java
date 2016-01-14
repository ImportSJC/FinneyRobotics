package cpi.auto.tele;

import cpi.CANTalon;

//import edu.wpi.first.wpilibj.CANTalon;

public class Elevator {
	public static final CANTalon elevatorMotor1 = CANTalon.getInstance("/Autonomous/Elevator", "mOTOR #1", 1);//bottom limit switch
	public static final CANTalon elevatorMotor2 =  CANTalon.getInstance("/Autonomous/Elevator", "mOTOR #2", 2);
}
