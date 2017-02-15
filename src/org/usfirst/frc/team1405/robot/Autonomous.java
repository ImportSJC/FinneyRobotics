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

public class Autonomous extends AutonomousBase{
	private static boolean aButtonDown = false;
	
	public static void robotInit(){
		AutoInputs.robotInit();
		AutoOutputs.robotInit();
		AutoInputs.resetGyros();
		
		createAutoModes();
	}
	
	public static void createAutoModes(){
		
		SuperClass[][] sideAirshipGear = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(-AutoValues.angle_turnToSideGear))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_centerOfBot_to_sideAirShip, false))} };
		
		SuperClass[][] centerAirshipGear = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false))}};
		
		SuperClass[][] turn90 = new SuperClass[][]{
				{ new And(new Auto_Drive(0, 0), new Encoder(90, 0, false, true))}};
		
		SuperClass[][] driveFwd77 = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false))}};
		
		SuperClass[][] driveFwd10 = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(10, false))}};
		
		MySet.addAutoMode(new AutoMode(driveFwd10, "Drive fwd 77in."));
		MySet.addAutoMode(new AutoMode(turn90, "Turn 90 degrees."));
		MySet.addAutoMode(new AutoMode(centerAirshipGear, "Drop off the center airship gear."));
		MySet.addAutoMode(new AutoMode(sideAirshipGear, "Drop off the side airship gear."));
		
		MySet.assignAutoMode(0);
	}
	
	public static void disabledPeriodic(){
		if(Robot.pilot.aButton() && !aButtonDown){
			MySet.assignNextAutoMode();
		}
		
		aButtonDown = Robot.pilot.aButton();
	}
	
	public static void autonomousInit(){
		AutonomousInit();
	}
}
