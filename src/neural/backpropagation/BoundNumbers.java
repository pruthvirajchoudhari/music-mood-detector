package neural.backpropagation;

public class BoundNumbers {
	
	/**
	 * Too small of a number.
	 */
	public static final double TOO_SMALL = -1.0E20;
	
	/**
	 * Too big of a number.
	 */
	public static final double TOO_BIG = 1.0E20;

	/**
	 * Bound the number so that it does not become too big or too small.
	 * 
	 * @param d
	 *            The number to check.
	 * @return The new number. Only changed if it was too big or too small.
	 */
	public static double bound(final double d) {
		if (d < TOO_SMALL) {
			return TOO_SMALL;
		} else if (d > TOO_BIG) {
			return TOO_BIG;
		} else {
			return d;
		}
	}
	
	/**
	 * A bounded version of Math.exp.
	 * @param d What to calculate.
	 * @return The result.
	 */
	public static double exp(final double d) {
		return bound(Math.exp(d));
	}
}
