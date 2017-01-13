package cpi;

public class DeadBand {
	private static double DEADBAND_VALUE = 0.1;
	
	public static double value(double input){
		if(Math.abs(input) < DEADBAND_VALUE){
			return 0;
		}
		
		return input;
	}
}
