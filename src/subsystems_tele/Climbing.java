package subsystems_tele;

import MotorController.MotorController;
import MotorController.MotorControllerRamped;
import general.CustomXBox;

public class Climbing {
	
	private MotorControllerRamped winch;
	private MotorController robotCarrier;
	private MotorController hooks;
	
	private CustomXBox controller;
	
	final private double WINCH_SPEED_UP = 1.0;
	final private double WINCH_SPEED_DOWN = -0.5;
	final private double ROBOT_CARRIER_SPEED = 0.5;
	final private double HOOKS_UP = 0.45;
	final private double HOOKS_DOWN = -0.20;

	public Climbing(CustomXBox controller, MotorControllerRamped winch, MotorController robotCarrier, MotorController hooks) {
		this.winch = winch;
		this.robotCarrier = robotCarrier;
		this.hooks = hooks;
		this.controller = controller;
	}
	
	/**
	 * Retract the winch for climbing when button is held
	 */
	public void winch() {
//		SimpleLogger.log("Winch is working");
		if (controller.yButton()) {
			winch.rampMotor(WINCH_SPEED_UP);
		}else if (controller.aButton()) {
			winch.set(WINCH_SPEED_DOWN);
		}else {
			winch.resetRamp();
		}	
	}
	
	/**
	 * Drop the forks that will carry the allied robots when we climb if button is held
	 */
	public void robotCarrier() {
		if (controller.rightTriggerPressed()) {
			robotCarrier.set(ROBOT_CARRIER_SPEED);
			//TODO add in limit switch to stop robot carrier from dropping
		}else {
			robotCarrier.set(0);
		}
	}
	
	/**
	 * Lifts and lowers the hooks for climbing when buttons are held
	 */
	public void hooks() {
		if (controller.directionalPadUp()) {
			hooks.set(HOOKS_UP);
		}else if (controller.directionalPadDown()) {
			hooks.set(HOOKS_DOWN);
		}else {
			hooks.set(0);
		}
	}
	
/**
 * If you want to find de wey my bruthe, you must use this method. *Click *Click *Click *Click
 */
	
//	public path showDeWey() {
//		return weyToDeQueen;
//	}
}
