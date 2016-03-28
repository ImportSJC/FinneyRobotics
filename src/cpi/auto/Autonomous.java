package cpi.auto;

import cpi.auto.conditions.*;
import cpi.auto.inputDevices.*;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.autoSupportClasses.Set;

public class Autonomous extends AutonomousBase{
	public static final String DO_NOTHING="do_nothing";
	public static final String APPROACH_DEFENSE="approach_defense";
	public static final String	CROSS_BASIC_DEFENSE="cross_basic_defense";
	public static final String	CROSS_HARD_DEFENSE="cross_hard_defense";
	public static final String SIMPLE_TEST="simple_test";
	public static final String DOUBLE_BASIC_DEFENSE="double_basic_defense";
	public static final String LOWBAR_SCORE="lowbar_score";
	public static final String JUST_SHOOT="just_shoot";
	
	public static void robotInit(){
		AutoOutputs.robotInit();
//		AutoInputs.myGyro.reset();
		Set.setDefault(CROSS_BASIC_DEFENSE);
		Set.addName(APPROACH_DEFENSE);
		Set.addName(DO_NOTHING);
		Set.addName(SIMPLE_TEST);
		Set.addName(DOUBLE_BASIC_DEFENSE);
		Set.addName(LOWBAR_SCORE);
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
		case(DOUBLE_BASIC_DEFENSE):
			Autonomous.doubleBasicDefense();
			break;
		case(LOWBAR_SCORE):
			Autonomous.shootLowBar();
			break;
		case(JUST_SHOOT):
			Autonomous.justShoot();
			break;
		case(CROSS_HARD_DEFENSE):
			Autonomous.crossHardDefense();
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
			{new And( new Auto_Drive(AutoValues.speed_drive), new Or(new Auto_Encoder(AutoValues.distance_toAlignmentLine), new Time(AutoValues.time_toAlignmentLine)) )}
//			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toAlignmentLine) )}//,
			/*{new And( new Auto_Drive(0.7), new Auto_Encoder(AutoValues.distance_toWall-AutoValues.distance_toAlignmentLine) )}*/ };
	}
	
	public static void crossHardDefense(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(AutoValues.speed_drive_fast), new Or(new Auto_Encoder(AutoValues.distance_toEndOfDefenses), new Time(AutoValues.time_toAlignmentLine_fast)) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Or(new Auto_Encoder(AutoValues.distance_toAlignmentLine-AutoValues.distance_toEndOfDefenses), new Time(AutoValues.time_toAlignmentLine_fast)) )}
//			{new And( new Auto_Drive(AutoValues.speed_drive_fast), new Auto_Encoder(AutoValues.distance_toAlignmentLine) )}//,
			/*{new And( new Auto_Drive(0.7), new Auto_Encoder(AutoValues.distance_toWall-AutoValues.distance_toAlignmentLine) )}*/ };
	}
	
	/** Drive forward over the basic defense in front of the robot (lowbar, rough terrain etc) */
	public static void shootLowBar(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toShootingTurn) )},
			{new And( new Auto_Drive(0,AutoValues.speed_turn), new Gyroscope(60, 1) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toTower) )},
			{new And( new Auto_Shoot(), new Time(2) )} };
	}
	
	public static void simpleTest(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Shoot(), new Time(2) )} };
	}
	
	public static void justShoot(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toTowerFromSpy) )},
			{new And( new Auto_Shoot(), new Time(2) )} };
	}
	
	public static void doubleBasicDefense(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Drive(0), new Time(2) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toCenterOfDefense) )},
			{new And( new Auto_Drive(-AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toBackup) )},
			{new And( new Auto_Drive(0,AutoValues.speed_turn), new Gyroscope(90, 1) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Auto_Encoder(AutoValues.distance_toAlignmentLine) )} };
	}
}
