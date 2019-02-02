package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;
import wrapper.*;
import hardware.*;
import interfaces.MotorGroup;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;

public class Robot extends TimedRobot {

  XboxController driverXbox;
  Gamepad operatorStick;

  MotorGroup leftMotors;
  MotorGroup rightMotors;
  MotorGroup intakeMotors;
  Talon rotator;

  SingleSolenoid pusher;
  DoubleSoleniod arms;

  Drive drive;
  AdaptableDrive intake;

  Compressor comp;

  Keybinder driverKeys;

  Keybinder operatorKeys;

  Elevator elevator;

  Arm arm;

  boolean firstTime;

  String driveMode;

  @Override
  public void robotInit() {

    driverXbox = new XboxController(1);
    operatorStick = new Gamepad(0);

    driverKeys = new Keybinder(driverXbox);
    operatorKeys = new Keybinder(operatorStick);

    leftMotors = new PIDSparkMaxGroup(2, 3);
    rightMotors = new PIDSparkMaxGroup(4, 5);
    intakeMotors = new VictorSPXGroup(8,9);
    rotator = new Talon(10);

    pusher = new SingleSolenoid(0);
    arms = new DoubleSoleniod(1, 2);

    drive = new Drive(leftMotors, rightMotors);
    intake = new AdaptableDrive(intakeMotors);

    comp = new Compressor();

    comp.setClosedLoopControl(true);

    elevator = new Elevator(6, 7);

    arm = new Arm(arms, pusher, intake, rotator);

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

  }

  @Override
  public void teleopPeriodic() {
    // don't touch this if statement please
    if (firstTime) {
      driveMode = SmashBoard.getDriveMode();
      driverKeys.bind(SmashBoard.receiveDriverKeys());
      operatorKeys.bind(SmashBoard.receiveOperatorKeys());
      firstTime = false;
    }

    double throttle = driverKeys.getThrottle();
    double turning = driverKeys.getTurning();
  //  double tankLeft = driverKeys.getTankLeftStick();
  //  double tankRight = driverKeys.getTankRightStick();
    boolean toggleForward = driverKeys.getToggleForward();
    if (toggleForward) {

      throttle = -throttle;
    //  tankLeft = -tankLeft;
   //   tankRight = -tankRight;

    }


    switch (driveMode){
      case("curvature") :
        drive.curvature(throttle, turning);        
        break;

      case("arcade") :
        drive.parabolicArcade(throttle, turning, 1);
        break;
      
     // case("tank") :
     //   drive.parabolicTank(tankLeft, tankRight, 1);
     //   break;

    }
    elevator.incrementBallHeight(operatorKeys.getBalUp(), operatorKeys.getBallDown());

    elevator.incrementHatchHeight(operatorKeys.getHatchUp(), operatorKeys.getHatchDown());

    arm.toggleArms(driverKeys.getArmsToggle());

    arm.setPush(driverKeys.getHatchLaunch());

  }

}