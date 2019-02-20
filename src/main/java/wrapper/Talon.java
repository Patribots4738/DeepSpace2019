package wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Talon {

public TalonSRX talon;
//  if you've gone looking here, something terrible has happened, turn back now,
//  don't touch anything, and find me. Zach 2019-1-27
//  if this has broken and I've graduated, then you have some googling to do

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

    public void changeToPotentiometer(){

        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

    }

    public void setInverted(boolean isInverted){

        talon.setInverted(isInverted);

    }

    public void setSensorPhase(boolean phase){

        talon.setSensorPhase(phase);

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

    public void setMinOutput(double minForward, double minReverse){

        talon.configNominalOutputForward(minForward, 20);
        talon.configNominalOutputReverse(minReverse, 20);

    }

    public void setPercent(double percentOutput) {

        talon.set(ControlMode.PercentOutput, percentOutput);

    }

    public void setPosition(double rotatations) {

        talon.set(ControlMode.Position, rotatations * 4096);

    }

    public void setRawPosition(int clicks){

        talon.set(ControlMode.Position, clicks);

    }

    public void addRotations(double rotatations){

        talon.set(ControlMode.Position, rotatations * 4096 + getEncoderValue());

    }

    public void addRawPosition(double clicks){

        talon.set(ControlMode.Position, clicks + getEncoderValue());

    }

    public int getEncoderValue() {
      
        return talon.getSelectedSensorPosition();

    }

    public int getEncoderSpeed(){

        return talon.getSelectedSensorVelocity();

    }
/*
    public double getRPM(){

        double clicksPerSecond = talon.getSelectedSensorVelocity() * 10;

        double returnVal = clicksPerSecond



    }
*/
    public void resetEncoder(){

        talon.setSelectedSensorPosition(0);

    }

    public void setPIDVelocity(double speed){

        talon.set(ControlMode.Velocity, speed);

    }

    public double getRotations(){

        return talon.getSelectedSensorPosition()/4096;

    }


}