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

    public Arm(DoubleSoleniod arms, SingleSolenoid pusher, AdaptableDrive intake, Talon talon) {

        push = pusher;

        armSoleniod = arms;

        this.intake = intake;
        intake.giveNames("intake");

        rotator = talon;

        rotator.setMaxOutput(1, -1);
        rotator.setP(1);
        rotator.setI(0);
        rotator.setD(0);
        rotator.setInverted(true);

    }

    public void setPosition(int pos) {

        switch (pos) {// all the values for the setPosition are placeholder

        case 0:

            rotator.setPosition(0);
            break;

        case 1:

            rotator.setPosition(20);
            break;

        case 2:

            rotator.setPosition(60);
            break;

        case 3:

            rotator.setPosition(40);
            break;

        }

    }

    public void setPush(boolean pushState) {

        push.setTo(pushState);

    }

    public void toggleArms(boolean toggleState) {

        armSoleniod.toggleWithState(toggleState);

    }

    public void setIntake(boolean backwardsOrForwards) {

        if (backwardsOrForwards) {

            intake.controlMotorGroups(1, "All");

        }

        if (!backwardsOrForwards) {

            intake.controlMotorGroups(-1, "All");

        } else {

            intake.controlMotorGroups(0, "All");

        }

    }

}