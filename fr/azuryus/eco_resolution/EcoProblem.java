package fr.azuryus.eco_resolution;

import java.util.ArrayList;
import java.util.List;

public abstract class EcoProblem {
	protected List<EcoAgent> agents;
	
	public EcoProblem() {
		agents = new ArrayList<>();
	}
	
	public boolean mondeSatisfait() {
		boolean satisfait = true;
		for(EcoAgent agent : agents) {
			satisfait &= agent.satisfait();
		}
		return satisfait;
	}
	
	public abstract void solve();
}
