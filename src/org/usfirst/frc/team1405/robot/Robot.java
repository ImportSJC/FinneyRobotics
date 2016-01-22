
package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import cpi.BallHandler;
//import edu.wpi.first.wpilibj.CANTalon;
//import cpi.CANTalon;
import cpi.Drive;
import cpi.Elevator;
import cpi.Shooter;
import cpi.XBox360;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    

   Drive drive;
   Shooter shooter;
   Elevator elevator;
   XBox360 pilot;
   BallHandler ball;
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	final static public String header="2016 Competition ver. 1.0"; // This is required
	
    public void robotInit() {
    	
    	initialize();
    }
    void initialize(){
    	
    	Autonomous.robotInit();
    	drive= new Drive("/Teleop Drive");
    	drive.robotInit();
    	shooter= new Shooter("/Teleop Shooter");
    	shooter.robotInit();
    	pilot=new XBox360("Pilot");
    	pilot.robotInit();
    	ball = new BallHandler();
    	cpi.Preferences.initialize();// !!Must be last statement in initialize!!
    	
    }
    
    public void autonomousInit(){
    	Autonomous.autonomousInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Autonomous.autonomousPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	pilot.teleopPeriodic();
    	drive.TeleopPeriodic();
    	shooter.teleopPeriodic();
    	ball.TeleopPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void disabledInit(){
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
    	cpi.CANTalon.testInit();
    }
    public void testPeriodic() {
    	pilot.teleopPeriodic();
    	cpi.CANTalon.testPeriodic();
    }
    
    public void disabledPeriodic(){
    	pilot.teleopPeriodic();
    	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}