package conditions;

import AutonomousControls.AutonomousControl;

/**
 * A condition that always returns true, used in cases such as connecting 3
 * motors to one input.
 * 
 * @author Stephen Cerbone
 *
 */
public class True extends AutonomousControl {
	public True() {

	}

	@Override
	public boolean check() {
		return true;
	}
}
