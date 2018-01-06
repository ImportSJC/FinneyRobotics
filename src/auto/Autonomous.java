package auto;

public class Autonomous {
	public int columnIndex = 0;
	public int rowIndex = 0;
	public boolean columnInit = false; //has all the modes in the column been started yet?
	
	public SuperClass[][] autoStates = null;
	
	/**
	 * Instantiate a new autonomous class by providing a list of all encoders and gyros.
	 * This is required since autonomous mode will be resetting them between steps in order to
	 * reduce drift and start fresh every step.
	 * @param encoders a list of encoders on the robot
	 * @param gyros a list of gyros on the robot
	 */
	public Autonomous(SuperClass[][] states){
		this.autoStates = states;
	}
	
	public void AutonomousInit(){
		columnIndex = 0;
		rowIndex = 0;
		columnInit = false;
	}
	
	public void AutonomousPeriodic() {
		if (columnIndex<autoStates.length){
//			currentAuto = autoModes[autoModeIndex];
			if (!columnInit){
				for (int i=0; i<autoStates[columnIndex].length; i++){
					autoStates[columnIndex][i].start();
				}
				columnInit = true;
			}
			else if(allChecksPassed()){
				System.out.println("All checks passed - NEXT STATE");
				columnIndex++;
				columnInit = false;
			}
		}
		else{
			System.out.println("End of Autonomous Loop");
			
			//TODO: remove this debug code for competitions. <== is this correct???? DO THIS AND CHECK!!!!!!
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean allChecksPassed(){
		//has all the checks passed?
		for (int i=0; i<autoStates[columnIndex].length; i++){
			if (!autoStates[columnIndex][i].check()){return false;}
		}
		for (int i=0; i<autoStates[columnIndex].length; i++){autoStates[columnIndex][i].stop();}
		return true;
	}
}
