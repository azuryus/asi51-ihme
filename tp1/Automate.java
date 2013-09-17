package tp1;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;

public class Automate {
    protected ArrayList<Etat> etats;
    protected Etat etatCourant;
    protected boolean debug;

    public Automate(String fichier, boolean debug) {
	this.debug = debug;
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
    
    protected void initEtats(BufferedReader buff) {
	if(debug) System.out.println("Ajout des états");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String[] infos = ligne.split(" ");
		etats.add(new Etat(infos[0], (infos.length > 1)));
		if(debug) System.out.println(" Etat "+ligne+" ajouté");
	    }
	}
	catch(Exception e) {
	    System.out.println(e.toString());
	}
    }
    
    protected void initTransitions(BufferedReader buff) {
	if(debug) System.out.println("Ajout des transitions");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String[] infos = ligne.split(" ");
		Etat etatEntrant = findByLabel(infos[1]);
		Etat etatSortant = findByLabel(infos[2]);
		etatEntrant.ajouterTransition(new Transition(infos[0], etatSortant));
		if(debug) System.out.println(" Transition "+ infos[0] +" ajouté");
	    }
	}
	catch(Exception e) {
	    System.out.println(e.toString());
	}
    }
    
    protected Etat findByLabel(String label) {
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