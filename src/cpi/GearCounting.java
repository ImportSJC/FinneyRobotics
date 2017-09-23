package cpi;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GearCounting {
	static String THIS_TABLE_NAME = "GearCounting";
	private static int gearsDelivered = 0;
	
	private static boolean add_pressed = false;
	private static boolean sub_pressed = false;
	
	static NetworkTable settings;
	
	public static void robotInit(){
		settings=NetworkTable.getTable(THIS_TABLE_NAME);
		settings.putNumber("gearsDelivered", gearsDelivered);
		settings.putNumber("gearsTil", gearsTilNextRotor());
		settings.putNumber("matchTime", DriverStation.getInstance().getMatchTime());
	}
	
	public static void teleInit(){
		gearsDelivered = 0;
	}
	
	public static void addGear(){
		if(gearsDelivered != 12){
			gearsDelivered++;
		}
	}
	
	public static void subGear(){
		if(gearsDelivered != 0){
			gearsDelivered--;
		}
	}
	
	public static int gearsTilNextRotor(){
		if(gearsDelivered < 2){
			return 2 - gearsDelivered;
		}else if(gearsDelivered < 6){
			return 6 - gearsDelivered;
		}else{
			return 12 - gearsDelivered;
		}
	}
	
	public static int gearsLeft(){
		return 12 - gearsDelivered;
	}
	
	public static void TeleopPeriodic(boolean addGear, boolean subGear){
		if(addGear && !add_pressed){
			addGear();
		}
		
		if(subGear && !sub_pressed){
			subGear();
		}
		
		sub_pressed = subGear;
		add_pressed = addGear;
		settings.putNumber("gearsDelivered", gearsDelivered);
		settings.putNumber("gearsTil", gearsTilNextRotor());
		settings.putNumber("matchTime", (DriverStation.getInstance().getMatchTime()));
	}
}
