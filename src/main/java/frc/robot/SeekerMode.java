package frc.robot;

import wrapper.PIDSparkMaxGroup;

public class SeekerMode {

    double rotationsPerDegree = 0.0111;

    double distancePerRotation = 25.1327;

    double currentLeftPosition = 0;
    double currentRightPosition = 0;

    boolean firstSeek = true;
    boolean firstGo = true;

    PIDSparkMaxGroup leftMotors;
    PIDSparkMaxGroup rightMotors;

    public SeekerMode(PIDSparkMaxGroup leftMotors, PIDSparkMaxGroup rightMotors) {

        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

    }

    public void seek(double angle) {

        if (firstSeek) {

            currentLeftPosition += angle * rotationsPerDegree;
            currentRightPosition += -angle * rotationsPerDegree;
            firstSeek = false;

        }

        leftMotors.position(currentLeftPosition, 0.5);
        rightMotors.position(currentRightPosition, 0.5);

    }

    public void go(double distance) {

        if (firstGo) {

            currentLeftPosition += distance * distancePerRotation;
            currentRightPosition += distance * distancePerRotation;
            firstGo = false;

        }

        leftMotors.position(currentLeftPosition, 0.5);
        rightMotors.position(currentRightPosition, 0.5);

    }

}