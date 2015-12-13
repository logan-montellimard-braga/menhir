package fr.bragabresolin.menhir;

import fr.bragabresolin.menhir.Vues.Vue;
import fr.bragabresolin.menhir.Vues.Console.InterfaceLigneCommande;
import fr.bragabresolin.menhir.Vues.GUI.VueMenhir;

public class Menhir {
	
	public static void main(String[] args) {
		boolean modeConsole = false;
		if (args.length != 0) {
			if (args[0].equals("console") || args[0].equals("--console") || args[0].equals("-c"))
				modeConsole = true;
		}

		Vue vue = null;

		if (modeConsole) vue = InterfaceLigneCommande.getInstance();
		else vue = new VueMenhir();

		vue.lancer();
	}

}
