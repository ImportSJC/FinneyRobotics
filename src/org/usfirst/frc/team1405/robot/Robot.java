
package org.usfirst.frc.team1405.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.CANTalon;
//import cpi.CANTalon;
import cpi.Drive;
import cpi.Elevator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
   cpi.NetString stest;
   cpi.NetBoolean btest;
   cpi.NetDouble dtest;
   cpi.SetBoolean bstest;
   cpi.SetDouble dstest;
   cpi.SetString sstest;
   cpi.Interface.BooleanOutput bIfTest;
   cpi.Interface.BooleanInput bIFTestIn;
   cpi.Interface.DoubleOutput dIfTest;
   cpi.Interface.DoubleInput dIFTestIn;
   cpi.Interface.StringOutput sIfTest;
   cpi.Interface.StringInput sIFTestIn;
   cpi.XBox360 pilot;
   cpi.XBox360 coPilot;
   double i=0;
   cpi.CANTalon cpiTalon;
   edu.wpi.first.wpilibj.CANTalon eduTalon;
   cpi.CANTalon namedTalon;
   Drive drive;
   Elevator elevator;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	final static public String header="Team 1405 Robot Template ver. 4.6"; // This is required
	
    public void robotInit() {
    	
    	initialize();
    }
    void initialize(){
    	//cpi.Preferences.printrm();
    	//cpi.Preferences.printhckl();
    	//cpi.Preferences.printpkl();
    	
    	cpi.Preferences.initialize();
    	btest=new cpi.NetBoolean("Boolean Table","B Key",true);
    	dtest = new cpi.NetDouble("Double Table/D1", " D Key",0.125);
    	stest=new cpi.NetString("stest", "This string","S Key");
    	//btest.lock();
    	//dtest.lock();
    	//stest.lock();
    	bstest=new cpi.SetBoolean("SBoolean Table", "BS Key",true);
    	dstest= new cpi.SetDouble("SDouble table", "DS Key", 1.254);
    	sstest =new cpi.SetString("SString Table", "SS Key", "Hello World!");
    	bIFTestIn=new cpi.Interface.BooleanInput("/bIFTestIn", "Input Interface1","bkey");
    	bIfTest=new cpi.Interface.BooleanOutput("bIfTest", "bkey");
    	dIFTestIn=new cpi.Interface.DoubleInput("/bIFTestIn", "Input Interface2","dkey");
    	dIfTest=new cpi.Interface.DoubleOutput("dIfTest", "dkey");
    	sIFTestIn=new cpi.Interface.StringInput("/bIFTestIn", "Input Interface3","skey");
    	sIfTest=new cpi.Interface.StringOutput("sIfTest", "skey");
    	pilot=new cpi.XBox360("Pilot");
    	pilot.robotInit();
    	coPilot=new cpi.XBox360("Copilot");
    	coPilot.robotInit();
    	//cpiTalon=new cpi.CANTalon(3);
    	//cpiTalon=cpi.CANTalon.getInstance(3);
    	//cpiTalon2=cpi.CANTalon2.getinstance(3);
    	//eduTalon = new edu.wpi.first.wpilibj.CANTalon(3);
    	//Autonomous.robotInit();
    	namedTalon=cpi.CANTalon.getInstance("/Drive1", "Left  Motor #1", 5);
    	//drive = new Drive("/Teleop Drive");
//    	elevator=new Elevator("/Elevator");
    	
    }
    
    public void autonomousInit(){
    	//Autonomous.autonomousInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//Autonomous.autonomousPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    
    	//pilot.teleopPeriodic();
    	//eduTalon.set(0.5);
    	//cpiTalon.set(0.5);
    	//cpiTalon2.set(0.5);
    	namedTalon.set(0.5);
    	//drive.TeleopPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void disabledInit(){
    	//CANTalon.disabledInit();
    }
    public void testInit(){
    	LiveWindow.setEnabled(false);
    	cpi.CANTalon.testInit();
    }
    public void testPeriodic() {
    	pilot.teleopPeriodic();
    	cpi.CANTalon.testPeriodic();
    }
    
    public void disabledPeriodic(){
    	pilot.teleopPeriodic();
    	cpi.autoSupportClasses.Set.disabledPeriodic();
    }
}