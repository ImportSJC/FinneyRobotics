package MotorController;

public class MotorController_Encoder {
	private double cpr = 0.0;
	private boolean reverse = false;
	
	public MotorController_Encoder(double cpr, boolean reverse){
		this.cpr = cpr;
		this.reverse = reverse;
	}
	
	public double getCPR(){
		return cpr;
	}
	
	public boolean getReverse(){
		return reverse;
	}
}
