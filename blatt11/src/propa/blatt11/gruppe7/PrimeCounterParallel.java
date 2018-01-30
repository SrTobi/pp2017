package propa.blatt11.gruppe7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class PrimeCounterParallel {

	public static int countPrimes(final int until) throws ExecutionException, InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(8);
		final int blockSize = 5000;

		List<FutureTask<Integer>> tasks = new ArrayList<>();

		for (int i = 2; i <= until; i += blockSize) {
			final int begin = i;
			final int end = Math.min(i + blockSize, until + 1);

			FutureTask<Integer> task = new FutureTask<Integer>(() -> {
				int result = 0;

				for (int j = begin; j < end; ++j) {
					if (PrimeTester.isPrime(j)) {
						++result;
					}
				}

				return result;
			});
			tasks.add(task);
			service.submit(task);
		}

		int result = 0;

		for (FutureTask<Integer> task : tasks) {
			result += task.get();
		}
		service.shutdown();
		return result;
	}

	@SuppressWarnings("Duplicates")
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final int target = 5000000;
		final long startTime = System.currentTimeMillis();
		int count = countPrimes(target);
		final long endTime = System.currentTimeMillis();
		System.out.println("Duration for interval [2, " + target + "] is "
				+ (endTime - startTime) + " ms\n" + count + " primes");
	}
}
