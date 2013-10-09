package fr.azuryus.bdi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BibliPlans {
	private List<List<Predicat>> croyances;
	private List<List<Predicat>> desirs;
	private List<List<Predicat>> plans;
	
	public BibliPlans(String fichier) {
		croyances = new ArrayList<>();
		desirs = new ArrayList<>();
		plans = new ArrayList<>();
		construirePlans(fichier);
	}
	
	public List<List<Predicat>> getCroyances() {
		return croyances;
	}

	public List<List<Predicat>> getDesirs() {
		return desirs;
	}

	public List<List<Predicat>> getPlans() {
		return plans;
	}
	
	public List<Predicat> trouverPlan(List<Predicat> croyances, List<Predicat> desirs) {
		// TODO implémenter trouverPlan(croyances, desirs)
		return plans.get(0);
	}
	
	private void construirePlans(String fichier) {
		// TODO implémenter construirePlans(fichier)
		try{
			InputStream flux=new FileInputStream(fichier);
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null) {
				System.out.println(ligne);
			}
			buff.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
