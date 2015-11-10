package fr.bragabresolin.menhir;

import java.util.Collection;
import java.util.Iterator;
import java.util.EnumMap;

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
	 * crée les joureur virtuels et le joueur physique pour la partie.
	 * @see demanderNombreJoueurs
	 */
	private void genererJoueurs() {

	}
	
	private void genererTas() {

	}
	
	/**
	 * Permet de connaitre le nombre de joueurs virtuels pour la partie.
	 * @return le nombre de joueur virtuel que le joueur physique à choisi.
	 */
	private int demanderNombreJoueurs() {

		return 0;

	}
	
	/**
	 * Permet de connaitre le type de partie que le joueur souhaite jouer.
	 * @see estPartieAvancee
	 * @return true si la partie est une partie avancée.
	 */
	private boolean demanderSiPartieAvancee() {

		return false;

	}
	
	/**
	 * 
	 * @return
	 */
	private int demanderAge() {

		return 0;

	}

	public void lancerPartie() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CarteIngredient c = new CarteIngredient();
		c.setNomCarte("Poussière de fée");

		EnumMap<Saison, EnumMap<ActionIngredient, Integer>> m = new EnumMap<Saison, EnumMap<ActionIngredient, Integer>>(Saison.class);

		EnumMap<ActionIngredient, Integer> mp = new EnumMap<ActionIngredient, Integer>(ActionIngredient.class);
		mp.put(ActionIngredient.GEANT, 2);
		mp.put(ActionIngredient.FARFADET, 3);
		mp.put(ActionIngredient.ENGRAIS, 1);

		EnumMap<ActionIngredient, Integer> me = new EnumMap<ActionIngredient, Integer>(ActionIngredient.class);
		me.put(ActionIngredient.GEANT, 0);
		me.put(ActionIngredient.FARFADET, 2);
		me.put(ActionIngredient.ENGRAIS, 3);

		EnumMap<ActionIngredient, Integer> ma = new EnumMap<ActionIngredient, Integer>(ActionIngredient.class);
		ma.put(ActionIngredient.GEANT, 1);
		ma.put(ActionIngredient.FARFADET, 4);
		ma.put(ActionIngredient.ENGRAIS, 0);

		EnumMap<ActionIngredient, Integer> mh = new EnumMap<ActionIngredient, Integer>(ActionIngredient.class);
		mh.put(ActionIngredient.GEANT, 3);
		mh.put(ActionIngredient.FARFADET, 1);
		mh.put(ActionIngredient.ENGRAIS, 1);

		m.put(Saison.PRINTEMPS, mp);
		m.put(Saison.ETE, me);
		m.put(Saison.AUTOMNE, ma);
		m.put(Saison.HIVER, mh);
		c.setMatrice(m);
		System.out.println(c);

		CarteAllie c2 = new CarteAllie(ActionAllie.CHIEN);
		EnumMap<Saison, Integer> m2 = new EnumMap<Saison, Integer>(Saison.class);
		m2.put(Saison.PRINTEMPS, 2);
		m2.put(Saison.ETE, 3);
		m2.put(Saison.AUTOMNE, 1);
		m2.put(Saison.HIVER, 0);
		c2.setMatrice(m2);
		System.out.println(c2);

		JoueurPhysique j = new JoueurPhysique();
		System.out.println(j);

		System.out.println("");

		JoueurVirtuel j2 = new JoueurVirtuel();
		j2.setComportementStrategy(new VoleurStrategy());
		System.out.println(j2);
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

}
