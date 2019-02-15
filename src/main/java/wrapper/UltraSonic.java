package wrapper;

import edu.wpi.first.wpilibj.AnalogInput;
import utils.MovingAverage;

public class UltraSonic{

    AnalogInput ultra;

    MovingAverage average;

    double[] largeArray = new double[1000];

    int index = 0;
    int port = 0;

    public UltraSonic(int port){//this is an analog port through PWM
        this.port = port;
        ultra = new AnalogInput(port);

        average = new MovingAverage(10);

        

    }

    public double getDistance(){

        average.average(ultra.getAverageVoltage());
        /*
        if(index  < 1000){
            largeArray[index] = average.getAverage();

            index++;
        } else if(index >= 999){

            System.out.println(port + "Average is : " + Mathd.average(largeArray));
            System.out.println(port + "STDEV is: " + Mathd.STDEV(largeArray));
            index++;
        }
        */
        return (average.getAverage() * 46.3175) + 5.81715;

    }


    

   




}