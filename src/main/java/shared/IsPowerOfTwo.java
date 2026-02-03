package shared;

public class IsPowerOfTwo {
	public static boolean check(double x) {
		if (2.0 == x) {
			return true;
		}
		if (2.0 > x) {
			return false;
		}
		return check(x / 2);
	}
}