package wrapper.interfaces;

public interface PIDMotor extends Motor{

    public void setSpeed(double speed);

    public void setRotations(double rotations, double speed);

    public void setP(double P);

    public void setI(double I);

    public void setD(double D);

}
