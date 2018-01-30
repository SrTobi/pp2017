package propa.blatt11.gruppe7;


public final class PrimeCounterSequential {
	public static int countPrimes(final int until) {
		int count = 0;
		for (int i = 2; i <= until; ++i) {
			if (PrimeTester.isPrime(i)) ++count;
		}
		return count;
	}

	public static void main(String[] args) {
		final int target = 5000000;
		final long startTime = System.currentTimeMillis();
		int count = countPrimes(target);
		final long endTime = System.currentTimeMillis();
		System.out.println("Duration for interval [2, " + target + "] is "
				+ (endTime - startTime) + " ms\n" + count + " primes");
	}
}
