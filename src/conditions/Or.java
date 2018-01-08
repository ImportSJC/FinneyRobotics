package conditions;

import AutonomousControls.AutonomousControl;

public class Or extends AutonomousControl{
	private AutonomousControl[] myObjects;
	
	public Or(AutonomousControl ... myObjects) {
		this.myObjects = myObjects;
	}
	
	@Override
	public void start(){
		for (int i=0; i<myObjects.length; i++)
		{
			myObjects[i].start();
		}
	}
	
	@Override
	public boolean check(){
		for (int i=0; i<myObjects.length; i++){
			if (myObjects[i].check()){return true;}
		}
		return false;
	}
	
	@Override
	public void stop(){
		for (int i=0; i<myObjects.length; i++){
			myObjects[i].stop();
		}
	}
}
