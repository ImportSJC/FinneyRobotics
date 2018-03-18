package sensors;

import edu.wpi.first.wpilibj.Encoder;

public class CustomEncoder {
	private Encoder myEncoder;

	public CustomEncoder(int aChannel, int bChannel) {
		// myEncoder = new Encoder();//TODO: set this up properly
	}

	public void Init() {
//		SimpleLogger.log("Encoder Init");
		myEncoder.reset();
	}

	public void reset() {
		myEncoder.reset();
	}

	public double getPos() {
		return myEncoder.get();
	}

	public void free() {
		myEncoder.free();
	}
}
