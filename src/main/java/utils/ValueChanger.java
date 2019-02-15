package utils;

public class ValueChanger {

    boolean lastCheck = false;

    public ValueChanger() {
    }

    public boolean valueChanged(boolean check) {

        if (lastCheck != check) {

            lastCheck = check;

            return true;

        }

        return false;

    }

}