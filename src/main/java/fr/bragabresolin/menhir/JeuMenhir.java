package fr.bragabresolin.menhir;

import java.util.*;
import java.io.*;

import fr.bragabresolin.menhir.ActionIngredient;
import fr.bragabresolin.menhir.Saison;

public class JeuMenhir {

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
	private ArrayList<Joueur> joueurs;

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
		String nomJoueur = CLIUtils.demanderString("Quel est votre nom ?");
		int ageJoueur = CLIUtils.demanderNombre("Quel âge avez-vous ?", 8, 150);
		int nombreJoueurs = CLIUtils.demanderNombre("Avez combien de joueurs virtuels souhaitez-vous jouer ?", 1, 5);

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
	
	public void lancerPartie() {
		this.genererJoueurs();
		this.estPartieAvancee = CLIUtils.demanderBool("Voulez-vous jouer en mode partie avancée ?");
		this.genererTas();

		CLIUtils.infoBox("Ok, on peut démarrer !" + "\n"
				+ "> "+ this.joueurs.size() + " joueurs, partie "
				+ (this.estPartieAvancee ? "avancée" : "rapide") + ".");

		int nombreManches = this.estPartieAvancee ? this.joueurs.size() : 1;
		for (int i = 0; i < nombreManches ; i++) {

			// Chaque joueur pioche 4 cartes ingrédient
			for (Joueur j : this.joueurs) {
				j.piocherCartes(this.tasCartesIngredients, 4);
				if (this.estPartieAvancee) {
					if (j.veutPiocherCarteAllie())
						j.piocherCartes(this.tasCartesAllies, 1);
					else
						j.augmenterGraines(2);
				}
			}

			// Pour chaque saison, on a un tour
			for (Saison saison : Saison.values()) {
				// Un tour se joue pour chaque joueur
				for (Joueur j : this.joueurs) {
					j.jouer(this.joueurs, saison);
				}
			}
			// On shift l'ordre des joueurs pour la manche suivante
			Joueur shiftJoueur = this.joueurs.remove(this.joueurs.size() - 1);
			this.joueurs.add(0, shiftJoueur);

			// Chaque joueur sauve ses points, vide son Champ, et rend ses 
			// cartes
			for (Joueur j : this.joueurs) {
				j.sauverPoints();
				j.reinitialiserChamp();
				this.recupererCartes(j.rendreCartes());
			}
		}
	}

	private void recupererCartes(List<Carte> cartes) {
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

		CLIUtils.afficherHeader();

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
