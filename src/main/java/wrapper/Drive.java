package wrapper;

import wrapper.utils.Mathd;
import wrapper.interfaces.MotorGroup;

public class Drive {

    MotorGroup LeftMotors;
    MotorGroup RightMotors;

    public Drive(MotorGroup LeftMotors, MotorGroup RightMotors) {

        this.LeftMotors = LeftMotors;
        this.RightMotors = RightMotors;

    }

    public void linearArcade(double throttle, double turning) {

        double leftMotorInput = (throttle + turning);
        double rightMotorInput = -(throttle - turning);

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(rightMotorInput);

    }

    public void parabolicArcade(double throttle, double turning, double speedMultiplier) {

        double leftMotorInput = (throttle + turning) * Math.abs((throttle + turning)) * speedMultiplier;
        double rightMotorInput = (throttle - turning) * Math.abs((throttle - turning)) * speedMultiplier;

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(-rightMotorInput);

    }

    public void linearTank(double leftStick, double rightStick) {

        double leftMotorInput = leftStick;
        double rightMotorInput = -rightStick;

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(rightMotorInput);

    }

    public void parabolicTank(double leftStick, double rightStick, double speedMultiplier) {

        double leftMotorInput = leftStick * Math.abs(leftStick) * speedMultiplier;
        double rightMotorInput = -(rightStick * Math.abs(rightStick) * speedMultiplier);

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(rightMotorInput);

    }

    public void curvature(double throttle, double turning) {

        boolean turnInPlace = Mathd.isBetween(throttle, 0.07, -0.07);


        if (turnInPlace) {

          partialParabolic(throttle, turning);
        }

        if (!turnInPlace) {

            double convertedTurning = turning * Math.signum(throttle);

            double speedDifference = (Math.atan(convertedTurning) * throttle);

            double leftMotorInput = throttle - speedDifference;
            double rightMotorInput = throttle + speedDifference;

            LeftMotors.setSpeed(leftMotorInput);
            RightMotors.setSpeed(-rightMotorInput);

        }

    }

    public void ultraParabolic(double throttle, double turning, int ridiculousFactor) {// this is for use with curvature, or for very precise turning

        double leftMotorInput = (throttle + turning) * Math.pow(Math.abs(throttle + turning), ridiculousFactor - 1);
        double rightMotorInput = (throttle - turning) * Math.pow(Math.abs(throttle - turning), ridiculousFactor - 1);

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(-rightMotorInput);

    }

    public void partialParabolic(double throttle, double turning){//this is the banana curve used for drive, its very fun

        if(Mathd.isBetween(throttle, 0.07, -0.07)){

            throttle = 0;

        }

        if(Mathd.isBetween(turning, 0.07, -0.07)){

            turning = 0;

        }

        double effectiveThrottle = Math.signum(throttle) * Math.pow(throttle * Math.signum(throttle), 1 + (throttle * Math.signum(throttle)));
        double effectiveTurning = Math.signum(turning) * Math.pow(turning * Math.signum(turning), 1 + (turning * Math.signum(turning)));

        double leftMotorInput = effectiveThrottle - effectiveTurning;
        double rightMotorInput = effectiveThrottle + effectiveTurning;

        LeftMotors.setSpeed(leftMotorInput);
        RightMotors.setSpeed(-rightMotorInput);

    }

}
