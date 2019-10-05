package wrapper;

import java.util.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import wrapper.interfaces.PIDMotorGroup;
import wrapper.SparkMax;

public class SparkMaxGroup implements PIDMotorGroup {

  public ArrayList<SparkMax> motors = new ArrayList<>();

    public SparkMaxGroup(int... deviceIDs) {

        for (int deviceID : deviceIDs) {

          SparkMax motor = new SparkMax(deviceID);
          motors.add(motor);

        }

    }

    public void setSpeed(double speed) {

        for (int i = 0; i < motors.size(); i++) {

            motors.get(i).setSpeed(speed);
        }

    }

    public void setRotations(double rotations, double speed){

        for(int i = 0; i < motors.size(); i++){

            motors.get(i).setRotations(rotations, speed);

        }

    }

    public void setP(double P){

      for(int i = 0; i < motors.size(); i++){

        motors.get(i).setP(P);

      }

    }

    public void setI(double I){

      for(int i = 0; i < motors.size(); i++){

        motors.get(i).setI(I);

      }

    }

    public void setD(double D){

      for(int i = 0; i < motors.size(); i++){

        motors.get(i).setD(D);
        
      }

    }


}
