package wrapper;

import com.mach.LightDrive.*;
import edu.wpi.first.wpilibj.Servo;

public class LEDStrip{

    LightDrivePWM LEDS;

    Servo servo1;
    Servo servo2;  

    Color off = new Color(0, 0, 0);

    int counter;

    boolean fadeIn = true;

    public LEDStrip(int PWMPort1, int PWMPort2){

        servo1 = new Servo(PWMPort1);
        servo2 = new Servo(PWMPort2);

        LEDS = new LightDrivePWM(servo1, servo2);

    }
    
    public void setColor(int r, int g, int b, int channel){//these values are 0 - 255

        int redPercent = RGBToPercent(r);
        int greenPercent = RGBToPercent(g);
        int bluePercent = RGBToPercent(b);

        Color color = new Color(redPercent, greenPercent, bluePercent);

        LEDS.SetColor(channel, color);
        LEDS.Update();

    }

    public int RGBToPercent(int val) {

        double valy = (double) val;

        double percent = (valy / 255) * 100;

        if (percent < 0) {

            percent = 0;

        }

        return (int) percent;

    }

    public int oneToZero(int val){

        if(val == 0){

            return 1;

        }

        return val;

    }

   public void transition(int R1, int G1, int B1, int R2, int G2, int B2, int channel) {

        double r1 = (double) oneToZero(R1);
        double g1 = (double) oneToZero(G1);
        double b1 = (double) oneToZero(B1);
        double r2 = (double) oneToZero(R2);
        double g2 = (double) oneToZero(G2);
        double b2 = (double) oneToZero(B2);
        
        /*
        
        double redRatio = Math.max(r1, r2) / Math.min(r1, r2);
        double greenRatio = Math.max(g1, g2) / Math.min(g1, g2);
        double blueRatio = Math.max(b1, b2) / Math.min(b1, b2);

        */

        if (counter > 100) {

            counter = 0;
            fadeIn = false;

        }

        if (counter < 0) {

            counter = 0;
            fadeIn = true;

        }

        double intialRed = RGBToPercent(R1) + (counter * r1 / r2 * Math.signum(r1 - r2));

        double initalGreen = RGBToPercent(G1) + (counter * g1 / g2 * Math.signum(g1 - g2));

        double initialBlue = RGBToPercent(B1) + (counter * b1 / b2 * Math.signum(b1 - b2));

        int redPercent = (int) intialRed;

        int greenPercent = (int) initalGreen;

        int bluePercent = (int) initialBlue;

        Color color = new Color(redPercent, greenPercent, bluePercent);

        LEDS.SetColor(channel, color);
        if (fadeIn) {

            counter++;

        } else {

            counter--;

        }

    }

}