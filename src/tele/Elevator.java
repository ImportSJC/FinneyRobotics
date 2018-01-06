package tele;

import com.ctre.CANTalon;

public class Elevator {
	public static final CANTalon elevatorMotor1 = new CANTalon(1);//bottom limit switch
	public static final CANTalon elevatorMotor2 = new CANTalon(2);
}
