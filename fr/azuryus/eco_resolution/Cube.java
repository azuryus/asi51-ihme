package fr.azuryus.eco_resolution;

public class Cube extends EcoAgent {
	private String nom;
	private Cube auDessus;
	private EcoAgent auDessous;
	private EcoAgent but;
	private Table table;

	public Cube(String nom, EcoAgent but, Table table) {
		this.nom = nom;
		this.but = but;
		this.table = table;
		
		this.auDessous = table;
		this.auDessus = null;
	}
	
	@Override
	public void faireSatisfaction() {
		if(this.but.getClass().equals(Cube.class))
			((Cube)this.but).auDessus = this;
		else
			((Table)this.but).auDessus = this;
		if(this.auDessous.getClass().equals(Cube.class))
			((Cube)this.auDessous).auDessus = null;
		else
			((Table)this.auDessous).auDessus = null;
		
		this.auDessous = but;
	}
	
	@Override
	public void faireFuite(EcoAgent agent) {
		if(this.auDessous.getClass().equals(Cube.class))
			((Cube)this.auDessous).auDessus = null;
		else
			((Table)this.auDessous).auDessus = null;
		
		this.auDessous = agent;
	}

	@Override
	public void agresser(EcoAgent agent) {
		agent.setAgresse(true);
	}
	
	@Override
	public boolean satisfait() {
		return this.auDessous==this.but;
	}

	@Override
	public void exec() {
//		System.out.println("Cube " + this);
//		System.out.println(auto.getEtatCourant());
		
		EcoAgent gene = null;
		String transition = "";
		
		switch(auto.getEtatCourant().toString()) {
		case "RS":
			gene = geneursSatisfaction();
			if(gene != null)
				setGene(true);
			
			if(isAgresse()) {
				transition = "trRF";
			}
			else if(isGene()) {
				agresser(gene);
				transition = "trRS";
			}
			else {
				faireSatisfaction();
				transition = "trS";
			}
			break;
		case "RF":
			gene = geneursFuite();
			if(gene != null)
				setGene(true);
			
			if(isAgresse()) {
				if(isGene()) {
					agresser(gene);
					transition = "trRF";
				}
				else {
					faireFuite(trouverPlacePourFuir());
					transition = "trF";
				}
			}
			else {
				transition = "trRS";
			}
			break;
		case "F":
			if(isAgresse()) {
				transition = "trRF";
			}
			else {
				transition = "trRS";
			}
			break;
		case "S":
			if(isAgresse()) {
				transition = "trRF";
			}
			else {
				transition = "trS";
			}
			break;
		}
		
//		System.out.println("transition " + transition);
		auto.changerEtat(transition);
		
		setGene(false);
		setAgresse(false);
		
//		System.out.println(this.auDessous);
	}
	
	private EcoAgent trouverPlacePourFuir() {
		return this.table;
	}
	
	private EcoAgent geneursFuite() {
		if(this.auDessus != null)
			return this.auDessus;
		else
			return null;
	}
	
	private EcoAgent geneursSatisfaction() {
		if(this.auDessus != null)
			return this.auDessus;
		else if(this.but.getClass().equals(Cube.class) && ((Cube)this.but).auDessus != null)
			return ((Cube)this.but).auDessus;
		else if(this.but.getClass().equals(Table.class) && ((Table)this.but).auDessus != null)
			return ((Table)this.but).auDessus;
		else
			return null;
	}
	
	@Override
	public String toString() {
		return this.nom;
	}
}
