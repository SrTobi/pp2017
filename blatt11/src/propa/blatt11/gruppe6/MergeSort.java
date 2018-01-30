package propa.blatt11.gruppe6;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MergeSort {

	private static class MergeSortTask extends RecursiveTask<List<Integer>> {

		private List<Integer> list;

		MergeSortTask(List<Integer> list) {
			this.list = new ArrayList<>(list);
		}

		@Override
		protected List<Integer> compute() {
			if (list.size() == 1) {
				return list;
			}
			int middle = list.size() / 2;

			MergeSortTask leftTask = new MergeSortTask(list.subList(0, middle));
			MergeSortTask rightTask = new MergeSortTask(list.subList(middle, list.size()));
			leftTask.fork();
			List<Integer> rightList = rightTask.compute();
			List<Integer> leftList = leftTask.join();

			List<Integer> result = new ArrayList<>();

			while (true) {
				if (rightList.isEmpty()) {
					result.addAll(leftList);
					break;
				}

				if (leftList.isEmpty()) {
					result.addAll(rightList);
					break;
				}
				if (leftList.get(0) < rightList.get(0)) {
					result.add(leftList.remove(0));
				} else {
					result.add(rightList.remove(0));
				}
			}

			return result;
		}
	}

	public static void main(String... args) {
		//ForkJoinPool pool = ForkJoinPool.commonPool();

		List<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(8);
		list.add(3);
		list.add(4);
		list.add(10);
		list.add(-10);
		list.add(0);
		//list.add(2);

		MergeSortTask mainTask = new MergeSortTask(list);


		System.out.println(list.toString());
		System.out.println(mainTask.compute().toString());
	}
}
