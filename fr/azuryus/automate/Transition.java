package fr.azuryus.automate;

public class Transition {
	private String label;
	private Etat etatSortant;
	private String conditions = "";
	private String actions = "";

	public Transition(String label, Etat etat) {
		this.label = label;
		this.etatSortant = etat;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Etat getEtatSortant() {
		return this.etatSortant;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getConditions() {
		return conditions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public String getActions() {
		return actions;
	}
}
