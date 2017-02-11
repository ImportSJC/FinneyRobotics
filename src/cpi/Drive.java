package cpi;

import java.util.concurrent.Callable;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.outputDevices.MotorController;

public class Drive {
	/**
	 * Max speed of the drive system
	 */
	static final double MAX_SPEED = 1.0;
	public static final String DIRECT_MECANUM="Direct Mecanum";
	public static final String DIRECT_TANK="Direct Tank";
	public static final String DIRECT_HDRIVE="Direct H Drive";
	public static final String FRC_ARCADE="FRC Arcade";
	public static final String FRC_MECANUM="FRC Mecanum";
	public static final String FRC_HDRIVE="FRC H Drive";
	public static final String CUSTOM_TANK_HDRIVE="Custom Tank H Drive";
	public static Callable<Void> controlStates = null;
	
	public Drive(String name){
		
		this.name=name;
	
		System.out.println("INSTANTIATE THE MOTOR CONTROLLERS");
			
		//old base
	//	right1 =  new MotorController(3);
	//	right2 =  new MotorController(4);
	//	left1 =  new MotorController(1);
	//	left2 =  new MotorController(2);
		
		//new base
		right1 =  new MotorController(4);
		right2 =  new MotorController(5);
		right3 =  new MotorController(6);
		left1 =  new MotorController(1);
		left2 =  new MotorController(2);
		left3 =  new MotorController(3);
		
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
		Callable<Void> tankDrive = CallableControlModes.tankDrive();
//		Callable<Void> arcadeDrive = new
//		Callable<Void> rightSingleStickArcadeDrive = new 
//		Callable<Void> leftSingleStickArcadeDrive = new 
		
		
		
		MySet.addControlMode(new ControlMode(tankDrive, "Tank Drive"));
//		MySet.addControlMode(new ControlMode(arcadeDrive, "Turn 90 degrees."));
//		MySet.addControlMode(new ControlMode(rightSingleStickArcadeDrive, "Drop off the center airship gear."));
//		MySet.addControlMode(new ControlMode(leftSingleStickArcadeDrive, "Drop off the side airship gear."));
		
		MySet.assignControlMode(0);
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
		  right1.set(right);
		  right2.set(right);
		  right3.set(right);
		  left1.set(left);
		  left2.set(left);
		  left3.set(left);
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
//		tankDrive();
//		arcadeDrive();
//		singlestickarcadeDrive();
		try {
			controlStates.call();
		} catch (Exception e) {
			System.out.println("The Call BROKE!!!");
			e.printStackTrace();
		}
		
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
  
  
  static double rightFrontMotor;
  static double leftFrontMotor;
  static double rightRearMotor;
  static double leftRearMotor;
  static double rightMotor;
  static double leftMotor;
  static double centerMotor;
 }