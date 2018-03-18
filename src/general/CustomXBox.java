/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package general;

import edu.wpi.first.wpilibj.Joystick;


/**
 * This class maps the XBox controller to the WPILIB
 * Joystick class Use the Joystick.GetRawAxis() method for the axies Use the
 * Joystick.GetRawButton() methods for the buttons
 * 
 * 
 * 
 * 1: A 2: B 3: X 4: Y 5: Left Bumper 6: Right Bumper 7: Back 8: Start 9: Left
 * Joystick 10: Right Joystick
 * 
 * The axis on the controller follow this mapping (all output is between -1 and
 * 1)
 * 
 * 0: Left Stick X Axis -Left:Negative ; Right: Positive 1: Left Stick Y Axis
 * -Up: Negative ; Down: Positive 2: Left Trigger 3: Right Trigger 4: Right
 * Stick X Axis -Left: Negative ; Right: Positive 5: Right Stick Y Axis -Up:
 * Negative ; Down: Positive 6: Directional Pad
 * 
 * 
 * Here is the mapping that I have found out as I have listed in the VI:
 * 
 * X Axis: Left Thumbstick Left-Right X Axis Rotation: Right Thumbstick
 * Left-Right Y Axis: Left Thumb Stick (Needs to be negated or else up-down
 * controlls are inverted) Y Axis Rotation: Right Thumb Stick (Needs to be
 * negated or else up-down controlls are inverted) Z Axis (>0): Left Trigger Z
 * Axis (<0): Right Trigger
 * 
 * 1: A Button 2: B Button 3: X Button 4: Y Button 5: Left Bumper 6: Right
 * Bumper 7: Stop/Back Button 8: Start Button 9: Left Thumbstick Button 10:
 * Right Thumbstick Button
 * 
 * DPAD: -1: No Thumbpad Button 0: North Thumbpad Button 45: North-East Thumbpad
 * Button 90: East Thumbpad Button 135: South-East Thumbpad Button 180: South
 * Thumbpad Button 225: South-West Thumbpad Button 270: West Thumbpad Button
 * 315: North-West Thumbpad Button
 * 
 * @author Thomas Wulff
 */
public class CustomXBox {
	Joystick joystick;

	/**
	 * @param port Joystick Port Number
	 */
	public CustomXBox(int port) {
		joystick = new Joystick(port);
	}

	/**
	 * @return the Left Stick X Axis value
	 */
	public double leftStickXaxis() {
		return DeadBand.value(joystick.getRawAxis(0));
	}

	/**
	 * @return the Left Stick Y Axis value, -1 is down and 1 is up
	 */
	public double leftStickYaxis() {
		return -DeadBand.value(joystick.getRawAxis(1));
	}

	/**
	 * @return returns the Left Trigger value
	 */
	public double leftTrigger() {
		return joystick.getRawAxis(2);
	}

	/**
	 * @return the Right Trigger value
	 */
	public double rightTrigger() {

		return joystick.getRawAxis(3);
	}

	/**
	 * @return true iff the left trigger is fully held down, allows the trigger to be used as a button
	 */
	public boolean leftTriggerPressed() {
		if (leftTrigger() == 1.0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return true iff the right trigger is fully held down, allows the trigger to be used as a button
	 */
	public boolean rightTriggerPressed() {
		if (rightTrigger() == 1.0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the Right Stick X Axis value
	 */
	public double rightStickXaxis() {
		return DeadBand.value(joystick.getRawAxis(4));
	}

	/**
	 * @return the Right Stick Y Axis value, -1 is down and 1 is up
	 */
	public double rightStickYaxis() {
		return -DeadBand.value(joystick.getRawAxis(5));
	}

	/**
	 * @return the directional pad value
	 */
	public double directionalPad() {
		return joystick.getPOV();
	}

	public boolean directionalPadUp() {
		if (directionalPad() == 0) {
			return true;
		}
		return false;
	}

	public boolean directionalPadRight() {
		if (directionalPad() == 90) {
			return true;
		}
		return false;
	}

	public boolean directionalPadDown() {
		if (directionalPad() == 180) {
			return true;
		}
		return false;
	}

	public boolean directionalPadLeft() {
		if (directionalPad() == 270) {
			return true;
		}
		return false;
	}

	/**
	 * @param value the value to set the right rumble to, value can be 0 - 1
	 */
	public void rightRumble(double value) {
		joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kRightRumble, (float) value);
	}

	/**
	 * @param value the value to set the left rumble to, value can be 0 - 1
	 */
	public void leftRumble(double value) {
		joystick.setRumble(edu.wpi.first.wpilibj.Joystick.RumbleType.kLeftRumble, (float) value);
	}

	/**
	 * @return the A button value
	 */
	public boolean aButton() {
		return joystick.getRawButton(1);
	}

	/**
	 * @return the B button value
	 */
	public boolean bButton() {
		return joystick.getRawButton(2);
	}

	/**
	 * @return the X button value
	 */
	public boolean xButton() {
		return joystick.getRawButton(3);
	}

	/**
	 * @return the Y button value
	 */
	public boolean yButton() {
		return joystick.getRawButton(4);
	}

	/**
	 * @return the left bumper value
	 */
	public boolean leftBumper() {
		return joystick.getRawButton(5);
	}

	/**
	 * @return the right bumper value
	 */
	public boolean rightBumper() {
		return joystick.getRawButton(6);
	}

	/**
	 * @return the stop/back button value
	 */
	public boolean stopBackButton() {
		return joystick.getRawButton(7);
	}

	/**
	 * @return the start button value
	 */
	public boolean startButton() {
		return joystick.getRawButton(8);
	}

	/**
	 * @return the left thumbstick button value
	 */
	public boolean leftThumbstickButton() {
		return joystick.getRawButton(9);
	}

	/**
	 * @return the right thumbstick button value
	 */
	public boolean rightThumbstickButton() {
		return joystick.getRawButton(10);
	}
}