package hardware;

import wrapper.Talon;
import wrapper.TogglableButton;
import utils.Mathd;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Elevator {

    
    TogglableButton hatch1;
    TogglableButton hatch2;
    TogglableButton hatch3;
    TogglableButton ball1;
    TogglableButton ball2;
    TogglableButton ball3;

  public  Talon talon;

    VictorSPX slave;

    int masterID;
    double rotationsPerBallLevel = 17;
    double rotationsToFirstHatch = 11.6;
    double rotationsPerHatchLevel = 17;
    int ballLevel = 0;
    int hatchLevel = 0;

    public Elevator(int talonCANID, int victorCANID) {

        talon = new Talon(talonCANID);
        slave = new VictorSPX(victorCANID);
        masterID = talonCANID;
        hatchLevel = 0;
        ballLevel = 0;

        talon.setMaxOutput(0.45, -0.45);
        talon.setP(1);
        talon.setI(0);
        talon.setD(0);
        talon.setInverted(false);
        

        hatch1 = new TogglableButton();
        hatch2 = new TogglableButton();
        hatch3 = new TogglableButton();
        ball1 = new TogglableButton();
        ball2 = new TogglableButton();
        ball3 = new TogglableButton();

    }

    public void setBallHeight(int level) {

        talon.setPosition(rotationsPerBallLevel * level);
        slave.follow(talon.talon);

    }

    public void resetEncoder(){

        talon.resetEncoder();

    }

    public void setHatchHeight(int level) {

        if(level == 0){

            talon.setPosition(0);
            slave.follow(talon.talon);

        }

        else{

        talon.setPosition(rotationsPerHatchLevel * level);
        slave.follow(talon.talon);
        }
        
    }

    public void setBallHeight(boolean height1, boolean height2, boolean height3) {

        if (ball1.isDown(height1)) {

            ballLevel = 0;

        }

        if (ball2.isDown(height2)) {

            ballLevel = 1 ;

        }

        if (ball3.isDown(height3)){

            ballLevel = 2;

        }

        setBallHeight((int)Mathd.clamp(ballLevel, 3, 0));

    }

    public void setHatchHeight(boolean height1, boolean height2, boolean height3) {
        if (hatch1.isDown(height1)) {

            hatchLevel = 1;

        }

        if (hatch2.isDown(height2)) {

            hatchLevel = 2 ;

        }

        if (hatch3.isDown(height3)){

            hatchLevel = 3;

        }

        setHatchHeight((int)Mathd.clamp(hatchLevel, 2, 0));

    }

    public void reset(){

        talon.setPosition(0);
        slave.follow(talon.talon);
        ballLevel = 0;
        hatchLevel = 0;

    }

    public void manual(double power){

        talon.setPercent(power);
        slave.follow(talon.talon);
        
    }

    public void stop(){

        talon.setPercent(0);
        slave.set(ControlMode.PercentOutput, 0);

    }

}