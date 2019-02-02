package wrapper;

import edu.wpi.first.wpilibj.Solenoid;

public class SingleSolenoid {

    Solenoid sol;
    boolean isOn = false;

    public SingleSolenoid(int port) {

        sol = new Solenoid(port);

    }

    public void setTo(boolean state) {

        isOn = state;

        sol.set(isOn);

    }

    public void toggle() {

        isOn = !isOn;

        sol.set(isOn);

    }

}