package org.usfirst.frc.team1405.robot;

import cpi.AutoMode;
import cpi.MySet;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.AutoValues;
import cpi.auto.Auto_Drive;
import cpi.auto.SuperClass;
import cpi.auto.conditions.And;
import cpi.auto.inputDevices.Encoder;
import cpi.auto.inputDevices.Gyroscope;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.autoSupportClasses.Set;

public class Autonomous extends AutonomousBase{
	static final String TEST_AUTO_MODE = "test_mode";
	static final String TEST_DRIVING = "test_driving";
	static final String SIDE_AIRSHIP_GEAR = "side_airship_gear";
	
	public static void robotInit(){
		AutoInputs.robotInit();
		AutoOutputs.robotInit();
		AutoInputs.resetGyros();
		Set.setDefault(SIDE_AIRSHIP_GEAR);
		Set.addName(SIDE_AIRSHIP_GEAR);
		Set.addName(TEST_AUTO_MODE);
		Set.addName(TEST_DRIVING);
		
		createAutoModes();
	}
	
	public static void createAutoModes(){
		
		SuperClass[][] tmpMatrix = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false))}};
			
		MySet.addAutoMode(new AutoMode(tmpMatrix, "someID", "drive forward"));
		
		MySet.assignAutoMode(0);
	}
	
	public static void autonomousInit(){
		AutonomousInit();
		//selectAutoMode(autoMode);
	}
	
	
	 public static void selectAutoMode(String mode){
		switch(mode){
		case(TEST_AUTO_MODE):
			Autonomous.testAutoMode();
			break;
		case(TEST_DRIVING):
			Autonomous.testDrive();
			break;
		case(SIDE_AIRSHIP_GEAR):
			Autonomous.sideAirshipGear();
			break;
		}
	}


	
	/////////////////*			FULL AUTONOMOUS MODES			*////////////////////
	public static void testAutoMode(){
		autoStates = new SuperClass[][]{
			{ /*new And( , )*/ }};
	}
	
	public static void testDrive(){
		autoStates = new SuperClass[][]{
//			{ new And(new Auto_Drive(0, 0), new Encoder(90, 10, true, true)) }};
			{ new And(new Auto_Drive(0, 0), new Gyroscope(90)) }};
//			{ new And(new Auto_Drive(0.35), new Encoder(63.6, false)) }};
	}
	
	public static void wallToAirshipGear(){
		autoStates = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false))}};
	}
	
	public static void sideAirshipGear(){
		autoStates = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_turn, false))}};//,
//				{ new And(new Auto_Drive(0, 0), new Gyroscope(-AutoValues.angle_turnToSideGear))},
//				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_to_sideAirShip, false))} };
	}
}
