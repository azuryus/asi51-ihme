package tp1;

import java.util.ArrayList;

public class Etat {
    private String label;
    private ArrayList<Transition> transitions;
    private boolean fin;

    public Etat(String label, boolean fin) {
	this.label = label;
	transitions = new ArrayList<Transition>();
	this.fin = fin;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public void ajouterTransition(Transition transition) {
	transitions.add(transition);
    }

    public Transition findTransitionByLabel(String label) {
	for(Transition tr : transitions) {
	    if(tr.getLabel().equals(label))
		return tr;
	}

	return null;
    }

    public Transition findTransitionEnvoi() {
	for(Transition tr : transitions)
	    if(tr.getLabel().startsWith("!"))
		return tr;

	return null;
    }
}