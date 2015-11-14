package fr.bragabresolin.menhir;

import java.util.*;
import java.io.*;

import fr.bragabresolin.menhir.ActionIngredient;
import fr.bragabresolin.menhir.Saison;

public class JeuMenhir {

	private static final String HEADER = "\n"
									   + "****** JEU DU MENHIR ******\n"
									   + "* Braga & Bresolin, A2015 *\n"
								   	   + "***************************";

	/**
	 * Permet de savoir quel type de partie on doit jouer : avancée ou classique.
	 */
	private boolean estPartieAvancee;
	
	/**
	 * Représente le tas de cartes alliées dans lequel le joueur doit piocher.
	 */
	private Tas<CarteAllie> tasCartesAllies = null;
	
	/**
	 * Représente le tas de cartes ingrédients dans lequel le joueur doit piocher.
	 */
	private Tas<CarteIngredient> tasCartesIngredients = null;

	/**
	 * Représente l'ensemble des joueur de la partie.
	 */
	private List<Joueur> joueurs;

	public JeuMenhir() {
		this.tasCartesAllies = new Tas<CarteAllie>();
		this.tasCartesIngredients = new Tas<CarteIngredient>();
		List<Joueur> joueurs = new ArrayList<Joueur>();
	}

	/**
	 * Crée les joueurs de la partie.
	 * @see demanderNombreJoueurs
	 */
	private void genererJoueurs() {
		String nomJoueur = this.demanderNomJoueur();
		int ageJoueur = this.demanderAge();
		int nombreJoueurs = this.demanderNombreJoueurs();

		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new JoueurPhysique(nomJoueur, ageJoueur));
		for (int i = 0; i < nombreJoueurs; i++) {
			int age = 12 + (int) (Math.random() * ((85 - 12) + 1));
			joueurs.add(new JoueurVirtuel("", age));
		}
		Collections.sort(joueurs, new AgeComparator());
		for (int i = 0, j = 1; i < joueurs.size(); i++)
			if (joueurs.get(i) instanceof JoueurVirtuel) {
				joueurs.get(i).setNom("J" + j);
				j++;
			}

		this.joueurs = joueurs;
	}
	
	private String demanderNomJoueur() {
		String input = "";
		Scanner reader = new Scanner(System.in);
		while (input.equals("")) {
			this.afficherPrompt("Quel est votre nom ?");
			input = reader.next();
		}
		return input;
	}
	
	/**
	 * Permet de connaitre le nombre de joueurs virtuels pour la partie.
	 * @return le nombre de joueur virtuel que le joueur physique à choisi.
	 */
	private int demanderNombreJoueurs() {
		int nombre = 0;
		Scanner reader = new Scanner(System.in);
		while (nombre < 1 || nombre > 5) {
			this.afficherPrompt("Avec combien de joueurs virtuels souhaitez-vous jouer ? [1-5]");
			try {
				nombre = reader.nextInt();
			} catch (Exception e) {
				System.out.println("Veuillez saisir un nombre valide.");
				reader.nextLine();
			}
		}
		return nombre;
	}
	
	/**
	 * Permet de connaitre le type de partie que le joueur souhaite jouer.
	 * @see estPartieAvancee
	 * @return true si la partie est une partie avancée.
	 */
	private boolean demanderSiPartieAvancee() {
		String input = "";
		Scanner reader = new Scanner(System.in);
		while (!input.equals("o") && !input.equals("n") && !input.equals("y")) {
			this.afficherPrompt("Voulez-vous jouer en mode partie avancée ? [o/n]");
			input = reader.next();
			input = input.toLowerCase();
		}
		if (input.equals("y") || input.equals("o")) return true;
		else return false;
	}
	
	/**
	 * 
	 * @return
	 */
	private int demanderAge() {
		int age = 0;
		Scanner reader = new Scanner(System.in);
		while (age < 1) {
			this.afficherPrompt("Quel âge avez-vous ?");
			try {
				age = reader.nextInt();
			} catch (Exception e) {
				System.out.println("Veuillez saisir un nombre valide.");
				reader.nextLine();
			}
		}
		if (age < 8) {
			System.out.println("Le jeu est accessible à partir de 8 ans !");
			System.exit(0);
		}
		return age;
	}

	private void afficherPrompt(String prompt) {
		System.out.println("");
		System.out.println(prompt);
		System.out.print(">> ");
	}

	public void lancerPartie() {
		this.genererJoueurs();
		this.estPartieAvancee = this.demanderSiPartieAvancee();
		this.genererTas();

		System.out.println("\nOK, on peut démarrer !");
		System.out.println("  > " + this.joueurs.size() + " joueurs, partie " + (this.estPartieAvancee ? "avancée" : "rapide") + ".");
	}

	private void recupererCartes(Joueur joueur) {
		List<Carte> cartes = joueur.rendreCartes();
		for (Carte carte : cartes) {
			if (carte instanceof CarteIngredient)
				this.tasCartesIngredients.push((CarteIngredient) carte);
			else if (carte instanceof CarteAllie)
				this.tasCartesAllies.push((CarteAllie) carte);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("\n\nFermeture du jeu...");
				System.out.println("À la prochaine !\n");
			}
		});

		System.out.println(HEADER);

		JeuMenhir jm = new JeuMenhir();
		jm.lancerPartie();
	}

	private void genererTas() {
		this.genererCartesIngredient("cartes/ingredient.txt");
		this.tasCartesIngredients.melanger();

		if (this.estPartieAvancee) {
			this.genererCartesAllie("cartes/chien.txt", ActionAllie.CHIEN);
			this.genererCartesAllie("cartes/taupe.txt", ActionAllie.TAUPE);
			this.tasCartesAllies.melanger();
		}
	}

	private void genererCartesAllie(String fichierCartes, ActionAllie action) {
		String chaineCarte = this.lireRessource(fichierCartes);
		String[] cartes = chaineCarte.split("\n");
		for (String carteS : cartes) {
			String[] matrice = carteS.split(" ");
			EnumMap<Saison, Integer> mat = new EnumMap<Saison, Integer>(Saison.class);
			for (Saison s : Saison.values())
				mat.put(s, Integer.parseInt(matrice[s.ordinal()]));

			CarteAllie carte = new CarteAllie(action);
			carte.setMatrice(mat);
			this.tasCartesAllies.add(carte);
		}
	}

	private void genererCartesIngredient(String fichierCartes) {
		String chaineCarte = this.lireRessource(fichierCartes);
		String[] cartes = chaineCarte.split("\n");
		for (String carteS : cartes) {
			String[] parties = carteS.split("@");
			String nomCarte = parties[1];
			String[] matrice = parties[0].split(" ");

			EnumMap<Saison, EnumMap<ActionIngredient, Integer>> mat = new EnumMap<Saison, EnumMap<ActionIngredient, Integer>>(Saison.class);
			for (Saison s : Saison.values()) {
				EnumMap<ActionIngredient, Integer> effetMat = new EnumMap<ActionIngredient, Integer>(ActionIngredient.class);
				for (ActionIngredient ai : ActionIngredient.values()) {
					effetMat.put(ai, Integer.parseInt(matrice[s.ordinal() * ActionIngredient.values().length + ai.ordinal()]));
				}
				mat.put(s, effetMat);
			}

			CarteIngredient carte = new CarteIngredient(nomCarte);
			carte.setMatrice(mat);
			this.tasCartesIngredients.add(carte);
		}
	}

	private String lireRessource(String fileName) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(fileName);

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null)
				sb.append(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}


	class AgeComparator implements Comparator<Joueur> {
		public int compare(Joueur a, Joueur b) {
			return a.getAge() < b.getAge() ? -1 : a.getAge() == b.getAge() ? 0 : 1;
		}
	}


	class ScoreComparator implements Comparator<Joueur> {
		public int compare(Joueur a, Joueur b) {
			int scoreA = a.getPoints();
			int scoreB = b.getPoints();

			if (scoreA < scoreB) {
				return -1;
			} else if (scoreA > scoreB) {
				return 1;
			} else {
				int grainesA = a.getNombreGraines();
				int grainesB = b.getNombreGraines();
				return grainesA < grainesB ? -1 : grainesA == grainesB ? 0 : 1;
			}
		}
	}
}
