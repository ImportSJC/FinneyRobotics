package AutonomousControls;

import edu.wpi.first.wpilibj.Timer;
import logging.SimpleLogger;
import subsystems_tele.Drive;

public class Auto_MotionProfile extends AutonomousControl{
	private double[][] leftMotionProfile;
	private double[][] rightMotionProfile;
	
	private Drive drive;
	
	private volatile int i =  0;
	private volatile double prevErrorL = 0;
	private volatile double prevErrorR = 0;
	private double errorSumL = 0;
	private double errorSumR = 0;
	
	double kPeriod = 0.02;
	double kDriveP = 0.012;
	double kDriveI = 0.01;
	double kDriveD = 0;
	
	double kV = 0;
	double kA = 0;// increase this slowly, this makes it accelerate faster
	
	private double startTime = 0;
	
	public Auto_MotionProfile(Drive drive, double[][] leftMotionProfile, double[][] rightMotionProfile, double p,
			double i, double d){
		this.drive = drive;
		this.leftMotionProfile = leftMotionProfile;
		this.rightMotionProfile = rightMotionProfile;
		
		this.kDriveP = p;
		this.kDriveI = i;
		this.kDriveD = d;
	}
	
	public Auto_MotionProfile(Drive drive, double[][] leftMotionProfile, double[][] rightMotionProfile, double p,
			double i, double d, double v, double a){
		this.drive = drive;
		this.leftMotionProfile = leftMotionProfile;
		this.rightMotionProfile = rightMotionProfile;
		
		this.kDriveP = p;
		this.kDriveI = i;
		this.kDriveD = d;
		this.kA = a;
		this.kV = v;
	}
	
	@Override
	public void start(){
		startTime = Timer.getFPGATimestamp();
		i = 0;
		prevErrorL = 0;
		prevErrorR = 0;
		errorSumL = 0;
		errorSumR = 0;
	}
	
	@Override 
	public boolean check(){
		i = (int) Math.round( (Timer.getFPGATimestamp() - startTime) / kPeriod );
		
		if(i < leftMotionProfile.length) {
			double goalPosL = leftMotionProfile[i][0];
			double goalVelL = leftMotionProfile[i][1];
			double goalAccL = leftMotionProfile[i][2];
			
			double goalPosR = rightMotionProfile[i][0];
			double goalVelR = rightMotionProfile[i][1];
			double goalAccR = rightMotionProfile[i][2];
			
			double errorL = goalPosL - drive.getLeftDistance();
			double errorDerivL = ((errorL - prevErrorL) / kPeriod) - goalVelL;
			
			//calculate the integral, scaled by time
			this.errorSumL += errorL * kPeriod;
			
			double errorR = goalPosR - drive.getRightDistance();
			double errorDerivR = ((errorR - prevErrorR) / kPeriod) - goalVelR;
			
			//calculate the integral, scaled by time
			this.errorSumR += errorR * kPeriod;
			
			double pwmL = (kDriveP * errorL) + (kDriveI * errorSumL) + (kDriveD * errorDerivL) + (kV * goalVelL) + (kA * goalAccL); //to add in an I term you would ad in the integral multiplied by the I coeff
			double pwmR = (kDriveP * errorR) + (kDriveI * errorSumR) + (kDriveD * errorDerivR) + (kV * goalVelR) + (kA * goalAccR);
			
			//ensure the PID doesn't try to make the resulting motor values greater than 1
			if(pwmL > 1.0){
				pwmL = 1.0;
			} else if(pwmL < -1.0){
				pwmL = -1.0;
			}
			
			if(pwmR > 1.0){
				pwmR = 1.0;
			} else if(pwmR < -1.0){
				pwmR = -1.0;
			}
			
//				SimpleLogger.log(goalPosL + ", " + goalPosR + ", " + talon1.getDriveDistance() + ", " + talon4.getDriveDistance());
			SimpleLogger.log(errorL+", "+errorR);
//				SimpleLogger.log(goalPosL + ", " + goalPosR);
			
			prevErrorL = errorL;
			prevErrorR = errorR;
			
			drive.setTankDrive(pwmL, pwmR);
			i++;
		}else {
			drive.setTankDrive(0, 0);
			return true;
		}
		return false;
	}
}
