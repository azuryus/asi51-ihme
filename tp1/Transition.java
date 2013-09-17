package tp1;

public class Transition {
    private String label;
    private Etat etatSortant;

    public Transition(String label, Etat etat) {
	this.label = label;
	this.etatSortant = etat;
    }

    public String getLabel() {
	return label;
    }
}