package wrapper;

import edu.wpi.first.wpilibj.Relay;

public class LEDRing{

    Relay relay;

    public LEDRing(int relayPort){

        relay = new Relay(relayPort);

    }

    public void set(boolean onOrOff){

        if(onOrOff){
        relay.set(Relay.Value.kOn);
        }

        else{

            relay.set(Relay.Value.kOff);

        }


    }





}