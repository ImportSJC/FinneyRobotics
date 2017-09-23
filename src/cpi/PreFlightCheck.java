package cpi;

import cpi.auto.AutoInputs;
import cpi.auto.AutoOutputs;

public class PreFlightCheck {
	
	private static boolean checkStarted = false;
	private static boolean allChecksPassed = true;
	private static boolean movingTests = false;
	
	private static final int NUMBER_OF_CHECKS = 100;
	
	private static int numberOfChecksRun = 0;
	
	private static boolean checkGyro(double gyroAngle){
		if(gyroAngle == 0){
			return false;
		}
		
//		if(gyroAngle > 1 || gyroAngle < -1)
//		{
//			return false;
//		}
		
		return true;
	}
	
	private static boolean checkEncoder(double encoderCount){
		if(encoderCount != 0){
			return false;
		}
		
		return true;
	}
	
	private static boolean checkEncoderMoving(double encoderCount){
		if(encoderCount == 0 || encoderCount < 0){
			return false;
		}
		
		return true;
	}
	
	private static void logResult(boolean checkResult, String componentName){
		if(checkResult){
//			System.out.print("PASSED: ");
			return;
		}else{
			System.out.print("FAILED: ");
			numberOfChecksRun = 100;
			checkStarted = false;
			AutoOutputs.setDriveFwd(0);
	    	AutoOutputs.reset_Drive();
			allChecksPassed = false;
		}
		
		System.out.println(componentName);
	}
	
	public static void init(){
		AutoInputs.resetEncoders();
		AutoInputs.resetGyros();
		
		numberOfChecksRun = 0;
		
		if(movingTests){
			AutoOutputs.setDriveFwd(.5);			
		}else{
			allChecksPassed = true;
		}
	}
	
	public static void run(boolean startCheck){
		if(startCheck && !checkStarted){
			checkStarted = true;
			init();
		}
		
		if(checkStarted){
			if(numberOfChecksRun < NUMBER_OF_CHECKS){
				if(movingTests){
					logResult( checkEncoderMoving(AutoInputs.getLeftEncoderCount()) , "Left Encoder");
					logResult( checkEncoderMoving(AutoInputs.getRightEncoderCount()) , "Right Encoder");
					
					logResult( checkGyro(AutoInputs.getGyroAngle()) , "Onboard Gyro");
				}else{
					logResult( checkEncoder(AutoInputs.getLeftEncoderCount()) , "Left Encoder");
					logResult( checkEncoder(AutoInputs.getRightEncoderCount()) , "Right Encoder");
					
					logResult( checkGyro(AutoInputs.getGyroAngle()) , "Onboard Gyro");
				}
				
				System.out.println("Finished Check Number: " + numberOfChecksRun + " Moving: " + movingTests);
				//increment checks run
				numberOfChecksRun++;
			}else{
				if(movingTests){
					checkStarted = false;
					if(allChecksPassed){
						System.out.println("ALL CHECKS PASSED! READY TO FLY!");
					}else{
						System.out.println("PRE-FLIGHT CHECK(S) FAILED!");
					}
					
					AutoOutputs.setDriveFwd(0);
			    	AutoOutputs.reset_Drive();
				}else{
					movingTests = true;
					init();
				}
			}
		}
	}
}
