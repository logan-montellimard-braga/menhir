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
	 *Représente l'ensemble des joueur de la partie.
	 */
	private Collection<Joueur> joueurs;

	public JeuMenhir() {
		this.tasCartesAllies = new Tas<CarteAllie>();
		this.tasCartesIngredients = new Tas<CarteIngredient>();
	}

	/**
	 * Getter of the property <tt>joueurs</tt>
	 * 
	 * @return Returns the joueurs.
	 * 
	 */
	public Collection<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Returns an iterator over the elements in this collection.
	 * 
	 * @return an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * 
	 */
	public Iterator<Joueur> joueursIterator() {
		return joueurs.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * 
	 * @return <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * 
	 */
	public boolean isJoueursEmpty() {
		return joueurs.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified
	 * element.
	 * 
	 * @param element
	 *            whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * 
	 */
	public boolean containsJoueurs(Joueur joueurs) {
		return this.joueurs.contains(joueurs);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements
	 * in the specified collection.
	 * 
	 * @param elements
	 *            collection to be checked for containment in this
	 *            collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * 
	 */
	public boolean containsAllJoueurs(Collection<Joueur> joueurs) {
		return this.joueurs.containsAll(joueurs);
	}

	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return the number of elements in this collection
	 * @see java.util.Collection#size()
	 * 
	 */
	public int joueursSize() {
		return joueurs.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * 
	 * @return an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * 
	 */
	public Joueur[] joueursToArray() {
		return joueurs.toArray(new Joueur[joueurs.size()]);
	}

	/**
	 * Setter of the property <tt>joueurs</tt>
	 * 
	 * @param joueurs
	 *            the joueurs to set.
	 * 
	 */
	public void setJoueurs(Collection<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	/**
	 * Ensures that this collection contains the specified element (optional
	 * operation).
	 * 
	 * @param element
	 *            whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * 
	 */
	public boolean addJoueurs(Joueur joueurs) {
		return this.joueurs.add(joueurs);
	}

	/**
	 * Removes a single instance of the specified element from this
	 * collection, if it is present (optional operation).
	 * 
	 * @param element
	 *            to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * 
	 */
	public boolean removeJoueurs(Joueur joueurs) {
		return this.joueurs.remove(joueurs);
	}

	/**
	 * Removes all of the elements from this collection (optional
	 * operation).
	 * 
	 * @see java.util.Collection#clear()
	 * 
	 */
	public void clearJoueurs() {
		this.joueurs.clear();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified
	 * element.
	 * 
	 * @param element
	 *            whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * 
	 */
	public boolean containsJoueur(Joueur joueur) {
		return this.joueurs.contains(joueur);
	}

	/**
	 * Ensures that this collection contains the specified element (optional
	 * operation).
	 * 
	 * @param element
	 *            whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * 
	 */
	public boolean addJoueur(Joueur joueur) {
		return this.joueurs.add(joueur);
	}

	/**
	 * Removes a single instance of the specified element from this
	 * collection, if it is present (optional operation).
	 * 
	 * @param element
	 *            to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * 
	 */
	public boolean removeJoueur(Joueur joueur) {
		return this.joueurs.remove(joueur);
	}
	
	/**
	 * Crée les joueurs de la partie.
	 * @see demanderNombreJoueurs
	 */
	private void genererJoueurs() {
		int ageJoueur = this.demanderAge();
		int nombreJoueurs = this.demanderNombreJoueurs();

		List<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new JoueurPhysique(ageJoueur));
		for (int i = 0; i < nombreJoueurs; i++) {
			int age = 12 + (int) (Math.random() * ((85 - 12) + 1));
			joueurs.add(new JoueurVirtuel(age));
		}
		Collections.sort(joueurs, new AgeComparator());

		this.joueurs = joueurs;
	}
	
	private void genererTas() {

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
		this.genererCartes();

		System.out.println("\nOK, on peut démarrer !");
		System.out.println(this.tasCartesIngredients);
		System.out.println(this.tasCartesAllies);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("*** Jeu du Menhir ***".toUpperCase());
		System.out.println("Braga & Bresolin, A2015");

		JeuMenhir jm = new JeuMenhir();
		jm.lancerPartie();
	}

	private void genererCartes() {
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
					effetMat.put(ai, Integer.parseInt(matrice[s.ordinal() * 3 + ai.ordinal()]));
				}
				mat.put(s, effetMat);
			}

			CarteIngredient carte = new CarteIngredient(nomCarte);
			carte.setMatrice(mat);
			this.tasCartesIngredients.add(carte);
		}
	}

	/**
	 * Getter of the property <tt>tasCartesIngredients</tt>
	 * 
	 * @return Returns the tasCartesIngredients.
	 * 
	 */

	public Tas<CarteIngredient> getTasCartesIngredients() {
		return tasCartesIngredients;
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
