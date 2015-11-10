package fr.bragabresolin.menhir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * 
 * @author simon
 *
 */
public abstract class Joueur {

	/**
	 * Représente l'âge du joueur.
	 * Doit être plus grand que 7.
	 */
	protected int age;
	
	/**
	 * Représente le nombre de points présents sur la carte point.
	 * Ce sont les points calculés à la fin de chaque manche, ils ne peuvent pas¨
	 * être enlevés par un autre joueur. 
	 */
	protected int points;
	
	/**
	 * Représente le nombre de graines sur la carte champ.
	 */
	protected int nombreGraines;
	
	/**
	 * Représente le nombre de menhirs adultes sur la carte champ.
	 */
	protected int nombreMenhirs;
	
	/**
	 * Représente le nombre de graines protégées par une carte chien active.
	 * Si aucune carte chien n'est active, ce champ est à 0.
	 */
	protected int nombreGrainesProteges;

	/**
	 * Représente la main du joueur. Cette main contient à la fois des cartes
	 * ingrédients et alliés.
	 */
	protected Collection<Carte> carte;
	
	
	/**
	 * Crée un joueur. Initialise tous ses champs.
	 */
	public Joueur() {
		this.nombreGraines = 0;
		this.nombreMenhirs = 0;
		this.nombreGrainesProteges = 0;
		this.points = 0;
		this.carte = new ArrayList<Carte>();
	}

	/**
	 * Augmente les graines du joueur de sa carte champ.
	 * 
	 * @param n Le nombre de graines à incrémenter du champ du joueur.
	 */
	public void augmenterGraines(int n) {
		if (n <= 0) return;
		
		this.nombreGraines += n;
	}
	
	/**
	 * Diminue les graines de la carte champ du joueur.
	 * 
	 * @param n Le nombre de graines à décrémenter du champ du joueur.
	 * @return Le nombre effectif de graines enlevées.
	 */
	public int diminuerGraines(int n) {
		if (n <= 0) return 0;
		
		int grainesAvant = this.nombreGraines;
		this.nombreGraines -= n;
		if (this.nombreGraines < 0) {
			this.nombreGraines = 0;
		}
		return grainesAvant - this.nombreGraines;	
	}
	
	/**
	 * Augmente les menhirs de la carte champ du joueur.
	 * 
	 * @param n
	 * @return Le nombre de menhir réellement augmentés.
	 */
	public int augmenterMehnirs(int n) {
		if (n <= 0) return 0;
		int augmentation = Math.min(this.nombreGraines, n);
		this.nombreMenhirs += augmentation;
		return augmentation;
	}
	
	/**
	 * Diminue les menhirs de la carte champ du joueur.
	 * 
	 * @param n Le nombre de menhirs à décrémenter du champ du joueur.
	 * @return Le nombre effectif de menhirs enlevés.
	 */
	public int diminuerMenhirs(int n) {
		if (n < 0) return 0;
		
		int menhirsAvant = this.nombreMenhirs;
		this.nombreMenhirs -= n;
		if (this.nombreMenhirs < 0) {
			this.nombreMenhirs = 0;
		}
		return menhirsAvant - this.nombreMenhirs;	
	}
	
	/**
	 * Diminue le nombre de graines du joueur lorsque ce dernier subit un vol.
	 * Prend en compte les graines protégées par un éventuel chien actif.
	 * 
	 * @param n Le nombre de graines à voler.
	 * @return Le nombre de graines effectivement enlevées.
	 */
	public int subirVolGraines(int n) {
		return this.diminuerGraines(n - this.nombreGrainesProteges);
	}

	public void jouerDansTourAdverse() {

	}

	//public void piocherCartes(Tas<Carte> tas, int nombreCartes) {

	//}

	public void piocherCartes(Tas<Carte> tas, int nombreCartes) {}

	public abstract void jouer(Joueur[] contexte, Saison saisonActuelle);

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
	public void setAge(int age) throws IllegalArgumentException {
		if (age >= 8)
			this.age = age;
		else
			throw new IllegalArgumentException("Le jeu est disponible à partir de 8 ans.");
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		String cartesStr = this.carte.size() > 1 ? "cartes" : "carte";
		String pointsStr = this.points > 1 ? "points" : "point";
		String grainesStr = this.nombreGraines > 1 ? "graines" : "graine";
		String menhirsStr = this.nombreMenhirs > 1 ? "menhirs" : "menhir";

		builder.append("(" + this.age + " ans, " + this.carte.size() + " " + cartesStr + ")")
			   .append("\n  > ")
			   .append(this.points + " " + pointsStr)
			   .append(", ")
			   .append(this.nombreGraines + " " + grainesStr);

		if (this.nombreGrainesProteges > 0)
			builder.append(" (" + this.nombreGrainesProteges + " guardées)");

		builder.append(", ")
			   .append(this.nombreMenhirs + " " + menhirsStr);

		return builder.toString();
	}
}
