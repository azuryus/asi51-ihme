package tp1;

// import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
	// ArrayList<Automate> automates = new ArrayList<Automate>();
    // 	for(int i=0; i<args.length; i++)
    // 	    automates.add(new Automate(args[i]));
	Automate auto = new Automate(args[0]);
	auto.run();
    }
}