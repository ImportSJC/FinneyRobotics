package templates;

import cpi.auto.conditions.*;
import cpi.auto.inputDevices.*;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.auto.*;;
/**
 * 
 * @author Stephen Cerbone, Mr. Wulff
 * 
 * Copy/Paste this template to another package
 *
 */
public class Autonomous extends AutonomousBase{
	
	/**
	 * Create mode names here eg.static final String TOTE_NO_BUMP="tote_noBump";
	 */
	/**
	 * Add resets for AutoInputs eg. AutoInputs.myGyro.reset();	
	 * Add a set name for all mode names eg. Set.addName(TOTE_NO_BUMP);
	 */
	public void RobotInit(){
		AutoInputs.robotInit(); // Do not remove
		AutoOutputs.robotInit();// Do not remove
	}
	
	
	/**
	 * 
	 * Add a case statement within the switch statement for each available mode eg.case(BOT_NO_BUMP):
	 * Within the case statement add the mode method eg Autonomous.bot();
	 * 
	 * 
	 *  Example:
	 public static void selectAutoMode(String mode){
		switch(mode){
		case(TOTE_NO_BUMP):
			isBump = false; // User variable
			Autonomous.tote();
			break;
		}
	}
	 * @param mode is the name of the desired mode eg. TOTE_NO_BUMP
	 */
	 public static void selectAutoMode(String mode){
		switch(mode){
		}
	}


	
	/////////////////*			FULL AUTONOMOUS MODES			*////////////////////
	/**
	 * Example of a user generated mode
	 //Drive forward over the bump to the center of the field
		autoStates = new SuperClass[][]{
				{new And( new Auto_Elevator(0.5), new Time(1) )},							//pick up tote
				{new And( new Auto_Drive(0, AutoValues.speed_turn), new Gyroscope(90, 0) )},//TODO: change to positive							//turn to the right 90 degrees
				{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distanceToCenter(isBump)) )},	//carry tote to the center of the field
				{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )} }; 	
	}
	 */
	/** Drive forward over the bump to the center of the field. */
}
