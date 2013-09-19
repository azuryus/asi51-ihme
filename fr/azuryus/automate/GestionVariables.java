package fr.azuryus.automate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Gère les variables pour des automates
 * @author sguingoin
 * 
 */
public class GestionVariables {
	//	private ScriptEngineManager mgr;
	private ScriptEngine engine;

	/**
	 * Constructeur
	 */
	public GestionVariables() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		engine = mgr.getEngineByName("JavaScript");
	}

	/**
	 * Ajoute une variable
	 * @param nom nom de la variable
	 * @param valeur valeur de la variable
	 */
	public void addVariable(String nom, float valeur) {
		engine.put(nom, valeur);
	}

	/**
	 * Récupère une variable
	 * @param nom nom de la variable
	 * @return valeur de la variable
	 * @throws NullPointerException 
	 */
	public float get(String nom) throws NullPointerException {
		Object obj = engine.get(nom);
		if(obj == null) throw new NullPointerException("Variable non trouvée");
		return Float.parseFloat(obj.toString());
	}

	/**
	 * <p>Execute une expression. Cette méthode
	 * peut initialiser des variables, calculer 
	 * des opérations, etc.</p>
	 * <p>Exemples:
	 *  <ul>
	 * 	 <li>x:=2</li>
	 *   <li>valY:=x+1</li>
	 *   <li>valY:=valY+3</li>
	 *   <li>x++</li>
	 *  </ul>
	 * </p>
	 * @param exp expression
	 */
	public void execExp(String exp) {
		if(!exp.matches("\\p{Alpha}+(:=.*|\\+\\+|--)")) {
			String var = getVarFromExp(exp);
			exp = var+"="+exp;
		}
		else {
			exp = exp.replace(":=", "=");
		}
		try {
			System.out.println(exp);
			engine.eval(exp);
		} catch (ScriptException e) {
			System.err.println("Expression mal formée");
		}
	}

	/**
	 * Evalue une expression
	 * @param cond condition
	 * @return le résultat du test
	 */
	public boolean evalCond(String cond) {
		boolean valide = false;
		cond = cond.replaceAll("([^<>])=", "$1==");
		try {
			valide = (Boolean)engine.eval(cond);
		}
		catch (ScriptException e) {
			System.err.println("Condition mal formée");
		}
		return valide;
	}

	private String getVarFromExp(String exp) {
		Pattern p = Pattern.compile("(\\p{Alpha}*).*");
		Matcher m = p.matcher(exp);
		if(!m.matches()) System.err.println("Erreur de syntaxe dans une action");
		return m.group(1);
	}


	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		System.out.println("Test GestionVariables");
		System.out.println("---------------------");

		GestionVariables gestion = new GestionVariables();
		System.out.println("Initialisations");
		gestion.execExp("x:=2");
		System.out.println("x:=2: " + gestion.get("x"));
		gestion.addVariable("valY", 3);
		System.out.println("valY,3: " + gestion.get("valY"));
		gestion.execExp("aze:=x+2");
		System.out.println("aze:=x+2: " + gestion.get("aze"));
		try {
			System.out.println("erreur: " + gestion.get("fsd"));
		}
		catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nConditions");
		System.out.println("x<=2: " + gestion.evalCond("x<=2"));
		System.out.println("x>3: " + gestion.evalCond("x>3"));
		System.out.println("valY>2: " + gestion.evalCond("valY>2"));
		System.out.println("aze=4: " + gestion.evalCond("aze=4"));

		System.out.println("\nActions");
		gestion.execExp("x:=x+2");
		System.out.println("x:=x+2: " + gestion.get("x"));
		gestion.execExp("valY++");
		System.out.println("valY++: " + gestion.get("valY"));
		gestion.execExp("aze+3");
		System.out.println("TODO aze+3: " + gestion.get("aze"));
	}
}
