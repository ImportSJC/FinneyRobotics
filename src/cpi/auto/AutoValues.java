package cpi.auto;

public class AutoValues {
	/*		Distances		*/
	public static final int distance_toCenter_noBump = 6300;//tested
	public static final int distance_toCenter_bump = 6500;//tested
	
	public static final int distance_canToTote = 1142;//tested
	public static final int distance_toteToTote = 4385;
	
	/*		Times		*/
	public static final double time_canOnTote = 1.2;//tested
	public static final double time_toteOnTote = 0.8;
	
	/*		Speeds		*/
	public static final double speed_drive = 0.3;
	public static final double speed_turn = 0.3;
	public static final double speed_turnSlow = 0.2;
	
	public static int distanceToCenter(boolean isBump){
		if(isBump){return AutoValues.distance_toCenter_bump;}
		else{return AutoValues.distance_toCenter_noBump;}
	}
}
