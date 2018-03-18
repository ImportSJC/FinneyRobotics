package subsystems_tele;

import edu.wpi.first.wpilibj.Servo;

public class BuddyBarRelease {
	
private double release=0;
private double home=1;
private Servo servo;
private boolean released=false;

public	BuddyBarRelease(Servo servo,Double homePos, Double releasePos){
		home=homePos;
		release=releasePos;
		servo.set(this.home);
		this.servo = servo;
	}
	public void release(boolean rel){
		released=rel;
		if(released){
			servo.set(release);
		} else{
			servo.set(home);
		}
	}

}
