package propa.blatt11.gruppe6;

import propa.blatt11.IBarrier;

public class Barrier implements IBarrier {

	private int numThreads;
	private int waitingThreads = 0;
	private boolean emptying = false;
	private boolean emergencyEmptying = false;
	private int overallWaitingThreads = 0;

	public Barrier(int numThreadsToFreeThemAll) {
		this.numThreads = numThreadsToFreeThemAll;
	}

	@Override
	public synchronized void await() throws InterruptedException {

		++overallWaitingThreads;
		while(emptying && !emergencyEmptying) {
			wait();
		}

		++waitingThreads;
		while (waitingThreads < numThreads && !emptying && !emergencyEmptying) {
			wait();
		}

		notifyAll();
		emptying = true;
		--waitingThreads;
		--overallWaitingThreads;

		if (waitingThreads == 0) {
			emptying = false;
		}

		if (overallWaitingThreads == 0) {
			emergencyEmptying = false;
		}
	}

	@Override
	public synchronized void freeAll() {
		if (waitingThreads > 0) {
			emergencyEmptying = true;
			notifyAll();
		}
	}
}
