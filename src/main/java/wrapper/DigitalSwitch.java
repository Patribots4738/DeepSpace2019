package wrapper;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalSwitch {

	DigitalInput digitalSwitch;
	TogglableButton toggleSwitch;
	
	public DigitalSwitch(int channel) {
	
		digitalSwitch = new DigitalInput(channel);
		toggleSwitch = new TogglableButton();
	}
	

	/**
	 * @param state Input button state.
	 * @return The toggle's state.
	 */
	public boolean getDownToggle(){
		return toggleSwitch.toggleOnPress(digitalSwitch.get());
	}
	
	/**
	 * @param state State of the button being released.
	 * @return Returns true on the button's release.
	 */
	public boolean getUp(){
		return toggleSwitch.isUp(digitalSwitch.get());
	}
	
	/**
	 * @param state State of the button being pressed down.
	 * @return Returns true on the button press.
	 */
	public boolean getDown(boolean state){
		return toggleSwitch.isDown(digitalSwitch.get());
	}
	
	/**
	 * @return The state of the button.
	 */
	public boolean get(){
		return digitalSwitch.get();
	}
	
}
