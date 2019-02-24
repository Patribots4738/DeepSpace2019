package hardware;

import wrapper.DoubleSoleniod;
import wrapper.Talon;

public class Climber{

    DoubleSoleniod ramp;
    DoubleSoleniod wheels;

    Talon drive;

    public Climber(DoubleSoleniod ramp, DoubleSoleniod wheels, Talon drive){

        this.ramp = ramp;
        this.wheels = wheels;
        this.drive = drive;

    }

    public void setRamp(boolean toggleState){

        ramp.toggleWithState(toggleState);

    }

    public void setWheels(boolean toggleState, double speed){

        if(toggleState){
            drive.setPercent(speed);
        }
        
        wheels.toggleWithState(toggleState);
        
    }

}