package fr.azuryus.bdi;

import java.util.ArrayList;
import java.util.List;

public class Environnement implements InterfaceEnvironnement {
	private List<Predicat> predicats;
	private List<Predicat> actions;
	
	public Environnement() {
		this.predicats = new ArrayList<>();
		this.actions = new ArrayList<>();
	}

	public List<Predicat> getPredicats() {
		return predicats;
	}

	public List<Predicat> observer() {
		return predicats;
	}
	
	public List<Predicat> getActions() {
		return actions;
	}
}
