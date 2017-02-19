package cpi;

import edu.wpi.first.wpilibj.DigitalOutput;

public class Arduino_LightControl {
	static DigitalOutput lightPort0;
	static DigitalOutput lightPort1;
	
static public void robotInit(int lightPort0,int lightPort1){
	Arduino_LightControl.lightPort0=new DigitalOutput(lightPort0);
	Arduino_LightControl.lightPort1=new DigitalOutput(lightPort1);
}


static public void Periodic(int lightState){
	switch(lightState){
	case 0:
		default:
			lightPort0.set(false);
			lightPort1.set(false);
			break;
	case 1:
			lightPort0.set(true);
			lightPort1.set(false);
			break;
	case 2:
		lightPort0.set(false);
		lightPort1.set(true);
		break;
	case 3:
		lightPort0.set(true);
		lightPort1.set(true);
		break;
	
	}
	
}
}
