package subsystems_tele;

import general.CustomXBox;
import general.MotorController;

//TODO add documentation to this class

public class CubeMovement {
	
	private MotorController forklift1;
	private MotorController forklift2;
	private MotorController cubeIntake1;
	private MotorController cubeIntake2;
	private MotorController retractIntake;
	
	private CustomXBox controller;
	
	final private double FORKLIFT_UP = 0.5;
	final private double FORKLIFT_DOWN = -0.5;
	final private double CUBE_IN = 0.5;
	final private double CUBE_OUT = -0.5;
	final private double RETRACT_SPEED = 0.5;
	
	private boolean buttonPreviousState = false;
	private boolean retractIntakePosition = true;
	
	/**
	 * Instantiate the 2 motors to use for the forklift
	 * @param controller
	 * @param forklift1
	 * @param forklift2
	 */
	public CubeMovement(CustomXBox controller, MotorController forklift1, MotorController forklift2,
			MotorController cubeIntake1, MotorController cubeIntake2, MotorController retractIntake){
		this.forklift1 = forklift1;
		this.forklift2 = forklift2;
		this.cubeIntake1 = cubeIntake1;
		this.cubeIntake2 = cubeIntake2;
		this.controller = controller;
		this.retractIntake = retractIntake;
		
	}
	
	public void forkLift(){
		if (controller.yButton()){
			forklift1.set(FORKLIFT_UP);
			forklift2.set(FORKLIFT_UP);
		} else if(controller.aButton()){
			forklift1.set(FORKLIFT_DOWN);
			forklift2.set(FORKLIFT_DOWN);
		} else{
			forklift1.set(0);
			forklift2.set(0);
		}
	}
	
	public void cubeIntakeMechanism(){
		if (controller.xButton()){
			cubeIntake1.set(CUBE_IN);
			cubeIntake2.set(CUBE_IN);
		} else if(controller.bButton()){
			cubeIntake1.set(CUBE_OUT);
			cubeIntake2.set(CUBE_OUT);
		} else{
			cubeIntake1.set(0);
			cubeIntake2.set(0);
		}
	}
	
	public void retract(){
		if(controller.rightBumper() && !buttonPreviousState){
			retractIntakePosition = !retractIntakePosition;
		}
		buttonPreviousState = controller.rightBumper();
		
		//TODO retract/extend the intake based on the retract intake position variable until the mechanism hits a limit switch
	}
}	
	
	
