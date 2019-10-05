package wrapper.utils;

public class Mathd {

	static boolean lastCheck;
	static boolean firstTime = true;

	public static double clamp(double value, double max, double min) {
		return Math.max(min, Math.min(max, value));
	}

	public static double normalize(double value, double max, double min) {
		return 0;
	}

	public static boolean isBetween(double input, double max, double min) {

		if (input <= max && input >= min) {

			return true;

		} else {

			return false;

		}

	} 

	public static double bananaCurve(double percentInput){

		return Math.signum(percentInput) * Math.pow(percentInput * Math.signum(percentInput), 1 + (percentInput * Math.signum(percentInput)));

	}

	public static double iteratedBananaCurve(double percentInput, int iterations){

		double input = percentInput;

		for(int i = 0; i < iterations; i++){

			input = bananaCurve(input);

		}

		return input;

	}

	public static double average(double[] arr){

		double total = 0;

		for(int i = 0; i < arr.length; i++){

			total += arr[i];

		}

		return total/arr.length;

	}

	public static double STDEV(double[] arr){

		double[] diffs = new double[arr.length];

		for(int i = 0; i < arr.length; i++){

			diffs[i] = (arr[i] - average(arr)) * (arr[i] - average(arr));

		}

		double totalDiffs = 0; 

		for(int j = 0; j < diffs.length; j++){

			totalDiffs += diffs[j];

		}

		totalDiffs /= (diffs.length -1 ) ;

		return Math.sqrt(totalDiffs);


	}
}
