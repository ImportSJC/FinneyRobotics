package cpi;


import cpi.Interface.*;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CANTalon;


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
	
	rightFrontMotor = new DoubleInput(name,"right Front Motor(s)");
	leftFrontMotor = new DoubleInput(name,"left Front Motor(s)");
	rightRearMotor = new DoubleInput(name,"right Rear Motor(s)");
	leftRearMotor = new DoubleInput(name,"left Rear Motor(s)");
	rightMotor = new DoubleInput(name,"right Motor(s)","XBox360-Pilot: Right Stick Y Axis");
	leftMotor = new DoubleInput(name,"left Motor(s)","XBox360-Pilot: Left Stick Y Axis");
	centerMotor = new DoubleInput(name,"center Motor(s)");
	
	motorGear = new BooleanInput(name,"extend Solenoid", "XBox360-Pilot:A Button");
	
//	rightFrontTalon1 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Right Front Motor #1",1);
//	rightFrontTalon2 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Right Front Motor #2",2);
//	leftFrontTalon1 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Left Front Motor #1",4);
//	leftFrontTalon2 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Left Front Motor #2",5);
//	rightRearTalon1 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Right Rear Motor #1",5);
//	rightRearTalon2 = CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Right Rear Motor #2",6);
//	leftRearTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Left Rear Motor #1",7);
//	leftRearTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_MECANUM,"Left Rear Motor #2",8);
	
//	rightTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_TANK,"Right Motor #1",1);
//	rightTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_TANK,"Right Motor #2",2);
//	leftTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_TANK,"Left Motor #1",3);
//	leftTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_TANK,"Left Motor #2",5);
	
	rightTalon1 = new CANTalon(1);
	rightTalon2 = new CANTalon(2);
	leftTalon1 = new CANTalon(3);
	leftTalon2 = new CANTalon(5);
	
//	rightHTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Right H Motor #1",1);
//	rightHTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Right H Motor #2",2);
//	leftHTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Left H Motor #1",3);;
//	leftHTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Left H Motor #2",4);;
//	centerHTalon1 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Center H Motor #1",5);;
//	centerHTalon2 =  CANTalon.getInstance(name+"/"+DIRECT_HDRIVE,"Center H Motor #2",6);;
	/*
	 * TODO Get rid of double semicolens.
	 */
	solenoid1 = new Solenoid(0);
	solenoid2 = new Solenoid(1);
	
	mode=new SetString(name,"Mode",DIRECT_TANK);
	tank=new NetBoolean(name+"/Select Mode",DIRECT_TANK,false);
	tank.lock();
	mecanun=new NetBoolean(name+"/Select Mode",DIRECT_MECANUM,false);
	mecanun.lock();
	HDrive=new NetBoolean(name+"/Select Mode",DIRECT_HDRIVE,false);
	HDrive.lock();
	
	
	
	tank.addActionListner(new ITableListener(){
		public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
			if(!(boolean)pvalue)return;
			mode.Value(DIRECT_TANK);
			tank.Value(false);
		}
	});
	mecanun.addActionListner(new ITableListener(){
		public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
			if(!(boolean)pvalue)return;
			mode.Value(DIRECT_MECANUM);
			mecanun.Value(false);
		}
	});
	HDrive.addActionListner(new ITableListener(){
		public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
			if(!(boolean)pvalue)return;
			mode.Value(DIRECT_HDRIVE);
			HDrive.Value(false);
		}
	});
	enableSecondMotors= new SetBoolean(name,"#2 motors are enabled",true);
	
	
	cpi.Preferences.addHardCodeListener(new ITableListener(){
		public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
			if((boolean)pvalue){
				tank.lock();
				mecanun.lock();
				HDrive.lock();
			}else{
				tank.unlock();	
				mecanun.unlock();	
				mecanun.unlock();	
			}
		}
	});
	}
	
	public void robotInit(){}
	
//	public void mecanumMotors(double rightFront,double rightRear,double leftFront,double leftRear){
//		  rightFrontTalon1.set(rightFront);
//		  if(enableSecondMotors.Value())rightFrontTalon2.set(rightFront);
//		  leftFrontTalon1.set(leftFront);
//		  if(enableSecondMotors.Value())leftFrontTalon2.set(leftFront);
//		  rightRearTalon1.set(rightRear);
//		  if(enableSecondMotors.Value())rightRearTalon2.set(rightRear);
//		  leftRearTalon1.set(leftRear);
//		  if(enableSecondMotors.Value())leftRearTalon2.set(leftRear);
//		
//	}
	
	public void tankMotors(double right,double left){
		
		  rightTalon1.set(right);
		  if(enableSecondMotors.Value())rightTalon2.set(right);
		  leftTalon1.set(left);//TODO fix this so it says leftTalon2, i think
		  if(enableSecondMotors.Value())leftTalon2.set(left);
		  System.out.println("TANK MODE right: " + right + " left: " + left);
	}
	
//	public void hdriveMotors(double right,double left,double center){
//		  rightTalon1.set(right);
//		  if(enableSecondMotors.Value())rightTalon2.set(right);
//		  leftTalon1.set(left);
//		  if(enableSecondMotors.Value())leftTalon2.set(left);
//		  centerHTalon1.set(center);
//		  if(enableSecondMotors.Value())centerHTalon2.set( center);
//		  
//	}
	
	public void gearBoxSolenoids (boolean motorGear){
		solenoid1.set(motorGear);
		solenoid2.set(!motorGear);
		System.out.println("s1: " + motorGear + " s2: " + !motorGear);
	}
	
	public void TeleopPeriodic(){
		switch(mode.Value()){
		case DIRECT_MECANUM:
//			mecanumMotors( rightFrontMotor.Value(), rightRearMotor.Value(),leftFrontMotor.Value(),leftRearMotor.Value());
	  break;
	  
		case DIRECT_TANK:
			tankMotors(rightMotor.Value(),leftMotor.Value());
	  break;
	  
		case DIRECT_HDRIVE: 
//			hdriveMotors(rightMotor.Value(),leftMotor.Value(),centerMotor.Value());
	  break;
		}
		
		gearBoxSolenoids(motorGear.Value());
	}

	SetString mode;
	NetBoolean tank;
	NetBoolean mecanun;
	NetBoolean HDrive;
	
	SetBoolean enableSecondMotors;
	String name;
  
//  CANTalon rightFrontTalon1;
//  CANTalon rightFrontTalon2;
//  CANTalon leftFrontTalon1;
//  CANTalon leftFrontTalon2;
//  CANTalon rightRearTalon1;
//  CANTalon rightRearTalon2;
//  CANTalon leftRearTalon1;
//  CANTalon leftRearTalon2;
  CANTalon rightTalon1;
  CANTalon rightTalon2;
  CANTalon leftTalon1;
  CANTalon leftTalon2;
//  CANTalon rightHTalon1;
//  CANTalon rightHTalon2;
//  CANTalon leftHTalon1;
//  CANTalon leftHTalon2;
//  CANTalon centerHTalon1;
//  CANTalon centerHTalon2;
  Solenoid solenoid1;
  Solenoid solenoid2;
  
  DoubleInput rightFrontMotor;
  DoubleInput leftFrontMotor;
  DoubleInput  rightRearMotor;
  DoubleInput  leftRearMotor;
  DoubleInput  rightMotor;
  DoubleInput  leftMotor;
  DoubleInput  centerMotor;
  
  BooleanInput motorGear;
}
