package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import wrapper.*;

public class Robot extends IterativeRobot {

  SparkMaxGroup leftMotors;
  SparkMaxGroup rightMotors;

  Drive drive;

  XboxController xbox;

  @Override
  public void robotInit() {
    // this initializes the left motors with ports of 1 and 2
    leftMotors = new SparkMaxGroup(Constants.can_ids[1], Constants.can_ids[2]);

    // this does the same with the right motors and ports 3 and 4
    rightMotors = new SparkMaxGroup(Constants.can_ids[3], Constants.can_ids[4]);

    // this creates a new drive class that controls the left and right motors
    drive = new Drive(leftMotors,rightMotors);

    // looks for an Xbox controller at usb port 0 in the computer
    xbox = new XboxController(0);

  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {

    // gets the y axis of the left stick and the x axis of the right stick and uses banana curve to drive
    drive.partialParabolic(xbox.getAxis(1), xbox.getAxis(4));

  }

}
