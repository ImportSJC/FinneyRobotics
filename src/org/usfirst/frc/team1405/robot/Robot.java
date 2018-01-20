
package org.usfirst.frc.team1405.robot;

import AutonomousControls.Auto_Time;
import AutonomousControls.AutonomousControl;
import auto_modes.AutonomousMode;
import auto_modes.AutonomousModes;
import conditions.And;
import control_modes.ArcadeDrive;
import control_modes.ControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import general.Autonomous;
import general.CustomXBox;
import general.MotorController;
import subsystems_auto.Auto_Drive;
import subsystems_tele.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//TODO implement generic control mode picking
	//TODO implement generic auto picking
	
	//Constants
	
	//Helper Classes
  	private Drive drive;
  	private Autonomous auto;
  	private ControlMode controlMode;
  	private CustomXBox pilot;

  	//Auto modes
  	private AutonomousMode TEST_MODE = new AutonomousMode("A test autonomous mode", 
  			new AutonomousControl[][] { {new And( new Auto_Time(5), new Auto_Drive(5, drive) )} } );
  	
	//CANTalons
	private MotorController talon1 = new MotorController(1);
	private MotorController talon2 = new MotorController(2);
	private MotorController talon3 = new MotorController(3);
	private MotorController talon4 = new MotorController(4, true);
	private MotorController talon5 = new MotorController(5, true);
	private MotorController talon6 = new MotorController(6, true);

	public void robotInit() {
    	controlMode = new ArcadeDrive();
    	pilot = new CustomXBox(0);
    	drive = new Drive(pilot, controlMode, talon1, talon2, talon3, talon4, talon5, talon6);
    	
    	AutonomousModes.addAutoMode(TEST_MODE);
    	auto = new Autonomous(AutonomousModes.getCurrentAutoMode());
    }
    
    public void autonomousInit(){
    	auto.AutonomousInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	drive.tank();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	
    }
    
    public void disabledPeriodic(){
//    	System.out.println("Their DisabledPeriodic was called");
//    	AutoOutputs.setBrake(false);
    }
}
