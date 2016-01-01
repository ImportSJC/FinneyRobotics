
package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import auto.AutoOutputs;
import auto.AutoInputs;
import auto.Autonomous;
import tele.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drive = new Drive();
    	drive.robotInit();
    	autoOutputs = new AutoOutputs();
    	autoOutputs.robotInit();
    	
    	autoInputs = new AutoInputs();
    	autoInputs.RobotInit();
    	
    	auto = new Autonomous();
    	auto.RobotInit();
//    	auto.AutonomousInit();
    }
    
    public void autonomousInit(){
    	System.out.println("Their Auto Init was called!!!!");
    	auto.AutonomousInit();
    	
    	autoInputs.AutoInit();
    	
    	drive.autoInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic(){
//    	auto = new Autonomous();
    	auto.AutonomousPeriodic();
    	autoOutputs.AutonomousPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	drive.teleopPeriodic();
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
    
    Autonomous auto;
    AutoOutputs autoOutputs;
    AutoInputs autoInputs;
    Drive drive;
    
}
