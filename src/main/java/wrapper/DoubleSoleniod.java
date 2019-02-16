package wrapper;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSoleniod {

	edu.wpi.first.wpilibj.DoubleSolenoid DoubleSol;

	public DoubleSoleniod(int port1, int port2) {

		DoubleSol = new edu.wpi.first.wpilibj.DoubleSolenoid(port1, port2);
		DoubleSol.set(Value.kOff);
	}

	public void activateForwardChannel() {

		DoubleSol.set(Value.kForward);

	}

	public void activateReverseChannel() {

		DoubleSol.set(Value.kReverse);

	}

	public void toggleWithState(boolean toggleState) {

		if (toggleState) {

			activateForwardChannel();

		} else {

			activateReverseChannel();

		}

	}

	public void deactivate() {

		DoubleSol.set(Value.kOff);

	}

}