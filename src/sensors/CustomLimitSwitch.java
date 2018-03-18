package sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class CustomLimitSwitch {
	
	private DigitalInput myLimitSwitch;
	
	public CustomLimitSwitch(int myChannel) {
		myLimitSwitch = new DigitalInput(myChannel);
		
	}
	
	public boolean getState(){
		
//		SimpleLogger.log("Position of the sensor:"+ myLimitSwitch.get());
		return myLimitSwitch.get();
	}
}
