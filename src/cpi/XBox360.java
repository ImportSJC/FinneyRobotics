/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpi;

import edu.wpi.first.wpilibj.Joystick;
import cpi.Interface.BooleanOutput;
import cpi.Interface.DoubleOutput;
import cpi.Interface.DoubleInput;

/**
 *
 * @author Robotics
 */
public class XBox360 {
    public XBox360(String pname){
        name=pname;
        instance++;
 
        leftStickXaxis=new DoubleOutput("XBox360/" +name,"Left Stick X Axis");
        leftStickYaxis=new DoubleOutput("XBox360/" +name,"Left Stick Y Axis");
        rightStickXaxis=new DoubleOutput("XBox360/" +name,"Right Stick X Axis");
        rightStickYaxis=new DoubleOutput("XBox360/" +name,"Right Stick Y Axis");
        directionalPad=new DoubleOutput("XBox360/" +name,"Directional Pad");
        triggers=new DoubleOutput("XBox360/" +name,"Triggers");
        leftTrigger=new DoubleOutput("XBox360/" +name,"Left Trigger");
        rightTrigger=new DoubleOutput("XBox360/" +name,"Right Trigger");

        rightRumble=new DoubleInput("/XBox360/" +name,"Right Rumble");
        leftRumble=new DoubleInput("/XBox360/" +name,"Left Rumble");
        
          aButton  =new BooleanOutput("XBox360/" +name,"A Button");
          bButton=new BooleanOutput("XBox360/" + name,"B Button");  
          xButton=new BooleanOutput("XBox360/" + name,"X Button");  
          yButton=new BooleanOutput("XBox360/" + name,"Y Button");
          leftBumper=new BooleanOutput("XBox360/" +name,"Left Bumper");  
          rightBumper=new BooleanOutput("XBox360/" +name,"Right Bumper");  
          stopBackButton=new BooleanOutput("XBox360/" +name,"Stop-Back Button");
          startButton=new BooleanOutput("XBox360/" +name,"Start Button");  
          leftThumbstickButton=new BooleanOutput("XBox360/" +name,"Left Thumbstick Button");
          rightThumbstickButton=new BooleanOutput("XBox360/" +name,"Right Thumbstick Button");

          dbLeftStickX=new DeadBand("XBox360/" +name,"Left Stick X Deadband value");
          dbLeftStickY=new DeadBand("XBox360/" +name,"Left Stick Y Deadband value");
          dbRightStickX=new DeadBand("XBox360/" +name,"Right Stick X Deadband value");
          dbRightStickX=new DeadBand("XBox360/" +name,"Right Stick Y Deadband value");
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
     leftStickXaxis.Value(dbLeftStickX.Value(joystick.getRawAxis(0)) );
     leftStickYaxis.Value(dbLeftStickY.Value( joystick.getRawAxis(1)));
     leftTrigger.Value(joystick.getRawAxis(2));
     rightTrigger.Value(joystick.getRawAxis(3));
     rightStickXaxis.Value(dbRightStickX.Value(joystick.getRawAxis(4)));
     rightStickYaxis.Value(dbRightStickX.Value(joystick.getRawAxis(5)));
     directionalPad.Value(joystick.getPOV());
     joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kRightRumble, (float)rightRumble.Value());
     joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kLeftRumble, (float)leftRumble.Value());
     
     
     aButton.Value(joystick.getRawButton(1)); 
     bButton.Value(joystick.getRawButton(2));
     xButton.Value(joystick.getRawButton(3));    
     yButton.Value(joystick.getRawButton(4)); 
     leftBumper.Value(joystick.getRawButton(5));  
     rightBumper.Value(joystick.getRawButton(6)); 
     stopBackButton.Value(joystick.getRawButton(7)); 
     startButton.Value(joystick.getRawButton(8));    
     leftThumbstickButton.Value(joystick.getRawButton(9));  
     rightThumbstickButton.Value(joystick.getRawButton(10));
    
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
    public DoubleOutput leftStickXaxis;
    public DoubleOutput leftStickYaxis; 
    public DoubleOutput rightStickXaxis;
    public DoubleOutput rightStickYaxis;
    public DoubleOutput directionalPad;
    public DoubleOutput triggers;
    public DoubleOutput leftTrigger;
    public DoubleOutput rightTrigger;
  
  
    public BooleanOutput aButton;  
    public BooleanOutput bButton;  
    public BooleanOutput xButton;  
    public BooleanOutput yButton;
    public BooleanOutput leftBumper;  
    public BooleanOutput rightBumper;  
    public BooleanOutput stopBackButton;
    public BooleanOutput startButton;  
    public BooleanOutput leftThumbstickButton;
    public BooleanOutput rightThumbstickButton;
    public cpi.Interface.DoubleInput rightRumble;
    public cpi.Interface.DoubleInput leftRumble;

    DeadBand dbLeftStickX;
    DeadBand dbLeftStickY;
    DeadBand dbRightStickX;
    DeadBand dbRightStickY;
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