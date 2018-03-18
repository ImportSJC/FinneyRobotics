package general;

public class ButtonState {
	private boolean current;
	private boolean previous;
	
	public ButtonState (boolean initialState) {
		current = initialState;
		previous = initialState;
	}
	
	public void setState (boolean state) {
		previous = current;
		current = state;
	}
	
	public boolean hasActivated() {
		return current && !previous;
	}
	
	public boolean hasDeactivated() {
		return !current && previous;
	}
	
	public boolean isActive() {
		return current;
	}

}
