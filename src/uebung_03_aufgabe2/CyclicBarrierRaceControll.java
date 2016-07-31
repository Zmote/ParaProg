package uebung_03_aufgabe2;

import java.util.concurrent.*;

public class CyclicBarrierRaceControll extends AbstractRaceControl {
	//n+1, weil der Controller ja auch 1x await() aufruft
	private CyclicBarrier wait = new CyclicBarrier(NOF_RACE_CARS+1);
	private CyclicBarrier nowait = new CyclicBarrier(1);
	

	protected void waitForAllToBeReady() throws InterruptedException {
		try {
			wait.await();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void readyToStart() {
	}

	public synchronized void waitForStartSignal() throws InterruptedException {
		try {
			wait.await();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	protected synchronized void giveStartSignal() {
	}

	protected synchronized void waitForFinishing() throws InterruptedException {
		try {
			nowait.await();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public synchronized boolean isOver() {
		//nowait.getParties() == 0; -> gibt fix Anzahl Teilnehmer an, kein Decrement, darum
		// nicht wirklich nützlich hier.
		return true;
	}

	public synchronized void passFinishLine() {
	}

	public synchronized void waitForLapOfHonor() throws InterruptedException {
		try {
			wait.await();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
