package sensors;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class CustomGyro implements Gyro {
	private Gyro myGyro;

	public CustomGyro(int myChannel) {
		//TODO setup this properly
	}

	public double getAngle() {
		return myGyro.getAngle();
	}

	public double getRate() {
		return myGyro.getRate();
	}

	public void free() {
		myGyro.free();
	}

	/**
	 * Init the gyro
	 */
	@Override
	public void calibrate() {
		System.out.println("Gyro Init");
		myGyro.reset();
	}

	@Override
	public void reset() {
		myGyro.reset();
	}
}
