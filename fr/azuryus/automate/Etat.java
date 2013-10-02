package fr.azuryus.automate;

import java.util.ArrayList;

import fr.azuryus.automate.exception.TransitionNonTrouveException;

public class Etat {
    private String label;
    private ArrayList<Transition> transitions;
    private boolean fin;
    private GestionVariables variables;

    public Etat(String label, boolean fin, GestionVariables variables) {
    	this.label = label;
		transitions = new ArrayList<Transition>();
		this.fin = fin;
		this.variables = variables;
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
    	boolean exist = true;
		try {
			findTransitionByLabel(label);
		} catch (TransitionNonTrouveException e) {
			exist = false;
		}
		return exist;
    }

    public Transition findTransitionByLabel(String label) throws TransitionNonTrouveException {
    	boolean trouve = false;
		for(Transition tr : transitions) {
		    if(tr.getLabel().equals(label)) {
		    	trouve = true;
		    	if(variables.evalCond(tr.getConditions(), ",")) {
		    		return tr;
		    	}
		    }
		}

		if(!trouve) {
			throw new TransitionNonTrouveException();
		}
		else {
			return null;
		}
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
