
package org.usfirst.frc.team1405.robot;

import cpi.Drive;
import cpi.SimpleCamera;
import cpi.XBox360;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    

   Drive drive;
   static public Control pilot;
	GRIP imageProcessor;
   
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
    	drive= new Drive(cpi.Drive.DIRECT_TANK);
    	drive.robotInit();
    	pilot=new Control(0);
    	AutoOutputs.robotInit();
    	AutoInputs.robotInit();
 //   	templates.GRIP3X1.robotInit();
 //   	templates.GRIPIntermediate2.robotInit();
 //   	templates.GRIPIntermediate.robotInit();
 //   	imageProcessor=new GRIP(0,1,2);
//    	SimpleCamera.init(0);
    	TestSimpleEncoder.robotInit();
    	TestSimpleMultiMotorPWM.robotInit();
    	TestSimpleSpikeRelay.robotInit();
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
    	drive.TeleopPeriodic();
    }
    
      
    /**
     * This function is called once every time the robot is disabled
     */
    public void disabledInit(){
    	TestSimpleSpikeRelay.disabledInit();
    	TestSimpleMultiMotorPWM.disabledInit();
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
    	TestSimpleMultiMotorPWM.testInit();
    	TestSimpleSpikeRelay.testInit();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	TestSimpleMultiMotorPWM.testPeriodic();
    	TestSimpleSpikeRelay.testPeriodic();
    }

    /**
     * This function is called periodically when the robot is disabled
     */
    public void disabledPeriodic(){
    	//System.out.println(timer.get());
    	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}