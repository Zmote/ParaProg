package uebung_03_aufgabe2;

import java.util.concurrent.CountDownLatch;

public class LatchBasedRaceControl extends AbstractRaceControl {
	private CountDownLatch ready = new CountDownLatch(NOF_RACE_CARS);
	private CountDownLatch start = new CountDownLatch(1);
	private CountDownLatch honor = new CountDownLatch(NOF_RACE_CARS);
	private CountDownLatch finals = new CountDownLatch(1);

	protected void waitForAllToBeReady() throws InterruptedException {
		ready.await();
	}

	public void readyToStart() {
		ready.countDown();
	}

	public synchronized void waitForStartSignal() throws InterruptedException {
		start.await();
	}

	protected synchronized void giveStartSignal() {
		start.countDown();
	}

	protected synchronized void waitForFinishing() throws InterruptedException {
		finals.await();
	}

	public synchronized boolean isOver() {
		return finals.getCount() == 0;
	}

	public synchronized void passFinishLine() {
		finals.countDown();
		honor.countDown();
	}

	public synchronized void waitForLapOfHonor() throws InterruptedException {
		honor.await();
	}
}
