package wrapper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmashBoard {

    public static void sendBoolean(String destination, boolean bool) {

        SmartDashboard.putBoolean(destination, bool);

    }

    public static boolean receiveBoolean(String origin) {

        return SmartDashboard.getBoolean(origin, false);

    }

    public static void sendDouble(String destination, double duble) {// no, that is not misspelled

        SmartDashboard.putNumber(destination, duble);

    }

    public static double receiveDouble(String origin) {

        return SmartDashboard.getNumber(origin, 0);

    }

    public static void sendString(String destination, String string) {

        SmartDashboard.putString(destination, string);

    }

    public static String receiveString(String origin) {

        return SmartDashboard.getString(origin, "");

    }

    public static String getDriveMode() {

        return SmartDashboard.getString("drivemode", "curvature");

    }

    public static String receiveDriverKeys() {

        return SmartDashboard.getString("driverKeys",
                "throttle:1,turning:4,forward:0,wheels:6,ramp:7");

    }

    public static String receiveOperatorKeys() {

        return SmartDashboard.getString("operatorKeys",
                "elevatorUp:8,elevatorDown:9,arm:4,slapIntake:5,intakeSuck:0,intakeBlow:3,eject:6");

    }

    public static void sendDoubleArray(String destination, double[] array) {

        SmartDashboard.putNumberArray(destination, array);

    }

}
