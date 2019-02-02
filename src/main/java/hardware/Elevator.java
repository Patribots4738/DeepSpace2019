package hardware;

import wrapper.Talon;
import utils.ValueChanger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Elevator {

    
    ValueChanger ballUp = new ValueChanger();
    ValueChanger ballDown = new ValueChanger();
    ValueChanger hatchUp = new ValueChanger();
    ValueChanger hatchDown = new ValueChanger();

    Talon talon;

    VictorSPX slave;

    int masterID;
    double rotationsPerBallLevel = 17;
    double rotationsToFirstHatch = 11.6;
    double rotationsPerHatchLevel = 17;
    int ballLevel = 0;
    int hatchLevel = 0;

    public Elevator(int talonCANID, int victorCANID) {

        talon = new Talon(talonCANID);
        slave = new VictorSPX(victorCANID);

        talon.setMaxOutput(0.5, -0.5);
        talon.setP(1);
        talon.setI(0);
        talon.setD(0);
        talon.setInverted(true);

    }

    public void setBallHeight(int level) {

        talon.setPosition(rotationsPerBallLevel * level);
        slave.set(ControlMode.Follower, masterID);

    }

    public void setHatchHeight(int level) {

        if(level == 1){

            talon.setPosition(rotationsToFirstHatch);
            slave.set(ControlMode.Follower, masterID);

        } else{

        talon.setPosition((rotationsPerHatchLevel * level) + rotationsToFirstHatch);
        slave.set(ControlMode.Follower, masterID);

        }
    }

    public void incrementBallHeight(boolean upButton, boolean downButton) {

        if (ballUp.valueChanged(upButton)) {

            ballLevel += 1;

        }

        if (ballDown.valueChanged(downButton)) {

            ballLevel -= 1;

        }

        setBallHeight(ballLevel);

    }

    public void incrementHatchHeight(boolean upButton, boolean downButton) {

        if (hatchUp.valueChanged(upButton)) {

            hatchLevel += 1;

        }

        if (hatchDown.valueChanged(downButton)) {

            hatchLevel -= 1;

        }

        setHatchHeight(hatchLevel);

    }

}