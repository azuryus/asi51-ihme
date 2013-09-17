package tp1;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;

public class Automate {
    private ArrayList<Etat> etats;
    private Etat etatCourant;

    public Automate(String fichier) {
	etats = new ArrayList<Etat>();

	try{
	    InputStream flux=new FileInputStream(fichier); 
	    InputStreamReader lecture=new InputStreamReader(flux);
	    BufferedReader buff=new BufferedReader(lecture);
	    String ligne;
	    while ((ligne=buff.readLine())!=null) {
		if (ligne.equals("[Etats]"))
		    initEtats(buff);
		if(ligne.equals("[Transitions]"))
		    initTransitions(buff);
	    }
	    buff.close();
	    etatCourant = etats.get(0);
	}		
	catch (Exception e){
	    System.out.println(e.toString());
	}
    }

    private void initEtats(BufferedReader buff) {
	System.out.println("Ajout des états");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String[] infos = ligne.split(" ");
		etats.add(new Etat(infos[0], (infos.length > 1)));
		System.out.println(" Etat "+ligne+" ajouté");
	    }
	}
	catch(Exception e) {
	    System.out.println(e.toString());
	}
    }

    private void initTransitions(BufferedReader buff) {
	System.out.println("Ajout des transitions");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String[] infos = ligne.split(" ");
		Etat etatEntrant = findByLabel(infos[1]);
		Etat etatSortant = findByLabel(infos[2]);
		etatEntrant.ajouterTransition(new Transition(infos[0], etatSortant));
		System.out.println(" Transition "+ infos[0] +" ajouté");
	    }
	}
	catch(Exception e) {
	    System.out.println(e.toString());
	}
    }

    private Etat findByLabel(String label) {
	for(Etat etat : etats) {
	    if(etat.getLabel().equals(label))
		return etat;
	}
	
	return null;
    }

    public Etat getEtatCourant() {
	return etatCourant;
    }

    public void run() {
	System.out.println("Lancement");
	System.out.println("---------");
	
	System.out.println("---------");
	System.out.println("Fin");
    }
}