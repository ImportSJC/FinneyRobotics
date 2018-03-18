package conditions;

import AutonomousControls.AutonomousControl;

/**
 * Run and stop each subtask individually.
 * @author team1405
 *
 */
public class Thread extends AutonomousControl{ //TODO rename this class and fix doc

private AutonomousControl[] myObjects;
	
	public Thread(AutonomousControl ... myObjects){
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
	/**
	 * Stop each task individually but run until all are stopped
	 */
	public boolean check(){
		boolean myVar = true;
		for (int i=0; i<myObjects.length; i++){
			if (myObjects[i].check()){
				myObjects[i].stop();
			}else{
				myVar = false;
			}
		}
		
		return myVar;
	}
	
	@Override
	public void stop(){
		for (int i=0; i<myObjects.length; i++){
			myObjects[i].stop();
		}
	}
	
}
