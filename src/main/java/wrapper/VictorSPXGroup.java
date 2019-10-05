package wrapper;

import java.util.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import wrapper.interfaces.MotorGroup;

public class VictorSPXGroup implements MotorGroup{

    ArrayList<VictorSPX> motors = new ArrayList<>();

    public VictorSPXGroup(int... deviceIDs) {
        for (int deviceID : deviceIDs) {

            VictorSPX motor = new VictorSPX(deviceID);
            motors.add(motor);
            
        }
    }

    public void setSpeed(double motorInput) {
        for (int i = 0; i < motors.size(); i++) {

            motors.get(i).set(ControlMode.PercentOutput, motorInput);

        }

    } 

    public VictorSPX getMotor(int index){

        return motors.get(index);

    }

}