package cpi;

import org.usfirst.frc.team1405.robot.ID_Assignments;
import org.usfirst.frc.team1405.robot.Robot;

import cpi.ConrolModes.ArcadeDrive;
import cpi.ConrolModes.LeftSSAD;
import cpi.ConrolModes.RightSSAD;
import cpi.ConrolModes.TankDrive;
import cpi.outputDevices.MotorController;

public class Drive {
	private boolean sixSimDriveBase = true;
	
	/**
	 * Max speed of the drive system
	 */
	public static final double MAX_SPEED = 1.0;
	
	public static final String DIRECT_MECANUM="Direct Mecanum";
	public static final String DIRECT_TANK="Direct Tank";
	public static final String DIRECT_HDRIVE="Direct H Drive";
	
	public static ControlMode controlStates = null;
	private static boolean bButtonDown = false;
	
	public Drive(String name){
		
		this.name=name;
	
		System.out.println("INSTANTIATE THE MOTOR CONTROLLERS");
		
		if(!sixSimDriveBase){
			//old base
			right1 =  new MotorController(ID_Assignments.DRIVE_RIGHT_JAGUAR_MOTOR_1);
			right2 =  new MotorController(ID_Assignments.DRIVE_RIGHT_JAGUAR_MOTOR_2);
			left1 =  new MotorController(ID_Assignments.DRIVE_LEFT_JAGUAR_MOTOR_1);
			left2 =  new MotorController(ID_Assignments.DRIVE_LEFT_JAGUAR_MOTOR_2);
		}else{
			//new base
			right1 =  new MotorController(ID_Assignments.DRIVE_RIGHT_TALON_MOTOR_1);
			right2 =  new MotorController(ID_Assignments.DRIVE_RIGHT_TALON_MOTOR_2);
			right3 =  new MotorController(ID_Assignments.DRIVE_RIGHT_TALON_MOTOR_3);
			left1 =  new MotorController(ID_Assignments.DRIVE_LEFT_TALON_MOTOR_1);
			left2 =  new MotorController(ID_Assignments.DRIVE_LEFT_TALON_MOTOR_2);
			left3 =  new MotorController(ID_Assignments.DRIVE_LEFT_TALON_MOTOR_3);
		}
		
	//	centerHTalon1 =  new CANTalon(5);
	//	centerHTalon2 =  new CANTalon(6);
		
		mode=name;
	}
	
	public void robotInit(){
		
//		    right1.EnableCurrentLimit(true);
//		    right2.EnableCurrentLimit(true);
//		    right3.EnableCurrentLimit(true);
//		    left1.EnableCurrentLimit(true);
//		    left2.EnableCurrentLimit(true);
//		    left3.EnableCurrentLimit(true);
//		    
//		    right1.setCurrentLimit(35);
//			right2.setCurrentLimit(35);
//			right3.setCurrentLimit(35);
//			left1.setCurrentLimit(35);
//			left2.setCurrentLimit(35);
//			left3.setCurrentLimit(35);
		
		createControlModes();
	}
	
	public static void createControlModes(){
		TankDrive tankDrive = new TankDrive();
		ArcadeDrive arcadeDrive = new ArcadeDrive();
		LeftSSAD leftSSAD = new LeftSSAD();
		RightSSAD rightSSAD = new RightSSAD();
		
		MySet.addControlMode(arcadeDrive);
		MySet.addControlMode(tankDrive);
		MySet.addControlMode(leftSSAD);
		MySet.addControlMode(rightSSAD);
		
		MySet.assignControlMode(0);
	}
	
	public static void DisabledPeriodic(){
		if(Robot.pilot.bButton() && !bButtonDown){
			MySet.assignNextControlMode();
		}
		bButtonDown = Robot.pilot.bButton();
	}
	
//	public void mecanumMotors(double rightFront,double rightRear,double leftFront,double leftRear){
//		  rightFrontTalon1.set(rightFront);
//		  rightFrontTalon2.set(rightFront);
//		  leftFrontTalon1.set(leftFront);
//		  leftFrontTalon2.set(leftFront);
//		  rightRearTalon1.set(rightRear);
//		  rightRearTalon2.set(rightRear);
//		  leftRearTalon1.set(leftRear);
//		  leftRearTalon2.set(leftRear);
//		
//	}
	
	public void tankMotors(double right,double left){
		//slow down the drive system
		if(Robot.pilot.leftBumper()){
			right = right/2;
			left = left/2;
		}
		
		if(sixSimDriveBase){
			right1.set(right);
			right2.set(right);
			right3.set(right);
			left1.set(left);
			left2.set(left);
			left3.set(left);
		}else{
			right1.set(right);
			right2.set(right);
			left1.set(left);
			left2.set(left);
		}
	}
	
//	public void hdriveMotors(double right,double left,double center){
//		  rightTalon1.set(right);
//		  rightTalon2.set(right);
//		  leftTalon1.set(left);
//		  leftTalon2.set(left);
//		  centerHTalon1.set(center);
//		  centerHTalon2.set(center);
//	}
	
	public void TeleopPeriodic(){//TODO split up drive class into a separate class for h,tank,and mechanum. no need for them all to be in a single class.
		controlStates.run();
		
		switch(mode){
		case DIRECT_MECANUM:
//			mecanumMotors( rightFrontMotor, rightRearMotor,leftFrontMotor,leftRearMotor);
	  break;
	  
		case DIRECT_TANK:
			tankMotors(rightMotor,leftMotor);
	  break;
	  
		case DIRECT_HDRIVE: 
//			hdriveMotors(rightMotor,leftMotor,centerMotor);
	  break;
		}
	}
	
	public void TestPeriodic(){
		TeleopPeriodic();
		
		if(!sixSimDriveBase){
			//old base
			right1.driveMotorCurrentUpdate();
			right2.driveMotorCurrentUpdate();
			left1.driveMotorCurrentUpdate();
			left2.driveMotorCurrentUpdate();
		}else{
			//new base
			right1.driveMotorCurrentUpdate();
			right2.driveMotorCurrentUpdate();
			right3.driveMotorCurrentUpdate();
			left1.driveMotorCurrentUpdate();
			left2.driveMotorCurrentUpdate();
			left3.driveMotorCurrentUpdate();
		}
		
		System.out.println("RightMotor: " + rightMotor);
		System.out.println("LeftMotor: " + leftMotor);
		System.out.println("Left Joystick ( " + -Robot.pilot.leftStickYaxis() + " , " + Robot.pilot.leftStickXaxis() + " )");
		System.out.println("Right Joystick ( " + -Robot.pilot.rightStickYaxis() + " , " + Robot.pilot.rightStickXaxis() + " )");
	}


	String mode;
	boolean tank;
	boolean mecanun;
	boolean HDrive;
	
  String name;
  
  static public MotorController right1;
  static public MotorController right2;
  static public MotorController right3;
  static public MotorController left1;
  static public MotorController left2;
  static public MotorController left3;
  
//  CANTalon centerHTalon1;
//  CANTalon centerHTalon2;
  
  
  public static double rightFrontMotor;
  public static double leftFrontMotor;
  public static double rightRearMotor;
  public static double leftRearMotor;
  public static double rightMotor;
  public static double leftMotor;
  public static double centerMotor;
 }