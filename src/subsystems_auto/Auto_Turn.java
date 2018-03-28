package subsystems_auto;

import com.kauailabs.navx.frc.AHRS;

import AutonomousControls.AutonomousControl;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import logging.SimpleLogger;
import subsystems_tele.Drive;

public class Auto_Turn extends AutonomousControl{

	PIDController pid;
	double currentTarget = 0;
	final double target;
	final double tolerance;

	private final double turn_P = 0.04;
	private final double turn_I = 0.00400;
	private final double turn_D = 0;
	
	AHRS gyro;
	Drive drive;
	
	public Auto_Turn(double target, double tolerance, AHRS gyro, Drive drive){
		this.target = target;
		this.tolerance = tolerance;
		this.gyro = gyro;
		this.drive = drive;
	}
	
	
	
	@Override
	public void start(){
		System.out.println("START AUTO TURN!!!!!!!!!!!!!");
		currentTarget = 0;
		
		gyro.zeroYaw();
    	
    	PIDSource gyroSource = new PIDSource() {
			
    		PIDSourceType mySource = PIDSourceType.kDisplacement;
    		
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				this.mySource = pidSource;
			}
			
			@Override
			public double pidGet() {
				return gyro.getAngle();
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				return mySource;
			}
		};
		PIDOutput gyroOutput = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				drive.setTankDrive(output, -output);
			}
		};
		
    	pid = new PIDController(turn_P, turn_I, turn_D, gyroSource, gyroOutput);
    	pid.setAbsoluteTolerance(tolerance);
    	pid.setOutputRange(-0.45, 0.45);
    	pid.setSetpoint(0);
    	pid.reset();
    	pid.enable();
	}
	
	@Override
	public boolean check(){
		if(currentTarget < target){
    		currentTarget += 2;
    	}else if(currentTarget > target){
    		currentTarget -= 2;
    	}

		SimpleLogger.log("currentTarget: " + currentTarget + " target: " + target);
		pid.setSetpoint(currentTarget);
		
		return pid.onTarget() && currentTarget == target && gyro.getRate() < 0.2;
	}
	
	@Override
	public void stop(){
		pid.disable();
		pid.reset();
	}
}
