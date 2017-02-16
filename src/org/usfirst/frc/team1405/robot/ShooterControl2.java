package org.usfirst.frc.team1405.robot;

//import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import cpi.*;
import cpi.outputDevices.MotorController;

public class ShooterControl2  {
	
	static String THIS_TABLE_NAME;
	
	static NetworkTable settings;
	static MotorController shooterMotor ;
	static MotorController gateMotor ;
	static double speed0;
	static double speed1;
	static int shooterID;
	static int gateID;
	static int mixerRelayID;
	static int ShooterEncoderChanelA;
	static int ShooterEncoderChanelB;
	
	static boolean isNotFirstInit;
	static Timer timer=new Timer();
	static String mode;
	
	//Defaults
	
	static double DEF_SHOOTER_HIGH_TOLLERANCE_VALUE=50;
	static double DEF_SHOOTER_LOW_THRESHOLD_VALUE=600;
	static double DEF_GATE_DIFFERENCE_THRESHOLD_VALUE=100;
	static double DEF_GATE_SPEED_VALUE=1;
	static double DEF_SHOOTER_INITIAL_VOLTAGE_VALUE=.5;
	static boolean DEF_isGateOn=false;
	static double DEF_spedAdjustIncrement=10;
	static boolean DEF_NEGATE_SPEED_SWITCH=false;
	static boolean DEF_REVERSE_SHOOTER_MOTOR_SWITCH=false;
	static boolean DEF_REVERSE_GATE_MOTOR_SWITCH=false;
	// End Defaults

	static String ENABLE="Enable";
	static String SHOOTER_HIGH_TOLLERANCE="Motor high speed tollerance";
	static String SHOOTER_LOW_THRESHOLD="Shooter low threshold";
	static String GATE_DIFFERENCE_THRESHOLD="Gate threshold difference(shooter threshold-this)";
	static String GATE_SPEED="Gate speed";
	static String SHOOTER_SPEED_EQUALS="Shooter speed = ";
	static Encoder shooterEncoder ;
	
	static String NEGATE_SPEED="Negate speed";
	static String REVERSE_SHOOTER_MOTOR="Reverse shooter motor";
	static String REVERSE_GATE_MOTOR="Reverse gate motor";

	static String SET_TO_DEFAULTS="Set settings to default";
	static String DEFAULTS="Defaults/";
	
	class Mode{
		public static final String PWM="PWM";
		public static final String TALON_SRX="Talon SRX";
	}
	ShooterControl2(){
		
	}
	static public void setInstance(String mode, int talonshooterID,int jagshooterID,int ShooterEncoderChanelA,int ShooterEncoderChanelB,int talonGateID,int jagGateID,int mixerRelayID){
		boolean useTalon=false;
		if(mode==Mode.TALON_SRX) useTalon=true;
		shooterMotor=new MotorController(talonshooterID,jagshooterID,useTalon);
		gateMotor=new MotorController(talonGateID,jagGateID,useTalon) ;
    	shooterEncoder = new Encoder(ShooterEncoderChanelA,ShooterEncoderChanelB);
		THIS_TABLE_NAME= "Robot"+"/Test Beds/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
		settings.putBoolean(NEGATE_SPEED, settings.getBoolean(NEGATE_SPEED,DEF_NEGATE_SPEED_SWITCH));
		settings.setPersistent(NEGATE_SPEED);
		settings.putBoolean(REVERSE_SHOOTER_MOTOR, settings.getBoolean(REVERSE_SHOOTER_MOTOR,DEF_REVERSE_SHOOTER_MOTOR_SWITCH));
		settings.setPersistent(REVERSE_SHOOTER_MOTOR);
		settings.putBoolean(REVERSE_GATE_MOTOR, settings.getBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH));
    	settings.putNumber(SHOOTER_LOW_THRESHOLD,settings.getNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE));
		settings.setPersistent(SHOOTER_LOW_THRESHOLD);
		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,settings.getNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE));
		settings.setPersistent(SHOOTER_HIGH_TOLLERANCE);
    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,settings.getNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE));
		settings.setPersistent(GATE_DIFFERENCE_THRESHOLD);
    	ShooterControl2.ShooterEncoderChanelA=ShooterEncoderChanelA;
    	ShooterControl2.ShooterEncoderChanelB=ShooterEncoderChanelB;
    	ShooterControl2.mixerRelayID=mixerRelayID;
    	ShooterControl2.mode=mode;
	}
    	
	
	
		
	static public void robotInit(){
	}
	
	static public void disabledPeriodic(){
		setToDefaults();	
		
		
	}
	
	static void showDefaults(){
		settings.putBoolean(DEFAULTS+DEFAULTS+NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
		settings.putBoolean(DEFAULTS+REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
		settings.putBoolean(DEFAULTS+REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
    	settings.putNumber(DEFAULTS+SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		settings.putNumber(DEFAULTS+SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
    	settings.putNumber(DEFAULTS+GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
	}
	
	static void setToDefaults(){
		if(!settings.getBoolean(DEFAULTS+SET_TO_DEFAULTS,false))return;
		settings.putBoolean(NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
		settings.putBoolean(REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
		settings.putBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
    	settings.putNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
    	settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
	}

	static public void disabledInit(){
		settings.putBoolean(ENABLE,false);
	    		shooterMotor.set(0);
	    		gateMotor.set(0);
	}	
	
	static public void teleopInit(){
		
	}

	static public void teleopPeriodic(boolean start, boolean stop, double adjustSpeed){
		boolean isProcess=false;
		if(start)isProcess=true;
		if(stop)isProcess=false;
		if(isProcess){
		process();
		}else{
    		shooterMotor.set(0);
    		gateMotor.set(0);
		}
	}
	
	static void process(){
		
		if(true){
			
		}
		else if(true){
			
		}
		else if(true){
			
		}
		else if(true){
			
		}
		else if(true){
			
		}
		else if(true){
			
		}
	}
}
