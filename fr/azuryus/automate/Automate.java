package fr.azuryus.automate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import fr.azuryus.automate.event.EtatEvent;
import fr.azuryus.automate.event.EtatListener;
import fr.azuryus.automate.exception.TransitionNonTrouveException;

/**
 * Un automate reçoit des messages, execute des actions
 * et réponds selon sa configuration.
 * @author sguingoin
 *
 */
public class Automate implements EtatListener {
	protected ArrayList<Etat> etats;
	protected Etat etatCourant;
	protected boolean debug;
	private GestionVariables variables;
	private InputStream input = System.in;
	private PrintStream output = System.out;

	/**
	 * Constructeur
	 * @param fichier fichier de description de l'automate
	 */
	public Automate(String fichier) {
		this(fichier, false);
	}
	
	public Automate(String fichier, boolean debug) {
		etats = new ArrayList<>();
		variables = new GestionVariables();
		this.debug = debug;
		initAutomate(fichier);
	}
	
	/**
	 * Dés/Active le mode debug 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	private void initAutomate(String fichier) {
		if(debug) System.out.println("Initialisation");
		if(debug) System.out.println("--------------");
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
				if(ligne.equals("[Variables]"))
					initVariables(buff);
			}
			buff.close();
			etatCourant = etats.get(0);
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		if(debug) System.out.println("--------------");
	}

	private void initEtats(BufferedReader buff) {
		if(debug) System.out.println("Ajout des états");
		try{
			String ligne;
			while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
				String[] infos = ligne.split(" ");
				boolean sortant = false;
				if(infos.length > 1) {
					sortant = infos[1].equals("final");
				}
				etats.add(new Etat(infos[0], sortant, variables));
				if(debug) System.out.println(" Etat "+ligne+" ajouté");
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	private void initTransitions(BufferedReader buff) {
		if(debug) System.out.println("Ajout des transitions");
		try{
			String ligne;
			while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
				String[] infos = ligne.split(" ");
				Etat etatEntrant = findByLabel(infos[1]);
				Etat etatSortant = findByLabel(infos[2]);
				Transition tr = new Transition(infos[0], etatSortant);
				if(debug) System.out.println(" Transition "+ infos[0] +" ajouté");
				if(infos.length > 3) {
					String conditions = infos[3].substring(1, infos[3].length()-1);
					tr.setConditions(conditions);
					if(debug) System.out.println("  + conditions");
					if(infos.length > 4) {
						String actions = infos[4].substring(1, infos[4].length()-1);
						tr.setActions(actions);
						if(debug) System.out.println("  + actions");
					}
				}
				etatEntrant.ajouterTransition(tr);
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	private void initVariables(BufferedReader buff) {
		if(debug) System.out.println("Ajout des variables");
		try{
			String ligne;
			while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
				String infos[] = ligne.split(":=");
				if(infos[0].contains("timer")) {
					infos[0] = infos[0].replace("timer ", "");
					variables.addTimer(infos[0], Float.parseFloat(infos[1]));
				}
				else {
					variables.addVariable(infos[0], Float.valueOf(infos[1]));
				}
				if(debug) System.out.println(" Variable " + infos[0] + " ajoutée");
			}
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * Trouve un état selon son label
	 * @param label label de l'état cherché
	 * @return l'état trouvé ou null
	 */
	public Etat findByLabel(String label) {
		for(Etat etat : etats) {
			if(etat.getLabel().equals(label))
				return etat;
		}
		return null;
	}

	/**
	 * Récupère l'état courant
	 * @return l'état courant
	 */
	public Etat getEtatCourant() {
		return etatCourant;
	}

	/**
	 * Fonction principale qui execute l'automate
	 */
	public void run() {
		System.out.println("Lancement");
		System.out.println("---------");

		boolean sortie = false;

		do {
			sortie = traiterEtat();
		}
		while(!sortie);

		variables.stopTimer();
		
		System.out.println("---------");
		System.out.println("Fin");
	}

	private boolean traiterEtat() {
		ArrayList<Transition> trEnvoi = etatCourant.findTransitionsEnvoi();
		for(Transition tr : trEnvoi) {
			boolean valide = variables.evalCond(tr.getConditions(), ",");
			if(valide) {
				output.println("!" + tr.getLabel().substring(1));
				variables.execExp(tr.getActions(), ",");
				etatCourant = tr.getEtatSortant();
				if(debug) System.out.println("Etat: " + etatCourant.getLabel());
				return false;
			}
		}
		boolean sortie = etatCourant.getFinal();
		
		String choix = demanderTransition();

		if(!choix.equals("")) {
			changerEtat(choix);
			return false;
		}
		return sortie;
	}

	private String demanderTransition() {
		if(debug) System.out.print("Action: ");
		
//		System.out.print("?");
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String choix = "";
		try {
			choix = br.readLine();
		}
		catch(Exception e) {
			System.err.println("IO error");
			System.exit(0);
		}
		
		return choix;
	}

	public void changerEtat(String transition) {
		try {
			Transition trRecu = etatCourant.findTransitionByLabel(transition);
			if(trRecu != null) {
				variables.execExp(trRecu.getActions(), ",");
				etatCourant = trRecu.getEtatSortant();
				if(debug) System.out.println("Etat: " + etatCourant.getLabel());
			}
			else {
				System.err.println("Transition impossible");
			}
		}
		catch(TransitionNonTrouveException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void nouvelEtat(EtatEvent e) {
		changerEtat(e.getTransition().toString());
	}
}
