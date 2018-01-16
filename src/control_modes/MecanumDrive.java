package control_modes;

import general.CustomXBox;

public class MecanumDrive extends ControlMode{
	
	public MecanumDrive() {
		super(4, "Mecanum Drive");
	}

	/**
	 * 
	 * @param controller
	 * @return double array with four axis values
	 * 			the first index contains the front left motor speeds
	 * 			the second index contains the back left motor speeds
	 * 			the third index contains the front right motor speeds
	 * 			the fourth index contains the back right motor speeds
	 */
	@Override
	public double[] getAxisValues(CustomXBox controller){
		double xAxis = controller.leftStickXaxis();
		double yAxis = -controller.leftStickYaxis();
		double zAxis = controller.rightStickXaxis();
		if(xAxis == -0){
			xAxis = 0;
		}
		if(yAxis == -0){
			yAxis = 0;
		}
		if(zAxis == -0){
			zAxis = 0;
		}
		
		double diag1 = mecanumMove(xAxis, yAxis, 1);
		double diag2 = mecanumMove(xAxis, yAxis, 2);
		
		double leftRot = zAxis;
		double rightRot = -zAxis;
		
		
		double frontLeft = -(diag2+leftRot);
		double backLeft = -(diag1+leftRot);
		double frontRight = diag1+rightRot;
		double backRight = diag2+rightRot;
		
		axisValues[0] = frontLeft;
		axisValues[1] = backLeft;
		axisValues[2] = frontRight;
		axisValues[3] = backRight;
		return axisValues;
	}
	
	private double mecanumMove(double x, double y, int diag){
		double theta;
		
		double thetaI = 0; //the lower bound theta in the slice of the unit circle (8 slices to the unit circle)
		
		double valueI = 0; //the initial value at thetaI
		
//		double myX = x;
//		double myY = y;
//		if(x == 0){ //if x is 0 and thus y/x is undefined
//			
//			if(y>=0){
//				theta = 0;
//			}else{
//				theta = 180;
//			}
//		}else{
			theta = 90.0d - Math.toDegrees(Math.atan2(y,x));
			if(theta < 0){
				theta += 360.0d;
			}
//		}
		
		if(diag == 2){
			theta = 360.0d - theta;
		}
		
		if(theta >= 360){
			theta -= 360.0;
		}

//		System.out.printf("move(x: %.3f,y: %.3f,diag: %d). theta: %f",x,y,diag,theta);
		
		double slope = 0;
		
		if(theta <= 45){
			slope = -1.2;
			thetaI = 0;
			valueI = 1;
		}else if(theta <= 90){
			slope = -0.8;
			thetaI = 45;
			valueI = -0.2;
		}else if(theta <= 135){
			slope = 0.0;
			thetaI = 90;
			valueI = -1;
		}else if(theta <= 180){
			slope = 0.0;
			thetaI = 135;
			valueI = -1;
		}else if(theta <= 225){
			slope = 1.2;
			thetaI = 180;
			valueI = -1;
		}else if(theta <= 270){
			slope = 0.8;
			thetaI = 225;
			valueI = 0.2;
		}else if(theta <= 315){
			slope = 0.0;
			thetaI = 270;
			valueI = 1;
		}else if(theta <= 360){
			slope = 0.0;
			thetaI = 315;
			valueI = 1;
		}
		
		double valueDifference = ( (theta-thetaI) / (45) ) * slope;
		
		double value = valueI + valueDifference;
		
//		System.out.printf(" s: %.3f v: %.3f\n",slope,value);
		
		return value*Math.max( Math.abs(x), Math.abs(y) );
	}
}
