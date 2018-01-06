
package org.usfirst.frc.team1405.robot;

import com.ctre.CANTalon;

import auto.Autonomous;
import auto.SuperClass;
import autoModes.Auto_Drive;
import conditions.And;
import edu.wpi.first.wpilibj.IterativeRobot;
import inputDevices.Time;
import tele.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	//Constants
	 
	//Helper Classes
  	private Drive drive;
  	private Autonomous auto;
	
	//AutoModes
	private final SuperClass[][] TEST_MODE = new SuperClass[][] {
		{new And( new Time(5), new Auto_Drive(5, drive) )} };
	
	//CANTalons
	private CANTalon talon1 = new CANTalon(1);
	private CANTalon talon2 = new CANTalon(2);
	private CANTalon talon3 = new CANTalon(3);
	private CANTalon talon4 = new CANTalon(4);
	private CANTalon talon5 = new CANTalon(5);
	private CANTalon talon6 = new CANTalon(6);
	
    public void robotInit() {
    	drive = new Drive(talon1, talon2, talon3, talon4);
    	
    	//TODO implement generic auto picking here
    	auto = new Autonomous(TEST_MODE);
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
