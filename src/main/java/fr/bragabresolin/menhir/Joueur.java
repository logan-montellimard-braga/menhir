package fr.bragabresolin.menhir;

import java.util.Collection;
import java.util.Iterator;

public abstract class Joueur {

	/*
	 * (non-javadoc)
	 */
	private int age;
	private int points;
	private int nombreGraines;
	private int nombreMenhirs;
	private int nombreGrainesProteges;

	/**
	 *
	 */
	private Collection<Carte> carte;

	public Joueur() {
	}

	public void augmenterGraines(int n) {}
	public void diminuerGraines(int n) {}
	public void augmenterMehnirs(int n) {}
	public void diminuerMenhirs(int n) {}
	public void subirVolGraines(int n) {}

	public void jouerDansTourAdverse() {

	}

	//public void piocherCartes(Tas<Carte> tas, int nombreCartes) {

	//}

	public void piocherCartes(Tas<Carte> tas, int nombreCartes) {}

	public abstract void jouer(Joueur[] contexte);

	protected abstract CarteAllie choisirJouerAllie();

	/**
	 * Getter of the property <tt>carte</tt>
	 * 
	 * @return Returns the carte.
	 * 
	 */

	public Collection<Carte> getCarte() {
		return carte;
	}

	/**
	 * Returns an iterator over the elements in this collection.
	 * 
	 * @return an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * 
	 */
	public Iterator<Carte> carteIterator() {
		return carte.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * 
	 * @return <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * 
	 */
	public boolean isCarteEmpty() {
		return carte.isEmpty();
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
	public boolean containsCarte(Carte carte) {
		return this.carte.contains(carte);
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
	public boolean containsAllCarte(Collection<Carte> carte) {
		return this.carte.containsAll(carte);
	}

	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return the number of elements in this collection
	 * @see java.util.Collection#size()
	 * 
	 */
	public int carteSize() {
		return carte.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * 
	 * @return an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * 
	 */
	public Carte[] carteToArray() {
		return carte.toArray(new Carte[carte.size()]);
	}

	/**
	 * Setter of the property <tt>carte</tt>
	 * 
	 * @param carte
	 *            the carte to set.
	 * 
	 */
	public void setCarte(Collection<Carte> carte) {
		this.carte = carte;
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
	public boolean addCarte(Carte carte) {
		return this.carte.add(carte);
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
	public boolean removeCarte(Carte carte) {
		return this.carte.remove(carte);
	}

	/**
	 * Removes all of the elements from this collection (optional
	 * operation).
	 * 
	 * @see java.util.Collection#clear()
	 * 
	 */
	public void clearCarte() {
		this.carte.clear();
	}

	/**
	 * Getter of the property <tt>age</tt>
	 * 
	 * @return Returns the age.
	 * 
	 */

	public int getAge() {
		return age;
	}

	/**
	 * Setter of the property <tt>age</tt>
	 * 
	 * @param age
	 *            The age to set.
	 * 
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
