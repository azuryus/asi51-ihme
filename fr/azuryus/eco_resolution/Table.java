package fr.azuryus.eco_resolution;

public class Table extends EcoAgent {
	protected Cube auDessus;
	
	@Override
	public boolean satisfait() {
		return true;
	}

	@Override
	public void faireFuite(EcoAgent agent) {
	}

	@Override
	public void faireSatisfaction() {
	}

	@Override
	public void agresser(EcoAgent agent) {
	}

	@Override
	public void exec() {
//		System.out.println("table");
//		System.out.println(auto.getEtatCourant());
//		System.out.println("transition trS");
		auto.changerEtat("trS");
	}
	
	public String toString() {
		return "Table";
	}
}
