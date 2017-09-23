
package org.usfirst.frc.team1405.robot;

import java.sql.Time;

import org.usfirst.frc.team1405.robot.Vision.Vision2017;

import com.ctre.CANTalon;
import com.sun.glass.ui.Timer;

import cpi.Arduino_LightControl;
import cpi.Drive;
import cpi.GearCounting;
import cpi.PreFlightCheck;
import cpi.XBox360;
import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;
import cpi.auto.Auto_Drive;
import cpi.auto.conditions.And;
import cpi.SimpleCamera;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
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
   static public XBox360 operator;
   
   static private double rumbleTime = -1;
   
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
    	operator=new XBox360(1);
    	Autonomous.robotInit();
    	drive= new Drive(cpi.Drive.DIRECT_TANK);
    	drive.robotInit();
    	GearControl.robotInit();
//    	Arduino_LightControl.robotInit(7, 8);
    	Ball_Intake.robotInit();
    	AutoOutputs.robotInit();
     	AutoInputs.robotInit();
    	AutoOutputs.setDriveBrake(true);
    	GearCounting.robotInit();
 //   	templates.GRIP3Cameras2Switched.robotInit();
//    	cpi.SimpleTwoCamera.init(0);
//    	Vision2017.robotInit(2);
  //  	templates.GRIP3X1v2.robotInit();
  //  	templates.GRIPIntermediate3.robotInit();
 //   	templates.GRIPIntermediate2.robotInit();
 //   	templates.GRIPIntermediate.robotInit();
 //   	imageProcessor=new GRIP(0,1,2);
    	SimpleCamera.init(0);
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
    	AutoOutputs.autoInit();
    	ShooterControl.teleopInit();
    	GearControl.TeleopPeriodic(false, false, false, false, false, false);
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
//    	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount() + " Right Encoder: " + AutoInputs.getRightEncoderCount());
//    	System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle());
//    	GearControl.TeleopPeriodic(false, false, false, false, false);
//    	ShooterControl.teleopPeriodic(false, false, false, false);
    	AutoInputs.updateEncoderRates();
//    	System.out.println("Summed Encoder Count: " + AutoInputs.getSummedEncoderCount() + " Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
//    	System.out.println("About to call left rate");
//    	System.out.println("Left Rate: " + AutoInputs.getLeftEncoderRate() + " Drive direction: " + AutoInputs.getEncoderDriveDirection());
//    	System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle() + " Rate: " + AutoInputs.getGyroRate());
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg() + " summed rate: " + AutoInputs.getSummedEncoderRate());
    	Autonomous.autonomousPeriodic();
    	ShooterControl.runTestingVoltage();
//    	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount() + " Right Encoder: " + AutoInputs.getRightEncoderCount());
    }
    
    @Override		    
    public void teleopInit(){
    	rumbleTime=-1;
    	AutoInputs.TeleInit();
    	ShooterControl.teleopInit();
    	GearControl.teleopInit();
    	GearCounting.teleInit();
    }

   
    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
//    	AutoInputs.updateEncoderRates();
    	//print the x and y from the vision on the camera
//    	System.out.println("length: " + Vision2017.getX().length);
//    	for(int i = 0; i < Vision2017.getX().length; i++){
//    		System.out.println("X: " + Vision2017.getX()[i] + " Y: " + Vision2017.getY()[i]);
//    	}
    	
//    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
//    	System.out.println("left Rate: " + AutoInputs.getLeftEncoderRate());
//    	System.out.println("Summed Count: " + AutoInputs.getSummedEncoderCount() + " Summed Rate: " + AutoInputs.getSummedEncoderRate());
//    	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount() + " Right Encoder: " + AutoInputs.getRightEncoderCount());
//    	System.out.println("Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Drive direction: " + AutoInputs.getEncoderDriveDirection());
    	ShooterControl.teleopPeriodic(pilot.leftTriggerPressed(), pilot.directionalPadUp(), pilot.directionalPadDown(), operator.rightBumper());
    	Ball_Intake.teleopPeriodic(pilot.leftBumper());
    	GearCounting.TeleopPeriodic(operator.aButton(), operator.yButton());
    	
    	//drive back and drop the gear
    	if(pilot.xButton() && !GearControl.ratchetEngaged){
    		GearControl.TeleopPeriodic(false, false, true, false, false, false);
    	}else{
    		drive.TeleopPeriodic();
    		GearControl.TeleopPeriodic(pilot.rightBumper(), pilot.rightTriggerPressed(), false, pilot.bButton(), pilot.yButton(), pilot.aButton());
    	}
    	
//    	System.out.println("MatchTime: " + DriverStation.getInstance().getMatchTime());
    	
    	if(DriverStation.getInstance().getMatchTime() < 30 && DriverStation.getInstance().getMatchTime() > 25 && rumbleTime == -1){
//    		System.out.println("Case 1");
    		setRumbleTime();
    	}else if(DriverStation.getInstance().getMatchTime() < 25 && DriverStation.getInstance().getMatchTime() > 20 && rumbleTime != -1){
//    		System.out.println("Case 2");
    		rumbleTime = -1;
    	}else if(DriverStation.getInstance().getMatchTime() < 20 && DriverStation.getInstance().getMatchTime() > 15 && rumbleTime == -1){
//    		System.out.println("Case 3");
    		setRumbleTime();
    	}
//    	System.out.println("rumbletime: " + rumbleTime);
    	if(rumbleTime > 0){
//    		System.out.println("rumble");
    		pilot.rightRumble(150);
    		pilot.leftRumble(150);
    		rumbleTime--;
    	}else{
//    		System.out.println("don't rumble");
    		pilot.rightRumble(0);
    		pilot.leftRumble(0);
    	}
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
    	AutoOutputs.disabledInit();
    	pilot.rightRumble(0);
		pilot.leftRumble(0);
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
  //  	TestSimpleMultiMotorPWM.testInit();
 //   	TestSimpleSpikeRelay.testInit();
 //   	TestSimpleEncoder.testInit();
    	
//    	ShooterControl.teleopInit();
    	teleopInit();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
  //  	TestSimpleMultiMotorPWM.testPeriodic();
//    	TestSimpleSpikeRelay.testPeriodic();
//    	TestSimpleEncoder.testPeriodic();
//    	drive.TestPeriodic();
//    	ShooterControl.teleopPeriodic(true, pilot.directionalPadUp(), pilot.directionalPadDown(), pilot.aButton());
//    	ShooterControl.testPeriodic();
    	
//    	AutoOutputs.setDrive(0.5, -0.1);
    	teleopPeriodic();
//    	GearControl.climberMotor.set(1.0);
//    	GearControl.gearFeedMotor.set(1.0);
//    	
//		System.out.println("Climber current: " + GearControl.climberMotor.getOutputCurrent());
//		System.out.println("Gear current: " + GearControl.gearFeedMotor.getOutputCurrent());
    	//pre-flight check run
    	PreFlightCheck.run(pilot.aButton());
    	
    	System.out.println("Avg Distance: " + AutoInputs.getEncoderDistanceAvg());
    	System.out.println("Left Encoder: " + AutoInputs.getLeftEncoderCount() + " Right Encoder: " + AutoInputs.getRightEncoderCount());
    	System.out.println("Gyro Angle: " + AutoInputs.getGyroAngle());
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
    
    private void setRumbleTime(){
    	rumbleTime = 40;
    }
}
