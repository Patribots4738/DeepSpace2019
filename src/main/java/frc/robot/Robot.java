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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends TimedRobot { 

  XboxController driverXbox;
  Gamepad operatorStick;

  PIDSparkMaxGroup leftMotors;
  PIDSparkMaxGroup rightMotors;

  Talon rotator;
  Talon climbDrive;

  VictorSPX leftIntake;
  VictorSPX rightIntake;

  SingleSolenoid pusher;
  DoubleSoleniod arms;

  DoubleSoleniod ramp;
  DoubleSoleniod climbWheels;

  Climber climber;

  Drive drive;

  UsbCamera cam;

  Compressor comp;

  Keybinder driverKeys;
  Keybinder operatorKeys;

  Elevator elevator;

  Arm arm;

  boolean firstTime;

  DigitalInput limitSwitch;

  String driveMode;

  boolean isFirst1 = true;
  boolean isFirst2 = true;

  LEDStrip LED;

  int counter;

  boolean fadeIn;

  DriverStation driverStat;

  @Override
  public void robotInit() {

    SmashBoard.sendBoolean("enabled", false);

    driverStat = DriverStation.getInstance();

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

    limitSwitch = new DigitalInput(0);

    LED = new LEDStrip(0, 1);

    climbDrive = new Talon(11);
    climbWheels = new DoubleSoleniod(7, 6);
    ramp = new DoubleSoleniod(2, 1);

    climber = new Climber(ramp, climbWheels, climbDrive);

    driverXbox = new XboxController(Constants.DRIVER_STATION_PORT[1]);
    operatorStick = new Gamepad(Constants.DRIVER_STATION_PORT[0]);

    driverKeys = new Keybinder(driverXbox);
    operatorKeys = new Keybinder(operatorStick);

    leftMotors = new PIDSparkMaxGroup(Constants.CAN_ID[2], Constants.CAN_ID[3]);
    rightMotors = new PIDSparkMaxGroup(Constants.CAN_ID[4], Constants.CAN_ID[5]);

    leftIntake = new VictorSPX(8);
    rightIntake = new VictorSPX(9);

    pusher = new SingleSolenoid(Constants.PCM_PORT[0]);
    arms = new DoubleSoleniod(Constants.PCM_PORT[4], Constants.PCM_PORT[5]);

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
    elevator.resetEncoder();
    SmashBoard.sendBoolean("enabled", true);
    elevator.stop();
    arm.resetEncoder();
    climber.setRamp(false);
    climber.setWheels(false, 0);

    fadeIn = true;

    counter = 0;

  }

  @Override
  public void disabledInit() {
     SmashBoard.sendBoolean("enabled", false);
  }

  @Override
  public void teleopPeriodic() {

    // don't touch this if statement please
    if (firstTime) {

      arm.resetEncoder();
      driveMode = SmashBoard.getDriveMode();
      driverKeys.bind(SmashBoard.receiveDriverKeys());
      operatorKeys.bind(SmashBoard.receiveOperatorKeys());
      elevator.resetEncoder();
      elevator.stop();
      firstTime = false;

    }

    boolean armIsBack = !limitSwitch.get(); 

    double throttle = -driverKeys.getThrottle();
    double turning = driverKeys.getTurning();

    climber.setWheels(driverKeys.getToggle("dropWheels"), throttle);
    climber.setRamp(driverKeys.getToggle("dropRamp"));

    boolean toggleForward = driverKeys.getToggleForward();
    if (toggleForward) {

      throttle = -throttle;

    }

    switch (driveMode) {
    case ("curvature"):
      drive.curvature(throttle, turning);
      break;

    case ("curvy"):
      drive.partialParabolic(throttle, -turning * (Math.abs(turning)));
      break;

    case ("arcade"):
      drive.parabolicArcade(throttle, turning, 1);
      break;
    }
   
    if (!Mathd.isBetween(operatorKeys.getJoystick("armThrottle"), 0.07, -0.07)) {

      double armThrottle = operatorKeys.getJoystick("armThrottle");

      armThrottle = Math.signum(armThrottle) * Math.pow(armThrottle * Math.signum(armThrottle), 1 + (armThrottle * Math.signum(armThrottle)));

      if(armIsBack && armThrottle < 0){

        armThrottle = 0;

      }

      arm.manual(armThrottle);
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

    else if (operatorKeys.getJoystick("intakeIn") > 0.07 && !(operatorKeys.getJoystick("intakeOut") > 0.4)) {

      double suckFactor = operatorKeys.getJoystick("intakeIn");

      suckFactor = Math.signum(suckFactor) * Math.pow(suckFactor * Math.signum(suckFactor), 1 + (suckFactor * Math.signum(suckFactor)));

      arm.setIntakeSuck(suckFactor * 0.63);

    }

    else {

      arm.stopIntake();

    }

    arm.toggleArms(operatorKeys.getArmsToggle());

    arm.setPush(operatorKeys.getHatchLaunch());

    if (!Mathd.isBetween(operatorKeys.getJoystick("throttle"), 0.09, -0.09)) {

      double eleThrottle = -operatorKeys.getThrottle();

      double eleSpeed = Math.signum(eleThrottle) * Math.pow(eleThrottle * Math.signum(eleThrottle), 1 + (eleThrottle * Math.signum(eleThrottle)));

      if (eleThrottle < 0) {

        eleSpeed = eleSpeed/2;

      }

      if(armIsBack){

        eleSpeed = 0;

      }

      elevator.manual(eleSpeed);
      isFirst2 = true;

    }

    if (Mathd.isBetween(operatorKeys.getJoystick("throttle"), 0.09, -0.09)) {

      if (isFirst2) {

        elevator.resetEncoder();
        isFirst2 = false;

      }

      elevator.talon.talon.set(ControlMode.Position, 0);

    }

    

    /*

    if(counter > 255){

      counter = 255;
      fadeIn = false;

    }

    if(counter < 0){

      counter = 0;
      fadeIn = true;

    }

    double redPrecursor = 120 - (( (double) 120/255 ) * ((double)counter));

    int redVal = (int) Math.round(redPrecursor);
    int greenVal = counter;
    int blueVal = 255;
 
    LED.setColor(redVal, greenVal, blueVal, 1);
    LED.setColor(redVal, greenVal, blueVal, 2);
    LED.setColor(redVal, greenVal, blueVal, 3);
    LED.setColor(redVal, greenVal, blueVal, 4);
    
    if(fadeIn){

      counter++;

    }

    if(!fadeIn){

      counter--;

    }

    */

    //LED.setColor(120, 0, 255, 1);
    //LED.setColor(120, 0, 255, 2);
    //LED.setColor(120, 0, 255, 3);
    //LED.setColor(120, 0, 255, 4);

  }
}
