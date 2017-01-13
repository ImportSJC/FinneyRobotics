package cpi;

public class DeadBand {
	private static double DEADBAND_VALUE = 0.2;
	
	public static double value(double input){
		if(Math.abs(input) < DEADBAND_VALUE){
			return 0;
		}
		
		return input;
	}
}
