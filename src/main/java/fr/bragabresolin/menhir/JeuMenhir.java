package fr.bragabresolin.menhir;

import java.util.Collection;
import java.util.Iterator;

public class JeuMenhir {
	/*
	 * (non-javadoc)
	 */
	private boolean estPartieAvancee;

	private Tas<CarteAllie> tasCartesAllies = null;

	private Tas<CarteIngredient> tasCartesIngredients = null;

	/**
	 *
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

	private void genererJoueurs() {

	}

	private void genererTas() {

	}

	private int demanderNombreJoueurs() {

		return 0;

	}

	private boolean demanderModeJeu() {

		return false;

	}

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
