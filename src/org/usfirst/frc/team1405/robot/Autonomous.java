package org.usfirst.frc.team1405.robot;

import cpi.AutoMode;
import cpi.MySet;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.AutoValues;
import cpi.auto.Auto_Drive;
import cpi.auto.Auto_Gear;
import cpi.auto.Auto_Shooter;
import cpi.auto.SuperClass;
import cpi.auto.conditions.And;
import cpi.auto.inputDevices.Encoder;
import cpi.auto.inputDevices.Gyroscope;
import cpi.auto.inputDevices.Time;
import cpi.autoSupportClasses.AutonomousBase;

public class Autonomous extends AutonomousBase{
	private static boolean aButtonDown = false;
	private static boolean yButtonDown = false;
	
	public static void robotInit(){
		AutoInputs.robotInit();
		AutoOutputs.robotInit();
		AutoInputs.resetGyros();
		
		createAutoModes();
	}
	
	public static void createAutoModes(){
		SuperClass[][] sideAirshipGear_rightRed = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(-AutoValues.angle_turnToSideGear))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_centerOfBot_to_sideAirShip, false, true))},
			{ new Time(2), new Auto_Gear(false, true)} };
			
		SuperClass[][] sideAirshipGear_leftRed = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(AutoValues.angle_turnToSideGear))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_centerOfBot_to_sideAirShip, false, true))},
			{ new Time(2), new Auto_Gear(false, true)} };
			
		SuperClass[][] sideAirshipGear_rightBlue = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(-AutoValues.angle_turnToSideGear))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_centerOfBot_to_sideAirShip, false, true))},
			{ new Time(2), new Auto_Gear(false, true)} };
			
		SuperClass[][] sideAirshipGear_leftBlue = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_centerOfBot_to_centerAirShip, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(AutoValues.angle_turnToSideGear))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_turn_centerOfBot_to_sideAirShip, false, true))},
			{ new Time(2), new Auto_Gear(false, true)} };
		
		SuperClass[][] centerAirshipGear = new SuperClass[][]{
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false, false))}, //inaccurate driving
			{ new Time(2), new Auto_Gear(false, true)},
			{ new And(new Auto_Drive(0), new Encoder(-24, false, false))}};
			
			SuperClass[][] centerAirshipGearShootRed = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false, false))}, //inaccurate driving
				{ new Time(0.5), new Auto_Gear(false, true)},
				{ new And(new Auto_Drive(0), new Encoder(-24, false, false))},
				{ new And(new Auto_Drive(0, 0), new Gyroscope(-80))},
				{ new And(new And(new Auto_Drive(0), new Auto_Shooter(true, 0.7)), new Encoder(-106, false, false))},
				{ new Time(8), new Auto_Shooter(true, 0.7)}};
				
			SuperClass[][] centerAirshipGearShootBlue = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false, false))}, //inaccurate driving
				{ new Time(0.5), new Auto_Gear(false, true)},
				{ new And(new Auto_Drive(0), new Encoder(-24, false, false))},
				{ new And(new Auto_Drive(0, 0), new Gyroscope(60))},
				{ new And(new And(new Auto_Drive(0), new Auto_Shooter(true, 0.7)), new Encoder(-106, false, false))},
				{ new Time(8), new Auto_Shooter(true, 0.7)}};
			
//		SuperClass[][] centerAirshipGearAccurate = new SuperClass[][]{
//			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false, true))}, //inaccurate driving
//			{ new Time(2), new Auto_Gear(false, true)},
//			{ new And(new Auto_Drive(0), new Encoder(-24, false, false))}};
			
		SuperClass[][] turn90 = new SuperClass[][]{
				{ new And(new Auto_Drive(0, 0), new Gyroscope(-90))}};
		
		SuperClass[][] driveFwd77 = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(40, false, true))}};
		
		SuperClass[][] driveFwd10 = new SuperClass[][]{
				{ new And(new Auto_Drive(0), new Encoder(10, false, true))}};
				
		SuperClass[][] shoot_red = new SuperClass[][]{
			{ new Time(8), new Auto_Shooter(true, 0.5)},
			{ new And(new Auto_Drive(0), new Encoder(-10, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(-90))},
			{ new And(new Auto_Drive(0), new Encoder(-AutoValues.distance_allianceWall_to_centerAirShip, false, true))}};
			
		SuperClass[][] shoot_blue = new SuperClass[][]{
			{ new Time(8), new Auto_Shooter(true, 0.5)},
			{ new And(new Auto_Drive(0), new Encoder(10, false, true))},
			{ new And(new Auto_Drive(0, 0), new Gyroscope(-90))},
			{ new And(new Auto_Drive(0), new Encoder(AutoValues.distance_allianceWall_to_centerAirShip, false, true))}};
		
		MySet.addAutoMode(new AutoMode(centerAirshipGear, "\nEITHER COLOR, Center - Drop off the center airship gear.\n"));
		MySet.addAutoMode(new AutoMode(centerAirshipGearShootRed, "\nRED, Center - Drop off the center airship gear and shoot!\n"));
		MySet.addAutoMode(new AutoMode(centerAirshipGearShootBlue, "\nBLUE, Center - Drop off the center airship gear and shoot!\n"));
//		MySet.addAutoMode(new AutoMode(centerAirshipGearAccurate, "\nEITHER COLOR, ACCURATE Center - Drop off the center airship gear.\n"));
		MySet.addAutoMode(new AutoMode(sideAirshipGear_rightRed, "\nRED, Right Corner - Drop off the side airship gear.\n"));
		MySet.addAutoMode(new AutoMode(sideAirshipGear_leftRed, "\nRED, Left Corner - Drop off the side airship gear.\n"));
		MySet.addAutoMode(new AutoMode(sideAirshipGear_rightBlue, "\nBLUE, Right Corner - Drop off the side airship gear.\n"));
		MySet.addAutoMode(new AutoMode(sideAirshipGear_leftBlue, "\nBLUE, Left Corner - Drop off the side airship gear.\n"));
		MySet.addAutoMode(new AutoMode(shoot_red, "\nRED, Shoot, turn, and drive.\n"));
		MySet.addAutoMode(new AutoMode(shoot_blue, "\nBLUE, Shoot, turn, and drive.\n"));
//		MySet.addAutoMode(new AutoMode(turn90, "TEST - Turn 90 degrees."));
		MySet.addAutoMode(new AutoMode(driveFwd77, "TEST - Drive fwd 77in."));
		
		MySet.assignAutoMode(0);
	}
	
	public static void disabledPeriodic(){
		if(Robot.pilot.aButton() && !aButtonDown){
			MySet.assignNextAutoMode();
		}
		
		if(Robot.pilot.yButton() && !yButtonDown){
			MySet.printAutoMode();
		}
		
		yButtonDown = Robot.pilot.yButton();
		aButtonDown = Robot.pilot.aButton();
	}
	
	public static void autonomousInit(){
		AutonomousInit();
	}
}
