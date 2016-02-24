package cpi;

import edu.wpi.first.wpilibj.DigitalInput;

public class BallRetain {
	public static DigitalInput ballButton;
	
	private static double intakingTimer = 0;
	private static final double INTAKING_TIME = 1*50;//time to run the motors to retain ball in secs: [secs]*50
	
	private static double ballOutTimer = 0;
	private static final double BALL_OUT_TIME = 1*50;//time to run the motors to retain ball in secs: [secs]*50
	
	private static int ballRetentionStage = 0;// 0=none, 1=intaking, 2=ballIn, 3=ballOut
	
	private static final boolean BUTTON_PRESSED = false;
	
	public static double motorValues = 0;
	
	public static void setBallRetentionStage(int stage){
		ballRetentionStage = stage;
		if(ballRetentionStage == 0){
			motorValues = 0;
			intakingTimer = 0;
			ballOutTimer = 0;
		}else if(ballRetentionStage == 1){
			motorValues = 0;
			intakingTimer = INTAKING_TIME;
			ballOutTimer = 0;
		}else if(ballRetentionStage == 2){
			motorValues = 0;
			intakingTimer = 0;
			ballOutTimer = 0;
		}else if(ballRetentionStage == 3){
			motorValues = Shooter.INTAKE_SPEED;
			intakingTimer = 0;
			ballOutTimer = BALL_OUT_TIME;
		}
	}
	
	public static int getBallRetentionStage(){
		return ballRetentionStage;
	}
	
	public static void robotInit(){
		ballButton = new DigitalInput(9);
	}
	
	public static void telopInit(){
		setBallRetentionStage(0);
	}
	
	public static void telopPeriodic(){
//		System.out.println("Ball Button: " + ballButton.get());
//		System.out.println("Ball Retention stage: " + ballRetentionStage);
//		System.out.println("Motor Values: " + motorValues);
//		System.out.println("intaking timer: " + intakingTimer + " ballout timer: " + ballOutTimer);
		
		if(ballRetentionStage == 0){
			
		}else if(ballRetentionStage == 1){
			 if(intakingTimer > 0 && ballButton.get() == BUTTON_PRESSED){
				 setBallRetentionStage(2);
			 }else if(intakingTimer <= 0){
				 setBallRetentionStage(0);
			 }
		}else if(ballRetentionStage == 2){
			if(ballButton.get() != BUTTON_PRESSED){
				setBallRetentionStage(3);
			}
		}else if(ballRetentionStage == 3){
			if(ballOutTimer > 0 && ballButton.get() == BUTTON_PRESSED){
				setBallRetentionStage(2);
			}else if(ballOutTimer <= 0){
				setBallRetentionStage(0);
			}
		}
		
		if(intakingTimer > 0){
			intakingTimer--;
		}else{
			intakingTimer = 0;
		}
		
		if(ballOutTimer > 0){
			ballOutTimer--;
		}else{
			ballOutTimer = 0;
		}
	}
}
