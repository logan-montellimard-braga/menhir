package fr.bragabresolin.menhir.Core;

import java.util.*;
import java.io.*;

import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Partie.*;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe principale contenant la logique du jeu du Menhir.
 * Cette classe est le "chef d'orchestre" d'une partie de jeu du Menhir. Une 
 * fois un jeu instancié, il s'occupe d'instancier et enclencher tous les 
 * composants nécessaires à son fonctionnement (joueurs, cartes, tas, ...).
 * Cette classe est un émetteur de messages.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
 * @see fr.bragabresolin.menhir.Core.Partie.Manche
 * @see fr.bragabresolin.menhir.Core.Saison
 * @see fr.bragabresolin.menhir.Core.Message.Message
 * @see fr.bragabresolin.menhir.Core.Message.MessageType
 */
public class JeuMenhir extends java.util.Observable {

	/**
	 * Permet de savoir quel type de partie on doit jouer : avancée ou classique.
	 */
	private boolean estPartieAvancee;
	
	/**
	 * Représente le tas de cartes allié dans lequel le joueur doit piocher.
	 * Une valeur nulle signifie que le jeu n'a pas encore été initialisé, et 
	 * n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 */
	private Tas<CarteAllie> tasCartesAllies = null;
	
	/**
	 * Représente le tas de cartes ingrédient dans lequel le joueur doit piocher.
	 * Une valeur nulle signifie que le jeu n'a pas encore été initialisé, et 
	 * n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
	 */
	private Tas<CarteIngredient> tasCartesIngredients = null;

	/**
	 * Représente l'ensemble des joueur de la partie.
	 * 
	 * La liste des joueurs contiendra, selon les règles du jeu, entre 2 et 6 
	 * joueurs.
	 * Une valeur nulle signifie que le jeu n'a pas encore été initialisé, et 
	 * n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	private ArrayList<Joueur> joueurs;
	
	/**
	 * Représente la manche en train d'être jouée pour le jeu.
	 * 
	 * Une valeur nulle est possible et signifie qu'aucune manche n'est 
	 * actuellement en train d'être jouée.
	 */
	private Manche manche;

	public JeuMenhir(int nombreJoueurs, String nomJoueur, int ageJoueur, boolean modeAvance) {
		this.estPartieAvancee = modeAvance;
		this.tasCartesAllies = new Tas<CarteAllie>();
		this.tasCartesIngredients = new Tas<CarteIngredient>();
		this.genererJoueurs(nomJoueur, ageJoueur, nombreJoueurs);
		this.genererTas();
		this.manche = null;
	}
	
	public Tas<CarteIngredient> getTasIng() {
		return this.tasCartesIngredients;
	}
	
	public Tas<CarteAllie> getTasAllie() {
		return this.tasCartesAllies;
	}
	
	public Joueur getJoueurPhysique() {
		Iterator<Joueur> it = this.joueurs.iterator();
		Joueur joueur = null;
		while (it.hasNext()) {
			Joueur j = it.next();
			if (j instanceof JoueurPhysique) {
				joueur = j;
				break;
			}
		}
		return joueur;
	}

	/**
	 * Crée les joueurs de la partie.
	 */
	private void genererJoueurs(String nomJoueur, int ageJoueur, int nombreJoueurs) {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new JoueurPhysique(nomJoueur, ageJoueur));
		for (int i = 0; i < nombreJoueurs; i++) {
			int age = 12 + (int) (Math.random() * ((85 - 12) + 1));
			joueurs.add(new JoueurVirtuel("", age));
		}
		Collections.sort(joueurs, new Comparator<Joueur>() {
			public int compare(Joueur a, Joueur b) {
				return a.getAge() < b.getAge() ? -1 : a.getAge() == b.getAge() ? 0 : 1;
			}
		});
		for (int i = 0, j = 1; i < joueurs.size(); i++)
			if (joueurs.get(i) instanceof JoueurVirtuel) {
				joueurs.get(i).setNom("J" + j);
				j++;
			}

		this.joueurs = joueurs;
	}
	
	public void lancerPartie() {
		if (this.estPartieAvancee) {
			int nombreManches = this.joueurs.size();
			for (int i = 0; i < nombreManches; i++) {
				this.tasCartesIngredients.melanger();
				this.tasCartesAllies.melanger();
				this.manche = new MancheAvancee(this.tasCartesIngredients,
						this.tasCartesAllies, this.joueurs);
				this.setChanged();
				this.notifyObservers(new Message(MessageType.DEBUT_PARTIE));
				this.manche.jouer();
				if (i == nombreManches - 1)
					this.manche.nettoyer(false);
				else
					this.manche.nettoyer(true);
			}
		} else {
			this.manche = new Manche(this.tasCartesIngredients, this.joueurs);
			this.setChanged();
			this.notifyObservers(new Message(MessageType.DEBUT_PARTIE));
			this.manche.jouer();
		}
		this.manche.classerJoueurs();

		this.setChanged();
		this.notifyObservers(new Message(MessageType.FIN_PARTIE));
	}

	private void genererTas() {
		this.genererCartesIngredient("cartes/ingredient.txt");
		this.tasCartesIngredients.melanger();

		if (this.estPartieAvancee) {
			this.genererCartesAllie("cartes/chien.txt", CarteAllieChien.class);
			this.genererCartesAllie("cartes/taupe.txt", CarteAllieTaupe.class);
			this.tasCartesAllies.melanger();
		}
	}

	private void genererCartesAllie(String fichierCartes, Class<? extends CarteAllie> type) {
		String chaineCarte = this.lireRessource(fichierCartes);
		String[] cartes = chaineCarte.split("\n");
		for (String carteS : cartes) {
			String[] matrice = carteS.split(" ");
			EnumMap<Saison, Integer> mat = new EnumMap<Saison, Integer>(Saison.class);
			for (Saison s : Saison.values())
				mat.put(s, Integer.parseInt(matrice[s.ordinal()]));

			try {
				CarteAllie carte = (CarteAllie) type.newInstance();
				carte.setMatrice(mat);
				this.tasCartesAllies.ajouterCarte(carte);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			this.tasCartesIngredients.ajouterCarte(carte);
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

	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}
	
	public Manche getMancheEnCours() {
		return this.manche;
	}
}
