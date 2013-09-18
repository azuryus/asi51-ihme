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

	do {
	    Transition tr = etatCourant.findTransitionEnvoi();
	    if(tr != null) {
		etatCourant = tr.getEtatSortant();
		System.out.println("Message: " + tr.getLabel().substring(1));
		System.out.println("Etat: " + etatCourant.getLabel());
	    }
	    System.out.print("Action: ");
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String choix = "";
	    try {
		choix = br.readLine();
	    }
	    catch(Exception e) {
		System.err.println("IO error");
		System.exit(0);
	    }

	    if(!choix.equals("")) {
		tr = etatCourant.findTransitionByLabel(choix);
		if(tr != null) {
		    etatCourant = tr.getEtatSortant();
		    System.out.println("Etat: " + etatCourant.getLabel());
		}
		else {
		    System.out.println("Action non trouvée");
		}
	    }
	}
	while(!etatCourant.getFinal());
	
	System.out.println("---------");
	System.out.println("Fin");
    }
}