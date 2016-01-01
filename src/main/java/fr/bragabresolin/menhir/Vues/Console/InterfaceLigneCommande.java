package fr.bragabresolin.menhir.Vues.Console;

import java.util.*;

import fr.bragabresolin.menhir.Core.JeuMenhir;
import fr.bragabresolin.menhir.Core.JeuMenhirThread;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.ActionIngredient;
import fr.bragabresolin.menhir.Core.Cartes.Carte;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllieChien;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllieTaupe;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Vues.Vue;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Vue textuelle (en ligne de commande) pouvant être connectée au jeu du Menhir.
 * Cette classe implémente un affichage textuel pour le jeu du Menhir.
 * L'implémentation est "bloquante" : le joueur ne peut intéragir avec 
 * l'interface que lorsqu'elle le lui permet explicitement.
 * Une implémentation future pourrait permettre de s'affranchir de cette 
 * contrainte en acceptant une entrée clavier permanente traitée en 
 * conséquences.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Vues.Vue
 */
public class InterfaceLigneCommande implements Vue {

	/**
	 * Représente le jeu actuellement observé par l'interface.
	 * 
	 * @see fr.bragabresolin.menhir.Core.JeuMenhir
	 */
	private JeuMenhir jeu;

	/**
	 * Représente le numéro de manche actuellement jouée.
	 * 
	 * TODO: à déprécier ; devrait être gardé dans le jeu
	 */
	private int mancheActuelle;

	/**
	 * Constante d'affichage utilisée pour signifier que l'utilisateur peut 
	 * saisir une entrée clavier.
	 */
	private static final String SIGNE_PROMPT = ">> ";

	/**
	 * Constante d'affichage utilisée pour représenter un séparateur de boîte 
	 * UTF-8.
	 */
	private static final String SEPARATEUR = "└─────────────────────────┘";

	/**
	 * Constante d'affichage utilisée pour représenter l'en-tête affiché lors du 
	 * lancement du jeu.
	 */
	private static final String HEADER     = "┌───── JEU DU MENHIR ─────┐\n"
									       + "│ Braga & Bresolin, A2015 │\n"
									       + SEPARATEUR;

	/**
	 * Représente le Scanner utilisé pour lire l'entrée clavier.
	 */
	private Scanner reader;

	/**
	 * Constructeur.
	 *
	 * Initialise l'interface textuelle en ajoutant l'écouteur d'événément de 
	 * fin de programme et en initialisant les outils nécessaires à la 
	 * lecture/écriture sur l'entrée/sortie standard.
	 */
	public InterfaceLigneCommande() { 
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("\n\nFermeture du jeu...");
				System.out.println("À la prochaine !\n");
			}
		});

		this.mancheActuelle = 0;
		this.reader = new Scanner(System.in);
	}

	/**
	 * Démarre l'affichage de la vue.
	 * 
	 * On affiche l'en-tête textuelle, puis on lance le jeu.
	 */
	public void lancer() {
		this.afficherHeader();
		this.demarrerJeu();
	}

	/**
	 * Demande à l'utilisateur une chaîne de caractère.
	 *
	 * Lit une chaîne de caractères (jusqu'au caractère d'entrée) en affichant
	 * le message fournit.
	 * 
	 * @param prompt Le message à afficher avant la saisie
	 * @return La chaîne de caractères lue
	 */
	public String demanderString(String prompt) {
		String input = "";
		this.afficherPrompt(prompt);
		input = this.reader.next();

		return input;
	}

	/**
	 * Demande à l'utilisateur un entier.
	 *
	 * Lit un entier compris entre les bornes min et max (bornes non strictes)
	 * en affichant le message fournit.
	 * Tant que l'entier est en dehors des bornes, ou que la lecture n'est pas 
	 * un entier valide, la saisie est re-demandée.
	 * 
	 * @param prompt Le message à afficher avant la saisie
	 * @param min L'entier minimal que l'utilisateur doit saisir
	 * @param max L'entier maximal que l'utilisateur doit saisir
	 * @return L'entier lu
	 */
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

	/**
	 * Demande à l'utilisateur une réponse booléenne.
	 *
	 * Lit une réponse oui/non en affichant le message fournit.
	 * Tant que l'entrée n'est pas "oui", "non", "o", "n" ou "y", la saisie est 
	 * re-demandée.
	 * 
	 * @param prompt Le message à afficher avant la saisie
	 * @return L'équivalent booléen de l'entrée lue
	 */
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

	/**
	 * Affiche un prompt de saisie.
	 *
	 * Affiche le message fourni, suivi d'une chaîne de prompt permettant de 
	 * faire comprendre à l'utilisateur qu'il doit saisir une entrée au clavier.
	 * 
	 * @param prompt Le message à afficher avant la saisie
	 */
	private void afficherPrompt(String prompt) {
		System.out.println("");
		System.out.println(prompt);
		System.out.print(SIGNE_PROMPT);
	}

	/**
	 * Affiche l'en-tête du programme dans la sortie standard.
	 */
	public void afficherHeader() {
		System.out.println("");
		System.out.println(HEADER);
	}

	/**
	 * Affiche le message de façon claire.
	 *
	 * Simple wrapper pour ahérer l'affichage de messages importants.
	 * Une implémentation alternative peut, par exemple, afficher le message 
	 * dans une boîte ASCII.
	 * 
	 * @param message Le message à afficher
	 */
	public void infoBox(String message) {
		System.out.println("");
		System.out.println(message);
		System.out.println("");
	}

	/**
	 * Affiche les joueurs de la partie dans l'ordre de victoire avec leur 
	 * position.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
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


	/**
	 * Démarre le jeu du Menhir.
	 *
	 * Initialise le jeu du Menhir en posant les questions de configuration à 
	 * l'utilisateur, et en enregistrant les écouteurs d'événéments nécessaires 
	 * aux composants du jeu.
	 * 
	 * @see fr.bragabresolin.menhir.Core.JeuMenhir
	 */
	private void demarrerJeu() {
		String nomJoueur = this.demanderString("Quel est votre nom ?");
		int ageJoueur = this.demanderNombre("Quel âge avez-vous ?", 8, 150);
		int nombreJoueurs = this.demanderNombre("Avez combien de joueurs virtuels souhaitez-vous jouer ?", 1, 5);
		boolean avancee = this.demanderBool("Voulez-vous jouer en mode partie avancée ?");

		this.jeu = new JeuMenhirThread(nombreJoueurs, nomJoueur, ageJoueur, avancee);
		
		Iterator<Joueur> itj = this.jeu.getJoueurs().iterator();
		while (itj.hasNext()) {
			itj.next().addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						switch (((Message) message).getType()) {
						case JOUEUR_DEBUT_TOUR:
							Joueur j = (Joueur) o;
							InterfaceLigneCommande.this.infoBox("Tour de " + j.getNom());
							break;
						default:
							break;
						}	
					}
				}
			});
		}
		
		this.jeu.addObserver(new Observer() {
			public void update(Observable o, Object message) {
				switch (((Message) message).getType()) {
				case DEBUT_PARTIE:
					InterfaceLigneCommande.this.infoBox("Ok, on peut démarrer !" + "\n"
							+ "> "+ (jeu.getJoueurs().size()) + " joueurs");
					InterfaceLigneCommande.this.observerManches();
					break;
				case FIN_PARTIE:
					InterfaceLigneCommande.this.afficherGagnants();
					break;
				default:
					break;
				}
			}
		});
		
		this.observerJoueurPhysique();
		this.observerCartes();
		
		this.jeu.lancerPartie();
	}
	
	/**
	 * Enregistre un écouteur d'événément pour toutes les cartes du jeu.
	 *
	 * Enregistre un écouteur d'événement sur les cartes du jeu, qui permet 
	 * d'afficher dans la console un message personnalisé en fonction de 
	 * l'action réalisée par la carte lorsqu'elle a été exécutée.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 */
	private void observerCartes() {
		Iterator<CarteIngredient> itc = this.jeu.getTasIng().iterator();
		while (itc.hasNext()) {
			CarteIngredient carte = itc.next();
			carte.addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						Message mes = (Message) message;
						switch (mes.getType()) {
						case CARTE_EXEC:
							CarteIngredient c = (CarteIngredient) o;
							switch (c.getAction()) {
							case GEANT:
								System.out.println(c.getOrigine().getNom() + " récupère " + (Integer) mes.getBody() + " graines.");
								break;
							case ENGRAIS:
								System.out.println(c.getOrigine().getNom() + " fait pousser " + (Integer) mes.getBody() + " menhirs.");
								break;
							case FARFADET:
								System.out.println(c.getOrigine().getNom() + " vole " + (Integer) mes.getBody() + " graines à " + c.getCible().getNom() + ".");
								break;
							}
							break;
						default:
							break;
						}
					}
				}
			});
		}
		
		Iterator<CarteAllie> itca = this.jeu.getTasAllie().iterator();
		while(itca.hasNext()) {
			CarteAllie carte = itca.next();
			carte.addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						Message mes = (Message) message;
						switch (mes.getType()) {
						case CARTE_EXEC:
							CarteAllie c = (CarteAllie) o;
							if (c instanceof CarteAllieChien) {
								System.out.println(c.getOrigine().getNom() + " appelle un chien qui protège " + (Integer) mes.getBody() + " graines.");
							} else if (c instanceof CarteAllieTaupe) {
								System.out.println(c.getOrigine().getNom() + " appelle une taupe géante qui détruit " + (Integer) mes.getBody() + " menhirs de " + c.getCible().getNom());
							}
						default:
							break;
						}
					}
				}
			});
		}
	}
	
	/**
	 * Enregistre un écouteur d'événément sur la manche en cours.
	 *
	 * Enregistre un écouteur d'événement sur la manche en cours afin d'afficher 
	 * dans la console les informations de changement de saison et de manche.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Partie.Manche
	 */
	private void observerManches() {
		this.jeu.getMancheEnCours().addObserver(new Observer() {
			public void update(Observable o, Object message) {
				Message mes = (Message) message;
				switch (mes.getType()) {
				case DEBUT_SAISON:
					InterfaceLigneCommande.this.infoBox("Saison : " + ((Saison) mes.getBody()).toString());
					break;
				case DEBUT_MANCHE:
					mancheActuelle++;
					InterfaceLigneCommande.this.infoBox("Manche " + mancheActuelle);
				default:
				}
			}
		});
	}
	
	/**
	 * Enregistre un écouteur d'événément sur le joueur physique de la partie.
	 *
	 * Enregistre un écouteur d'événement sur le joueur réel du jeu afin de 
	 * pouvoir communiquer avec le coeur du jeu lorsqu'une action de 
	 * l'utilisateur est nécessaire (réponse booléenne, choix de carte, ...).
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.JoueurPhysique
	 * @see fr.bragabresolin.menhir.Core.Message.TamponCarte
	 * @see fr.bragabresolin.menhir.Core.Message.TamponBooleen
	 */
	private void observerJoueurPhysique() {
		this.jeu.getJoueurPhysique().addObserver(new Observer() {
			public void update(Observable o, Object message) {
				if (message instanceof Message) {
					Message mes = (Message) message;
					switch(mes.getType()) {
					case JOUEUR_CHOIX_JOUER_ING:
						InterfaceLigneCommande.this.infoBox("C'est à votre tour ! Choisissez une carte ingrédient.");
						System.out.println("Rappel de votre situation :");
						System.out.println(InterfaceLigneCommande.this.jeu.getJoueurPhysique());
						CarteIngredient carteIng = InterfaceLigneCommande.this.demanderCarteIng();
						TamponCarte.getInstance().deposerCarte(carteIng);
						break;
					case JOUEUR_CHOIX_JOUER_ALLIE:
						if (!InterfaceLigneCommande.this.jeu.getJoueurPhysique().carteAllieDispo()) {
							TamponCarte.getInstance().setIgnorer(true);
							break;
						}
						
						boolean veutJouerAllie = InterfaceLigneCommande.this.demanderBool("Voulez-vous jouer une carte allié ?");
						if (veutJouerAllie) {
							CarteAllie carteAllie = InterfaceLigneCommande.this.demanderCarteAllie();
							TamponCarte.getInstance().deposerCarte(carteAllie);
						} else {
							TamponCarte.getInstance().setIgnorer(true);
						}
						break;
					case JOUEUR_CHOIX_PIOCHER_ALLIE:
						boolean veutAllie = InterfaceLigneCommande.this.demanderBool("Voulez-vous piocher une carte allié ? Si non, vous obtiendrez 2 graines");
						TamponBooleen.getInstance().deposerBool(veutAllie);
						break;
					default:
						break;
					}
				}

			}
		});
	}
	
	/**
	 * Demande à l'utilisateur de choisir une carte ingrédient.
	 *
	 * Les cartes de l'utilisateur lui sont proposées, afin qu'il fasse son 
	 * choix. Suivant la carte choisie, d'autres informations lui sont 
	 * demandées.
	 * 
	 * @return La carte ingrédient choisie par le joueur
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	private CarteIngredient demanderCarteIng() {
		Joueur joueur = this.jeu.getJoueurPhysique();
		List<Carte> cartes = joueur.getCartes();
		
		List<CarteIngredient> cartesIng = new ArrayList<CarteIngredient>();
		
		for (int i = 0, j = 1; i < cartes.size(); i++) {
			if (!cartes.get(i).getDejaJouee() && cartes.get(i) instanceof CarteIngredient) {
				cartesIng.add((CarteIngredient) cartes.get(i));
				System.out.println(j + ")\n" + cartes.get(i) + "\n");
				j++;
			}
		}
		
		int choix = this.demanderNombre("Quelle carte voulez-vous jouer ?",	1, cartesIng.size());
		CarteIngredient carteChoisie = cartesIng.get(choix - 1);
		System.out.println("Vous avez choisi la carte " + carteChoisie.getNomCarte() + ".");
		
		System.out.print("\nActions disponibles : ");
		for (ActionIngredient ai : ActionIngredient.values())
			System.out.print(" (" + (ai.ordinal() + 1) + ") " + ai + "  ");
		int choixAction = this.demanderNombre("Quelle action voulez-vous jouer ?", 1, ActionIngredient.values().length);
		ActionIngredient action = ActionIngredient.values()[choixAction - 1];
		System.out.println("Vous avez choisi l'effet " + action + ".");
		
		carteChoisie.setAction(action);
		
		if (action == ActionIngredient.FARFADET) {
			System.out.println("\n\nVoici les joueurs de cette partie :\n");
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++)
				System.out.println((i + 1) + ") " + this.jeu.getJoueurs().get(i) + "\n");

			int numSelf = this.jeu.getJoueurs().indexOf(joueur);
			int numJoueur = numSelf + 1;
			while (numSelf == (numJoueur - 1)) {
				numJoueur = this.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, this.jeu.getJoueurs().size());
			}
			Joueur cible = this.jeu.getJoueurs().get(numJoueur - 1);
			System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
			carteChoisie.setCible(cible);
		}
		
		return carteChoisie;
	}
	
	/**
	 * Demande à l'utilisateur de choisir une carte allié.
	 *
	 * Les cartes de l'utilisateur lui sont proposées, afin qu'il fasse son 
	 * choix. Suivant la carte choisie, d'autres informations lui sont 
	 * demandées.
	 * 
	 * @return La carte allié choisie par le joueur
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllieTaupe
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllieChien
	 */
	private CarteAllie demanderCarteAllie() {
		Joueur joueur = this.jeu.getJoueurPhysique();
		
		List<CarteAllie> cartesAllie = new ArrayList<CarteAllie>();
		for (Carte carte : joueur.getCartes())
			if (!carte.getDejaJouee() && carte instanceof CarteAllie)
				cartesAllie.add((CarteAllie) carte);

		if (cartesAllie.size() == 0) return null;

		System.out.println("Voici vos cartes allié en main :");
		for (Carte carte : cartesAllie)
			System.out.println((cartesAllie.indexOf(carte) + 1) + ")\n" + carte + "\n");
		
		int choix = this.demanderNombre("Quelle carte voulez-vous jouer ?",	1, cartesAllie.size());
		CarteAllie carteChoisie = cartesAllie.get(choix - 1);
		
		if (carteChoisie instanceof CarteAllieTaupe) {
			System.out.println("\n\nVoici les joueurs de cette partie :\n");
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++)
				System.out.println((i + 1) + ") " + this.jeu.getJoueurs().get(i) + "\n");

			int numSelf = this.jeu.getJoueurs().indexOf(joueur);
			int numJoueur = numSelf + 1;
			while (numSelf == (numJoueur - 1)) {
				numJoueur = this.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, this.jeu.getJoueurs().size());
			}
			Joueur cible = this.jeu.getJoueurs().get(numJoueur - 1);
			System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
			carteChoisie.setCible(cible);
		}

		return carteChoisie;
	}
}
