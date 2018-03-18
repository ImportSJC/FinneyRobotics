package debugging;

/**
 * Calculate the amount of time between calls to the iterative robots methods (ie TeleopPeriodic, AutonomousPeriodic, etc)
 * You should call the getLoopTime function in an iterative robot method (ie call getLoopTime in TeleopPeriodic)
 * If you want to test multiple iterative robot methods create multiple timers
 * @author Stephen Cerbone
 *
 */
public class RobotLoopTimer {

	private double previousTime = 0;
	
	/**
	 * Return the time between function calls
	 * This function should be called in TeleopPeriodic, AutonomousPeriodic ... etc
	 * @return
	 */
	public double getLoopTime(){
		if (previousTime == 0){
			double currentTime = System.nanoTime() * Math.pow(10, -6.0);
			
	//		SimpleLogger.log("Time: " + (currentTime-previousTime));
	    	
			previousTime = currentTime;
	    	return currentTime;
		}
		
		return 0;
	}
	
}
