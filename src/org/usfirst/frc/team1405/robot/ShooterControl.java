package org.usfirst.frc.team1405.robot;

import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import cpi.*;

public class ShooterControl {
	
	static String THIS_TABLE_NAME;
	
	static NetworkTable settings;
	static Jaguar shooterMotor ;
	static Jaguar gateMotor ;
	static double speed0;
	static double speed1;
	static int shooterID;
	static int gateID;
	static int mixerRelayID;
	static int ShooterEncoderChanelA;
	static int ShooterEncoderChanelB;
	
	static boolean isNotFirstInit;
	static Timer timer=new Timer();
	

	static String ENABLE="Enable";
	static String TOLLERANCE="Motor set speed tollerance";
	static String SHOOTER_THRESHOLD="Shooter threshold";
	static String GATE_THRESHOLD="Gate threshold";
	static double SHOOTER_THRESHOLD_VALUE=600;
	static double GATE_THRESHOLD_VALUE=500;
	static String GATE_SPEED="Gate speed";
	static double GATE_SPEED_VALUE=0.5;
	static String SHOOTER_SPEED="Shooter speed";
	static double SHOOTER_SPEED_VALUE=.47;
	static String SHOOTER_SPEED_EQUALS="Shooter speed = ";
	static EncoderControl shooterEncoder ;
	static boolean setGateOn;
	
	class Mode{
		public static final String PWM="PWM";
		public static final String TALON_SRX="Talon SRX";
	}
	ShooterControl(){
		
	}
	static public void setInstance(String mode, int shooterID,int ShooterEncoderChanelA,int ShooterEncoderChanelB,int gateID,int mixerRelayID){
	if(mode == Mode.PWM){
		THIS_TABLE_NAME= Set.TITLE+"/Test Beds/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
    	settings.putNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE);
    	settings.putNumber(GATE_THRESHOLD, GATE_THRESHOLD_VALUE);
    	settings.putNumber(GATE_SPEED, GATE_SPEED_VALUE);
    	settings.putNumber(SHOOTER_SPEED,SHOOTER_SPEED_VALUE);
    	settings.putBoolean(ENABLE,false);
    	ShooterControl.ShooterEncoderChanelA=ShooterEncoderChanelA;
    	ShooterControl.ShooterEncoderChanelB=ShooterEncoderChanelB;
    	ShooterControl.mixerRelayID=mixerRelayID;
    	ShooterControl.gateID=gateID;
    	ShooterControl.shooterID=shooterID;
    	
    	
	}

	if(mode == Mode.TALON_SRX){
	}
		
	}
	static public void setInstance(){
		THIS_TABLE_NAME= "Robot"+"/Test Beds/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		settings.putNumber(TOLLERANCE, 50.0);
    	settings.putNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE);
    	settings.putNumber(GATE_THRESHOLD, GATE_THRESHOLD_VALUE);
    	settings.putNumber(GATE_SPEED, GATE_SPEED_VALUE);
    	settings.putNumber(SHOOTER_SPEED,SHOOTER_SPEED_VALUE);
    	settings.putBoolean(ENABLE,false);
    	settings.putNumber("Shooter speed = ", 0.0);
    	shooterID=8;
    	ShooterEncoderChanelA=0;
    	ShooterEncoderChanelB=1;
    	gateID=9;
		
	}
	
	static public void robotInit(){
		
	}
		
	static public void testInit(){
		if(!settings.getBoolean(ENABLE, false))return;
    	if(isNotFirstInit)return;
    	shooterMotor=new Jaguar(shooterID);
    	gateMotor=new Jaguar(gateID);
    	isNotFirstInit=true;

    	shooterEncoder = new EncoderControl(ShooterEncoderChanelA,ShooterEncoderChanelB);
    	shooterEncoder.Init();
		
	}

	static public void testPeriodic(){
	    	if(!settings.getBoolean(ENABLE, false)){
	    		if(!isNotFirstInit)return;
	    		shooterMotor.set(0);
	    		gateMotor.set(0);
	    		return;
	    	}
	    	testInit();
	    	process();
		
	}	

	static public void process(){
		settings.putNumber("Shooter speed = ",shooterEncoder.getSpeed() );
		if(shooterEncoder.getSpeed()<settings.getNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE)){
			settings.putString("Process", "Below threshold");
			shooterMotor.set(1.0);
			if(shooterEncoder.getSpeed()<settings.getNumber(GATE_THRESHOLD, GATE_THRESHOLD_VALUE)){
				gateMotor.set(0.0);
			}else{
				gateMotor.set(settings.getNumber(GATE_THRESHOLD,GATE_THRESHOLD_VALUE));
			}
			
		}else if(shooterEncoder.getSpeed()>(settings.getNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE)+settings.getNumber(TOLLERANCE, 50.0))){
			settings.putString("Process", "Above  threshold + tollerance");
			shooterMotor.set(settings.getNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE)/2);
			shooterMotor.set(settings.getNumber(GATE_SPEED,GATE_SPEED_VALUE));
			
		}
		
		
		else{
			settings.putString("Process", "At threshold");
			shooterMotor.set(settings.getNumber(SHOOTER_SPEED,SHOOTER_SPEED_VALUE));
			gateMotor.set(settings.getNumber(GATE_SPEED,GATE_SPEED_VALUE));
			
		}
		
	}
	
	static public void disabledInit(){
		settings.putBoolean(ENABLE,false);
		if(!isNotFirstInit)return;
		shooterMotor.free();
		gateMotor.free();
		shooterEncoder.free();
		isNotFirstInit=false;
	}
}
