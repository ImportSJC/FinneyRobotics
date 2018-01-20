package sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class CustomLimitSwitch {
	
	private DigitalInput myLimitSwitch;
	
	public CustomLimitSwitch(int myChannel) {
		myLimitSwitch = new DigitalInput(myChannel);
		
	}
	
	public boolean getState(){
		return myLimitSwitch.get();
	}
}
