package tp2;

import java.util.ArrayList;
import java.lang.reflect.Array;

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
	if(existTransition(transition.getLabel().substring(1))) {
	    String label = transition.getLabel();
	    int i = 1;
	    while(existTransition(label.substring(1)+i))
		i++;
	    transition.setLabel(label+i);
	}
	transitions.add(transition);
    }

    public boolean existTransition(String label) {
	return (findTransitionByLabel(label) != null);
    }

    public Transition findTransitionByLabel(String label) {
	for(Transition tr : transitions) {
	    if(tr.getLabel().equals("?"+label))
		return tr;
	}

	return null;
    }

    public ArrayList<Transition> findTransitionsEnvoi() {
	ArrayList<Transition> trEnvoi = new ArrayList<>();
	for(Transition tr : transitions)
	    if(tr.getLabel().startsWith("!"))
		trEnvoi.add(tr);

	return trEnvoi;
    }

    public boolean getFinal() {
	return fin;
    }
}
