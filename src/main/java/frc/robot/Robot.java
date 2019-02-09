package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import wrapper.*;
import hardware.*;
import interfaces.MotorGroup;
import edu.wpi.first.wpilibj.Compressor;

public class Robot extends TimedRobot {

  XboxController driverXbox;
  Gamepad operatorStick;
  

  MotorGroup leftMotors;
  MotorGroup rightMotors;

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

  Elevator elevator;

  Arm arm;

  boolean firstTime;

  String driveMode;

  @Override
  public void robotInit() {

    comp = new Compressor(0);

    comp.setClosedLoopControl(true);

 //   driverXbox = new XboxController(Constants.DRIVER_STATION_PORT[1]);
    operatorStick = new Gamepad(Constants.DRIVER_STATION_PORT[0]);
    


    driverKeys = new Keybinder(driverXbox);
    operatorKeys = new Keybinder(operatorStick);
    

    leftMotors = new PIDSparkMaxGroup(Constants.CAN_ID[2], Constants.CAN_ID[3]);
    rightMotors = new PIDSparkMaxGroup(Constants.CAN_ID[4], Constants.CAN_ID[5]);

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
    arm.resetEncoder();
    elevator.stop();
    elevator.reset();
    
  }

  @Override
  public void teleopPeriodic() {

    //don't touch this if statement please
    if (firstTime) {
      driveMode = SmashBoard.getDriveMode();
    //  driverKeys.bind(SmashBoard.receiveDriverKeys());
      operatorKeys.bind(SmashBoard.receiveOperatorKeys());
     
      arm.setPosition(0);
      elevator.reset();
      elevator.stop();
      firstTime = false;
    }
/*
    double throttle = driverKeys.getThrottle();
    double turning = driverKeys.getTurning();
    //double tankLeft = driverKeys.getTankLeftStick();
    //double tankRight = driverKeys.getTankRightStick();
    boolean toggleForward = driverKeys.getToggleForward();
    if (toggleForward) {

      throttle = -throttle;
      //tankLeft = -tankLeft;
      //tankRight = -tankRight;

    }

    switch (driveMode) {
    case ("curvature"):
      drive.curvature(throttle, turning);
      break;

    case ("arcade"):
      drive.parabolicArcade(throttle, turning, 1);
      break;

      
   // case("tank") :
   // drive.parabolicTank(tankLeft, tankRight, 1);
   // break;
    

    }
*/
    //elevator.manual(operatorKeys.getThrottle());


    
    if(operatorKeys.test1()){

      arm.setPosition(0);

    }

    if(operatorKeys.test2()){

      arm.setPosition(1);

    }

    if(operatorKeys.test3()){

      arm.setPosition(2);

    }

    if(operatorKeys.test4()){

      arm.setPosition(3);

    }


    

    System.out.println(arm.rotator.getEncoderValue());
    arm.setPush(operatorKeys.getHatchLaunch());

  
  //elevator.setBallHeight(operatorKeys.test1(), operatorKeys.test2(), operatorKeys.test3());
   //elevator.manual(driverKeys.getThrottle());


    

  }

}