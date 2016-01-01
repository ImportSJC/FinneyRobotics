package auto;

import autoModes.Auto_Drive;
import autoModes.Auto_Elevator;
import conditions.*;
import inputDevices.*;

public class Autonomous {
	public static int columnIndex = 0;
	public static int rowIndex = 0;
	public static boolean columnInit = false; //has all the modes in the column been started yet?
//	public static boolean[] checks; //an array storing the boolean values of all mode checks in the current row
	public static SuperClass[][] autoStates = null;
//	public static String autoMode = "bot_bump";//this should be set to "" or "default"
	public static String autoMode = "canAndTote_bump";
	public static boolean isBump = false;
	
	public void RobotInit(){
		AutoInputs.myGyro.reset();
		
	}
	
	public void pickAutoMode(String mode){
		switch(mode){
		case("bot_noBump"):
			isBump = false;
			Autonomous.bot();
			break;
		case("bot_bump"):
			isBump = true;
			Autonomous.bot();
			break;
		case("tote_noBump"):
			isBump = false;
			Autonomous.tote();
			break;
		case("tote_bump"):
			isBump = true;
			Autonomous.tote();
			break;
		case("can_noBump"):
			isBump = false;
			Autonomous.can();
			break;
		case("can_bump"):
			isBump = true;
			Autonomous.can();
			break;
		case("canAndTote_noBump"):
			isBump = false;
			Autonomous.canAndTote();
			break;
		case("canAndTote_bump"):
			isBump = true;
			Autonomous.canAndTote();
			break;
		case("threeTote"):
			isBump = true;
			Autonomous.threeTote();
			break;
		}
	}
	
	public void AutonomousInit(){
		pickAutoMode(autoMode);
		columnIndex = 0;
		rowIndex = 0;
		columnInit = false;
		AutoOutputs.setDriveBrake(true);
		AutoInputs.myGyro.reset();
	}
	
	public void AutonomousPeriodic() {
		if (columnIndex<autoStates.length){
//			currentAuto = autoModes[autoModeIndex];
			if (!columnInit){
				AutoInputs.myGyro.reset();
				AutoInputs.resetEncoders();
				for (int i=0; i<autoStates[columnIndex].length; i++){
					autoStates[columnIndex][i].start();
				}
				columnInit = true;
			}
			else if(allChecksPassed()){
				System.out.println("All checks passed - NEXT STATE");
//				AutoInputs.myGyro.reset();
				columnIndex++;
				columnInit = false;
			}
		}
		else{
			System.out.println("End of Autonomous Loop");
			
			//TODO: remove this debug code for competitions
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			AutoOutputs.setDriveBrake(false);
		}
	}
	
//	public void DisabledInit(){
//		if (autoStates != null){
//			for (int i=0; i<autoStates.length; i++){
//				System.out.println("Running disabled init on a state");
//				for (int j=0; j<autoStates[i].length; j++){
//					autoStates[i][j].DisabledInit();
//				}
//			}
//		}
//	}
	
	public static boolean allChecksPassed(){
		//has all the checks passed?
		for (int i=0; i<autoStates[columnIndex].length; i++){
			if (!autoStates[columnIndex][i].check()){return false;}
		}
		for (int i=0; i<autoStates[columnIndex].length; i++){autoStates[columnIndex][i].stop();}
		return true;
	}
	
	
	/////////////////*			FULL AUTONOMOUS MODES			*//////////////////// TODO: add elevator down to limit switch at the end of all modes that lift the elevator
	/** Drive forward to the center of the field. */
//	public static void bot_noBump(){
//		autoStates = new SuperClass[][]{
//				{new And( new Auto_Drive(0.4), new Encoder(AutoValues.distance_toCenter_noBump) )} };
//	}
	
	/** Drive forward over the bump to the center of the field. */
	public static void bot(){
		autoStates = new SuperClass[][]{
				{new And( new Auto_Drive(AutoValues.speed_elevatorUp), new Encoder(AutoValues.distanceToCenter(isBump)) )} };
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
