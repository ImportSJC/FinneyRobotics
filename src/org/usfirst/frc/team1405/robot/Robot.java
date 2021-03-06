package org.usfirst.frc.team1405.robot;

import com.kauailabs.navx.frc.AHRS;

import MotorController.MotorController;
import MotorController.MotorControllerRamped;
import MotorController.MotorController_Encoder;
import auto_modes.AutonomousGoal;
import auto_modes.AutonomousModes;
import auto_modes.RobotFieldPosition;
import control_modes.ArcadeDrive;
import control_modes.ControlMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.XboxController;
import general.Autonomous;
import general.CameraSwitch;
import general.CustomXBox;
import general.GameDataFirstPowerUp;
import general.GameDataFirstPowerUp.FieldSide;
import general.ServoController;
import logging.SimpleLogger;
import logging.SimpleLogger.LogLevel;
import logging.SimpleLogger.LogSubsystem;
import sensors.CustomLimitSwitch;
import subsystems_tele.BuddyBarRelease;
import subsystems_tele.Climbing;
import subsystems_tele.CubeMovement;
import subsystems_tele.Drive;


 /**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//TODO implement generic control mode picking
	//TODO implement generic auto picking
	
	//Constants
	
	//Helper Classes
  	private Drive drive;
  	private Autonomous auto;
  	private ControlMode controlMode;
  	private CustomXBox pilot;
  	private CustomXBox operator;
  	private CubeMovement cubeMovement;
  	private Climbing climb;
  	private AutonomousModes modes;
  	private GameDataFirstPowerUp field;
  	private CustomLimitSwitch upSensor;
	private CustomLimitSwitch downSensor;
	private BuddyBarRelease buddyBarRelease;
  	
  	//Encoder Configurations
  	private MotorController_Encoder driveEncoderLeft = new MotorController_Encoder(1024.0, true);
  	private MotorController_Encoder driveEncoderRight = new MotorController_Encoder(1024.0, true);
  	
  	//PID Configurations
  	
	//CANTalons
	private MotorController talon4 = new MotorController(4, true, 15.0/12.0, driveEncoderRight);
	private MotorController talon5 = new MotorController(5, true);
//	private MotorController talon6 = new MotorController(6, false);
	private MotorController talon1 = new MotorController(1, false, 15.0/12.0, driveEncoderLeft);
	private MotorController talon2 = new MotorController(2, false);
//	private MotorController talon3 = new MotorController(3, true);

	private MotorControllerRamped forklift = new MotorControllerRamped(7, 0.1, true);
	private MotorController cubeIntake1 = new MotorController(8);
	private MotorController cubeIntake2 = new MotorController(9, true, null);
	private MotorController cubeArmLeft = new MotorController(11);
	private MotorController retractIntake = new MotorController(51);
	
	private MotorControllerRamped winch = new MotorControllerRamped(10, 0.04, true);
	private MotorController robotCarrier = new MotorController(52);//Not on robot currently
	private MotorController hooks = new MotorController(3);//Not powered currently
	
  	private ServoController harvesterArmLeft = new ServoController(0, true, 0.5);
  	private ServoController harvesterArmRight = new ServoController(1, true , 0.5);
  	
//  	private ADXRS450_Gyro gyro = null;//new ADXRS450_Gyro();
//  	private AHRS gyro = new AHRS(Port.kMXP);
  	private AHRS gyro = new AHRS(I2C.Port.kMXP);
  	
  	private boolean bButton = false;
  	private boolean xButton = false;
  	private AutonomousGoal autoGoal = AutonomousGoal.SWITCH;
  	private RobotFieldPosition fieldPos = RobotFieldPosition.MIDDLE;

  	private Servo buddyBarServo;
	//Camera
	UsbCamera [] camera=new UsbCamera[2] ;
	CameraSwitch cameraSwitch;
	
	private final double drive_P = 4;
	private final double drive_I = 0.0005;
	private final double drive_D = 0;
	private final double drive_F = 0;
	
	public void robotInit() {
		talon2.setMasterTalon(1);
		talon5.setMasterTalon(4);
		
		talon1.setupMotionProfile(drive_P, drive_I, drive_D, drive_F);
		talon4.setupMotionProfile(drive_P, drive_I, drive_D, drive_F);
		
//		talon1.configCurrentLimitPeak(65, 50);
//		talon2.configCurrentLimitPeak(65, 50);
//		talon4.configCurrentLimitPeak(65, 50);
//		talon5.configCurrentLimitPeak(65, 50);
		
    	controlMode = new ArcadeDrive();
    	pilot = new CustomXBox(0);
    	operator = new CustomXBox(1);
    	upSensor = new CustomLimitSwitch(0);
    	downSensor = new CustomLimitSwitch(1);
    	buddyBarServo=new Servo(5);
    	buddyBarRelease= new BuddyBarRelease(buddyBarServo,0.0,1.0);
    	
    	//6-sim drive
//    	drive = new Drive(pilot, controlMode, talon4, talon5, talon6, talon1, talon2, talon3);
    	
    	//4-sim drive
    	drive = new Drive(pilot, controlMode, gyro, talon1, talon4);
    	
    	cubeMovement = new CubeMovement(pilot, operator, forklift, cubeIntake1, cubeIntake2, cubeArmLeft,
    			retractIntake, harvesterArmLeft, harvesterArmRight, upSensor, downSensor);
    	climb = new Climbing(operator, winch, robotCarrier, hooks);
   
    	field = new GameDataFirstPowerUp();
    	
    	SimpleLogger.log("About to start auto modes");
    	modes = new AutonomousModes(drive, cubeMovement, gyro);
    	auto = new Autonomous(modes.getCurrentAutoMode());
    	
    	camera[0] = CameraServer.getInstance().startAutomaticCapture(0);
    	camera[1] = CameraServer.getInstance().startAutomaticCapture(1);
    	camera[0].setResolution(640, 480);
    	camera[1].setResolution(640, 480);
    	camera[0].setBrightness(50);
    	camera[1].setBrightness(50);
    	camera[0].setExposureAuto();
    	camera[1].setExposureAuto();
    	
    	cameraSwitch=new CameraSwitch(camera);
    }
    
    public void autonomousInit(){
    	SimpleLogger.log("STARTING AUTO INIT", LogLevel.COMP, LogSubsystem.AUTO);
    	auto.AutonomousInit();
    	
    	drive.setTankDrive(0.0, 0.0);
    	resetSensors();
    	FieldSide myScale = field.getScale();
    	FieldSide mySwitch = field.getAllianceSwitch();
    	SimpleLogger.log("MyScale: " + myScale + " MySwitch: " + mySwitch, LogLevel.COMP, LogSubsystem.AUTO);
    	auto.setAutoMode(modes.chooseAutonomousMode(fieldPos, autoGoal,
    			myScale, mySwitch));
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic(){
    	auto.AutonomousPeriodic();
    	SimpleLogger.log("Drive distance: " + drive.getLeftDistance());
    }
    
    @Override
    public void teleopInit(){
    	resetSensors();
    }

    /**
     * This function is called periodically during operator control
     */
    
    private double right;
    private double left;
    
    public void teleopPeriodic() {
//    	SimpleLogger.log(talon4.getPosition()+", "+talon4.getDriveDistance());
    	cameraSwitch.incrementCamera(operator.aButton());
    	
//    	sensor.getState();
    	
    	drive.tank();
    	
    	cubeMovement.forkLift();
    	cubeMovement.cubeIntakeMechanism();
    	cubeMovement.cubeArms();
    	cubeMovement.cubeSpinMechanism();
    	//cubeMovement.retract();
    	
    	climb.winch();
    	//climb.robotCarrier();
    	climb.hooks();
    	
//    	if (right != talon4.getPosition() || left != talon1.getPosition()) {
//        	SimpleLogger.log("Talon 1: " + talon1.getPosition() + "\t Talon 4: " + talon4.getPosition());
//    	}
//    	right = talon4.getPosition();
//    	left = talon1.getPosition();
    	
    	buddyBarRelease.release(operator.xButton() && pilot.xButton());
 
  
    }
    
    public void disabledInit(){
    	resetSensors();
    	drive.setTankDrive(0, 0);
    	if(pid != null){
    		pid.disable();
    		pid.reset();
    	}
    	
//    	if(pid2 != null){
//    		pid2.disable();
//    		pid2.reset();
//    	}
    }
    
    public void disabledPeriodic(){
//    	SimpleLogger.log("Their DisabledPeriodic was called");
//    	AutoOutputs.setBrake(false);
    	
    	cameraSwitch.incrementCamera(pilot.aButton());
    	
  //  	auto.setAutoMode(modes.chooseAutonomousMode(RobotFieldPosition.RIGHT, AutonomousGoal.SWITCH,
    //			field.getScale(), field.getAllianceSwitch()));
    	
    	setFieldPosition();
    	setAutoGoal();
    	
       	
       	//switch between xbox controller and guitar controller for operator
//    	boolean operatorController = false;
//    	boolean previousYButtonState = pilot.yButton();
    	
//    	if (pilot.yButton() && !operatorController && !previousYButtonState) {
//
//    			SimpleLogger.log("Operator is using an Xbox controller");
//    			operatorController = true;
//    			
//    	} else if (pilot.yButton() && operatorController && !previousYButtonState) {
//    		
//    			SimpleLogger.log("operator is using a guitar");
//    			operatorController = false;
//    	}
    }		
    
    private void setFieldPosition(){
    	if(pilot.bButton() && !bButton){
    		if(autoGoal == AutonomousGoal.LINE){
    			autoGoal = AutonomousGoal.SWITCH;
    			SimpleLogger.log("Autonomous Goal Set: SWITCH", LogLevel.COMP, LogSubsystem.ALL);
    		}else{
    			autoGoal = AutonomousGoal.LINE;
    			SimpleLogger.log("Autonomous Goal Set: LINE", LogLevel.COMP, LogSubsystem.ALL);
    		}
    	}
    	
    	bButton = pilot.bButton();
    }
    
    private void setAutoGoal(){
    	if(pilot.xButton() && !xButton){
    		if(fieldPos == RobotFieldPosition.LEFT){
    			fieldPos = RobotFieldPosition.MIDDLE;
    			SimpleLogger.log("Robot Field Position Set: MIDDLE", LogLevel.COMP, LogSubsystem.ALL);
    		}else if (fieldPos == RobotFieldPosition.MIDDLE){
    			fieldPos = RobotFieldPosition.RIGHT;
    			SimpleLogger.log("Robot Field Position Set: RIGHT", LogLevel.COMP, LogSubsystem.ALL);
    		}else{
    			fieldPos = RobotFieldPosition.LEFT;
    			SimpleLogger.log("Robot Field Position Set: LEFT", LogLevel.COMP, LogSubsystem.ALL);
    		}
    	}
    	
    	xButton = pilot.xButton();
    }
    
    private void resetSensors(){
    	talon1.setPosition(0);
    	talon4.setPosition(0);
    	gyro.zeroYaw();//reset the yaw gyro
//    	if(gyro != null){
//    		gyro.reset();
//    	}
    }
    
    PIDController pid;
//    PIDController pid2;
    double currentTarget = 0;
    final double target = 90;
    
    @Override
    public void testInit(){
    	resetSensors();
//    	currentTarget = 0;
//    	
//    	PIDSource gyroSource = new PIDSource() {
//			
//    		PIDSourceType mySource = PIDSourceType.kDisplacement;
//    		
//			@Override
//			public void setPIDSourceType(PIDSourceType pidSource) {
//				this.mySource = pidSource;
//			}
//			
//			@Override
//			public double pidGet() {
//				return gyro.getAngle();
//			}
//			
//			@Override
//			public PIDSourceType getPIDSourceType() {
//				return mySource;
//			}
//		};
//		PIDOutput gyroOutput = new PIDOutput() {
//			
//			@Override
//			public void pidWrite(double output) {
//				drive.setTankDrive(output, -output);
//			}
//		};
//		
//    	pid = new PIDController(turn_P, turn_I, turn_D, gyroSource, gyroOutput);
//    	pid.setAbsoluteTolerance(0.1);
//    	pid.setOutputRange(-0.45, 0.45);
//    	pid.setSetpoint(0);
//    	pid.reset();
//    	pid.enable();
    }
    
    @Override
    public void testPeriodic(){
//    	SimpleLogger.log("L: " + talon1.getDriveDistance() + " R: " + talon4.getDriveDistance());
//    	SimpleLogger.log("gyro: " + gyro.getAngle() + " error: " + pid.getError() + " PID: " + pid.get());
//    	if(pid.onTarget() && gyro.getRate() < 0.1){
//    		pid.disable();
//    	}
    	
//    	if(currentTarget < target){
//    		currentTarget += 2;
////    		SimpleLogger.log("currentTarget: " + currentTarget + " target: " + target);
//    		pid.setSetpoint(currentTarget);
//    	}
    }
}

/**
 * Stephen Haiku Collection:
 * 
 * (1).
 * 
 * Stephen lost his car
 * He is so stupid and dumb
 * Where is your soda
 * 
 * (2).
 * 
 * Stephen is confused
 * His car is somehow missing
 * Just like the Pats score
 * 
 * (3).
 * 
 * Water reflections
 * Distorted by raindrops
 * And Stephen's not here
 */