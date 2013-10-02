package fr.azuryus.eco_resolution;

public class Cube extends EcoAgent {
	private Cube auDessus;
	private EcoAgent auDessous;
	private EcoAgent but;

	@Override
	public void faireSatisfaction() {
		((Cube)this.but).auDessus = this;
		((Cube)this.auDessous).auDessus = null;
		this.auDessous = but;
	}
	
	@Override
	public void faireFuite() {
		
	}

	@Override
	public void agresser(EcoAgent agent) {
		
	}
	
	@Override
	public boolean satisfait() {
		return this.auDessous==this.but;
	}

	@Override
	public void exec() {
		
	}
}
