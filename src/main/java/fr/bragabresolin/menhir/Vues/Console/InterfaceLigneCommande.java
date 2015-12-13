package fr.bragabresolin.menhir.Vues.Console;

import java.util.*;
import java.io.*;

import fr.bragabresolin.menhir.Core.JeuMenhir;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Vues.Vue;
import fr.bragabresolin.menhir.Core.Message.*;

public class InterfaceLigneCommande implements Observer, Vue {

	private JeuMenhir jeu;
	private static InterfaceLigneCommande instance;

	private static final String SIGNE_PROMPT = ">> ";
	private static final String SEPARATEUR = "└─────────────────────────┘";
	private static final String HEADER     = "┌───── JEU DU MENHIR ─────┐\n"
									       + "│ Braga & Bresolin, A2015 │\n"
									       + SEPARATEUR;

	private Scanner reader;

	private InterfaceLigneCommande() { 
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("\n\nFermeture du jeu...");
				System.out.println("À la prochaine !\n");
			}
		});

		this.reader = new Scanner(System.in);
	}

	public static InterfaceLigneCommande getInstance() {
		if (InterfaceLigneCommande.instance == null)
			InterfaceLigneCommande.instance = new InterfaceLigneCommande();
		return InterfaceLigneCommande.instance;
	}

	public void lancer() {
		this.afficherHeader();
		this.demarrerJeu();
	}

	public String demanderString(String prompt) {
		String input = "";
		this.afficherPrompt(prompt);
		input = this.reader.next();

		return input;
	}

	public void update(Observable o, Object message) {
		if (message instanceof Message)
			System.out.println(((Message) message).getType());
		else
			this.infoBox("--> " + message);
	}

	public int demanderNombre(String prompt, int min, int max) {
		int nombre = 0;
		while (nombre < min || nombre > max) {
			this.afficherPrompt(prompt + " [" + min + "-" + max + "]");
			try {
				nombre = this.reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Veuillez saisir un nombre valide.");
				this.reader.nextLine();
			}
		}
		return nombre;
	}

	public boolean demanderBool(String prompt) {
		String input = "";
		while (!input.equals("o") && !input.equals("oui")
				&& !input.equals("n") && !input.equals("non")
				&& !input.equals("y")) {
			input = this.demanderString(prompt + " [o/n]");
			input = input.toLowerCase();
		}
		if (input.equals("y") || input.equals("o") || input.equals("oui"))
			return true;
		return false;
	}

	private void afficherPrompt(String prompt) {
		System.out.println("");
		System.out.println(prompt);
		System.out.print(SIGNE_PROMPT);
	}

	public void afficherHeader() {
		System.out.println("");
		System.out.println(HEADER);
	}

	public static void infoBox(String message) {
		System.out.println("");
		System.out.println(message);
		System.out.println("");
	}

	private void afficherGagnants() {
		ArrayList<Joueur> joueurs = this.jeu.getJoueurs();
		ListIterator<Joueur> it = joueurs.listIterator();

		this.infoBox("Partie terminée ! Voici le classement des joueurs :");
		while (it.hasNext()) {
			Joueur joueur = it.next();
			System.out.println(it.nextIndex() + " -- " +
					joueur.getNom() + " (" + joueur.getPoints() + " points)");
		}
	}


	private void demarrerJeu() {
		String nomJoueur = this.demanderString("Quel est votre nom ?");
		int ageJoueur = this.demanderNombre("Quel âge avez-vous ?", 8, 150);
		int nombreJoueurs = this.demanderNombre("Avez combien de joueurs virtuels souhaitez-vous jouer ?", 1, 5);
		boolean avancee = this.demanderBool("Voulez-vous jouer en mode partie avancée ?");

		this.jeu = new JeuMenhir(nombreJoueurs, nomJoueur, ageJoueur, avancee);
		this.jeu.registerObserver(this);
		this.infoBox("Ok, on peut démarrer !" + "\n"
				+ "> "+ (nombreJoueurs + 1) + " joueurs, partie "
				+ (avancee ? "avancée" : "rapide") + ".");

		this.jeu.lancerPartie();
		this.afficherGagnants();
	}
}
