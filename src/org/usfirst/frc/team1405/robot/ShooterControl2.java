package org.usfirst.frc.team1405.robot;

//import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import cpi.*;

public class ShooterControl2 {
	
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
	static double TOLLERANCE_VALUE=-50;
	static String SHOOTER_THRESHOLD="Shooter threshold";
	static String GATE_THRESHOLD="Delta Gate threshold";
	static double SHOOTER_THRESHOLD_VALUE=-600;
	static double GATE_THRESHOLD_VALUE=-100;
	static String GATE_SPEED="Gate speed";
	static double GATE_SPEED_VALUE=-1;
	static String SHOOTER_SPEED="Shooter speed";
	static double SHOOTER_SPEED_VALUE=-.47;
	static String SHOOTER_SPEED_EQUALS="Shooter speed = ";
	static Encoder shooterEncoder ;
	static boolean setGateOn;
	static double DELTA_V=0.01;
	static double shooterSpeed=0.5;
	
	class Mode{
		public static final String PWM="PWM";
		public static final String TALON_SRX="Talon SRX";
	}
	ShooterControl2(){
		
	}
	static public void setInstance(String mode, int shooterID,int ShooterEncoderChanelA,int ShooterEncoderChanelB,int gateID,int mixerRelayID){
	if(mode == Mode.PWM){
		THIS_TABLE_NAME= "Robot"+"/Test Beds/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
    	settings.putNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE);
    	settings.putNumber(GATE_THRESHOLD, GATE_THRESHOLD_VALUE);
    	settings.putNumber(GATE_SPEED, GATE_SPEED_VALUE);
    	settings.putNumber(SHOOTER_SPEED,SHOOTER_SPEED_VALUE);
    	settings.putBoolean(ENABLE,false);
    	ShooterControl2.ShooterEncoderChanelA=ShooterEncoderChanelA;
    	ShooterControl2.ShooterEncoderChanelB=ShooterEncoderChanelB;
    	ShooterControl2.mixerRelayID=mixerRelayID;
    	ShooterControl2.gateID=gateID;
    	ShooterControl2.shooterID=shooterID;
    	
    	
	}

	if(mode == Mode.TALON_SRX){
	}
		
	}
	static public void setInstance(){
		THIS_TABLE_NAME= "Robot"+"/Test Beds/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		settings.putNumber(TOLLERANCE,-100.0);
    	settings.putNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE);
    	settings.putNumber(GATE_THRESHOLD, GATE_THRESHOLD_VALUE);
    	settings.putNumber(GATE_SPEED, GATE_SPEED_VALUE);
    	settings.putNumber(SHOOTER_SPEED,SHOOTER_SPEED_VALUE);
    	settings.putBoolean(ENABLE,false);
    	settings.putNumber("Shooter speed = ", 0.0);
    	shooterID=8;
    	ShooterEncoderChanelA=8;
    	ShooterEncoderChanelB=9;
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

    	shooterEncoder = new Encoder(ShooterEncoderChanelA,ShooterEncoderChanelB);
    	//shooterEncoder.Init();
		
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
		settings.putNumber("Shooter speed = ",shooterEncoder.getRate() );
		double tmp=shooterEncoder.getRate();
		if(tmp>0)tmp=-tmp;
		double tmp2=settings.getNumber(SHOOTER_THRESHOLD, SHOOTER_THRESHOLD_VALUE);
		if(tmp2>0)tmp2=-tmp2;
		double tmp3=settings.getNumber(GATE_THRESHOLD, SHOOTER_THRESHOLD_VALUE+GATE_THRESHOLD_VALUE);
		if(tmp3>0)tmp3=-tmp3;
		double tmp4=settings.getNumber(TOLLERANCE, TOLLERANCE_VALUE);
		if(tmp4>0)tmp4=-tmp4;
		if(tmp<0){
			tmp3=-1;
			DELTA_V=-DELTA_V;
			shooterSpeed=-shooterSpeed;
			
		}
		if(tmp>tmp2){
			settings.putString("Process", "Below threshold");
			shooterSpeed=shooterSpeed+DELTA_V;
	    	settings.putNumber(SHOOTER_SPEED,shooterSpeed);
			shooterMotor.set(shooterSpeed);
			if(shooterEncoder.getRate()>tmp3){
				gateMotor.set(0.0);
			}else{
				gateMotor.set(settings.getNumber(GATE_SPEED,GATE_SPEED_VALUE));
			}
			
		}else if(shooterEncoder.getRate()<(tmp2+tmp4)){
			settings.putString("Process", "Above  threshold + tollerance");
			shooterSpeed=shooterSpeed-DELTA_V;
	    	settings.putNumber(SHOOTER_SPEED,shooterSpeed);
			shooterMotor.set(shooterSpeed);
			gateMotor.set(settings.getNumber(GATE_SPEED,GATE_SPEED_VALUE));
			
		}
		
		
		else{
			settings.putString("Process", "At threshold");
	    	settings.putNumber(SHOOTER_SPEED,shooterSpeed);
			shooterMotor.set(shooterSpeed);
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
