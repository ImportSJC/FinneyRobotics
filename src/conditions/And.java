package conditions;

import AutonomousControls.AutonomousControl;

/**
 * The AND conditional block in the autonomous system.
 * This block can start/check/stop any number of sub-AutonomousControl Objects
 * @author Stephen Cerbone
 *
 */
public class And extends AutonomousControl {
	
	private AutonomousControl[] myObjects;
	
	public And(AutonomousControl ... myObjects){
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
			if (!myObjects[i].check()){return false;}
		}
		return true;
	}
	
	@Override
	public void stop(){
		for (int i=0; i<myObjects.length; i++){
			myObjects[i].stop();
		}
	}
}
