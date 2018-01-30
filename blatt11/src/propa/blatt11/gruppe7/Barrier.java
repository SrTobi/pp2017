package propa.blatt11.gruppe7;

import propa.blatt11.IBarrier;

public class Barrier implements IBarrier {

	private int maxThreads;
	private int threadsInCurrentWaitingPhase = 0;
	private int currentWaitingPhase = 1;

	public Barrier(int maxThreads) {
		this.maxThreads = maxThreads;
	}


	@Override
	public synchronized void await() throws InterruptedException {

		// Threats müssen in await bleiben solange ihre
		// waitingPhase aktiv ist. Eine solche Phase ist beendet,
		// wenn entweder maxThreads await aufgerufen haben oder
		// aber freeAll aufgerufen wird.
		final int myphase = currentWaitingPhase;

		++threadsInCurrentWaitingPhase;

		if (threadsInCurrentWaitingPhase == maxThreads) {
			freeAll();
		}

		while (currentWaitingPhase == myphase) {
			this.wait();
		}
	}

	@Override
	public synchronized void freeAll() {
		// alle threads die await aufgerufen haben,
		// haben eine waitingPhase <= currentWaitingPhase.
		// die sollen alle await verlassen, was dadurch erreicht wird,
		// dass die currentWaitingPhase erhöht wird.
		// Alle threads die await nach diesem freeAll aufrufen
		// haben dann eine neue waitingPhase
		++currentWaitingPhase;
		threadsInCurrentWaitingPhase = 0;
		this.notifyAll();
	}
}
