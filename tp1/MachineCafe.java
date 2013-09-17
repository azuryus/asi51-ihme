package tp1;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class MachineCafe extends Automate {
    private int coin;

    public MachineCafe(String fichier, boolean debug) {
	super(fichier, debug);
    }

    @Override
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
		if(choix.equals("coin")) {
		    coin++;
		    if(coin>=5)
			choix+=1;
		}
		tr = etatCourant.findTransitionByLabel(choix);
		if(tr != null) {
		    etatCourant = tr.getEtatSortant();
		    // System.out.println("Message: " + tr.getLabel());
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