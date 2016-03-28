package cpi.auto;

public class Auto_Shoot extends SuperClass{
	
	public Auto_Shoot(){
		
	}
	
	@Override
	public void start(){
		AutoOutputs.startShooting();
	}
	
	@Override
	public boolean check(){
		AutoOutputs.shooting();
		return true;
	}
	
	@Override
	public void stop(){
		stopMotors();
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void stopMotors(){
		//reset motor speeds
		AutoOutputs.stopShooting();
	}
}
