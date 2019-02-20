package hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import wrapper.DoubleSoleniod;
import wrapper.SingleSolenoid;
import wrapper.Talon;

public class Arm {

    SingleSolenoid push;

    DoubleSoleniod armSoleniod;

    public Talon rotator;

    VictorSPX left;

    VictorSPX right;

    double degreeConverter = 3.75 * 1 / 360; // multiply by the number of degrees you want the arm to turn and it will
                                             // rotate that much

    String pos = "resting";

    public Arm(DoubleSoleniod arms, SingleSolenoid pusher, VictorSPX right, VictorSPX left, int talonID) {

        push = pusher;

        armSoleniod = arms;
        arms.activateReverseChannel();

        this.left = left;
        this.right = right;

        rotator = new Talon(talonID);

        rotator.setMaxOutput(0.5, -0.5);
        rotator.setP(1);
        rotator.setI(0);
        rotator.setD(0);
        rotator.setInverted(false);
        rotator.setSensorPhase(false);
        rotator.changeToPotentiometer();
        rotator.setPosition(0);
        rotator.talon.configContinuousCurrentLimit(30);

    }

    public void setPosition(String pos) {

        this.pos = pos;

        switch (pos) {

        case "resting":
            // resting, should be base position at the start of the match to keep it inside
            // our frame limit
            rotator.setMaxOutput(0.5, -0.75);
            rotateDegrees(10);
            break;

        case "shoot":
            // perpendicular to the floor, ready to shoot
            rotator.setMaxOutput(0.5, -1);
            rotateDegrees(30);
            break;

        case "slap":
            // parallel to the floor, slapped down on top of a hatch
            rotator.setMaxOutput(0.15, -0.45);
            rotateDegrees(128);
            break;

        case "ball":
            // angled a good bit relative to the floor, partitally down to get a ball
            rotator.setMaxOutput(0.42, -0.5);
            rotateDegrees(60);
            break;

        }

    }

    public void setPush(boolean pushState) {

        push.setTo(pushState);

    }

    public void toggleArms(boolean toggleState) {

        armSoleniod.toggleWithState(toggleState);

    }

    public void setIntakeBlow() {

        left.set(ControlMode.PercentOutput, -1);
        right.set(ControlMode.PercentOutput, 1);

    }

    public void stopIntake() {

        left.set(ControlMode.PercentOutput, 0);
        right.set(ControlMode.PercentOutput, 0);

    }

    public void setIntakeSuck(double percent) {

        left.set(ControlMode.PercentOutput, percent);
        right.set(ControlMode.PercentOutput, -percent);

    }

    public void resetEncoder() {

        rotator.resetEncoder();

    }

    public String getPos() {

        return pos;

    }

    public void rotateDegrees(double degrees) {

        rotator.setPosition(degrees * degreeConverter);

    }

    public void manual(double input) {

        rotator.setPercent(input);

    }

    public void PIDmanual(double input){

        rotator.setPIDVelocity(input);

    }

    public void stop(){

        rotator.setPercent(0);

    }
    
}