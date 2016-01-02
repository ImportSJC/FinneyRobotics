/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author Robotics
 */
public class SimpleXBox360 {
    public SimpleXBox360(String pname){
        name=pname;
        instance++;
        
    }
    
    public void robotInit(){
        joystick=new Joystick(instance);
        
       
        // Test remote equivalents to interfacs instantiations
    }
    
       
    public void autonomousInit(){
    }
    public void autonomousPeriodic() {
    }
    
    public void teleopInit(){
    }
    
    public void teleopPeriodic() {
     // Get values from joystick     
     leftStickXaxis=joystick.getRawAxis(0);
     leftStickYaxis=joystick.getRawAxis(1);
     leftTrigger=joystick.getRawAxis(2);
     rightTrigger=joystick.getRawAxis(3);
     rightStickXaxis=joystick.getRawAxis(4);
     rightStickYaxis=joystick.getRawAxis(5);
     directionalPad=joystick.getPOV();
     joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kRightRumble, (float)rightRumble);
     joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kLeftRumble, (float)leftRumble);
     
     
     aButton=joystick.getRawButton(1); 
     bButton=joystick.getRawButton(2);
     xButton=joystick.getRawButton(3);    
     yButton=joystick.getRawButton(4); 
     leftBumper=joystick.getRawButton(5);  
     rightBumper=joystick.getRawButton(6); 
     stopBackButton=joystick.getRawButton(7); 
     startButton=joystick.getRawButton(8);    
     leftThumbstickButton=joystick.getRawButton(9);  
     rightThumbstickButton=joystick.getRawButton(10);
    
    }
    
    //$$$$ Begin Test Code - BO NOT REMOVE THIS COMMENT!!!
    public void testInit(){
    }
    
    public void testPeriodic() {
    }
    //$$$$ End Test Code - BO NOT REMOVE THIS COMMENT!!!
    
    
    public void disabledInit(){
    }
    Joystick joystick;
    java.lang.String name;
  static int instance=-1;
  
 // joystick declarations   
  // remote joystick declarations
    public double leftStickXaxis;
    public double leftStickYaxis; 
    public double rightStickXaxis;
    public double rightStickYaxis;
    public double directionalPad;
    public double triggers;
    public double leftTrigger;
    public double rightTrigger;
  
  
    public boolean aButton;  
    public boolean bButton;  
    public boolean xButton;  
    public boolean yButton;
    public boolean leftBumper;  
    public boolean rightBumper;  
    public boolean stopBackButton;
    public boolean startButton;  
    public boolean leftThumbstickButton;
    public boolean rightThumbstickButton;
    public double rightRumble;
    public double leftRumble;
}
    /**
     * XBox 360
     */
    /*
 * Description XBox 360:
    This maps the XBox controller to the WPILIB Joystick class
    Use the Joystick.GetRawAxis() method for the axies
    Use the Joystick.GetRawButton() methods for the buttons
    
 */
/*
1: A
2: B
3: X
4: Y
5: Left Bumper
6: Right Bumper
7: Back
8: Start
9: Left Joystick
10: Right Joystick

The axis on the controller follow this mapping
(all output is between -1 and 1)

    0: Left Stick X Axis
    -Left:Negative ; Right: Positive
    1: Left Stick Y Axis
    -Up: Negative ; Down: Positive
    2: Left Trigger
    3: Right Trigger
    4: Right Stick X Axis
    -Left: Negative ; Right: Positive
    5: Right Stick Y Axis
    -Up: Negative ; Down: Positive
    6: Directional Pad 


Here is the mapping that I have found out as I have listed in the VI:

X Axis: Left Thumbstick Left-Right
X Axis Rotation: Right Thumbstick Left-Right
Y Axis: Left Thumb Stick (Needs to be negated or else up-down controlls are inverted)
Y Axis Rotation: Right Thumb Stick (Needs to be negated or else up-down controlls are inverted)
Z Axis (>0): Left Trigger
Z Axis (<0): Right Trigger

1: A Button
2: B Button
3: X Button
4: Y Button
5: Left Bumper
6: Right Bumper
7: Stop/Back Button
8: Start Button
9: Left Thumbstick Button
10: Right Thumbstick Button

DPAD:
-1: No Thumbpad Button
0: North Thumbpad Button
45: North-East Thumbpad Button
90: East Thumbpad Button
135: South-East Thumbpad Button
180: South Thumbpad Button
225: South-West Thumbpad Button
270: West Thumbpad Button
315: North-West Thumbpad Button
 * 
 */
    /**
     * Axis
     */