package org.usfirst.frc.team1405.robot;

//import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.DriverStation;
import cpi.Arduino_LightControl;

public class ShooterControl  {
	
	static String THIS_TABLE_NAME;
	
	
	
	static NetworkTable settings;
	static MotorController shooterMotor ;
	static MotorController gateMotor ;
	static MotorController mixer ;
	static int shooterID;
	static int gateID;
	static int talonMixerID;
	static int jagMixerID;
	static int ShooterEncoderChanelA;
	static int ShooterEncoderChanelB;
	
	
	//light control
	static final int LIGHT_CONTROL_OFF_STATE=0;
	static final int THIS_LIGHT_CONTROL_INDICATION=1;
	//end light control
	 
	
	//Process States
	static final String SPEED_IN_BOUNDS="Speed between low threshold and low threshold + high tollerance values";
	static final String SPEED_LOW="Speed between low threshold and low threshold - gate difference threshold values";
	static final String SPEED_SHOOTING="Speed below low threshold - gate difference threshold values";
	static final String SPEED_AFTER_SHOT="Speed below low threshold";
	static final String SPEED_HIGH="Speed above low threshold + high tollerance values";
	static final String SPEED_STARTUP="Startup condition";
	
	//end Process States

	static boolean isNotFirstInit;
	static String mode;
	static String processState=SPEED_STARTUP;

	
	
	//Defaults
	
	static double DEF_SHOOTER_HIGH_TOLLERANCE_VALUE=50;
	static double DEF_SHOOTER_LOW_THRESHOLD_VALUE=600;
	static double DEF_GATE_DIFFERENCE_THRESHOLD_VALUE=100;
	static double DEF_GATE_VOLTAGE_VALUE=1;
	static double DEF_MIXER_VOLTAGE_VALUE=.5;
	static double DEF_SHOOTER_INITIAL_VOLTAGE_VALUE=.5;
	static double DEF_SPEED_ADJUST_INCREMENT=10;
	static double DEF_VOLTAGE_ADJUST_INCREMENT=0.01;
	static boolean DEF_NEGATE_SPEED_SWITCH=false;
	static boolean DEF_REVERSE_SHOOTER_MOTOR_SWITCH=false;
	static boolean DEF_REVERSE_GATE_MOTOR_SWITCH=false;
	static boolean DEF_REVERSE_MIXER_MOTOR_SWITCH=false;
	// End Defaults
	

	static boolean negateSpeed=DEF_NEGATE_SPEED_SWITCH;
	static boolean reverseShooterMotor=DEF_REVERSE_SHOOTER_MOTOR_SWITCH;
	static boolean reverseGateMotor=DEF_REVERSE_GATE_MOTOR_SWITCH;
	static boolean reverseMixerMotor=DEF_REVERSE_MIXER_MOTOR_SWITCH;
	static double lowThreshold=DEF_SHOOTER_LOW_THRESHOLD_VALUE;
	static double shooterVoltage=0;
	static double gateVoltage=0;
	static double highSpeed=DEF_SHOOTER_LOW_THRESHOLD_VALUE+DEF_SHOOTER_HIGH_TOLLERANCE_VALUE;
	static double lowSpeed=DEF_SHOOTER_LOW_THRESHOLD_VALUE-DEF_GATE_DIFFERENCE_THRESHOLD_VALUE;
	static double currentSpeed=DEF_GATE_DIFFERENCE_THRESHOLD_VALUE;
	
	

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
	static String REVERSE_MIXER_MOTOR="Reverse mixer motor";

	static String SET_TO_DEFAULTS="Set settings to default";
	static String DEFAULTS="Defaults/";
	
	class Mode{
		public static final String PWM="PWM";
		public static final String TALON_SRX="Talon SRX";
	}
	ShooterControl(){
		
	}
	static public void robotInit(String mode){
		shooterMotor=new MotorController(ID_Assignments.SHOOTER_TALON_SHOOT_MOTOR);
		gateMotor=new MotorController(ID_Assignments.SHOOTER_TALON_GATE_MOTOR) ;
		mixer=new MotorController(ID_Assignments.SHOOTER_TALON_MIXER_MOTOR) ;
    	shooterEncoder = new Encoder(ID_Assignments.SHOOTER_ENCODER_1A,ID_Assignments.SHOOTER_ENCODER_1B);
		THIS_TABLE_NAME= "Robot"+"/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
		settings.putBoolean(NEGATE_SPEED, settings.getBoolean(NEGATE_SPEED,DEF_NEGATE_SPEED_SWITCH));
		settings.setPersistent(NEGATE_SPEED);
		settings.putBoolean(REVERSE_SHOOTER_MOTOR, settings.getBoolean(REVERSE_SHOOTER_MOTOR,DEF_REVERSE_SHOOTER_MOTOR_SWITCH));
		settings.setPersistent(REVERSE_SHOOTER_MOTOR);
		settings.putBoolean(REVERSE_GATE_MOTOR, settings.getBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH));
		settings.setPersistent(REVERSE_GATE_MOTOR);
		settings.putBoolean(REVERSE_MIXER_MOTOR, settings.getBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH));
		settings.setPersistent(REVERSE_MIXER_MOTOR);
		settings.putNumber(SHOOTER_LOW_THRESHOLD,settings.getNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE));
		settings.setPersistent(SHOOTER_LOW_THRESHOLD);
		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,settings.getNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE));
		settings.setPersistent(SHOOTER_HIGH_TOLLERANCE);
    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,settings.getNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE));
		settings.setPersistent(GATE_DIFFERENCE_THRESHOLD);
    	ShooterControl.ShooterEncoderChanelA=ID_Assignments.SHOOTER_ENCODER_1A;
    	ShooterControl.ShooterEncoderChanelB=ID_Assignments.SHOOTER_ENCODER_1B;
    	ShooterControl.talonMixerID=ID_Assignments.SHOOTER_TALON_MIXER_MOTOR;
    	ShooterControl.jagMixerID=ID_Assignments.SHOOTER_JAGUAR_MIXER_MOTOR;
    	ShooterControl.mode=mode;
    	showDefaults();
	}
    	
	

	
	static public void testPeriodic(){;
		
		
	}
	
	static void showDefaults(){
		settings.putBoolean(DEFAULTS+DEFAULTS+NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
		settings.putBoolean(DEFAULTS+REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
		settings.putBoolean(DEFAULTS+REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
		settings.putBoolean(DEFAULTS+REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
    	settings.putNumber(DEFAULTS+SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		settings.putNumber(DEFAULTS+SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
    	settings.putNumber(DEFAULTS+GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
	}
	
	static void setToDefaults(){
		if(!DriverStation.getInstance().isDisabled())return;
		if(!settings.getBoolean(DEFAULTS+SET_TO_DEFAULTS,false))return;
		settings.putBoolean(NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
		settings.putBoolean(REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
		settings.putBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
		settings.putBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
    	settings.putNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
    	settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
	}
	
	static void setToNetValues(){
		if(!DriverStation.getInstance().isDisabled())return;
		if(!settings.getBoolean(DEFAULTS+SET_TO_DEFAULTS,false))return;
		negateSpeed=settings.getBoolean(NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
		reverseShooterMotor=settings.getBoolean(REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
		reverseGateMotor=settings.getBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
		reverseMixerMotor=settings.getBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
    	lowThreshold=settings.getNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
    	highSpeed=lowThreshold+settings.getNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
    	lowSpeed=lowThreshold-settings.getNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
	}
	
	static void adjustSpeed(boolean increaseSpeed, boolean decreaseSpeed){
		if(increaseSpeed){
			lowThreshold=lowThreshold+DEF_SPEED_ADJUST_INCREMENT;
			highSpeed=highSpeed+DEF_SPEED_ADJUST_INCREMENT;
			lowSpeed=lowSpeed+DEF_SPEED_ADJUST_INCREMENT;
		
		}else 
			if(decreaseSpeed){
				lowThreshold=lowThreshold-DEF_SPEED_ADJUST_INCREMENT;
				highSpeed=highSpeed-DEF_SPEED_ADJUST_INCREMENT;
				lowSpeed=lowSpeed-DEF_SPEED_ADJUST_INCREMENT;
				
			}
    	settings.putNumber(SHOOTER_LOW_THRESHOLD,lowThreshold);

	}

	static public void disabledInit(){
		Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
		settings.putBoolean(ENABLE,false);
	    		shooterMotor.set(0);
	    		gateMotor.set(0);
	    		mixer.set(0);
	    		processState=SPEED_STARTUP;
	}	
	static public void disabledPeriodic(){
		
		if(!settings.getBoolean(ENABLE,false))return;
		setToDefaults();
		teleopPeriodic(true,false,false,false);
	}
	static public void teleopInit(){
	}

	static public void teleopPeriodic(boolean start, boolean stop, boolean increaseSpeed, boolean decreaseSpeed){
		boolean isProcess=false;
		if(start)isProcess=true;
		if(stop)isProcess=false;
		if(isProcess){
			Arduino_LightControl.Periodic(THIS_LIGHT_CONTROL_INDICATION);;
		adjustSpeed(increaseSpeed,decreaseSpeed);
		process();
		}else{
			Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
    		shooterMotor.set(0);
    		gateMotor.set(0);
    		mixer.set(0);
    		processState=SPEED_STARTUP;
		}
	}
	
	static void process(){
		
		/*
		 * 
	static final String SPEED_IN_BOUNDS="Speed between low threshold and low threshold + high tollerance values";
	static final String SPEED_LOW="Speed between low threshold and low threshold - gate difference threshold values";
	static final String SPEED_SHOOTING="Speed below low threshold - gate difference threshold values";
	static final String SPEED_AFTER_SHOT="Speed below low threshold";
	static final String SPEED_HIGH="Speed above low threshold + high tolerance values";
	static final String SPEED_STARTUP="Startup condition";
	
		 */
		
			currentSpeed=shooterEncoder.getRate();
			if(!negateSpeed){
				currentSpeed=-currentSpeed;	
			}
		switch(processState){
		
		default:
		case SPEED_STARTUP:
    		processState=SPEED_SHOOTING;
			break;
			
		case SPEED_IN_BOUNDS:
			if(currentSpeed<lowThreshold)processState=SPEED_LOW;
			if(currentSpeed>lowThreshold)processState=SPEED_HIGH;
			double ms=shooterVoltage;
			if(reverseGateMotor)ms=-ms;
			double gs=DEF_GATE_VOLTAGE_VALUE;
			if(reverseGateMotor)gs=-gs;
			double xs=DEF_MIXER_VOLTAGE_VALUE;
			if(reverseMixerMotor)xs=-xs;
    		shooterMotor.set(xs);
    		gateMotor.set(gs);
    		mixer.set(xs);
			break;
			
		case SPEED_LOW:
			shooterVoltage=shooterVoltage+DEF_VOLTAGE_ADJUST_INCREMENT;
			if(currentSpeed<lowSpeed){
	    		processState=SPEED_SHOOTING;
			}else{
    		processState=SPEED_IN_BOUNDS;
			}
			break;
			
		case SPEED_SHOOTING:
			double ms2=1.0;
			if(reverseGateMotor)ms2=-ms2;
    		shooterMotor.set(1);
    		gateMotor.set(0);
    		if(currentSpeed>=lowThreshold)processState=SPEED_IN_BOUNDS;
			break;
			
		case SPEED_HIGH:
			shooterVoltage=shooterVoltage-DEF_VOLTAGE_ADJUST_INCREMENT;
    		processState=SPEED_IN_BOUNDS;
			break;
		}
		
	}
}
