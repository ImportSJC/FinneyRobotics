package cpi.auto;

import cpi.auto.conditions.*;
import cpi.auto.inputDevices.*;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.autoSupportClasses.Set;

public class Autonomous extends AutonomousBase{
	static final String DO_NOTHING="do_nothing";
	static final String APPROACH_DEFENSE="approach_defense";
	static final String	CROSS_BASIC_DEFENSE="cross_basic_defense";
	static final String SIMPLE_TEST="simple_test";
	
	public static void robotInit(){
		AutoOutputs.robotInit();
//		AutoInputs.myGyro.reset();
		Set.setDefault(CROSS_BASIC_DEFENSE);
		Set.addName(APPROACH_DEFENSE);
		Set.addName(DO_NOTHING);
		Set.addName(SIMPLE_TEST);
	}
	
	public static void autonomousInit(){
		AutonomousInit();
		selectAutoMode(autoMode);
	}
	
	
	public static void selectAutoMode(String mode){
		switch(mode){
		case(DO_NOTHING):
			break;
		case(APPROACH_DEFENSE):
			Autonomous.approachDefense();
			break;
		case(CROSS_BASIC_DEFENSE):
			Autonomous.crossBasicDefense();
			break;
		case(SIMPLE_TEST):
			Autonomous.simpleTest();
			break;
		}
	}


	
	/////////////////*			FULL AUTONOMOUS MODES			*////////////////////

	/** Drive forward to enter the volume of the outerworks, but don't cross*/
	public static void approachDefense(){
		autoStates = new SuperClass[][]{
				{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toDefenses) )} };
	}
	
	/** Drive forward over the basic defense in front of the robot (lowbar, rough terrain etc) */
	public static void crossBasicDefense(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toAlignmentLine) )} };
	}
	
	/** Drive forward over the basic defense in front of the robot (lowbar, rough terrain etc) */
	public static void shootLowBar(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toShootingTurn) )},
			{new And( new Auto_Drive(0,60), new Gyroscope(90, 1) )} };
	}
	
	public static void simpleTest(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(0,0.5), new Gyroscope(90, 1) )} };
	}
}
