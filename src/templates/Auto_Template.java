package templates;

import cpi.auto.SuperClass;


/*
 * Test Template Description
 */

public class Auto_Template extends SuperClass{
	private double templateSpeed = 0;
	
	public Auto_Template(double mySpeed){
		this.templateSpeed = mySpeed;
	}
	
	@Override
	public void start(){
		startMotors(templateSpeed);
	}
	
	/************** END OF AUTO LOOPS *****************/
	
	private void startMotors(double mySpeed){
		//Set motor speeds
//		AutoOutputs.setTemplate(mySpeed);
		
		System.out.println("Start Auto_Template at: " + mySpeed);
	}
	
	private void stopMotors(){
		//reset motor speeds
//		AutoOutputs.reset_Template();
		
		System.out.println("Stop Auto_Template");
	}
}
