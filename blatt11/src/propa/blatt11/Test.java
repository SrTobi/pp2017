package propa.blatt11;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Test {

	static class Task extends RecursiveTask<Integer> {

		private int from, to;
		Task(int from, int to) {
			System.out.println("fromm " + from + " to " + to);
			this.from = from;
			this.to = to;
		}

		@Override
		protected Integer compute() {
			if (to == from) {
				return to;
			} else if (from == to - 1) {
				return to + from;
			} else {
				int middle = (from + to) / 2;
				Task left = new Task(from, middle);
				left.fork();
				Task right = new Task(middle + 1, to);
				return right.compute() + left.join();
			}
		}
	}

	public static void main(String args[]) {
		ForkJoinPool pool = new ForkJoinPool();

		Task t = new Task(1, 4);

		System.out.println(t.compute());
	}
}
