
package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CameraServer;
import cpi.BallHandler;
import cpi.BallRetain;
import cpi.Climber_Scissors;
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
	public static GyroControl gyro;
//	public static ADXRS450_Gyro gyro;
//	public static AnalogInput ultra; 
	public static Drive drive;
	Shooter shooter;
	Elevator elevator;
	public static XBox360 pilot;
	BallHandler ball;
	//TODO
//	Climber_Scissors climber;
	CameraServer server;
	
	public static boolean disableCANTalons = false; //make this true when you need to run the code without can talons connected
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	final static public String header="2016 Competition ver. 2.0"; // This is required	
	public static double targetAngleDistance;
	
    public void robotInit() {
    	initialize();
    }
    
    void initialize(){
    	server=CameraServer.getInstance();
    	server.startAutomaticCapture("cam0");;
    	Autonomous.robotInit();
    	enc1 = new Encoder(1, false);
//    	enc1 = new Encoder();
    	enc1.robotInit();
    	enc3 = new Encoder(3, true);
//    	enc3 = new Encoder ();
    	enc3.robotInit();
    	gyro = new GyroControl(1);//TODO change back to 1
//    	gyro = new ADXRS450_Gyro();
//    	ultra = new AnalogInput(1);
    	drive= new Drive("/Teleop Drive");
    	drive.robotInit();
    	shooter= new Shooter("/Teleop Shooter");
    	shooter.robotInit();
    	pilot=new XBox360("Pilot");
    	pilot.robotInit();
    	ball = new BallHandler("/Ball Handler");
    	ball.robotInit();
    	BallRetain.robotInit();
    	//TODO
    	//climber = new Climber_Scissors("/Climber_Scissors");
    	//climber.robotInit();
    	cpi.Preferences.initialize();// !!Must be last statement in initialize!!
    }
    
    public void autonomousInit(){
    	Autonomous.autonomousInit();
    	gyro.reset();
//    	ultra.resetAccumulator();
    	enc1.autoInit();
    	enc3.autoInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Autonomous.autonomousPeriodic();
//    	System.out.println("Encoder turn: " + Math.abs(enc1.getRotation()-enc3.getRotation()));
    }
    
    public void teleopInit(){
    	drive.TeleopInit();
    	enc1.TeleopInit();
    	enc3.TeleopInit();
    	gyro.reset();
    	BallRetain.telopInit();
//    	ultra.resetAccumulator();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	pilot.teleopPeriodic();
    	drive.TeleopPeriodic();
    	BallRetain.telopPeriodic();
    	shooter.teleopPeriodic();
    	ball.TeleopPeriodic();
    	enc1.TeleopPeriodic();
    	enc3.TeleopPeriodic();
    	//TODO
    	//climber.teleopPeriodic();
//    	System.out.println("Ultrasonic: " + ultra.getAccumulatorCount());
//    	System.out.println("Encoder avg rotaion: " + enc1.getAverageRotation(enc3));
//    	System.out.println("Gyro Angle: " + gyro.getAngle());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void disabledInit(){
//    	gyro.free();
//    	ultra.free();
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