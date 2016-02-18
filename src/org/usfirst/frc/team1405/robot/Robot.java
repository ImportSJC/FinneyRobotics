
package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import cpi.BallHandler;
//import edu.wpi.first.wpilibj.CANTalon;
//import cpi.CANTalon;
import cpi.Drive;
import cpi.Elevator;
import cpi.Encoder;
import cpi.Shooter;
import cpi.XBox360;
import cpi.auto.Autonomous;
import cpi.auto.GyroControl;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
	public static Encoder enc1;
	public static Encoder enc3;
	public static GyroControl gyroControl;
	public static Drive drive;
	Shooter shooter;
	Elevator elevator;
	public static XBox360 pilot;
	BallHandler ball;
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	final static public String header="2016 Competition ver. 1.0"; // This is required	
	public static double targetAngleDistance;
	
    public void robotInit() {
    	initialize();
    }
    
    void initialize(){
    	Autonomous.robotInit();
    	enc1 = new Encoder(1, false);
//    	enc1 = new Encoder();
    	enc1.robotInit();
    	enc3 = new Encoder(3, true);
//    	enc3 = new Encoder ();
    	enc3.robotInit();
    	gyroControl = new GyroControl(1);
    	drive= new Drive("/Teleop Drive");
    	drive.robotInit();
    	shooter= new Shooter("/Teleop Shooter");
    	shooter.robotInit();
    	pilot=new XBox360("Pilot");
    	pilot.robotInit();
    	ball = new BallHandler("/Ball Handler");
    	ball.robotInit();
//    	cpi.Preferences.initialize();// !!Must be last statement in initialize!!
    	
    }
    
    public void autonomousInit(){
    	Autonomous.autonomousInit();
    	enc1.autoInit();
    	enc3.autoInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Autonomous.autonomousPeriodic();
//    	System.out.println("Encoder turn: " + Math.abs(enc1.getRotation()-enc3.getRotation()));
    	enc1.autoPeriodic();
    	enc3.autoPeriodic();
    }
    
    public void teleopInit(){
    	drive.TeleopInit();
    	enc1.TeleopInit();
    	enc3.TeleopInit();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	pilot.teleopPeriodic();
    	drive.TeleopPeriodic();
    	shooter.teleopPeriodic();
    	ball.TeleopPeriodic();
    	enc1.TeleopPeriodic();
    	enc3.TeleopPeriodic();
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
//    	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}