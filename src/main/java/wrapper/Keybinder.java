package wrapper;

import wrapper.Gamepad;
import utils.KeyBinderDictionary;

public class Keybinder {// this is a dedicated class for deep space 2019

	Gamepad joystick;

	public KeyBinderDictionary dictionary;

	

	public String[] keysAndValues;

	public Keybinder(Gamepad joystick) {

		this.joystick = joystick;

	}

	public void bind(String keybindString) {
		// key:value,key:value...
		// strings containing a key and a value seperated by a colon
		String[] keyBindPairs = keybindString.split(",");
		dictionary = new KeyBinderDictionary(keyBindPairs.length);
		for (int i = 0; i < keyBindPairs.length; i++) {

			// keys and their values at alternating indexes, with keys first at 0
			keysAndValues = keyBindPairs[i].split(":");
			
			dictionary.add(keysAndValues[0], Integer.parseInt(keysAndValues[1]));

		}

	}

	public double getJoystick(String stickKey){

		return joystick.getAxis(dictionary.get(stickKey));

	}

	public boolean getButton(String buttonKey){

		return joystick.getButton(dictionary.get(buttonKey));

	}

	public boolean getToggle(String buttonKey){

		return joystick.getToggle(dictionary.get(buttonKey));

	}

	public double getThrottle() {
		
		return joystick.getAxis(dictionary.get("throttle"));
		
	}

	public double getTurning() {
		
		return joystick.getAxis(dictionary.get("turning"));

	}

	public boolean getArmsToggle() {

		return joystick.getToggle(dictionary.get("arm"));

	}

	public boolean getHatchLaunch(){

		return joystick.getButton(dictionary.get("eject"));

	}

	public boolean getSlapIntake(){

		return joystick.getToggle(dictionary.get("slapIntake"));

	}

	public boolean getDropRamp(){

		return joystick.getToggle(dictionary.get("ramp"));

	}

	public boolean getDropWheels(){

		return joystick.getToggle(dictionary.get("wheels"));

	}

	public boolean getIntakeSuck() {

		return joystick.getButton(dictionary.get("intakeSuck"));

	}

	public boolean getIntakeBlow(){

		return joystick.getButton(dictionary.get("intakeBlow"));

	}

	public boolean getToggleForward(){

		return joystick.getToggle(dictionary.get("forward"));

	}

	public boolean getBall1(){

		return joystick.getButton(dictionary.get("ball1"));

	}

	public boolean getBall2(){

		return joystick.getButton(dictionary.get("ball2"));

	}

	public boolean getBall3(){

		return joystick.getButton(dictionary.get("ball3"));

	}

	public boolean getHatch1(){

		return joystick.getButton(dictionary.get("hatch1"));

	}

	public boolean getHatch2(){

		return joystick.getButton(dictionary.get("hatch2"));

	}

	public boolean getHatch3(){

		return joystick.getButton(dictionary.get("hatch3"));

	}

	public boolean test1(){

		return joystick.getButton(dictionary.get("test1"));

	}

	public boolean test2(){

		return joystick.getButton(dictionary.get("test2"));

	}

	public boolean test3(){

		return joystick.getButton(dictionary.get("test3"));

	}

	public boolean test4(){

		return joystick.getButton(dictionary.get("test4"));

	}

}