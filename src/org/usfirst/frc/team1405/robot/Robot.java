
package org.usfirst.frc.team1405.robot;

import org.usfirst.frc.team1405.robot.Vision.Vision2017;

import cpi.Arduino_LightControl;
import cpi.Drive;
import cpi.XBox360;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
	
    public void robotInit() {
    	System.out.println("Robot Init");
    	initialize();
    }
    void initialize(){
    	pilot=new XBox360(0);
    	Autonomous.robotInit();
    	drive= new Drive(cpi.Drive.DIRECT_TANK);
    	drive.robotInit();
    	GearControl.robotInit();
    	Arduino_LightControl.robotInit(2, 3);
    	Ball_Intake.robotInit();
    	AutoOutputs.robotInit();
     	AutoInputs.robotInit();
    	AutoOutputs.setDriveBrake(true);
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
    	String MODE=ShooterControl.Mode.TALON_SRX;
    	int shooterTalonID=10;
    	int shooterJagID=8;
    	int encoderA=5;
    	int encoderB=6;
    	int gateTalonID=11;
    	int gateJagID=9;
    	int mixerTalon=12;
    	int mixerJag=7;
    	ShooterControl.robotInit( MODE);
    }
    
    public void autonomousInit(){
    	AutoInputs.AutoInit();
    	Autonomous.autonomousInit();
    	AutoOutputs.ResetValues();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	AutoInputs.updateEncoderRates();
    	System.out.println("Summed Encoder Count: " + AutoInputs.getSummedEncoderCount() + " Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
    	System.out.println("About to call left rate");
    	System.out.println("Left Rate: " + AutoInputs.getLeftEncoderRate() + " Drive direction: " + AutoInputs.getEncoderDriveDirection());
//    	System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle() + " Rate: " + AutoInputs.getGyroRate());
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
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
//    	AutoInputs.updateEncoderRates();
    	drive.TeleopPeriodic();
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
//    	System.out.println("Summed Count: " + AutoInputs.getSummedEncoderCount());
//    	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount() + " Right Encoder: " + AutoInputs.getRightEncoderCount());
//    	System.out.println("Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Drive direction" + AutoInputs.getEncoderDriveDirection());
    	GearControl.TeleopPeriodic(pilot.rightBumper());
    	ShooterControl.teleopPeriodic(true, false, false, false);
    	Ball_Intake.teleopPeriodic(pilot.leftBumper());
    }
    
      
    /**
     * This function is called once every time the robot is disabled
     */
    public void disabledInit(){
 //   	TestSimpleSpikeRelay.disabledInit();
  //  	TestSimpleMultiMotorPWM.disabledInit();
 //   	TestSimpleEncoder.disabledInit();
 //   	AutoInputs.freeEncoders();
    	ShooterControl.disabledInit();
    	
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
  //  	TestSimpleMultiMotorPWM.testInit();
 //   	TestSimpleSpikeRelay.testInit();
 //   	TestSimpleEncoder.testInit();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
  //  	TestSimpleMultiMotorPWM.testPeriodic();
//    	TestSimpleSpikeRelay.testPeriodic();
//    	TestSimpleEncoder.testPeriodic();
 //   	drive.TestPeriodic();
    	ShooterControl.testPeriodic();
    }

    /**
     * This function is called periodically when the robot is disabled
     */
    public void disabledPeriodic(){
    	//System.out.println(timer.get());
 //   	cpi.autoSupportClasses.Set.disabledPeriodic();
    	Autonomous.disabledPeriodic();
    	Drive.DisabledPeriodic();
    }
}
