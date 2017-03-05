package cpi.auto;

import cpi.Drive;
import cpi.outputDevices.MotorController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDController.Tolerance;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoOutputs {
//	public static double leftMotor1 = 0.0;
//	public static double rightMotor1 = 0.0;
//	public static double leftMotor2 = 0.0;
//	public static double rightMotor2 = 0.0;
	private static final String THIS_TABLE_NAME = "AutoOutputs";
	
	public static MotorController leftMotor1;
	public static MotorController leftMotor2;
	public static MotorController leftMotor3;
	public static MotorController rightMotor1;
	public static MotorController rightMotor2;
	public static MotorController rightMotor3;
	
	private static boolean gyroAssist = false;
	private static double driveSpeed = 0.0;
	private static double turnSpeed = 0.0;
	
	private static double adjustment;
	private static double perfectRateTurn_Gyro;
	private static double perfectRateTurn_Encoder;
	private static double perfectRateDrive_Encoder;
	
	private static double leftEnc_driveFwd_zero = 0;
	private static double rightEnc_driveFwd_zero = 0;
	private static double DRIVE_FWD_MARGIN = 0.2; //number of counts the right encoder can be off the left encoder while driving fwd
	private static double TURN_MARGIN = 0.2; //number of degrees the gyro can be off while turning
	private static double driveFwd_adjustment = 0.01;
	
	private static PIDSource src_drive;
	private static PIDOutput out_drive;
	private static PIDController pid_drive;
	private static double Pdrive = 0.6;
	private static double Idrive = 0.2;
	private static double Ddrive = 1.2;
	
	private static PIDSource src_turn;
	private static PIDOutput out_turn;
	private static PIDController pid_turn;
	private static double Pturn = 0.3;
	private static double Iturn = 0.03;
	private static double Dturn = 0.2;
	
	private static NetworkTable settings;
	
	public static void robotInit(){
		leftMotor1 = Drive.left1;
		leftMotor2 = Drive.left2;
		leftMotor3 = Drive.left3;
		rightMotor1 = Drive.right1;
		rightMotor2 = Drive.right2;
		rightMotor3 = Drive.right3;
		
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		
		src_drive = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public double pidGet() {
				double tmp = AutoInputs.getEncoderDistanceAvg();
//				System.out.println("Drive PID in: " + tmp);
				return tmp;
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		out_drive = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				System.out.println("Drive PID out: " + output/4);
				setDriveFwd(output/4);
			}
		};
		
		pid_drive = new PIDController(Pdrive, Idrive, Ddrive, src_drive, out_drive);
		
		settings.putNumber("Pdrive", Pdrive);
		settings.putNumber("Idrive", Idrive);
		settings.putNumber("Ddrive", Ddrive);
		
		src_turn = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public double pidGet() {
				double tmp = AutoInputs.getGyroAngle();
//				System.out.println("Turn PID in: " + tmp);
				return tmp;
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		out_turn = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				System.out.println("Turn PID out: " + output/2);
				setDriveTurn(output/4);
//				setDriveFwd(output/4);
			}
		};
		
		pid_turn = new PIDController(Pturn, Iturn, Dturn, src_turn, out_turn);
		
		settings.putNumber("Pturn", Pturn);
		settings.putNumber("Iturn", Iturn);
		settings.putNumber("Dturn", Dturn);
	}
	
	public static void ResetValues(){
		adjustment = .05;
		perfectRateTurn_Gyro = 1;
		perfectRateTurn_Encoder = 1200;
//		perfectRateDrive_Encoder = 1500; //old drive base option
		perfectRateDrive_Encoder = 1; //new drive base option
	}
	
public static void autoInit(){
		Pdrive = settings.getNumber("Pdrive", Pdrive);
		Idrive = settings.getNumber("Idrive", Idrive);
		Ddrive = settings.getNumber("Ddrive", Ddrive);
	
		pid_drive = new PIDController(Pdrive, Idrive, Ddrive, src_drive, out_drive);
		
		Pturn = settings.getNumber("Pturn", Pturn);
		Iturn = settings.getNumber("Iturn", Iturn);
		Dturn = settings.getNumber("Dturn", Dturn);
	
		pid_turn = new PIDController(Pturn, Iturn, Dturn, src_turn, out_turn);
	}
	
	public void AutonomousPeriodic(){
		
//		if(gyroAssist){
//			if (driveSpeed>=0){
//				//Moderate gyro assist (not as fast of correction)
////				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
////				setDrive(driveSpeed, -(1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
//				
//				//Drastic gyro assist (a faster correction)
////				System.out.println("Gyro Assisted Driving Enabled, correction: " + -(1.0/(50*driveSpeed))*AutoInputs.getGyroAngle()
////						 + " Gyro angle: " + AutoInputs.getGyroAngle());
//				setDrive(driveSpeed, -(1.0/(50*driveSpeed))*AutoInputs.getGyroAngle());
//			}
//			else{
//				//Moderate gyro assist (not as fast of correction)
////				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
////				setDrive(driveSpeed, (1.0/(200*driveSpeed))*AutoInputs.getGyroAngle());
//				
//				//Drastic gyro assist (a faster correction)
////				System.out.println("Gyro Assisted Driving Enabled, correction: " + (1.0/(50*driveSpeed))*AutoInputs.getGyroAngle()
////						 + " Gyro angle: " + AutoInputs.getGyroAngle());
//				setDrive(driveSpeed, (1.0/(50*driveSpeed))*AutoInputs.getGyroAngle());
//			}
//		}
	}
	
	public static void setDriveBrake(boolean value){
		leftMotor1.enableBrakeMode(value);
		leftMotor2.enableBrakeMode(value);
		leftMotor3.enableBrakeMode(value);
		rightMotor1.enableBrakeMode(value);
		rightMotor2.enableBrakeMode(value);
		rightMotor3.enableBrakeMode(value);
	}
	
	public static void reset_Drive(){
		leftMotor1.set(0);
		leftMotor2.set(0);
		leftMotor3.set(0);
		rightMotor1.set(0);
		rightMotor2.set(0);
		rightMotor3.set(0);
		gyroAssist = false;
		driveSpeed = 0.0;
		turnSpeed = 0.0;
	}
	
	public static void setDrive(double drivingSpeed, double turningSpeed){
//		System.out.println("Drive Motors are assigned the drivespeed: " + drivingSpeed + " turnSpeed: " + turningSpeed);
		leftMotor1.set(-drivingSpeed-turningSpeed);
		leftMotor2.set(-drivingSpeed-turningSpeed);
		leftMotor3.set(-drivingSpeed-turningSpeed);
		rightMotor1.set(drivingSpeed-turningSpeed);
		rightMotor2.set(drivingSpeed-turningSpeed);
		rightMotor3.set(drivingSpeed-turningSpeed);
		turnSpeed = turningSpeed;
		driveSpeed = drivingSpeed;
	}
	
	public static void setDriveFwd(double speed){
//		leftMotor1 = speed;
//		leftMotor2 = speed;
//		rightMotor1 = -speed;
//		rightMotor1 = -speed;
		
		double leftChange = 0;
		double rightChange = 0;
		
//		if(leftEnc_driveFwd_zero-driveFwd_margin>rightEnc_driveFwd_zero){
//			leftChange=driveFwd_adjustment;
//		}else if(rightEnc_driveFwd_zero-driveFwd_margin>leftEnc_driveFwd_zero) {
//			rightChange=driveFwd_adjustment;
//		}
		
//		rightChange+=driveFwd_adjustment;
		
		leftMotor1.set(speed+leftChange);
		leftMotor2.set(speed+leftChange);
		leftMotor3.set(speed+leftChange);
		rightMotor1.set(-speed-rightChange);
		rightMotor2.set(-speed-rightChange);
		rightMotor3.set(-speed-rightChange);
		
		driveSpeed = speed;
//		gyroAssist = true;
//		System.out.println("Drive Motors are assigned the speed: " + speed);
//		System.out.println("Left Enc: " + AutoInputs.getLeftEncoderCount() + " Right enc: " + AutoInputs.getRightEncoderCount());
//		System.out.println("left change: " + leftChange + " right change: " + rightChange);
//		leftEnc_driveFwd_zero = Math.abs(AutoInputs.getLeftEncoderCount());
//		rightEnc_driveFwd_zero = Math.abs(AutoInputs.getRightEncoderCount());
	}
	
	public static void setDriveTurn(double speed){
//		System.out.println("Drive Motors are assigned the speed: " + speed);
		leftMotor1.set(speed);
		leftMotor2.set(speed);
		leftMotor3.set(speed);
		rightMotor1.set(speed);
		rightMotor2.set(speed);
		rightMotor3.set(speed);
	}
	
	public static void startPID_Turn(double targetAngle){
		System.out.println("Start Turn PID: " + targetAngle);
		pid_turn.setSetpoint(targetAngle);
		Tolerance tol = new Tolerance() {
			
			@Override
			public boolean onTarget() {
				if(AutoInputs.getGyroAngle()>targetAngle-TURN_MARGIN && AutoInputs.getGyroAngle()<targetAngle+TURN_MARGIN){
					if(Math.abs(AutoInputs.getGyroRate())<perfectRateTurn_Gyro){return true;}
				}
				return false;
			}
		};
		
		pid_turn.setTolerance(tol);
		pid_turn.enable();
	}
	
	public static boolean checkPID_Turn(){
//		System.out.println("on target?: " + pid.onTarget());
		return pid_turn.onTarget();
	}
	
	public static void stopPID_Turn(){
		pid_turn.disable();
		System.out.println("\nPID Turn Stop\n");
	}
	
	public static void rampTurn_Gyro(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		double tmpTurn = turnSpeed;
		
		if(remainingAngle < 0 && perfectRateTurn_Gyro > 0){
			perfectRateTurn_Gyro/=2;
			perfectRateTurn_Gyro = -perfectRateTurn_Gyro;
		}else if (remainingAngle > 0 && perfectRateTurn_Gyro < 0){
			perfectRateTurn_Gyro = -perfectRateTurn_Gyro;
			perfectRateTurn_Gyro/=2;
		}
		
		if(AutoInputs.getGyroRate() > perfectRateTurn_Gyro){
			tmpTurn = tmpTurn - adjustment;
		}else if(AutoInputs.getGyroRate() < perfectRateTurn_Gyro){
			tmpTurn = tmpTurn + adjustment;
		}
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
//		System.out.println("Gyro Rate: " + AutoInputs.getGyroRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + tmpTurn);
		
		setDrive(tmpDrive, tmpTurn);
	}
	
	public static void startPID_Drive(double targetDistance){
		System.out.println("Start Drive PID: " + targetDistance);
		System.out.println("Left Count: " + AutoInputs.getLeftEncoderCount() + " Right count: " + AutoInputs.getRightEncoderCount());
		pid_drive.setSetpoint(targetDistance);
		Tolerance tol = new Tolerance() {
			
			@Override
			public boolean onTarget() {
				if(AutoInputs.getEncoderDistanceAvg()>targetDistance-DRIVE_FWD_MARGIN && AutoInputs.getEncoderDistanceAvg()<targetDistance+DRIVE_FWD_MARGIN){
					if(Math.abs(AutoInputs.getSummedEncoderRate())<perfectRateDrive_Encoder){System.out.println("ON TARGET!!!");return true;}
				}
				return false;
			}
		};
		
		pid_drive.setTolerance(tol);
		pid_drive.enable();
	}
	
	public static boolean checkPID_Drive(){
//		System.out.println("on target?: " + pid.onTarget());
		return pid_drive.onTarget();
	}
	
	public static void stopPID_Drive(){
		pid_drive.disable();
		System.out.println("\nPID Drive Stop\n");
	}
	
	public static void rampDrive_Encoder(double remainingDistance, double targetDistance){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		
		if(remainingDistance < 0 && perfectRateDrive_Encoder > 0){
			System.out.print("Case 1 - ");
			perfectRateDrive_Encoder/=2;
			perfectRateDrive_Encoder = -perfectRateDrive_Encoder;
		}else if (remainingDistance > 0 && perfectRateDrive_Encoder < 0){
			System.out.print("Case 2 - ");
			perfectRateDrive_Encoder = -perfectRateDrive_Encoder;
			perfectRateDrive_Encoder/=2;
		}
		
		if(AutoInputs.getEncoderDriveDirection() == -1 && perfectRateDrive_Encoder > 0 && tmpDrive<1){
			tmpDrive = tmpDrive + adjustment;
		}else if(AutoInputs.getEncoderDriveDirection() == 1 && perfectRateDrive_Encoder < 0 && tmpDrive>-1){
			tmpDrive = tmpDrive - adjustment;
		}else{
			if(remainingDistance > 0){
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateDrive_Encoder) && tmpDrive>-1){
					System.out.print("Case 3 - ");
					tmpDrive = tmpDrive - adjustment;
				}else if(tmpDrive<1){
					System.out.print("Case 4 - ");
					tmpDrive = tmpDrive + adjustment;
				}
			}else{
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateDrive_Encoder) && tmpDrive<1){
					System.out.print("Case 5 - ");
					tmpDrive = tmpDrive + adjustment;
				}else if(tmpDrive>-1){
					System.out.print("Case 6 - ");
					tmpDrive = tmpDrive - adjustment;
				}
			}
		}
		
		System.out.println("Perfect Rate: " + perfectRateDrive_Encoder + " Summed Rate: " + AutoInputs.getSummedEncoderRate());
		System.out.println("Encoder Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Remaining distance: " + remainingDistance + " drive Speed: " + tmpDrive);
		setDriveFwd(tmpDrive);
	}
	
	public static void rampTurn_Encoder(double remainingAngle, double targetAngle){
		//intelligently turn the robot smoothly into the target angle 
		
		double tmpDrive = driveSpeed;
		double tmpTurn = turnSpeed;
		
		if(remainingAngle < 0 && perfectRateTurn_Encoder > 0){
//			System.out.print("Case 1 - ");
			perfectRateTurn_Encoder/=2;
			perfectRateTurn_Encoder = -perfectRateTurn_Encoder;
		}else if (remainingAngle > 0 && perfectRateTurn_Encoder < 0){
//			System.out.print("Case 2 - ");
			perfectRateTurn_Encoder = -perfectRateTurn_Encoder;
			perfectRateTurn_Encoder/=2;
		}
		
		if(AutoInputs.getEncoderTurnDirection() == -1 && perfectRateTurn_Encoder > 0){
			tmpTurn = tmpTurn + adjustment;
		}else if(AutoInputs.getEncoderTurnDirection() == 1 && perfectRateTurn_Encoder < 0){
			tmpTurn = tmpTurn - adjustment;
		}else{
			if(remainingAngle > 0){
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateTurn_Encoder)){
//					System.out.print("Case 3 - ");
					tmpTurn = tmpTurn - adjustment;
				}else{
//					System.out.print("Case 4 - ");
					tmpTurn = tmpTurn + adjustment;
				}
			}else{
				if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRateTurn_Encoder)){
//					System.out.print("Case 5 - ");
					tmpTurn = tmpTurn + adjustment;
				}else{
//					System.out.print("Case 6 - ");
					tmpTurn = tmpTurn - adjustment;
				}
			}
		}
		
//		if(AutoInputs.getSummedEncoderRate() > Math.abs(perfectRate_Encoder)){
//			if(remainingAngle > 0){
//				System.out.print("Case 3 - ");
//				tmpTurn = tmpTurn - adjustment;
//			}else{
//				System.out.print("Case 4 - ");
//				tmpTurn = tmpTurn + adjustment;
//			}
//		}else if(AutoInputs.getSummedEncoderRate() < Math.abs(perfectRate_Encoder)){
//			if(remainingAngle > 0){
//				System.out.print("Case 5 - ");
//				tmpTurn = tmpTurn + adjustment;
//			}else{
//				System.out.print("Case 6 - ");
//				tmpTurn = tmpTurn - adjustment;
//			}
//		}
		
		//turn the robot slower until it reaches the target angle (remaining angle == 0)
//		System.out.println("Perfect Rate: " + perfectRateTurn_Encoder);
//		System.out.println("Encoder Summed Rate: " + AutoInputs.getSummedEncoderRate() + " Remaining angle: " + remainingAngle + " turn Speed: " + tmpTurn);
		setDrive(tmpDrive, tmpTurn);
	}

	public static void disabledInit() {
		pid_drive.disable();
		pid_turn.disable();
	}
}
