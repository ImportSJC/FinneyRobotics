
package org.usfirst.frc.team1405.robot;

import cpi.Drive;
import cpi.GRIP;
import cpi.XBox360;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
   
  
   
   Drive drive;
   static public XBox360 pilot;
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	final static public String header="2016 Competition ver. 1.0"; // This is required
	private static NetworkTable settings;
	
    public void robotInit() {
    	initialize();
    }
    void initialize(){
    	settings = NetworkTable.getTable("Test Table/SJC");
    	settings.putNumber("testVar", 5.5);
    	Autonomous.robotInit();
    	drive= new Drive("Teleop Drive");
    	drive.robotInit();
    	pilot=new XBox360(0);
    	AutoOutputs.robotInit();
    	AutoInputs.robotInit();
    	
    	AutoOutputs.setDriveBrake(true);
    	
    	GRIP.init();
    }
    
    public void autonomousInit(){
    	AutoInputs.AutoInit();
    	Autonomous.autonomousInit();
    	AutoOutputs.AutoInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//    	System.out.println("Summed Encoder Count: " + AutoInputs.getSummedEncoderCount());
    	System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle() + " Rate: " + AutoInputs.getGyroRate());
    	Autonomous.autonomousPeriodic();
    }
    
    @Override
    public void teleopInit(){
    	AutoInputs.TeleInit();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	drive.TeleopPeriodic();
    	System.out.println("Left Encoder: " + AutoInputs.getSummedEncoderCount());
    	System.out.println("Right Encoder: " + AutoInputs.getRightEncoderCount());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void disabledInit(){
    	AutoInputs.freeEncoders();
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
    }
    public void testPeriodic() {
    	drive.TestPeriodic();
    }
    
    public void disabledPeriodic(){
    	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}