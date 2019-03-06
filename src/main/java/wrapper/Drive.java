package wrapper;

import utils.Mathd;
import interfaces.MotorGroup;

public class Drive {

    MotorGroup LeftMotors;
    MotorGroup RightMotors;

    double linearCutoff = 0.5;
    double turningCutoff = 0.5;
    double linearSpeedMultiplier = 1;
    int turnInPlacePrecision = 4;

    public Drive(MotorGroup LeftMotors, MotorGroup RightMotors) {

        this.LeftMotors = LeftMotors;
        this.RightMotors = RightMotors;

    }

    public void linearArcade(double throttle, double turning) {

        double leftMotorInput = (throttle + turning);
        double rightMotorInput = -(throttle - turning);

        LeftMotors.control(leftMotorInput);
        RightMotors.control(rightMotorInput);

    }

    public void parabolicArcade(double throttle, double turning, double speedMultiplier) {

        double leftMotorInput = (throttle + turning) * Math.abs((throttle + turning)) * speedMultiplier;
        double rightMotorInput = (throttle - turning) * Math.abs((throttle - turning)) * speedMultiplier;

        LeftMotors.control(leftMotorInput);
        RightMotors.control(-rightMotorInput);

    }

    public void linearTank(double leftStick, double rightStick) {

        double leftMotorInput = leftStick;
        double rightMotorInput = -rightStick;

        LeftMotors.control(leftMotorInput);
        RightMotors.control(rightMotorInput);

    }

    public void parabolicTank(double leftStick, double rightStick, double speedMultiplier) {

        double leftMotorInput = leftStick * Math.abs(leftStick) * speedMultiplier;
        double rightMotorInput = -(rightStick * Math.abs(rightStick) * speedMultiplier);

        LeftMotors.control(leftMotorInput);
        RightMotors.control(rightMotorInput);

    }

    public void configureCurvature(double[] configs) {

        linearCutoff = configs[0];
        turningCutoff = configs[1];
        linearSpeedMultiplier = configs[2];
        turnInPlacePrecision = (int) configs[3];

    }

    public void curvature(double throttle, double turning) {

        boolean turnInPlace = Mathd.isBetween(throttle, 0.07, -0.07);

        double linearParabolicConverter = 1;

      //  double wideTurnConverter = 1;
/*
        if (!Mathd.isBetween(throttle, 0.5, -0.5)) {

            linearParabolicConverter = Math.abs(throttle);

        }

        if (!Mathd.isBetween(turning, 0.5, -0.5)) {

            linearParabolicConverter = Math.abs(turning);

        }
*/
        if (turnInPlace) {

          //  ultraParabolic(throttle, turning * 0.75, turnInPlacePrecision);
          partialParabolic(throttle, turning);

        }

        if (!turnInPlace) {

            double convertedThrottle = throttle * linearParabolicConverter;

            double convertedTurning = turning * Math.signum(throttle);

            double speedDifference = (Math.atan(convertedTurning) * convertedThrottle);

            double leftMotorInput = convertedThrottle - speedDifference;
            double rightMotorInput = convertedThrottle + speedDifference;

            LeftMotors.control(leftMotorInput);
            RightMotors.control(-rightMotorInput);

        }

        /*
         * for those of you looking at this and wondering what the hell all this math
         * means, a lot of it is tuning, so here is the original math so we dont get
         * confused
         * 
         * double angleToMaintain = (Math.PI * turning) / Math.PI;
         * 
         * double speedDifference = Math.atan(angleToMaintain) * throttle;
         * 
         * double leftMotorInput = throttle - speedDifference; 
         * double rightMotorInput = throttle + speedDifference;
         * 
         * there if you need it Zach 2019-1-20
         */

    }

    public void cheesy(double throttle, double turning) {// this is kinda experimental...

        double leftMotorInput = (throttle * Math.abs(turning)) - turning;
        double rightMotorInput = (throttle * Math.abs(turning)) + turning;

        LeftMotors.control(leftMotorInput);
        RightMotors.control(-rightMotorInput);

    }

    public void ultraParabolic(double throttle, double turning, int ridiculousFactor) {// this is for use with curvature
                                                                                       // drive

        double leftMotorInput = (throttle + turning) * Math.pow(Math.abs(throttle + turning), ridiculousFactor - 1);
        double rightMotorInput = (throttle - turning) * Math.pow(Math.abs(throttle - turning), ridiculousFactor - 1);

        LeftMotors.control(leftMotorInput);
        RightMotors.control(-rightMotorInput);

    }

    public void partialParabolic(double throttle, double turning){

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

        LeftMotors.control(leftMotorInput);
        RightMotors.control(-rightMotorInput);

    }

}