package uebung_03_aufgabe2;

public class FormulaOne {
	public static void main(String[] args) throws InterruptedException {
		new MonitorBasedRaceControl().runRace();
//		new LatchBasedRaceControl().runRace();
		new CyclicBarrierRaceControll().runRace();
	}
}
