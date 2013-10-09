package fr.azuryus.bdi;

import java.util.ArrayList;
import java.util.List;

public abstract class Agent {
	private InterfaceEnvironnement env;
	private List<Predicat> croyances;
	private List<Predicat> desirs;
	private List<Predicat> intentions;
	private BibliPlans biblio;
	
	public Agent(InterfaceEnvironnement env, BibliPlans biblio) {
		this.env = env;
		this.biblio = biblio;
		
		this.croyances = new ArrayList<>();
		this.desirs = new ArrayList<>();
		this.intentions = new ArrayList<>();
	}
	
	public void run() {
		croyances = env.observer();
		updateIntentions();
		executer();
	}

	private void executer() {
		Predicat pr = intentions.remove(0);
		System.out.println(pr);
	}

	private void updateIntentions() {
		biblio.trouverPlan(croyances, desirs);
	}
}
