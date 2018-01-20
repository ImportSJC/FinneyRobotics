package general;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Jaguar;

public class MotorController {
	private final static boolean USE_TALON = true; // if false all motor controllers will be instantiated as jaguars instead of talons
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
		if (USE_TALON) {
			talon = new CANTalon(myID);
			enableCurrentLimit(true);
			setCurrentLimit(35);
		} else {
			jaguar = new Jaguar(myID);
		}
	}

	public void set(double speed) {
		if (USE_TALON) {
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
		if (USE_TALON) {
			// return talon.getPosition();
			return talon.getEncPosition();
		} else {
			return jaguar.getPosition();
		}
	}

	public void setPosition(int position) {
		if (USE_TALON) {
			talon.setEncPosition(position);
		} else {
			jaguar.setPosition(position);
		}
	}

	public double getVelocity() {
		if (USE_TALON) {
			return talon.getEncVelocity();
		}

		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputCurrent() {
		if (USE_TALON) {
			return talon.getOutputCurrent();
		}

		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputVoltage() {
		if(USE_TALON){
			return talon.getBusVoltage();
		}
		
		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public void enableBrakeMode(boolean value) {
		if (USE_TALON) {
			talon.enableBrakeMode(value);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void enableCurrentLimit(boolean enable) {
		if (USE_TALON) {
			talon.EnableCurrentLimit(enable);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void setCurrentLimit(int amps) {
		if (USE_TALON) {
			talon.setCurrentLimit(amps);
		}
	}

	/**
	 * Talon ONLY
	 */
	public void setControlMode(TalonControlMode myMode) {
		if (USE_TALON) {
			talon.changeControlMode(myMode);
		}
	}
}
