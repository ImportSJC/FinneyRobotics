package subsystems_tele;

import MotorController.MotorController;
import MotorController.MotorControllerRamped;
import general.ButtonState;
import general.CustomXBox;
import general.NanoTimer;
import general.ServoController;
import sensors.CustomLimitSwitch;

//TODO add documentation to this class

public class CubeMovement {
	
	private MotorControllerRamped forklift;
	private MotorController cubeIntake1;
	private MotorController cubeIntake2;
	private MotorController retractIntake;
	
	private CustomXBox controller1;
	private CustomXBox controller2;
	
	private ServoController servo1;
	private ServoController servo2;

	private CustomLimitSwitch upSensor;
	private CustomLimitSwitch downSensor;
	
	final private double FORKLIFT_UP = 1.0;
	final private double FORKLIFT_DOWN = 0.5;
	final private double CUBE_IN = 1;
	final private double CUBE_OUT = -1;
	final private double CUBE_ARM = 0.1;
	final private double RETRACT_SPEED = 0.5;
	
	private boolean buttonPreviousState = false;
	private boolean retractIntakePosition = true;
	
	private ButtonState autoSecureCube = new ButtonState(false);
	private NanoTimer autoSecureTimer = new NanoTimer((long) 1e8);
	
	/**
	 * Instantiate the motor to use for the forklift and the motors for the cubeIntake mechanism
	 * @param controler1
	 * @param forklift
	 * @param cubeIntake1
	 * @param cubeIntake2
	 * @param retractIntake
	 */
	public CubeMovement(CustomXBox controller1, CustomXBox controller2, MotorControllerRamped forklift, 
			MotorController cubeIntake1,MotorController cubeIntake2, MotorController cubeArm1,
			MotorController retractIntake, ServoController servo1, ServoController servo2, 
			CustomLimitSwitch upSensor, CustomLimitSwitch downSensor){
		this.controller1 = controller1;
		this.controller2 = controller2;
		this.forklift = forklift;
		this.cubeIntake1 = cubeIntake1;
		this.cubeIntake2 = cubeIntake2;
		this.retractIntake = retractIntake;
		this.servo1 = servo1;
		this.servo2 = servo2;
		this.upSensor = upSensor;
		this.downSensor = downSensor;
	}
	
	public void forkLift(){
		if (controller2.leftStickYaxis() >= .1 && upSensor.getState()){
			setForkliftRamp(FORKLIFT_UP * controller2.leftStickYaxis());
		}else if(controller2.leftStickYaxis() <= -.1 && downSensor.getState()) { //TODO get rid of possible deadband here?
			setForkliftRamp(FORKLIFT_DOWN * controller2.leftStickYaxis());
		}else{
			forklift.resetRamp();
		}

		if (   (controller2.leftStickYaxis() >= .1)  && !upSensor.getState()
			|| (controller2.leftStickYaxis() <= -.1) && !downSensor.getState()) {
			controller2.leftRumble(1.0);
			controller2.rightRumble(1.0);
		} else {
			controller2.leftRumble(0.0);
			controller2.rightRumble(0.0);
		}
	}
	
	public void setForkliftRamp(double speed){
		if(speed == 0){
			forklift.resetRamp();
		}else{
			forklift.rampMotor(speed);
		}
	}
	
	public void setForklift(double speed){
		forklift.set(speed);
	}
	
	public void cubeIntakeMechanism(){
		if (controller1.leftTrigger() > 0){
//			SimpleLogger.log("left: " + controller1.leftTrigger());
			CubeIntake_Input();
		} else if(controller1.rightTrigger() > 0){
//			SimpleLogger.log("right: " + controller1.rightTrigger());
			CubeIntake_Output();
		} else{
			if (autoSecureCube.hasDeactivated()) {
				CubeIntake_Input();
				autoSecureTimer.resetTimer();
			} else if (autoSecureTimer.hasExpired()) {
				cubeSpinOff();
			}
		}
	}

	public void CubeIntake_Reset() {
		cubeIntake1.set(0);
		cubeIntake2.set(0);
	}

	public void CubeIntake_Output() {
		cubeIntake1.set(CUBE_OUT * controller1.rightTrigger());
		cubeIntake2.set(CUBE_OUT * controller1.rightTrigger());
	}

	public void CubeIntake_Input() {
		cubeIntake1.set(CUBE_IN * controller1.leftTrigger());
		cubeIntake2.set(CUBE_IN * controller1.leftTrigger());
	}
	
	public void setIntake(double speed){
		cubeIntake1.set(speed);
		cubeIntake2.set(speed);
	}
	
	public void cubeSpinMechanism(){
		if(controller1.leftTrigger() == 0 && controller1.rightTrigger() == 0 && !controller1.rightBumper() && !controller1.leftBumper()){
			if(controller1.aButton()){
				cubeSpinOn();
			}else{
				cubeSpinOff();
			}
		}
	}

	public void cubeSpinOff() {
		CubeIntake_Reset();
		autoSecureCube.setState(false);
		
	}

	public void cubeSpinOn() {
		cubeIntake1.set(CUBE_IN);
		cubeIntake2.set(CUBE_OUT);
		autoSecureCube.setState(true);
	}
	
	public void cubeArms(){
		if(controller1.leftTrigger() > 0){
			servo1.setServoPos(1);
			servo2.setServoPos(1);
		} else{
			servo1.setServoPos(0);
			servo2.setServoPos(0);
		}
		
	}
	
	public void retract(){
		if(controller1.rightBumper() && !buttonPreviousState){
			retractIntakePosition = !retractIntakePosition;
		}
		buttonPreviousState = controller1.rightBumper();
		
		//TODO retract/extend the intake based on the retract intake position variable until the mechanism hits a limit switch
	}
	
}
