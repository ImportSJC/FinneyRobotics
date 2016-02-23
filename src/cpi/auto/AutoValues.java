package cpi.auto;

public class AutoValues {
	/*		Distances(in inches)		*/
	
	//distance from the front of the robot (w/ bumpers) in its starting auto position (an inch over the auto line)
	//to the middle of the defenses ramp in inches, this should be used in approaching the defenses
	public static final double distance_toDefenses = 35.36;
	
	//distance fromt the front of the robot in its starting auto position to the alignment line
	//travelling through the lowbar, in inches.
	public static final double distance_toAlignmentLine = 137.76;
	
	//the distance from the auto line to the point where we need to turn 60 deg, move foward, and shoot the ball.
	public static final double distance_toShootingTurn = 220.3676159475;
	
	public static final double distance_robotCircumference = 27.5 * Math.PI;//22
	public static final double distance_degreeTurn = distance_robotCircumference/360;
	
	/*		Times		*/
	
	/*		Speeds		*/
	public static final double speed_drive = 0.3;
	public static final double speed_turn = 0.3;
	public static final double speed_turnSlow = 0.2;
}
