package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import wrapper.*;
import hardware.*;
import interfaces.MotorGroup;
import edu.wpi.first.wpilibj.Compressor;

public class Robot extends TimedRobot {

  XboxController driverXbox;
  Gamepad operatorStick;
  Gamepad keyboard;

  PIDSparkMaxGroup leftMotors;
  PIDSparkMaxGroup rightMotors;

  MotorGroup intakeMotors;
  Talon rotator;

  SingleSolenoid pusher;
  DoubleSoleniod arms;

  DoubleSoleniod test1;
  DoubleSoleniod test2;
  SingleSolenoid test3;

  Drive drive;
  AdaptableDrive intake;

  Compressor comp;

  Keybinder driverKeys;
  Keybinder operatorKeys;
  Keybinder keyKeys;

  Elevator elevator;

  Arm arm;

  SeekerMode autoRotate;

  boolean firstTime;

  String driveMode;

  @Override
  public void robotInit() {

    comp = new Compressor(0);

    comp.setClosedLoopControl(false);

    driverXbox = new XboxController(Constants.DRIVER_STATION_PORT[1]);
    operatorStick = new Gamepad(Constants.DRIVER_STATION_PORT[0]);
    keyboard = new Gamepad(4);

    driverKeys = new Keybinder(driverXbox);
    operatorKeys = new Keybinder(operatorStick);
    keyKeys = new Keybinder(keyboard);

    leftMotors = new PIDSparkMaxGroup(Constants.CAN_ID[2], Constants.CAN_ID[3]);
    rightMotors = new PIDSparkMaxGroup(Constants.CAN_ID[4], Constants.CAN_ID[5]);

    autoRotate = new SeekerMode(leftMotors, rightMotors);

    intakeMotors = new VictorSPXGroup(Constants.CAN_ID[8],Constants.CAN_ID[9]);

    pusher = new SingleSolenoid(Constants.PCM_PORT[7]);
    arms = new DoubleSoleniod(Constants.PCM_PORT[1], Constants.PCM_PORT[2]);

    drive = new Drive(leftMotors, rightMotors);
    intake = new AdaptableDrive(intakeMotors);

    elevator = new Elevator(Constants.CAN_ID[6], Constants.CAN_ID[7]);

    arm = new Arm(arms, pusher, intake, Constants.CAN_ID[10]);

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
    SmashBoard.sendBoolean("enabled", false);
  //arm.resetEncoder();
    elevator.stop();

  }

  @Override
  public void teleopPeriodic() {

//  don't touch this if statement please
    if (firstTime) {
//     driveMode = SmashBoard.getDriveMode();
//     driverKeys.bind(SmashBoard.receiveDriverKeys());
//     operatorKeys.bind(SmashBoard.receiveOperatorKeys());
     
//     arm.setPosition("resting");
//     elevator.reset();
      keyKeys.bind("test1:0,test2:1,test3:2,test4:3");
      elevator.stop();
      firstTime = false;
    }

   

//    double throttle = driverKeys.getThrottle();
//    double turning = driverKeys.getTurning();

/*
    double tankLeft = driverKeys.getTankLeftSticFk();
    double tankRight = driverKeys.getTankRightStick();
    boolean toggleForward = driverKeys.getToggleForward();
    if (toggleForward) {

      throttle = -throttle;
      tankLeft = -tankLeft;
      tankRight = -tankRight;

    }
*/
    

    
/*
    switch (driveMode) {
    case ("curvature"):
      drive.curvature(throttle, turning);
      break;

    case ("arcade"):
      drive.parabolicArcade(throttle, turning, 1);
      break;
/*
    case("tank") :
      drive.parabolicTank(tankLeft, tankRight, 1);
      break;
*/
//    }

  

/*
    if(operatorKeys.test1()){

      arm.setPosition("resting");

    }

    if(operatorKeys.test2()){

      arm.setPosition("shoot");

    }

    if(operatorKeys.test3()){

      arm.setPosition("slap");

    }

    if(operatorKeys.test4()){

      arm.setPosition("ball");

    }
*/    
//    arm.setPush(operatorKeys.getHatchLaunch());
  
//    elevator.setBallHeight(operatorKeys.test1(), operatorKeys.test2(), operatorKeys.test3());


/*
  if (!arm.getPos().equals("resting")){

    double elevatorSpeed = operatorKeys.getThrottle();

    if(elevatorSpeed < 0){

     elevatorSpeed = elevatorSpeed * 0.5;

    }

   elevator.manual(elevatorSpeed);

  }
*/

    if(keyKeys.getButton("test1")){

      arm.setPosition("resting");

    }

    if(keyKeys.getButton("test2")){

      arm.setPosition("shoot");

    }

    if(keyKeys.getButton("test3")){

      arm.setPosition("slap");

    }

    if(keyKeys.getButton("test4")){

      arm.setPosition("ball");

    }
    

  }

}