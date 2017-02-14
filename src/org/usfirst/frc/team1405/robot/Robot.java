
package org.usfirst.frc.team1405.robot;

import org.usfirst.frc.team1405.robot.Vision.Vision2017;

import cpi.Drive;
import cpi.SimpleCamera;
import cpi.XBox360;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.testBeds.TestSimpleEncoder;
import cpi.testBeds.TestSimpleMultiMotorPWM;
import cpi.testBeds.TestSimpleSpikeRelay;
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
//	GRIP imageProcessor;
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	final static public String header="2016 Competition ver. 1.0"; // This is required
	
    public void robotInit() {
    	initialize();
    }
    void initialize(){
    	
//    	Autonomous.robotInit();
//    	drive= new Drive(cpi.Drive.DIRECT_TANK);
//    	drive.robotInit();
//    	pilot=new Control(0);
    	GearControl.robotInit();
 //   	AutoOutputs.robotInit();
 //   	AutoInputs.robotInit();
 //   	AutoOutputs.setDriveBrake(true);
 //   	templates.GRIP3Cameras2Switched.robotInit();
 //   	cpi.SimpleTwoCamera.init(0);
    	Vision2017.robotInit(2);
  //  	templates.GRIP3X1v2.robotInit();
  //  	templates.GRIPIntermediate3.robotInit();
 //   	templates.GRIPIntermediate2.robotInit();
 //   	templates.GRIPIntermediate.robotInit();
 //   	imageProcessor=new GRIP(0,1,2);
 //   	SimpleCamera.init(0);
 //   	TestSimpleEncoder.robotInit();
  //  	TestSimpleMultiMotorPWM.robotInit();
 //   	TestSimpleSpikeRelay.robotInit();
    	ShooterControl2.setInstance();
    	ShooterControl2.robotInit();
    }
    
    public void autonomousInit(){
 //   	Autonomous.autonomousInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//    	Autonomous.autonomousPeriodic();
    }
    
    
   
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//    	drive.TeleopPeriodic();
    	GearControl.TeleopPeriodic(pilot.aButton());
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
 //   	System.out.println("Summed: " + AutoInputs.getSummedEncoderCount());
 //   	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount());
 //   	System.out.println("Right Encoder: " + AutoInputs.getRightEncoderCount());
    	
    }
    
      
    /**
     * This function is called once every time the robot is disabled
     */
    public void disabledInit(){
 //   	TestSimpleSpikeRelay.disabledInit();
  //  	TestSimpleMultiMotorPWM.disabledInit();
 //   	TestSimpleEncoder.disabledInit();
 //   	AutoInputs.freeEncoders();
    	ShooterControl2.disabledInit();
    	
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
  //  	TestSimpleMultiMotorPWM.testInit();
 //   	TestSimpleSpikeRelay.testInit();
 //   	TestSimpleEncoder.testInit();
    	ShooterControl2.testInit();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
  //  	TestSimpleMultiMotorPWM.testPeriodic();
//    	TestSimpleSpikeRelay.testPeriodic();
//    	TestSimpleEncoder.testPeriodic();
 //   	drive.TestPeriodic();
    	ShooterControl2.testPeriodic();
    }

    /**
     * This function is called periodically when the robot is disabled
     */
    public void disabledPeriodic(){
    	//System.out.println(timer.get());
 //   	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}