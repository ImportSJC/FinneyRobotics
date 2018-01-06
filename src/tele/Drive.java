package tele;

import com.ctre.CANTalon;

public class Drive {
	private CANTalon frontLeft;
	private CANTalon frontRight;
	private CANTalon backLeft;
	private CANTalon backRight;
	
	/**
	 * Instantiate a new drive base with four motors, one in each corner of the bot
	 * @param frontLeft
	 * @param frontRight
	 * @param backLeft
	 * @param backRight
	 */
	public Drive(CANTalon frontLeft, CANTalon frontRight, CANTalon backLeft, CANTalon backRight){
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;
	}
	
	
}
