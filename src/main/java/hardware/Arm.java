package hardware;

import wrapper.AdaptableDrive;
import wrapper.DoubleSoleniod;
import wrapper.SingleSolenoid;
import wrapper.Talon;

public class Arm {

    SingleSolenoid push;

    DoubleSoleniod armSoleniod;

    Talon rotator;

    AdaptableDrive intake;

    int postition = 0;// 0 is resting, 1 is ready to shoot, 2 is slapped down to get a hatch, and 3 is
                      // down to get a ball

    public Arm(DoubleSoleniod arms, SingleSolenoid pusher, AdaptableDrive intake, int talonID) {

        push = pusher;

        armSoleniod = arms;

        this.intake = intake;
        intake.giveNames("intake");

        rotator = new Talon(talonID);

        rotator.setMaxOutput(1, -1);
        rotator.setP(1);
        rotator.setI(0);
        rotator.setD(0);
        rotator.setInverted(false);
        rotator.resetEncoder();
        rotator.setSensorPhase(false);
        rotator.changeToPotentiometer();
        rotator.setPosition(0);

    }

    public void setPosition(int pos) {

        switch (pos) {// all the values for the setPosition are placeholder

        case 0:

            rotator.setPosition(0);//resting, should be base position at the start of the match to keep it inside our frame limit
            break;

        case 1:

            rotator.setPosition(0.15);//perpendicular to the floor, ready to shoot
            break;
 
        case 2:

            rotator.setPosition(0.85);//parallel to the floor, slapped down on top of a hatch
            break;

        case 3:

            rotator.setPosition(0.5);//angled a good bit relative to the floor, partitally down to get a ball
            break;

        }

    }

    public void setPush(boolean pushState) {

        push.setTo(pushState);

    }

    public void test(double percent){

        rotator.setPercent(percent);

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

    public void manual(double power){

        double pos = 0;

        pos += power * 15;

        rotator.setPosition(pos);

    }

}