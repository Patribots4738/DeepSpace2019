package wrapper.interfaces;

public interface PIDMotorGroup extends MotorGroup{

    public void setSpeed(double speed);

    public void setRotations(double rotations, double speed);

    public void setP(double P);

    public void setI(double I);

    public void setD(double D);

}
