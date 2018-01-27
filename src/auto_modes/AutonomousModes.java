package auto_modes;

import java.util.Arrays;
import java.util.stream.Stream;

import AutonomousControls.Auto_Time;
import AutonomousControls.AutonomousControl;
import conditions.And;
import subsystems_auto.Auto_Drive;
import subsystems_tele.Drive;

public class AutonomousModes {
	private AutonomousMode currentAutoMode = null;
	private Drive drive;
	private AutonomousControl[] newArray;
	
	private AutonomousControl[] middleMovementLeft = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ),
														new And( new Auto_Time(3), new Auto_Drive(0.5, drive) ),
														new And( new Auto_Time(2), new Auto_Drive(-1, drive) ) };
  	private AutonomousControl[] middleMovementRight = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	private AutonomousControl[] wrongSideLeft = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	private AutonomousControl[] wrongSideRight = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	
  	private AutonomousControl[] driveToSwitchLeft = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	private AutonomousControl[] driveToSwitchRight = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	private AutonomousControl[] driveToScaleLeft = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	private AutonomousControl[] driveToScaleRight = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	
  	private AutonomousControl[] moveAcrossLine = { new And( new Auto_Time(5), new Auto_Drive(1, drive) ) };
  	
	
/*	private AutonomousMode scaleRightRobotRight = new AutonomousMode ("Scale right robot right" ,
			new AutonomousControl[][] { {new And( new Auto_Time(5), new Auto_Drive(5, drive) )} });
	private AutonomousMode scaleLeftRobotRight = new AutonomousMode ("Scale left robot right" , null);
	private AutonomousMode scaleLeftRobotLeft = new AutonomousMode ("Scale left robot left" , null);
	private AutonomousMode scaleRightRobotLeft = new AutonomousMode ("Scale right robot left" , null);*/
	
	
	public AutonomousModes(FieldPositions myPos , AutonomousGoal myGoal , String fieldScale , String fieldSwitch ,
			Drive drive) {
		this.drive = drive;
		
		if(myGoal == AutonomousGoal.LINE){
			currentAutoMode = new AutonomousMode("Drive across the line", moveAcrossLine);
		} else if(myGoal == AutonomousGoal.SWITCH){
			if(fieldSwitch.equals("L")){
				if(myPos == FieldPositions.LEFT){
					newArray = mergeArrays(middleMovementLeft, driveToSwitchLeft);
					
					currentAutoMode = new AutonomousMode("Starting left and going to left switch", newArray);
				}
			}
		}
		
	}
	
	/**
	 * @return the current autonomous mode
	 */
	public AutonomousMode getCurrentAutoMode(){
//		return currentAutoMode;
		return new AutonomousMode("TEST", middleMovementLeft);
	}
	
	public static AutonomousControl[] mergeArrays(AutonomousControl[] first, AutonomousControl[] second){
		
		return Stream.concat(Arrays.stream(first), Arrays.stream(second)).toArray(AutonomousControl[]::new);
				
	}
	
}
