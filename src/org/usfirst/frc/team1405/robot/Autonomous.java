package org.usfirst.frc.team1405.robot;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.AutoValues;
import cpi.auto.Auto_Drive;
import cpi.auto.Auto_Elevator;
import cpi.auto.SuperClass;
import cpi.auto.conditions.*;
import cpi.auto.inputDevices.*;
import cpi.autoSupportClasses.AutonomousBase;
import cpi.autoSupportClasses.Set;

public class Autonomous extends AutonomousBase{
	static final String BOT_NO_BUMP="bot no Bump";
	static final String BOT_BUMP="bot Bump";
	static final String TOTE_BUMP="tote_bump";
	static final String TOTE_NO_BUMP="tote_noBump";
	static final String CAN_NO_BUMP="can_noBump";
	static final String CAN_BUMP="can_bump";
	static final String CAN_AND_TOTE_NO_BUMP="canAndTote_noBump";
	static final String CAN_AND_TOTE_BUMP="canAndTote_bump";
	static final String THREE_TOTE="threeTote";
	static final String SIMPLE_TEST="Simple Test";
	
	public static boolean isBump = false;
	
	public static void robotInit(){
		AutoInputs.robotInit();
		AutoOutputs.robotInit();
		AutoInputs.myGyro.reset();
		Set.setDefault(CAN_BUMP);
		Set.addName(BOT_NO_BUMP);
		Set.addName(BOT_BUMP);
		Set.addName(TOTE_BUMP);
		Set.addName(TOTE_NO_BUMP);
		Set.addName(CAN_NO_BUMP);
		Set.addName(CAN_BUMP);
		Set.addName(CAN_AND_TOTE_NO_BUMP);
		Set.addName(CAN_AND_TOTE_BUMP);
		Set.addName(THREE_TOTE);
		Set.addName(SIMPLE_TEST);
	}
	
	public static void autonomousInit(){
		AutonomousInit();
		selectAutoMode(autoMode);
	}
	
	
	 public static void selectAutoMode(String mode){
		switch(mode){
		case(BOT_NO_BUMP):
			isBump = false;
			Autonomous.bot();
			break;
		case(BOT_BUMP):
			isBump = true;
			Autonomous.bot();
			break;
		case(TOTE_NO_BUMP):
			isBump = false;
			Autonomous.tote();
			break;
		case(TOTE_BUMP):
			isBump = true;
			Autonomous.tote();
			break;
		case(CAN_NO_BUMP):
			isBump = false;
			Autonomous.can();
			break;
		case(CAN_BUMP):
			isBump = true;
			Autonomous.can();
			break;
		case(CAN_AND_TOTE_NO_BUMP):
			isBump = false;
			Autonomous.canAndTote();
			break;
		case(CAN_AND_TOTE_BUMP):
			isBump = true;
			Autonomous.canAndTote();
			break;
		case(THREE_TOTE):
			isBump = true;
			Autonomous.threeTote();
			break;
		case(SIMPLE_TEST):
			Autonomous.simpleTest();
			break;
		}
	}


	
	/////////////////*			FULL AUTONOMOUS MODES			*//////////////////// TODO: add elevator down to limit switch at the end of all modes that lift the elevator

		/** Drive forward over the bump to the center of the field. */
		public static void bot(){
			autoStates = new SuperClass[][]{
					{new And( new Auto_Drive(AutoValues.speed_elevatorUp), new Encoder(AutoValues.distanceToCenter(isBump)) )} };
		}
		/** Drive forward over the bump to the center of the field. */
		public static void simpleTest(){
			autoStates = new SuperClass[][]{
					{new And( new Auto_Drive(1.0), new Time(10) )} };
		}
	
	/** Drive forward over the bump to the center of the field. */
	public static void tote(){
		autoStates = new SuperClass[][]{
				{new And( new Auto_Elevator(0.5), new Time(1) )},							//pick up tote
				{new And( new Auto_Drive(0, AutoValues.speed_turn), new Gyroscope(90, 0) )},//TODO: change to positive							//turn to the right 90 degrees
				{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distanceToCenter(isBump)) )},	//carry tote to the center of the field
				{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )} }; 	
	}
	
	public static void can(){
		autoStates = new SuperClass[][]{
				{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_canOnTote) )},			//pick up can
				{new And( new Auto_Drive(0, -AutoValues.speed_turn), new Gyroscope(-90, 0) )},							//turn to the left
				{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distanceToCenter(isBump)) )},	//drive to the center
				{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )}						//drop the elevator to the floor
		};
	}
	
	/** Put can on top of tote, turn to the right, and drive over the bump to the center of the field */
	public static void canAndTote(){
		autoStates = new SuperClass[][]{
				{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_canOnTote) )},	//pick up elevator with can
				{new And( new Auto_Drive(AutoValues.speed_turnSlow), new Encoder(AutoValues.distance_canToTote) )},							//move forward until can is above tote
				{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )},			//set can on tote and hook tote
				{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_toteOnTote) )},							//pick up the tote a little bit
				{new And( new Auto_Drive(0, -AutoValues.speed_turn), new Gyroscope(-90, 0) )},					//turn 90 degrees to the right, facing the center
				{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distanceToCenter(isBump)) )},	//carry can and tote to the center of the field
				{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )} };					//drop the elevator to the floor
	}
	
	public static void alignRight(){
		
	}
	
	public static void alignLeft(){
		
	}
	
	public static void threeTote(){
		autoStates = new SuperClass[][]{
			{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_toteOnTote) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distance_toteToTote) )},
			{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )},
			{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_toteOnTote) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distance_toteToTote) )},
			{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )},
			{new And( new Auto_Elevator(AutoValues.speed_elevatorUp), new Time(AutoValues.time_toteOnTote) )},
			{new And( new Auto_Drive(0, -AutoValues.speed_turn), new Gyroscope(-90, 0) )},
			{new And( new Auto_Drive(AutoValues.speed_drive), new Encoder(AutoValues.distance_toCenter_bump) )},
			{new And( new Auto_Elevator(AutoValues.speed_elevatorDown), new LimitSwitch("bottom") )}};
	}
}
