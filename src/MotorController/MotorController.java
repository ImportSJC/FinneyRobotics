package MotorController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Jaguar;
import logging.SimpleLogger;

public class MotorController {
	private final static int ID_THRESHOLD = 50; //this threshold indicates that any number at or above is not to be instantiated
	private final static boolean USE_TALON = true; // if false all motor controllers will be instantiated as jaguars instead of talons
	private final static int TIMEOUT = 100;
	private final static double WHEEL_DIAMETER = 6.0 / 12.0; // wheel diameter in inches / 12.0 (convert to feet)
	private boolean reverse = false; // reverse the motor direction, used to ensure that a positive number results in a rotation that makes sense
	
	private MotorController_Encoder encoder;
	private TalonSRX talon;
	private Jaguar jaguar;

	private int myID = 0;
	private double mySpeed = 0;
	private double myGearRatio = 1;
	
	//Motion profile constants
	public static final int kTimeoutMs = 10;
	public static final double kNeutralDeadband = 0.01; //motor deadband, set to 1%
	
	public MotorController(int ID){
		myID = ID;
		
		initMotorController();
	}
	
	public MotorController(int ID, boolean reverseMotor) {
		myID = ID;
		this.reverse = reverseMotor;
		
		initMotorController();
	}
	
	public MotorController(int ID, boolean reverseMotor, MotorController_Encoder encoder) {
		myID = ID;
		reverse = reverseMotor;
		this.encoder = encoder;

		initMotorController();
	}
	
	public MotorController(int ID, double gearRatio, MotorController_Encoder encoder) {
		myID = ID;
		myGearRatio = gearRatio;
		this.encoder = encoder;

		initMotorController();
	}

	public MotorController(int ID, boolean reverseMotor, double gearRatio, MotorController_Encoder encoder) {
		myID = ID;
		reverse = reverseMotor;
		myGearRatio = gearRatio;
		this.encoder = encoder;
		

		initMotorController();
	}
	
	/**
	 * Get the value of the USE_TALON constant
	 * @return true iff the motor controllers on the robot are talons
	 */
	public boolean usingTalons(){
		return USE_TALON;
	}

	private void initMotorController() {
		if(isActive()){
			if (USE_TALON) {
				talon = new TalonSRX(myID);
//				enableCurrentLimit(true);
//				configCurrentLimitPeak(35, TIMEOUT);
//				configCurrentLimitContinuous(35, TIMEOUT);
//				talon.configPeakCurrentDuration(1, TIMEOUT);
				talon.setInverted(reverse);
				if(encoder != null){
					talon.setSensorPhase(encoder.getReverse());
				}
			} else {
				jaguar = new Jaguar(myID);
			}
		}
	}

	public void set(double speed) {
		mySpeed = speed;
		if(isActive()){
			if (USE_TALON) {
				SimpleLogger.log("set: " + speed);
				talon.set(ControlMode.PercentOutput, speed);
			} else {
				if (reverse) {
					jaguar.set(-speed);
				} else {
					jaguar.set(speed);
				}
			}
		}
	}
	
	public double get(){
		return mySpeed;
	}

	public double getDriveDistance(){
		if(isActive()){
			return ((getPosition() / encoder.getCPR()) * myGearRatio * WHEEL_DIAMETER * Math.PI);
		}
		return 0;
	}
	
	/**
	 * convert a motion profile from the format [position (ft), velocity(ft/s), duration(ms)]
	 * 							  to the format [position (encoder rotations), velocity (encoder rotations/min), duration(ms)]
	 * @param mp
	 * @return
	 */
	public double[][] convertMP(double[][] mp){
		for(int i=0; i<mp.length; i++){
			mp[i][0] = mp[i][0] / (myGearRatio * WHEEL_DIAMETER * Math.PI);
			mp[i][1] = (mp[i][1] * 60.0) / (myGearRatio * WHEEL_DIAMETER * Math.PI);
		}
		
		return mp;
	}
	
	public double getPosition() {
		if(isActive()){
			if (USE_TALON) {
				return talon.getSelectedSensorPosition(0)/4;
			} else {
				if(reverse){
					return -jaguar.getPosition()/4;
				}else{
					return jaguar.getPosition()/4;
				}
			}
		}
		return 0;
	}

	public void setPosition(int position) {
		if(isActive()){
			if (USE_TALON) {
				talon.setSelectedSensorPosition(position, 0, TIMEOUT);
			} else {
				jaguar.setPosition(position);
			}
		}
	}
	
	public void setMasterTalon(int masterTalonID){
		talon.set(ControlMode.Follower, masterTalonID);
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputCurrent() {
		if(isActive()){
			if (USE_TALON) {
				return talon.getOutputCurrent();
			}
		}

		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public double getOutputVoltage() {
		if(isActive()){
			if(USE_TALON){
				return talon.getBusVoltage();
			}
		}
		
		return 0;
	}

	/**
	 * Talon ONLY
	 */
	public void enableBrakeMode(boolean value) {
		if(isActive()){
			if (USE_TALON) {
				if(value){
					talon.setNeutralMode(NeutralMode.Brake);
				}else{
					talon.setNeutralMode(NeutralMode.Coast);
				}
			}
		}
	}

	/**
	 * Talon ONLY
	 */
	public void enableCurrentLimit(boolean enable) {
		if(isActive()){
			if (USE_TALON) {
				talon.enableCurrentLimit(enable);
			}
		}
	}

	/**
	 * Talon ONLY
	 */
	public void configCurrentLimitContinuous(int amps, int timeout) {
		if (isActive()){
			if (USE_TALON) {
				talon.configContinuousCurrentLimit(amps, timeout);
			}
		}
	}
	
	/**
	 * Talon ONLY
	 */
	public void configCurrentLimitPeak(int amps, int timeout) {
		if(isActive()){
			if (USE_TALON) {
				talon.configPeakCurrentLimit(amps, timeout);
			}
		}
	}
	
	public void setReversed(boolean reverse){
		this.reverse = reverse;
	}
	
	public boolean isReversed(){
		return this.reverse;
	}
	
	public void setupMotionProfile(double p, double i, double d, double f){
		//setup talon for motion profile PID
		
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		talon.configNeutralDeadband(kNeutralDeadband, kTimeoutMs);

		talon.config_kF(0, f, kTimeoutMs);
		talon.config_kP(0, p, kTimeoutMs); //6
		talon.config_kI(0, i, kTimeoutMs); //0.0005
		talon.config_kD(0, d, kTimeoutMs);

		/* Our profile uses 10ms timing */
		talon.configMotionProfileTrajectoryPeriod(10, kTimeoutMs); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
	}
	
	public TalonSRX getTalon(){
		return talon;
	}
	
	/**
	 * Indicate whether the current motor controller is active (ie not above the threshold).
	 * This function was added so we could disable motor controllers without removing them from the code.
	 * @return true iff the ID is below the threshold
	 */
	private boolean isActive(){
		if(myID < ID_THRESHOLD){
			return true;
		}
		return false;
	}
}
