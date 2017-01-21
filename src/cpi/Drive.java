package cpi;

import org.usfirst.frc.team1405.robot.Robot;

import cpi.outputDevices.MotorController;

public class Drive {
	/**
	 * Max speed of the drive system
	 */
	static final double MAX_SPEED = 0.5;
	//comment here
	static final String DIRECT_MECANUM="Direct Mecanum";
	static final String DIRECT_TANK="Direct Tank";
	static final String DIRECT_HDRIVE="Direct H Drive";
	static final String FRC_ARCADE="FRC Arcade";
	static final String FRC_MECANUM="FRC Mecanum";
	static final String FRC_HDRIVE="FRC H Drive";
	static final String CUSTOM_TANK_HDRIVE="Custom Tank H Drive";
	public Drive(String name){
	this.name=name;
	
	right1 =  new MotorController(3);
	right2 =  new MotorController(4);
	left1 =  new MotorController(1);
	left2 =  new MotorController(2);
	
//	centerHTalon1 =  new CANTalon(5);
//	centerHTalon2 =  new CANTalon(6);
	
	mode=DIRECT_TANK;
	}
	
	public void robotInit(){}
	
	private void tankDrive(){
		rightMotor = -(Robot.pilot.rightStickYaxis() * MAX_SPEED);
		leftMotor = (Robot.pilot.leftStickYaxis() * MAX_SPEED);
	}
	
	private void arcadeDrive(){
		rightMotor = (-Robot.pilot.leftStickYaxis() * MAX_SPEED) - (Robot.pilot.rightStickXaxis() * MAX_SPEED);
		leftMotor = (-Robot.pilot.leftStickYaxis() * MAX_SPEED) + (Robot.pilot.rightStickXaxis() * MAX_SPEED);
		leftMotor = -leftMotor;
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
		  left1.set(left);
		  left2.set(left);
		
	}
	
//	public void hdriveMotors(double right,double left,double center){
//		  rightTalon1.set(right);
//		  rightTalon2.set(right);
//		  leftTalon1.set(left);
//		  leftTalon2.set(left);
//		  centerHTalon1.set(center);
//		  centerHTalon2.set( center);
//		
//	}
	
	public void TeleopPeriodic(){//TODO split up drive class into a separate class for h,tank,and mechanum. no need for them all to be in a single class.
		tankDrive();
//		arcadeDrive();
		
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
  static public MotorController left1;
  static public MotorController left2;
  
//  CANTalon centerHTalon1;
//  CANTalon centerHTalon2;
  
  
  double rightFrontMotor;
  double leftFrontMotor;
  double rightRearMotor;
  double leftRearMotor;
  double rightMotor;
  double leftMotor;
  double centerMotor;
}