package fr.azuryus.bdi;

import java.util.ArrayList;
import java.util.List;

public class Predicat {
	private String label;
	private List<Predicat> params;
	
	public Predicat() {
		this.label = "";
		this.params = new ArrayList<>();
	}
	
	public Predicat(String predicat) {
		this();
		
		// TODO Décomposer le prédicat
		this.label = predicat;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Predicat> getParams() {
		return params;
	}
	public void setParams(List<Predicat> params) {
		this.params = params;
	}
	public void addParam(Predicat param) {
		this.params.add(param);
	}
	public void removeParam(String predicat) {
		
	}
	
	public String toString() {
		String val = this.label;
		if(!params.isEmpty()) {
			val += "(";
			boolean first = true;
			for(Predicat pr : params) {
				if(!first)
					val += ",";
				else 
					first = false;
				
				val += pr;
			}
			val += ")";
		}
		return val;
	}
	
	public static void main(String[] args) {
		Predicat pr2 = new Predicat("take");
		pr2.addParam(new Predicat("cube_B"));
		
		Predicat pr = new Predicat("does");
		pr.addParam(pr2);
		
		System.out.println(pr);
	}
}
