package utils;

public class Smoother {

	boolean firstTime = true;
	double value;

	public double smoothedValue(double input, double incrementPercentage, double max, double min) {

		if (firstTime) {

			value = input;
			firstTime = false;

		}

		if (input < min) {
			value = min;

			return value;
		}

		if (input > max) {
			value = max;

			return value;
		}

		double increment = incrementPercentage * max;

		if (value < input) {

			value = value + increment;

			return value;

		}

		if (value > input) {

			value -= increment;

			return value;

		} else {

			return value;

		}

	}
}
