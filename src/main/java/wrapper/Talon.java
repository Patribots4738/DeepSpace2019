package wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Talon {

    TalonSRX talon;
    // if youve gone looking here, something terrible has happened, turn back now,
    // dont touch anything, and find me. Zach 2019-1-27

    public Talon(int CANID) {

        talon = new TalonSRX(CANID);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        talon.configAllowableClosedloopError(0, 0, 20);
        talon.config_kF(0, 0, 20);
        talon.configNominalOutputForward(0, 20);
        talon.configNominalOutputReverse(0, 20);
        talon.configPeakOutputForward(1, 20);
        talon.configPeakOutputReverse(-1, 20);
        talon.setSensorPhase(true);

    }

    public void setInverted(boolean isInverted){

        talon.setInverted(isInverted);

    }

    public void setP(double P) {

        talon.config_kP(0, P, 20);

    }

    public void setI(double I) {

        talon.config_kI(0, I, 20);

    }

    public void setD(double D) {

        talon.config_kD(0, D, 20);

    }

    public void setMaxOutput(double maxForward, double maxReverse) {

        talon.configPeakOutputForward(maxForward, 20);
        talon.configPeakOutputReverse(maxReverse, 20);

    }

    public void setPercent(double percentOutput) {

        talon.set(ControlMode.PercentOutput, percentOutput);

    }

    public void setPosition(double rotatations) {

        talon.set(ControlMode.Position, rotatations * 4096);

    }

    public int getEncoderValue() {
      
        return talon.getSelectedSensorPosition();

    }

    public int getEncoderSpeed(){

        return talon.getSelectedSensorVelocity();

    }

}