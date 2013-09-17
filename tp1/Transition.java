package tp1;

public class Transition {
    private String label;
    private Etat etatSortant;

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
}