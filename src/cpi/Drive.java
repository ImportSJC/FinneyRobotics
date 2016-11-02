package cpi;

import java.awt.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class Drive {
	static final String DIRECT_MECANUM="Direct Mecanum";
	static final String DIRECT_TANK="Direct Tank";
	static final String DIRECT_HDRIVE="Direct H Drive";
	static final String FRC_ARCADE="FRC Arcade";
	static final String FRC_MECANUM="FRC Mecanum";
	static final String FRC_HDRIVE="FRC H Drive";
	static final String CUSTOM_TANK_HDRIVE="Custom Tank H Drive";
	public Drive(String name){
	this.name=name;
	
//	rightFrontTalon1 = new CANTalon(1);
//	rightFrontTalon2 = new CANTalon(2);
//	leftFrontTalon1 = new CANTalon(3);
//	leftFrontTalon2 = new CANTalon(4);
//	rightRearTalon1 = new CANTalon(5);
//	rightRearTalon2 = new CANTalon(6);
//	leftRearTalon1 =  new CANTalon(7);
//	leftRearTalon2 =  new CANTalon(8);
	
	rightTalon1 =  new CANTalon(3);
	rightTalon2 =  new CANTalon(4);
	leftTalon1 =  new CANTalon(1);
	leftTalon2 =  new CANTalon(2);
	
//	centerHTalon1 =  new CANTalon(5);
//	centerHTalon2 =  new CANTalon(6);
	
	mode=DIRECT_TANK;
	}
	
	public void robotInit(){}
	
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
		  rightTalon1.set(right);
		  rightTalon2.set(right);
		  leftTalon1.set(left);
		  leftTalon2.set(left);
		
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
		rightMotor = org.usfirst.frc.team1405.robot.Robot.pilot.rightStickYaxis;
		leftMotor = -org.usfirst.frc.team1405.robot.Robot.pilot.leftStickYaxis;
		System.out.println("RightMotor: " + rightMotor);
		System.out.println("LeftMotor: " + leftMotor);
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

	String mode;
	boolean tank;
	boolean mecanun;
	boolean HDrive;
	
  String name;
  
//  CANTalon rightFrontTalon1;
//  CANTalon rightFrontTalon2;
//  CANTalon leftFrontTalon1;
//  CANTalon leftFrontTalon2;
//  CANTalon rightRearTalon1;
//  CANTalon rightRearTalon2;
//  CANTalon leftRearTalon1;
//  CANTalon leftRearTalon2;
  
  static public CANTalon rightTalon1;
  static public CANTalon rightTalon2;
  static public CANTalon leftTalon1;
  static public CANTalon leftTalon2;
  
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
