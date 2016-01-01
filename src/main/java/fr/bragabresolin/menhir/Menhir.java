package fr.bragabresolin.menhir;

import fr.bragabresolin.menhir.Vues.Vue;
import fr.bragabresolin.menhir.Vues.Console.InterfaceLigneCommande;
import fr.bragabresolin.menhir.Vues.GUI.VueMenhir;

/**
 * Cette classe est le point d'entrée du programme du jeu du Menhir.
 * Elle contient la logique de démarrage du jeu selon les réglages et 
 * l'interface demandée.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 */
public class Menhir {
	
	/**
	 * Point d'entrée du jeu du Menhir.
	 *
	 * Détermine quel type d'interface lancer selon les arguments passés par 
	 * l'utilisateur. On considère que c'est l'interface graphique qui doit être
	 * lancée par défaut, sauf indication contraire.
	 * 
	 * @param args Tableau des arguments passés à l'application
	 */
	public static void main(String[] args) {
		boolean modeConsole = false;
		if (args.length != 0) {
			if (args[0].equals("console") || args[0].equals("--console") || args[0].equals("-c"))
				modeConsole = true;
		}

		Vue vue = null;

		if (modeConsole) vue = new InterfaceLigneCommande();
		else vue = new VueMenhir();

		vue.lancer();
	}

}
