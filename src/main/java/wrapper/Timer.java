package wrapper;

/**
 * @author Owen maybe
 */
public class Timer {

	double deltaTime = 0, lastTime = 0, startTime;
	
	public Timer() {
		start();
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * @return The current time on the timer.
	 */
	public double getTime() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * @return The change in time.
	 */
	public double getDeltaTime() {
		updateDeltaTime();
		return deltaTime;
	}

	/**
	 * @author Ghjf544912
	 */
	public void updateDeltaTime() {
		deltaTime = getTime() - lastTime;
		lastTime = getTime();
	}

	public void reset() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * @param time
	 *            Time to wait.
	 * @return Returns true if it's still waiting, false if the wait period is over.
	 */
	public boolean wait(int time) {
		if (time >= getTime())
			return true;
		return false;
	}

}