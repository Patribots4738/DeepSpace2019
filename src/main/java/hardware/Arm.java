package hardware;

import wrapper.AdaptableDrive;
import wrapper.DoubleSoleniod;
import wrapper.SingleSolenoid;
import wrapper.Talon;

public class Arm {

    SingleSolenoid push;

    DoubleSoleniod armSoleniod;

   public Talon rotator;

    AdaptableDrive intake;

    double gearRotationsPerArmRotation = 3.75 * 1/360;

    String pos ="resting";

    public Arm(DoubleSoleniod arms, SingleSolenoid pusher, AdaptableDrive intake, int talonID) {

        push = pusher;

        armSoleniod = arms;

        this.intake = intake;
        intake.giveNames("intake");

        rotator = new Talon(talonID);

        rotator.setMaxOutput(0.5, -0.5);
        rotator.setP(2);
        rotator.setI(0);
        rotator.setD(0);
        rotator.setInverted(false);
    rotator.resetEncoder();
        rotator.setSensorPhase(false);
        rotator.changeToPotentiometer();


    }

    public void setPosition(String pos) {

        this.pos = pos;

        switch (pos) {

        case "resting":
//          resting, should be base position at the start of the match to keep it inside our frame limit 
            rotator.setMaxOutput(0.5, -0.30);
            rotateDegrees(0);
            break;

        case "shoot":
//          perpendicular to the floor, ready to shoot
            rotator.setMaxOutput(0.5, -0.5);
            rotateDegrees(20);
            break;
 
        case "slap":
//          parallel to the floor, slapped down on top of a hatch
            rotator.setMaxOutput(0.15, -0.30); 
            rotateDegrees(110);
            break;

        case "ball":
//          angled a good bit relative to the floor, partitally down to get a ball
            rotator.setMaxOutput(0.5, -0.5);
            rotateDegrees(55);
            break;

        }

    }

    public void setPush(boolean pushState) {

        push.setTo(pushState);

    }

    public void toggleArms(boolean toggleState) {

        armSoleniod.toggleWithState(toggleState);

    }

    public void setIntakeForward() {

        intake.controlMotorGroups(1, "All");

    }

    public void setIntakeBackward(){

        intake.controlMotorGroups(-1, "All");

    }

    public void stopIntake(){

        intake.controlMotorGroups(0, "All");

    }

    public void resetEncoder(){

        rotator.resetEncoder();

    }

    public String getPos(){

        return pos;

    }

    public void rotateDegrees(double degrees){

        rotator.setPosition(degrees * gearRotationsPerArmRotation);

    }

}