package auto_modes;

import com.kauailabs.navx.frc.AHRS;

import AutonomousControls.Auto_Encoder;
import AutonomousControls.Auto_MotionProfile;
import AutonomousControls.AutonomousControl;
import conditions.And;
import conditions.Thread;
import general.GameDataFirstPowerUp;
import logging.SimpleLogger;
import logging.SimpleLogger.LogLevel;
import logging.SimpleLogger.LogSubsystem;
import motion_profile.TrapezoidalMPGenerator;
import motion_profiles.LeftSwitchMiddleRobot;
import motion_profiles.MotionProfiles;
import motion_profiles.MotionProfilesHelper;
import subsystems_auto.Auto_Drive;
import subsystems_auto.Auto_Elevator;
import subsystems_auto.Auto_Turn;
import subsystems_tele.CubeMovement;
import subsystems_tele.Drive;

public class AutonomousModes {
	private AutonomousMode currentAutoMode = null;
	private Drive drive;
	private CubeMovement forklift;
	private AHRS gyro;
	
  	private AutonomousControl[] moveAcrossLine = {null};
  	
  	private AutonomousControl[] robotMiddleSwitchLeft = {null, null, null, null, null};
  	private AutonomousControl[] robotMiddleSwitchRight = {null};
  	private AutonomousControl[] robotLeftSwitchLeft = {null, null, null, null};
  	private AutonomousControl[] robotRightSwitchRight = {null, null, null, null};
  	
  	private AutonomousControl[] doNothing = {null};
  	private AutonomousControl[] driveSlow = {null};
  	
/*	private AutonomousMode scaleRightRobotRight = new AutonomousMode ("Scale right robot right" ,
			new AutonomousControl[][] { {new And( new Auto_Time(5), new Auto_Drive(5, drive) )} });
	private AutonomousMode scaleLeftRobotRight = new AutonomousMode ("Scale left robot right" , null);
	private AutonomousMode scaleLeftRobotLeft = new AutonomousMode ("Scale left robot left" , null);
	private AutonomousMode scaleRightRobotLeft = new AutonomousMode ("Scale right robot left" , null);*/
	
  	private void initAutoModes(){
  		doNothing[0] = new Auto_Drive(0, drive);
//  		driveSlow[0] = new And(new Auto_Drive(0.2, drive), new Auto_Encoder(8.8, drive.getLeftMotorController()));
//  		driveSlow[0] = new Auto_MotionProfile(drive, MotionProfiles.rightSwitchMiddleRobot_L, MotionProfiles.rightSwitchMiddleRobot_R);
  		driveSlow[0] = new Auto_MotionProfile(drive, LeftSwitchMiddleRobot.LSMR2_L, LeftSwitchMiddleRobot.LSMR2_R);
  		
  		moveAcrossLine[0] = new Thread(new And(new Auto_Drive(0.3, drive), new Auto_Encoder(8.8, drive.getLeftMotorController())), new Auto_Elevator(forklift, 0.5, 2400, false));
//  		moveAcrossLine[0] = new Auto_Elevator(forklift, 0.5, 2000, false);
  		
//  		robotMiddleSwitchLeft[0] = new Thread(new Auto_MotionProfile(drive, MotionProfiles.leftSwitchMiddleRobot_L, MotionProfiles.leftSwitchMiddleRobot_R), new Auto_Elevator(forklift, 0.5, 2400, false));
  		robotMiddleSwitchLeft[0] = new Auto_MotionProfile(drive, LeftSwitchMiddleRobot.LSMR1_L, LeftSwitchMiddleRobot.LSMR1_R);
  		robotMiddleSwitchLeft[1] = new Auto_Turn(-90, 0.2, gyro, drive);
  		robotMiddleSwitchLeft[2] = new Auto_MotionProfile(drive, LeftSwitchMiddleRobot.LSMR2_L, LeftSwitchMiddleRobot.LSMR2_R);
  		robotMiddleSwitchLeft[3] = new Auto_Turn(90, 0.2, gyro, drive);
  		robotMiddleSwitchLeft[4] = new Auto_MotionProfile(drive, LeftSwitchMiddleRobot.LSMR3_L, LeftSwitchMiddleRobot.LSMR3_R);
//  		robotMiddleSwitchLeft[1] = new Auto_CubeMovement(forklift, false, 2000);
  		
//  		robotMiddleSwitchRight[0] = new Thread(new Auto_MotionProfile(drive, MotionProfiles.rightSwitchMiddleRobot_L, MotionProfiles.rightSwitchMiddleRobot_R), new Auto_Elevator(forklift, 0.5, 2400, false));
  		robotMiddleSwitchRight[0] = new Auto_MotionProfile(drive, MotionProfilesHelper.rightSwitchMiddleRobot_L, MotionProfilesHelper.rightSwitchMiddleRobot_R);
//  		robotMiddleSwitchRight[1] = new Auto_CubeMovement(forklift, false, 2000);
  		
  		robotRightSwitchRight[0] = new Auto_MotionProfile(drive, MotionProfilesHelper.rightSwitchRightRobot_L, MotionProfilesHelper.rightSwitchRightRobot_R);
//  		robotRightSwitchRight[1] = new Auto_CubeMovement(forklift, false, 2000);
  		
  		robotLeftSwitchLeft[0] = new Auto_MotionProfile(drive, MotionProfilesHelper.leftSwitchLeftRobot_L, MotionProfilesHelper.leftSwitchLeftRobot_R);
//  		robotLeftSwitchLeft[1] = new Auto_CubeMovement(forklift, false, 2000);
  		
  		//
  		// HARD CODED AUTO MODES
  		//
  		
//  		//drive to the middle of the switch on the left side
////  		robotLeftSwitchLeft[0] = new And(new Auto_Drive(0.3, drive), new Auto_Encoder(12.4, drive.getLeftMotorController()));
//  		robotLeftSwitchLeft[0] = new Thread(new And(new Auto_Drive(0.3, drive), new Auto_Encoder(12.4, drive.getLeftMotorController())), new Auto_Elevator(forklift, 0.5, 2400, false));
//  		robotLeftSwitchLeft[1] = new And(new Auto_Drive(0, 0.4, drive), new Auto_Gyroscope(drive, 90));
//  		robotLeftSwitchLeft[2] = new And(new Auto_Drive(0.3, drive), new Auto_Encoder(1.5, drive.getLeftMotorController()));
//  		robotLeftSwitchLeft[3] = new Auto_CubeMovement(forklift, false, 2000);
////  		
//  		//drive to the middle of the switch on the right side
////  		robotRightSwitchRight[0] = new And(new Auto_Drive(0.3, drive), new Auto_Encoder(12.4, drive.getLeftMotorController()));
//  		robotRightSwitchRight[0] = new Thread(new And(new Auto_Drive(0.3, drive), new Auto_Encoder(12.4, drive.getLeftMotorController())), new Auto_Elevator(forklift, 0.5, 2400, false));
//  		robotRightSwitchRight[1] = new And(new Auto_Drive(0, -0.4, drive), new Auto_Gyroscope(drive, -90));
//  		robotRightSwitchRight[2] = new And(new Auto_Drive(0.3, drive), new Auto_Encoder(1.5, drive.getLeftMotorController()));
//  		robotRightSwitchRight[3] = new Auto_CubeMovement(forklift, false, 2000);
  	}
	
	public AutonomousModes(Drive drive, CubeMovement forklift, AHRS gyro) {
		SimpleLogger.log("Start auto modes");
		this.drive = drive;
		this.forklift = forklift;
		this.gyro = gyro;
		initAutoModes();
		currentAutoMode = new AutonomousMode("Test Motion Profile", driveSlow);
	}
	
	public AutonomousMode chooseAutonomousMode(RobotFieldPosition myPos, AutonomousGoal myGoal, GameDataFirstPowerUp.FieldSide fieldScale,
			GameDataFirstPowerUp.FieldSide fieldSwitch){
//		SimpleLogger.log("AutoModes, myPos: " + myPos + " myGoal: " + myGoal + " scale: "+ fieldScale + " switch: " + fieldSwitch);
		
		if(myGoal == AutonomousGoal.LINE){
			//currentAutoMode = new AutonomousMode("Drive across the line", moveAcrossLine);
			currentAutoMode = new AutonomousMode("Moving across the line", moveAcrossLine);
		} else if (myGoal == AutonomousGoal.SWITCH){
			if (myPos == RobotFieldPosition.RIGHT){
				if (fieldSwitch == GameDataFirstPowerUp.FieldSide.RIGHT){
					SimpleLogger.log("robot RIGHT switch RIGHT autonomous", LogLevel.COMP, LogSubsystem.AUTO);
					currentAutoMode = new AutonomousMode("Starting right and going to right switch", robotRightSwitchRight);
				} else if (fieldSwitch == GameDataFirstPowerUp.FieldSide.LEFT){
					SimpleLogger.log("robot RIGHT switch LEFT autonomous", LogLevel.COMP, LogSubsystem.AUTO);
					currentAutoMode = new AutonomousMode("Starting left and going to right switch", driveSlow);//was right left
				}
			} else if (myPos == RobotFieldPosition.LEFT){
				if (fieldSwitch == GameDataFirstPowerUp.FieldSide.RIGHT){
					SimpleLogger.log("robot LEFT switch RIGHT autonomous", LogLevel.COMP, LogSubsystem.AUTO);
					currentAutoMode = new AutonomousMode("Starting left and going to right switch", driveSlow);
				} else if (fieldSwitch == GameDataFirstPowerUp.FieldSide.LEFT){
					SimpleLogger.log("robot LEFT switch LEFT autonomous", LogLevel.COMP, LogSubsystem.AUTO);//was switch left
					currentAutoMode = new AutonomousMode("Starting left and going to left switch", robotLeftSwitchLeft);
				}
			} else {
				if (myPos == RobotFieldPosition.MIDDLE){
					if (fieldSwitch == GameDataFirstPowerUp.FieldSide.RIGHT){
						SimpleLogger.log("robot MIDDLE switch RIGHT autonomous", LogLevel.COMP, LogSubsystem.AUTO);
						currentAutoMode = new AutonomousMode("Starting middle and going to right switch", robotMiddleSwitchRight);
					} else if (fieldSwitch == GameDataFirstPowerUp.FieldSide.LEFT){
						SimpleLogger.log("robot MIDDLE switch LEFT autonomous", LogLevel.COMP, LogSubsystem.AUTO);
	//					currentAutoMode = new AutonomousMode("Starting middle and going to left switch", robotMiddleSwitchLeft);
						currentAutoMode = new AutonomousMode("Starting middle and going to left switch", robotMiddleSwitchLeft);
	//					currentAutoMode = new AutonomousMode("Starting middle and going to left switch, BUT DOING NOTHING", doNothing);
					}
				}
			}
		} else if (myGoal == AutonomousGoal.SCALE){
//			if (myPos == RobotFieldPosition.RIGHT){
//				if (fieldScale == GameDataFirstPowerUp.FieldSide.RIGHT){
//					newArray = mergeArrays(sameSideRight, driveToScaleRight);
//					
//					currentAutoMode = new AutonomousMode("Starting right and going to right scale", newArray);
//				} else if (fieldScale == GameDataFirstPowerUp.FieldSide.LEFT){
//					newArray = mergeArrays(wrongSideLeft, driveToScaleLeft);
//					
//					currentAutoMode = new AutonomousMode("Starting right and going to left scale", newArray);
//					
//				}
//			} else if (myPos == RobotFieldPosition.LEFT){
//				if (fieldScale == GameDataFirstPowerUp.FieldSide.RIGHT){
//					newArray = mergeArrays(wrongSideRight, driveToScaleRight);
//					
//					currentAutoMode = new AutonomousMode("Starting left and going to right scale", newArray);
//				} else if (fieldScale == GameDataFirstPowerUp.FieldSide.LEFT){
//					newArray = mergeArrays(sameSideLeft, driveToScaleLeft);
//					
//					currentAutoMode = new AutonomousMode("Starting left and going to left scale", newArray);
//				}
//			} else
			if (myPos == RobotFieldPosition.MIDDLE){
				if (fieldScale == GameDataFirstPowerUp.FieldSide.RIGHT){
					currentAutoMode = new AutonomousMode("Starting middle and going to right scale", null); //TODO fix this for scale
				} else if (fieldScale == GameDataFirstPowerUp.FieldSide.LEFT){
					currentAutoMode = new AutonomousMode("Starting middle and going to left scale", null); //TODO fix!
				}
			}
		}
		
		return currentAutoMode;
	}
	
	/**
	 * @return the current autonomous mode
	 */
	public AutonomousMode getCurrentAutoMode(){
		return currentAutoMode;
//		return new AutonomousMode("TEST", middleMovementLeft);
	}
	
	public static AutonomousControl[] mergeArrays(AutonomousControl[] first, AutonomousControl[] second){
		AutonomousControl[] mergedArray = new AutonomousControl[first.length+second.length];
		for(int i = 0; i < first.length; i++){
			mergedArray[i] = first[i];
		}
		
		for(int i = 0; i < second.length; i++){
			mergedArray[i+first.length] = second[i];
		}
		return mergedArray;
	}
	
}
