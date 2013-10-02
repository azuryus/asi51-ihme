package fr.azuryus.automate.event;

import fr.azuryus.automate.Etat;
import fr.azuryus.automate.Transition;

public class EtatEvent {
	private Etat etat;
	private Transition transition;
	
	public EtatEvent(Etat etat, Transition transition) {
		this.etat = etat;
		this.transition = transition;
	}
	
	public Etat getEtat() {
		return this.etat;
	}
	
	public Transition getTransition() {
		return this.transition;
	}
}
