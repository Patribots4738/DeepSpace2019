package wrapper;

import interfaces.MotorGroup;
import edu.wpi.first.wpilibj.Victor;
import java.util.*;

public class PWMMotorGroup implements MotorGroup {

    ArrayList<Victor> motors = new ArrayList<>();

    public PWMMotorGroup(int... ports) {

        for (int port : ports) {

            Victor vicky = new Victor(port);
            motors.add(vicky);

        }

    }

    public void control(double motorInput) {

        for (int i = 0; i < motors.size(); i++) {

            motors.get(i).set(motorInput);

        }

    }

}
