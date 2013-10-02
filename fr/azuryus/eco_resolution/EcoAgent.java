package fr.azuryus.eco_resolution;

import fr.azuryus.automate.Automate;

public abstract class EcoAgent {
	protected Automate auto;
	private boolean agresse;
	private boolean gene;
	
	public EcoAgent() {
		auto = new Automate("eco-agent.txt", true);
	}

	public boolean isAgresse() {
		return agresse;
	}

	public void setAgresse(boolean agresse) {
		this.agresse = agresse;
	}

	public boolean isGene() {
		return gene;
	}

	public void setGene(boolean gene) {
		this.gene = gene;
	}
	
	public abstract boolean satisfait();
	
	public abstract void faireFuite();
	
	public abstract void faireSatisfaction();
	
	public abstract void agresser(EcoAgent agent);
	
	public abstract void exec();
}
