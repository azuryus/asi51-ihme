package fr.azuryus.automate;


public class Main {
    public static void main(String[] args) {
	// ArrayList<Automate> automates = new ArrayList<Automate>();
    // 	for(int i=0; i<args.length; i++)
    // 	    automates.add(new Automate(args[i]));

	String fichier = "";
	boolean debug = false;
	boolean load = false;

	
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

	Automate auto = new Automate(fichier, debug);
	if(!load) auto.run();
    }
}
