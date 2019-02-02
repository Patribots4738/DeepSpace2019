package wrapper;

public class TogglableButton {

	boolean lastPressed = false; // for all of these, being true means they are pressed down
	boolean lastPressWasToggle = false;
	boolean toggleState = false;

	public TogglableButton() {
	}

	public boolean isDown(boolean isPressed) {
		if (isPressed == true && lastPressed == false) { // this checks if the button is currently down, and if it wasn't the last time, if so, it must have been pressed
														
			lastPressed = true;
			return true;

		}
		lastPressed = isPressed;
		return false;

	}

	public boolean isUp(boolean isPressed) {
		if (isPressed == false && lastPressed == true) { //this checks if the button is currently up, and if it wasn't the last time, if so, it must have been released
			lastPressed = false;
			return true;
		}
		lastPressed = isPressed;
		return false;

	}
	
	public boolean toggleOnPress(boolean isPressed) {
		if(isPressed == true && lastPressWasToggle == false) { // this checks if the button is currently down, and if the last time it was pressed wasn't a toggle
			toggleState = !toggleState; // this inverts the toggle
			lastPressWasToggle = true; 
			
		} else if(isPressed == false) { // this checks if the button is currently up
			lastPressWasToggle = false; // if so, then it now can toggle again, which is what this line is for
			
		}
		
		return toggleState;
		
		
	}

}