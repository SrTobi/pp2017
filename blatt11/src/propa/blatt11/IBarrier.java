package propa.blatt11;

public interface IBarrier {
	void await() throws InterruptedException;
	void freeAll();
}