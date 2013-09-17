package tp1;

// import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
	// ArrayList<Automate> automates = new ArrayList<Automate>();
    // 	for(int i=0; i<args.length; i++)
    // 	    automates.add(new Automate(args[i]));

	String fichier = "";
	boolean debug = false;
	boolean load = false;

	if(args.length == 1)
	    fichier = args[0];
	else if(args.length >= 2) {
	    if(args[0].equals("--debug"))
		debug = true;
	    if(args[0].equals("--load")) {
		load = true;
		debug = true;
	    }
	    fichier = args[1];
	}

	Automate auto = new MachineCafe(fichier, debug);
	if(!load) auto.run();
    }
}