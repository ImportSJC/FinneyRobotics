package general;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Jaguar;

public class MotorController {
	private static boolean useTalon = true; // if false all motor controllers will be instantiated as jaguars instead of talons
	private boolean reverse = false; // reverse the motor direction, used to ensure that a positive number results in a rotation that makes sense

	private CANTalon talon;
	private Jaguar jaguar;

	private int myID = 0;

	public MotorController(int ID) {
		myID = ID;

		initMotorController();
	}

	public MotorController(int ID, boolean reverseMotor) {
		myID = ID;
		reverse = reverseMotor;

		initMotorController();
	}

	private void initMotorController() {
		if (useTalon) {
			talon = new CANTalon(myID);
			enableCurrentLimit(true);
			setCurrentLimit(35);
		} else {
			jaguar = new Jaguar(myID);
		}
	}

	public void set(double speed) {
		if (useTalon) {
			if (reverse) {
				talon.set(-speed); // TODO ensure that a -0 value won't mess this up
			} else {
				talon.set(speed);
			}
		} else {
			if (reverse) {
				jaguar.set(-speed);
			} else {
				jaguar.set(speed);
			}
		}
	}

	public double getPosition() {
		if (useTalon) {
			// return talon.getPosition();
			return talon.getEncPosition();
		} else {
			return jaguar.getPosition();
		}
	}

	public void setPosition(int position) {
		if (useTalon) {
			talon.setEncPosition(position);
		} else {
			jaguar.setPosition(position);
		}
	}

	public double getVelocity() {
		if (useTalon) {
			return talon.getEncVelocity();
		}

		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputCurrent() {
		if (useTalon) {
			return talon.getOutputCurrent();
		}

		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputVoltage() {
		if(useTalon){
			return talon.getBusVoltage();
		}
		
		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public void enableBrakeMode(boolean value) {
		if (useTalon) {
			talon.enableBrakeMode(value);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void enableCurrentLimit(boolean enable) {
		if (useTalon) {
			talon.EnableCurrentLimit(enable);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void setCurrentLimit(int amps) {
		if (useTalon) {
			talon.setCurrentLimit(amps);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void setControlMode(TalonControlMode myMode) {
		if (useTalon) {
			talon.changeControlMode(myMode);
		}
	}
}
