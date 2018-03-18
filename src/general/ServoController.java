package general;

import edu.wpi.first.wpilibj.Servo;

public class ServoController {
	private Servo myServo;
	private boolean reverseServo = false;
	
	public ServoController(int ID, boolean reverseServo, double initialServoPos){
		myServo = new Servo(ID);
		myServo.setPosition(initialServoPos);
		this.reverseServo = reverseServo;
		
	}
	
	public double getServoPos(){
		return myServo.getPosition();
	}
	
	public void setServoPos(double position){
		if(reverseServo){
			myServo.set(1-myServo.getPosition());	
		} else{
			myServo.set(position);
		}
	}
	
}
