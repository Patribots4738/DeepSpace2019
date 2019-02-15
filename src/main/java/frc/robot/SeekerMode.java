package frc.robot;

import wrapper.PIDSparkMaxGroup;

public class SeekerMode {

    double rotationsPerDegree = 61/2880;

    double rotationsPerInch = 360/(8 * Math.PI);

    PIDSparkMaxGroup leftMotors;
    PIDSparkMaxGroup rightMotors;

    public SeekerMode(PIDSparkMaxGroup leftMotors, PIDSparkMaxGroup rightMotors) {

        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

    }

    public void rotate(double angle){

        leftMotors.addPosition(angle * rotationsPerDegree, 0.3);
        rightMotors.addPosition(angle * rotationsPerDegree, 0.3);

    }

    public void move(double distance){

        leftMotors.addPosition(distance * rotationsPerInch, 0.3);
        rightMotors.addPosition(distance * rotationsPerInch, -0.3);

    }


}