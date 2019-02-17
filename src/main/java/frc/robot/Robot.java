package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import wrapper.*;
import hardware.*;
import utils.Mathd;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;

public class Robot extends TimedRobot {

  XboxController driverXbox;
  Gamepad operatorStick;
  Gamepad keyboard;

  PIDSparkMaxGroup leftMotors;
  PIDSparkMaxGroup rightMotors;

  Talon rotator;

  VictorSPX leftIntake;
  VictorSPX rightIntake;

  SingleSolenoid pusher;
  DoubleSoleniod arms;

  DoubleSoleniod test1;
  DoubleSoleniod test2;
  SingleSolenoid test3;

  Drive drive;

  UsbCamera cam;

  Compressor comp;

  Keybinder driverKeys;
  Keybinder operatorKeys;
  Keybinder keyKeys;

  Elevator elevator;

  Arm arm;

  boolean firstTime;

  String driveMode;

  boolean isFirst1 = true;
  boolean isFirst2 = true;

  @Override
  public void robotInit() {

    SmashBoard.sendBoolean("enabled", false);

    try {
      cam = CameraServer.getInstance().startAutomaticCapture();
      cam.setResolution(240, 160);
      cam.setFPS(30);
      cam.setExposureManual(50);
    } catch (Exception e) {
      System.err.println("camera not there");
    }
    comp = new Compressor(0);

    comp.setClosedLoopControl(true);

    driverXbox = new XboxController(Constants.DRIVER_STATION_PORT[1]);
    operatorStick = new Gamepad(Constants.DRIVER_STATION_PORT[0]);
    keyboard = new Gamepad(4);

    driverKeys = new Keybinder(driverXbox);
    operatorKeys = new Keybinder(operatorStick);
    keyKeys = new Keybinder(keyboard);

    leftMotors = new PIDSparkMaxGroup(Constants.CAN_ID[2], Constants.CAN_ID[3]);
    rightMotors = new PIDSparkMaxGroup(Constants.CAN_ID[4], Constants.CAN_ID[5]);

    leftIntake = new VictorSPX(8);
    rightIntake = new VictorSPX(9);

    pusher = new SingleSolenoid(Constants.PCM_PORT[7]);
    arms = new DoubleSoleniod(Constants.PCM_PORT[1], Constants.PCM_PORT[2]);

    drive = new Drive(leftMotors, rightMotors);

    elevator = new Elevator(Constants.CAN_ID[6], Constants.CAN_ID[7]);

    arm = new Arm(arms, pusher, rightIntake, leftIntake, Constants.CAN_ID[10]);

  }

  @Override
  public void autonomousInit() {

    teleopInit();

  }

  @Override
  public void autonomousPeriodic() {

    teleopPeriodic();

  }

  @Override
  public void teleopInit() {

    firstTime = true;
    elevator.reset();
    SmashBoard.sendBoolean("enabled", true);
    elevator.stop();
    arms.deactivate();

  }

  @Override
  public void disabledInit() {
     SmashBoard.sendBoolean("enabled", false);
  }

  @Override
  public void teleopPeriodic() {

    // don't touch this if statement please
    if (firstTime) {

      driveMode = SmashBoard.getDriveMode();
      driverKeys.bind(SmashBoard.receiveDriverKeys());
      operatorKeys.bind(SmashBoard.receiveOperatorKeys());

      elevator.reset();
      elevator.stop();
      firstTime = false;
    }

    double throttle = driverKeys.getThrottle();
    double turning = driverKeys.getTurning();

    boolean toggleForward = driverKeys.getToggleForward();
    if (toggleForward) {

      throttle = -throttle;

    }

    switch (driveMode) {
    case ("curvature"):
      drive.curvature(throttle, turning);
      break;

    case ("curvy"):
      drive.partialParabolic(throttle, -turning);
      break;

    case ("arcade"):
      drive.parabolicArcade(throttle, turning, 1);
      break;

    }

    if (operatorKeys.getButton("resetArm")) {

      arm.resetEncoder();

    }
   
    if (!Mathd.isBetween(operatorKeys.getJoystick("armThrottle"), 0.07, -0.07)) {

      arm.manual(-operatorKeys.getJoystick("armThrottle"));
      isFirst1 = true;

    }

    if (Mathd.isBetween(operatorKeys.getJoystick("armThrottle"), 0.07, -0.07)) {

      if (isFirst1) {

        arm.resetEncoder();
        isFirst1 = false;

      }

      arm.rotator.talon.set(ControlMode.Position, 0);

    }

    if (operatorKeys.getJoystick("intakeOut") > 0.4) {

      arm.setIntakeBlow();

    }

    else if (operatorKeys.getJoystick("intakeIn") > 0.1 && !(operatorKeys.getJoystick("intakeOut") > 0.4)) {

      arm.setIntakeSuck(operatorKeys.getJoystick("intakeIn") * 0.63);

    }

    else {

      arm.stopIntake();

    }

    arm.toggleArms(operatorKeys.getArmsToggle());

    arm.setPush(operatorKeys.getHatchLaunch());
    /*
     * if (!arm.getPos().equals("resting")) {
     * 
     * double elevatorSpeed = operatorKeys.getThrottle();
     * 
     * if (elevatorSpeed < 0) {
     * 
     * elevatorSpeed = elevatorSpeed * 0.5;
     * 
     * }
     * 
     * elevator.manual(elevatorSpeed);
     * 
     * }
     * 
     * if(arm.getPos().equals("resting")){
     * 
     * elevator.manual(0);
     * 
     * }
     */

    if (!Mathd.isBetween(operatorKeys.getJoystick("throttle"), 0.07, -0.07)) {

      double elevatorSpeed = -operatorKeys.getThrottle();

      if (elevatorSpeed < 0) {

        elevatorSpeed = elevatorSpeed * 0.5;

      }

      elevator.manual(elevatorSpeed);
      isFirst2 = true;

    }

    if (Mathd.isBetween(operatorKeys.getJoystick("throttle"), 0.07, -0.07)) {

      if (isFirst2) {

        elevator.resetEncoder();
        isFirst2 = false;

      }

      elevator.talon.talon.set(ControlMode.Position, 0);

    }

  }
}
