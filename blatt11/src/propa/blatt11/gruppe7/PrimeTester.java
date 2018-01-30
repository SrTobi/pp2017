package propa.blatt11.gruppe7;

public final class PrimeTester {
	public static boolean isPrime(int n) {
		if (n < 2) return false;
		for (int i = 2; i <= Math.sqrt(n); ++i) {
			if (n % i == 0) return false;
		}
		return true;
	}
}
