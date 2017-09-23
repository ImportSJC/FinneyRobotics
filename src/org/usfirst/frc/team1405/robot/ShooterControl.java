package org.usfirst.frc.team1405.robot;

import com.ctre.CANTalon.TalonControlMode;

import cpi.Arduino_LightControl;
import cpi.auto.AutoInputs;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
//import cpi.auto.EncoderControl;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDController.Tolerance;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ShooterControl  {
	
	static String THIS_TABLE_NAME = "Shooter Control";
	static boolean pastState = false;
	static DoubleSolenoid solenoid;
	static boolean solenoidState = false;
	
	static final double SHOOTER_DELAY = 50;
	static final double GATE_PERIOD_MAGNITUDE = 5;//5
	static double gatePeriod = 1;
	static double SHOOTING_PERCENTAGE_DEFAULT = 0.7;
	static double SHOOTING_PERCENTAGE_AUTO_DEFAULT = 0.9;
	static double shootingPercentage = SHOOTING_PERCENTAGE_DEFAULT;
	
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
	
	static double DEF_SHOOTER_HIGH_TOLLERANCE_VALUE=5;//50
	static double DEF_SHOOTER_LOW_THRESHOLD_VALUE=800;
	static double DEF_GATE_DIFFERENCE_THRESHOLD_VALUE=5;//100
	static double DEF_GATE_VOLTAGE_VALUE=1;//1
	static double DEF_MIXER_VOLTAGE_VALUE=.75;
	static double DEF_SHOOTER_INITIAL_VOLTAGE_VALUE=.5;
	static double DEF_SPEED_ADJUST_INCREMENT=10;
	static double DEF_VOLTAGE_ADJUST_INCREMENT=0.005;//0.0005
//	static double DEF_VOLTAGE_ADJUST_INCREMENT=0.005;
	static boolean DEF_NEGATE_SPEED_SWITCH=false;
	static boolean DEF_REVERSE_SHOOTER_MOTOR_SWITCH=false;
	static boolean DEF_REVERSE_GATE_MOTOR_SWITCH=false;
	static boolean DEF_REVERSE_MIXER_MOTOR_SWITCH=true;
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
	
	static private double voltageAdjustment = DEF_VOLTAGE_ADJUST_INCREMENT;
	static private double SPEED_MARGIN_SHOOT = 20;
	static private double SPEED_MARGIN_SLOW_SHOOTER = 20;
	static private double remainingSpeedCount = 0;
	static private double remainingSpeedSum = 0;
	static private boolean trackAverage = false;
	
	static public PIDController pid;
	static private Tolerance tol;
	static private PIDSource src;
	static private PIDOutput out;
	static private double Ishooter = 0.00005;
	static private double Dshooter = 0.002;
	static private double constantSpeed = 0.45;
	static private double feedForward = 0.0;
	static private double period = 50;
	
	static private double[] speedArray = new double[250];
	static private int speedArrayIndex = 0;
	static private boolean writeToFile = false;
	
	static private boolean increaseSpeedLast = false;
	static private boolean decreaseSpeedLast = false;
	
	static private boolean runShooterAuto = false;
	
	class Mode{
		public static final String PWM="PWM";
		public static final String TALON_SRX="Talon SRX";
	}
	ShooterControl(){
		
	}
	static public void robotInit(String mode){
		solenoid = new DoubleSolenoid(6,7);//6.7
		shooterMotor=new MotorController(ID_Assignments.SHOOTER_TALON_SHOOT_MOTOR, true);
		shooterMotor.setControlMode(TalonControlMode.PercentVbus);
		gateMotor=new MotorController(ID_Assignments.SHOOTER_TALON_GATE_MOTOR) ;
		mixer=new MotorController(ID_Assignments.SHOOTER_TALON_MIXER_MOTOR) ;
    	shooterEncoder = new Encoder(ID_Assignments.SHOOTER_ENCODER_1A,ID_Assignments.SHOOTER_ENCODER_1B);
		THIS_TABLE_NAME= "Robot"+"/Shooter";
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
//		settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
//		settings.putBoolean(NEGATE_SPEED, settings.getBoolean(NEGATE_SPEED,DEF_NEGATE_SPEED_SWITCH));
//		settings.setPersistent(NEGATE_SPEED);
//		settings.putBoolean(REVERSE_SHOOTER_MOTOR, settings.getBoolean(REVERSE_SHOOTER_MOTOR,DEF_REVERSE_SHOOTER_MOTOR_SWITCH));
//		settings.setPersistent(REVERSE_SHOOTER_MOTOR);
//		settings.putBoolean(REVERSE_GATE_MOTOR, settings.getBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH));
//		settings.setPersistent(REVERSE_GATE_MOTOR);
//		settings.putBoolean(REVERSE_MIXER_MOTOR, settings.getBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH));
//		settings.setPersistent(REVERSE_MIXER_MOTOR);
//		settings.putNumber(SHOOTER_LOW_THRESHOLD,settings.getNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE));
//		settings.setPersistent(SHOOTER_LOW_THRESHOLD);
//		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,settings.getNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE));
//		settings.setPersistent(SHOOTER_HIGH_TOLLERANCE);
//    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,settings.getNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE));
//		settings.setPersistent(GATE_DIFFERENCE_THRESHOLD);
//		settings.putString("ShooterSpeedArray", "");
		settings.putNumber("shootingSpeed", (int)(lowThreshold));
		ShooterControl.ShooterEncoderChanelA=ID_Assignments.SHOOTER_ENCODER_1A;
    	ShooterControl.ShooterEncoderChanelB=ID_Assignments.SHOOTER_ENCODER_1B;
//    	ShooterControl.talonMixerID=ID_Assignments.SHOOTER_TALON_MIXER_MOTOR;
//    	ShooterControl.jagMixerID=ID_Assignments.SHOOTER_JAGUAR_MIXER_MOTOR;
//    	ShooterControl.mode=mode;
//    	showDefaults();
    	
    	src = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public double pidGet() {
				//test with a joystick instead of an encoder
//				double tmp = -Robot.pilot.leftStickYaxis();
//				if(tmp>0){
//					tmp=(tmp*400)+400;
//				}else{
//					tmp=(1-Math.abs(tmp))*400;
//				}
				
				double tmp = -shooterEncoder.getRate();
				currentSpeed = tmp;
//				System.out.println("PID input: " + tmp);
				return tmp;
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		out = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
//				System.out.println("PID output: " + output);
				if(output>0.1){
					shooterVoltage = output;
				}else{
					shooterVoltage = 0;
				}
//				shooterVoltage+=output;
			}
		};
		
		pid = new PIDController(0, Ishooter, Dshooter, feedForward, src, out);//P=0.0023
		
		tol = new Tolerance() {
			
			@Override
			public boolean onTarget() {
				double tmp = -shooterEncoder.getRate();
				if(tmp>lowThreshold-SPEED_MARGIN_SHOOT && tmp<lowThreshold+SPEED_MARGIN_SHOOT){
//					System.out.println("In tolerance: " + lowThreshold);
					return true;
				}
				return false;
			}
		};
		
		settings.putNumber("Ishooter", Ishooter);
		settings.putNumber("Dshooter", Dshooter);
		settings.putBoolean("trackAverage", trackAverage);
		settings.putNumber("feedForward", feedForward);
		settings.putNumber("period", period);
		settings.putNumber("targetSpeed", DEF_SHOOTER_LOW_THRESHOLD_VALUE);
	}
    	
	

	
	static public void testPeriodic(){;
		
		
	}
	
//	static void showDefaults(){
//		settings.putBoolean(DEFAULTS+DEFAULTS+NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
//		settings.putBoolean(DEFAULTS+REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
//		settings.putBoolean(DEFAULTS+REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
//		settings.putBoolean(DEFAULTS+REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
//    	settings.putNumber(DEFAULTS+SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
//		settings.putNumber(DEFAULTS+SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
//    	settings.putNumber(DEFAULTS+GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
//	}
//	
//	static void setToDefaults(){
//		if(!DriverStation.getInstance().isDisabled())return;
//		if(!settings.getBoolean(DEFAULTS+SET_TO_DEFAULTS,false))return;
//		settings.putBoolean(NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
//		settings.putBoolean(REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
//		settings.putBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
//		settings.putBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
//    	settings.putNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
//		settings.putNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
//    	settings.putNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
//    	settings.putBoolean(DEFAULTS+SET_TO_DEFAULTS,false);
//	}
//	
//	static void setToNetValues(){
//		if(!DriverStation.getInstance().isDisabled())return;
//		if(!settings.getBoolean(DEFAULTS+SET_TO_DEFAULTS,false))return;
//		negateSpeed=settings.getBoolean(NEGATE_SPEED, DEF_NEGATE_SPEED_SWITCH);
//		reverseShooterMotor=settings.getBoolean(REVERSE_SHOOTER_MOTOR, DEF_REVERSE_SHOOTER_MOTOR_SWITCH);
//		reverseGateMotor=settings.getBoolean(REVERSE_GATE_MOTOR,DEF_REVERSE_GATE_MOTOR_SWITCH);
//		reverseMixerMotor=settings.getBoolean(REVERSE_MIXER_MOTOR,DEF_REVERSE_MIXER_MOTOR_SWITCH);
//    	lowThreshold=settings.getNumber(SHOOTER_LOW_THRESHOLD,DEF_SHOOTER_LOW_THRESHOLD_VALUE);
//    	highSpeed=lowThreshold+settings.getNumber(SHOOTER_HIGH_TOLLERANCE,DEF_SHOOTER_HIGH_TOLLERANCE_VALUE);
//    	lowSpeed=lowThreshold-settings.getNumber(GATE_DIFFERENCE_THRESHOLD,DEF_GATE_DIFFERENCE_THRESHOLD_VALUE);
//	}
	
	static void adjustSpeed(boolean increaseSpeed, boolean decreaseSpeed){
		if(increaseSpeed && !increaseSpeedLast){
			shootingPercentage+=0.05;
			lowThreshold=lowThreshold+DEF_SPEED_ADJUST_INCREMENT;
			highSpeed=highSpeed+DEF_SPEED_ADJUST_INCREMENT;
			lowSpeed=lowSpeed+DEF_SPEED_ADJUST_INCREMENT;
		}else 
			if(decreaseSpeed && !decreaseSpeedLast){
				shootingPercentage-=0.05;
				lowThreshold=lowThreshold-DEF_SPEED_ADJUST_INCREMENT;
				highSpeed=highSpeed-DEF_SPEED_ADJUST_INCREMENT;
				lowSpeed=lowSpeed-DEF_SPEED_ADJUST_INCREMENT;
			}
		
		increaseSpeedLast = increaseSpeed;
		decreaseSpeedLast = decreaseSpeed;
		settings.putNumber("shootingSpeed", (int)(lowThreshold));
//    	settings.putNumber(SHOOTER_LOW_THRESHOLD,lowThreshold);

	}

	static public void disabledInit(){
		pid.disable();
		pid.reset();
		Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
//		settings.putBoolean(ENABLE,false);
		shooterMotor.set(0);
		gateMotor.set(0);
		mixer.set(0);
		processState=SPEED_STARTUP;
		runShooterAuto = false;
	}
	static public void disabledPeriodic(){
//		if(!settings.getBoolean(ENABLE,false))return;
//		setToDefaults();
//		teleopPeriodic(true,false,false);
	}
	static public void teleopInit(){
		shootingPercentage = SHOOTING_PERCENTAGE_DEFAULT;
		speedArrayIndex = 0;
		writeToFile = false;
		lowThreshold=DEF_SHOOTER_LOW_THRESHOLD_VALUE;
		voltageAdjustment = DEF_VOLTAGE_ADJUST_INCREMENT;
		shooterVoltage = 0;
		
		Ishooter = settings.getNumber("Ishooter",0);
		Dshooter = settings.getNumber("Dshooter",0);
		feedForward = settings.getNumber("feedForward", 0);
		period = settings.getNumber("period", 50);
		DEF_SHOOTER_LOW_THRESHOLD_VALUE = settings.getNumber("targetSpeed", DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		
		remainingSpeedSum = 0;
		remainingSpeedCount = 0;
		
		pid = new PIDController(0, Ishooter, Dshooter, feedForward, src, out);
		pid.setSetpoint(DEF_SHOOTER_LOW_THRESHOLD_VALUE);
		pid.setTolerance(tol);
		pid.enable();
	}
	
	static public void toggleRunShooterAuto(double percentage){
		shootingPercentage = percentage;
		runShooterAuto = !runShooterAuto;
	}

	static public void teleopPeriodic(boolean start, boolean increaseSpeed, boolean decreaseSpeed, boolean angleShooter){
//		speedUpdate();
		adjustSpeed(increaseSpeed,decreaseSpeed);
		if(start){
			if(!pid.isEnabled()){
				pid.enable();
				System.out.println("start shooting pid");
			}
			Arduino_LightControl.Periodic(THIS_LIGHT_CONTROL_INDICATION);
//			rampShooter(lowThreshold-currentSpeed, lowThreshold);
			testingVoltage(lowThreshold-currentSpeed, lowThreshold);
//			process();
		}else{
			Arduino_LightControl.Periodic(LIGHT_CONTROL_OFF_STATE);
			if(pid.isEnabled()){
				shooterVoltage = 0;
				pid.reset();
			}
    		shooterMotor.set(0);
    		gateMotor.set(0);
    		mixer.set(0);
    		processState=SPEED_STARTUP;
    	}
		
		if (!pastState && angleShooter){
			solenoidState = !solenoidState;
			if (solenoidState){
				solenoid.set(DoubleSolenoid.Value.kForward);
			}
			else{
				solenoid.set(DoubleSolenoid.Value.kReverse);
			}
		}
		pastState = angleShooter;
		trackAverage = settings.getBoolean("trackAverage", false);
//		System.out.println("Shooter position: " + shooterEncoder.getRaw());
//		System.out.println("Changing Solenoid State To: " + solenoidState);
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
		
			currentSpeed=shooterMotor.getVelocity();
			if(!negateSpeed){
				currentSpeed=-currentSpeed;	
			}
//			System.out.println("processState: " + processState);
			System.out.println("speed: " + currentSpeed);
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
//    		shooterMotor.set(ms);
			shooterMotor.set(0);
    		System.out.println("running motor: " + xs);
    		gateMotor.set(gs);
//    		mixer.set(xs);
    		mixer.set(0);
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
    		shooterMotor.set(-1);
//    		System.out.println("running motor full speed");
    		gateMotor.set(0);
    		if(currentSpeed>=lowThreshold)processState=SPEED_IN_BOUNDS;
			break;
			
		case SPEED_HIGH:
			shooterVoltage=shooterVoltage-DEF_VOLTAGE_ADJUST_INCREMENT;
    		processState=SPEED_IN_BOUNDS;
			break;
		}
		
	}
	
	private static void speedUpdate(){
//		System.out.println("index: " + speedArrayIndex + " currentSpeed: "  + currentSpeed);
		if(speedArrayIndex < speedArray.length){
			speedArray[speedArrayIndex] = currentSpeed;
			speedArrayIndex++;
		}else if (!writeToFile){
			String tmp = "";
			for(int i = 0; i<speedArray.length; i++){
				tmp+=speedArray[i]+",";
			}
//			settings.putString("ShooterSpeedArray", tmp);
			writeToFile = true;
//			System.out.println("\n\nWritten to file!\n\n");
		}
	}
	
	public static void rampShooter(double remainingSpeed, double targetSpeed){
//		currentSpeed=shooterMotor.getVelocity();
		currentSpeed=shooterEncoder.getRate();
		if(!negateSpeed){
			currentSpeed=-currentSpeed;	
		}
		
		//using the joystick for testing
//		currentSpeed = -Robot.pilot.leftStickYaxis();
//		if(currentSpeed>0){
//			currentSpeed=(currentSpeed*400)+400;
//		}else{
//			currentSpeed=(1-Math.abs(currentSpeed))*400;
//		}
		
		//calculate speed direction
//		if(currentSpeed>lastSpeed){
//			currentDirection = 1;
//		}else if (currentSpeed<lastSpeed){
//			currentDirection = -1;
//		}else{
//			currentDirection = 0;
//		}
		
		//run the mixer and gate
		if(Math.abs(remainingSpeed)<SPEED_MARGIN_SHOOT){
			//if within margin run the mixer and the gate motors
			System.out.println("INSIDE THE MARGIN");
			gateMotor.set(DEF_GATE_VOLTAGE_VALUE);
			mixer.set(DEF_MIXER_VOLTAGE_VALUE);
		}else{
			gateMotor.set(0);
			mixer.set(0);
		}
		
		if(remainingSpeed>0 && voltageAdjustment<0){
			if(Math.abs(remainingSpeed)>SPEED_MARGIN_SLOW_SHOOTER){
				voltageAdjustment=DEF_VOLTAGE_ADJUST_INCREMENT;
			}else{
				voltageAdjustment=(DEF_VOLTAGE_ADJUST_INCREMENT/2);
			}
//			voltageAdjustment/=2;
		}else if(remainingSpeed<0 && voltageAdjustment>0){
//			voltageAdjustment/=2;
			if(Math.abs(remainingSpeed)>SPEED_MARGIN_SLOW_SHOOTER){
				voltageAdjustment=-DEF_VOLTAGE_ADJUST_INCREMENT;
			}else{
				voltageAdjustment=-(DEF_VOLTAGE_ADJUST_INCREMENT/2);
			}
		}
		
//		tmpSpeed = tmpSpeed + voltageAdjustment;
		
//		if(currentDirection == -1 && lowThreshold > 0 && tmpSpeed<1){
//			tmpSpeed = tmpSpeed + voltageAdjustment;
//		}else if(currentDirection == 1 && lowThreshold < 0 && tmpSpeed>-1){
//			tmpSpeed = tmpSpeed - voltageAdjustment;
//		}else{
			if(remainingSpeed > 0){
				if(currentSpeed > Math.abs(lowThreshold) ){//&& shooterVoltage>-1){
					System.out.print("Case 3 - ");
					shooterVoltage = shooterVoltage - voltageAdjustment;
				}else {//if(shooterVoltage<1){
					System.out.print("Case 4 - ");
					shooterVoltage = shooterVoltage + voltageAdjustment;
				}
			}else{
				if(currentSpeed > Math.abs(lowThreshold) ){//&& shooterVoltage<1){
					System.out.print("Case 5 - ");
					shooterVoltage = shooterVoltage + voltageAdjustment;
				}else {//if(shooterVoltage>-1){
					System.out.print("Case 6 - ");
					shooterVoltage = shooterVoltage - voltageAdjustment;
				}
			}
//		}
//		System.out.println("shooter voltage: " + shooterVoltage + " remaining speed: " + remainingSpeed);
//		System.out.println("target speed: " + lowThreshold + " currentspeed: " + currentSpeed + " voltage adjustment: " + voltageAdjustment);
		shooterMotor.set(shooterVoltage);
	}
	
	public static void runTestingVoltage(){
		if(runShooterAuto){
			testingVoltage(lowThreshold-currentSpeed, lowThreshold);
		}
	}
	
	public static void testingVoltage(double remainingSpeed, double targetSpeed){
//		currentSpeed=shooterEncoder.getRate();
//		if(!negateSpeed){
//			currentSpeed=-currentSpeed;
//		}
		
		if(trackAverage){
			remainingSpeedSum+=Math.abs(remainingSpeed);
			remainingSpeedCount++;
		}
		
		if(pid.onTarget()){
			gateMotor.set(-1);
			mixer.set(1);
		}else{
			gateMotor.set(0);
			mixer.set(0);
		}
		
		
//		if(gatePeriod > 0 && gatePeriod <= GATE_PERIOD_MAGNITUDE){
////			System.out.println("shooting: " + gatePeriod);
//			gateMotor.set(-1);
//			mixer.set(1);
//			gatePeriod++;
//		}else if(gatePeriod < 0 && gatePeriod >= -GATE_PERIOD_MAGNITUDE){
////			System.out.println("not shooting");
//			gateMotor.set(0);
//			mixer.set(0);
//			gatePeriod--;
//		}else if(gatePeriod > GATE_PERIOD_MAGNITUDE || gatePeriod < -GATE_PERIOD_MAGNITUDE){
//			if(gatePeriod > 0){
//				gatePeriod = -1;
//			}else{
//				gatePeriod = 1;
//			}
//		}
		
//		System.out.println("remaining speed avg: " + (remainingSpeedSum/remainingSpeedCount) + " remaining speed: " + remainingSpeed);
//		System.out.println("shooting percentage: " + shootingPercentage);
		shooterMotor.set(shooterVoltage);// /1.75
//		shooterMotor.set(constantSpeed);
		
//		System.out.println("shooter encoder: " + shooterEncoder.get());
		
//		gateMotor.set(0.5);
//		shooterMotor.set(shooterVoltage/4);
//		shooterMotor.set(0.44);
//		System.out.println("average error: " + pid.getAvgError());
		
		System.out.println("shooter voltage: " + (shooterVoltage) + " remaining speed: " + remainingSpeed);
		System.out.println("target speed: " + lowThreshold + " currentspeed: " + currentSpeed + " voltage adjustment: " + voltageAdjustment);
	}
	
	public static void stopMotors(){
		shooterMotor.set(0);
		mixer.set(0);
		gateMotor.set(0);
		shooterVoltage = 0;
	}
}
