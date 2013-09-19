package fr.azuryus.automate;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;

// import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class Main {
    public static void main(String[] args) {
	// ArrayList<Automate> automates = new ArrayList<Automate>();
    // 	for(int i=0; i<args.length; i++)
    // 	    automates.add(new Automate(args[i]));

	String fichier = "";
	boolean debug = false;
	boolean load = false;

	CommandLineParser parser = new GnuParser();
	
	Options options = new Options();
	
	options.addOption("d", "debug", false, "Activer le mode debug");
	options.addOption("l", "load", false, "Charger le fichier de configuration de l'automate puis quitter");
	
	
	if(args.length == 0) {
	    System.err.println("Aucun fichier");
	    System.exit(0);
	}
	if(args.length >= 1)
	    fichier = args[0];
	if(args.length >= 2) {
	    if(args[1].equals("--debug"))
		debug = true;
	    if(args[1].equals("--load")) {
		load = true;
		debug = true;
	    }
	}

	Automate auto = new Automate(fichier);
	auto.setDebug(debug);
	if(!load) auto.run();
    }
}
