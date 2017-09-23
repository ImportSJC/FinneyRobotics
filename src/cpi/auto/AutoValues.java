package cpi.auto;

public class AutoValues {
	/*		Distances		*/
	public static final double LENGTH_OF_BOT_WITH_BUMPERS = 36.5;
	public static final double WIDTH_OF_BOT_WITH_BUMPERS = 35;
	public static final double distance_allianceWall_to_centerAirShip = 113.5 - (LENGTH_OF_BOT_WITH_BUMPERS) - 1;//114.3
	
	//Drive from corner of alliance wall to side of airship
//	public static final double distance_allianceWall_to_turn = 58.3;
//	public static final double distance_turn_to_sideAirShip = 89.6;
	
//	public static final double distance_allianceWall_centerOfBot_to_centerAirShip = 72.5 + 24.1 - (LENGTH_OF_BOT_WITH_BUMPERS/2) - 8;// - (LENGTH_OF_BOT_WITH_BUMPERS/2) //77.56; //-6 is to correct for a distance an issue im having
//	public static final double distance_turn_centerOfBot_to_sideAirShip = 112.5 - (LENGTH_OF_BOT_WITH_BUMPERS/2) - 11 - 20.2;//70.36//distance to spring + distance into spring
//	
	
	//alex's numbers
//	public static final double distance_allianceWall_centerOfBot_to_centerAirShip = 94 - (LENGTH_OF_BOT_WITH_BUMPERS/2);// - (LENGTH_OF_BOT_WITH_BUMPERS/2) //77.56; //-6 is to correct for a distance an issue im having
//	public static final double distance_turn_centerOfBot_to_sideAirShip = 65;//70.36//distance to spring + distance into spring
	
	//hiltons numbers
	public static final double distance_allianceWall_centerOfBot_to_centerAirShip = 88-19+2.5;// - (LENGTH_OF_BOT_WITH_BUMPERS/2) //77.56; //-6 is to correct for a distance an issue im having
	public static final double distance_turn_centerOfBot_to_sideAirShip = 73.8 + 2;//70.36//distance to spring + distance into spring
	
	
	/*		Angles		*/
	public static final double angle_turnToSideGear = 60; // the angle you need to turn to align your gear to the airship, on one of the sides
	
	/*		Times		*/
	
	/*		Speeds		*/
	public static final double speed_driveCarpet = 0.8;
	
	public static final double speed_turnCarpet = 0.5;
	public static final double speed_turnTile = 0.35;
}
