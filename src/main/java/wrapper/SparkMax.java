package wrapper;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import wrapper.interfaces.PIDMotor;

public class SparkMax implements PIDMotor{

    CANSparkMax motor;
    CANEncoder encoder;
    CANPIDController CANSpark;

    public SparkMax(int CANID){

        motor = new CANSparkMax(CANID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = new CANEncoder(motor);
        CANSpark = new CANPIDController(motor);

        motor.setSmartCurrentLimit(30);
        motor.setIdleMode(IdleMode.kCoast);
        CANSpark.setP(1.5);
        CANSpark.setI(0.2);
        CANSpark.setD(0);
        CANSpark.setIZone(0);
        CANSpark.setFF(0);

    }

    public void setSpeed(double speed){

        CANSpark.setOutputRange(speed* 0.96, speed * 1.04);
        CANSpark.setReference(speed, ControlType.kVelocity);

    }

    public void setRotations(double rotations, double speed){

      CANSpark.setOutputRange(speed * 0.98, speed * 1.02);
      CANSpark.setReference(rotations, ControlType.kPosition);

    }

    public void setP(double P){

      CANSpark.setP(P);

    }

    public void setI(double I){

      CANSpark.setI(I);

    }

    public void setD(double D){

      CANSpark.setD(D);

    }

    public CANSparkMax getMotorObject(){

      return motor;

    }

    public CANEncoder getEncoderObject(){

      return encoder;

    }

    public CANPIDController getControllerObject(){

      return CANSpark;

    }





}
