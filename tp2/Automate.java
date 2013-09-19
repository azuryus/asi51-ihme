package tp2;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class Automate {
    protected ArrayList<Etat> etats;
    protected Etat etatCourant;
    protected boolean debug;
    protected HashMap<String, Float> variables;

    public Automate(String fichier, boolean debug) {
	this.debug = debug;
	etats = new ArrayList<>();
	variables = new HashMap<>();
	initAutomate(fichier);
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
    
    protected void initEtats(BufferedReader buff) {
	if(debug) System.out.println("Ajout des états");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String[] infos = ligne.split(" ");
		boolean sortant = false;
		if(infos.length > 1) {
		    sortant = infos[1].equals("final");
		}
		etats.add(new Etat(infos[0], sortant));
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

    protected void initVariables(BufferedReader buff) {
	if(debug) System.out.println("Ajout des variables");
	try{
	    String ligne;
	    while((ligne = buff.readLine())!=null && !ligne.trim().isEmpty()) {
		String infos[] = ligne.split(":=");
		variables.put(infos[0], Float.valueOf(infos[1]));
		if(debug) System.out.println(" Variable " + infos[0] + " ajoutée");
	    }
	}
	catch(Exception e) {
	    System.err.println(e.toString());
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

    public boolean verifConditions(String[] conditions) {
	boolean valide = true;
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("JavaScript");
	for(String cond : conditions) {
	    Pattern p = Pattern.compile("(\\p{Alpha}*).*");
	    Matcher m = p.matcher(cond);
	    if(!m.matches()) System.err.println("Erreur de syntaxe dans une condition");
	    String var = m.group(1);
	    cond = cond.replaceAll("[^<>]=", "==");
	    boolean result = false;
	    try{
		engine.put(var, variables.get(var));
		result = (Boolean)engine.eval(cond);
	    }
	    catch(Exception e) {
		System.err.println(e.toString());
	    }
	    if(debug) System.out.println(" cond: " + cond + " " + result);
	    valide &= result;
	}
	return valide;
    }

    public void execActions(String[] actions) {
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("JavaScript");
	for(String act : actions) {
	    Pattern p = Pattern.compile("(\\p{Alpha}*).*");
	    Matcher m = p.matcher(act);
	    if(!m.matches()) System.err.println("Erreur de syntaxe dans une action");
	    String var = m.group(1);
	    act = act.replace(":=", "=");
	    if(debug) System.out.println(" action: " + act);
	    float result = variables.get(var);
	    try{
		engine.put(var, result);
		engine.eval(act);
		result = Float.parseFloat(engine.get(var).toString());
		// result = (Double)engine.get(var);
	    }
	    catch(Exception e) {
		System.err.println(e.toString());
	    }
	    if(debug) System.out.println(" result: " + result);
	    variables.put(var, result);
	}
    }
    
    public void run() {
	System.out.println("Lancement");
	System.out.println("---------");
	
	boolean sortie = false;

	do {
	    sortie = etatCourant.getFinal();
	    ArrayList<Transition> trEnvoi = etatCourant.findTransitionsEnvoi();
	    for(Transition tr : trEnvoi) {
		String[] conditions = tr.getConditions();
		boolean valide = verifConditions(tr.getConditions());
		if(valide) {
		    sortie = false;
		    System.out.println("Message: " + tr.getLabel().substring(1));
		    execActions(tr.getActions());
		    etatCourant = tr.getEtatSortant();
		    System.out.println("Etat: " + etatCourant.getLabel());
		}
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
		Transition trRecu = etatCourant.findTransitionByLabel(choix);
		if(trRecu != null) {
		    sortie = false;
		    execActions(trRecu.getActions());
		    etatCourant = trRecu.getEtatSortant();
		    System.out.println("Etat: " + etatCourant.getLabel());
		}
		else {
		    System.out.println("Action non trouvée");
		}
	    }
	}
	while(!sortie);
	
	System.out.println("---------");
	System.out.println("Fin");
    }
}
