package org.usfirst.frc.team1405.robot;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.Auto_Drive;
import cpi.auto.SuperClass;
import cpi.auto.conditions.And;
import cpi.auto.inputDevices.Time;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.autoSupportClasses.Set;

public class Autonomous extends AutonomousBase{
	static final String TEST_AUTO_MODE = "test_mode";
	static final String TEST_DRIVING = "test_driving";
	
	public static void robotInit(){
		AutoInputs.robotInit();
		AutoOutputs.robotInit();
		AutoInputs.resetGyro();
		Set.setDefault(TEST_DRIVING);
		Set.addName(TEST_AUTO_MODE);
		Set.addName(TEST_DRIVING);
	}
	
	public static void autonomousInit(){
		AutonomousInit();
		selectAutoMode(autoMode);
	}
	
	
	 public static void selectAutoMode(String mode){
		switch(mode){
		case(TEST_AUTO_MODE):
			Autonomous.testAutoMode();
			break;
		case(TEST_DRIVING):
			Autonomous.testDrive();
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
			{ new And(new Auto_Drive(.5), new Time(4)) }};
	}
}
