package conditions;

import AutonomousControls.AutonomousControl;

public class Not extends AutonomousControl{
	private AutonomousControl myObject;
	
	public Not (AutonomousControl myObject) {
		this.myObject = myObject;
	}
	
	@Override
	public void start(){
		myObject.start();
	}
	
	@Override
	public boolean check(){
		return !myObject.check();
	}
	
	@Override
	public void stop(){
		myObject.stop();
	}
}
