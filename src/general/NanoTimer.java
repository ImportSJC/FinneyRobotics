package general;

public class NanoTimer {
	private final long NANO_SECONDS;
	private long stopTime;
	public NanoTimer(long nanoSeconds) {
		this.NANO_SECONDS = nanoSeconds;
		this.stopTime = 0;
	}
	
	public void resetTimer() {
		stopTime = System.nanoTime() + NANO_SECONDS;
	}

	public boolean hasExpired() {
		return System.nanoTime() > stopTime;
	}
}
