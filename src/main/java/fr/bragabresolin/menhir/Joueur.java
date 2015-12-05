package fr.bragabresolin.menhir;

import java.util.*;

/**
 * 
 * @author simon
 *
 */
public abstract class Joueur extends Observable {

	/**
	 * Représente le nom du joueur.
	 * Utilisé pour un affichage un peu plus chaleureux.
	 */
	protected String nom;

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
	protected List<Carte> cartes;
	
	
	/**
	 * Crée un joueur. Initialise tous ses champs.
	 */
	public Joueur() {
		this.nom = "?";
		this.age = 8; // minimum
		this.nombreGraines = 0;
		this.nombreMenhirs = 0;
		this.nombreGrainesProteges = 0;
		this.points = 0;
		this.cartes = new ArrayList<Carte>();
	}

	public Joueur(String nom, int age) {
		this();
		this.nom = nom;
		this.age = age;
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
		int effet = this.diminuerGraines(n - this.nombreGrainesProteges);
		this.nombreGrainesProteges = 0;
		return effet;
	}

	public abstract void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle);

	public void sauverPoints() {
		this.points += this.nombreMenhirs;
	}

	public void reinitialiserChamp() {
		this.nombreGraines = 0;
		this.nombreMenhirs = 0;
		this.nombreGrainesProteges = 0;
	}

	public void setNombreGrainesProteges(int n) {
		this.nombreGrainesProteges = n;
	}

	public int getNombreGrainesProteges() {
		return this.nombreGrainesProteges;
	}

	public abstract boolean veutPiocherCarteAllie();

	public void piocherCartes(Tas<? extends Carte> tas, int nombreCartes) {
		String type;
		if (tas.peek() instanceof CarteIngredient)
			type = "ingrédient";
		else
			type = "allié";
		this.setChanged();
		this.notifyObservers(this.nom + " pioche " + nombreCartes + " carte" + (nombreCartes > 1 ? "s " : " ") + type + ".");

		for (int i = 0; i < nombreCartes; i++) {
			this.cartes.add(tas.donnerCarte());
		}
	}

	public List<Carte> rendreCartes() {
		List<Carte> copie = new ArrayList<Carte>(this.cartes);
		Collections.copy(copie, this.cartes);
		this.cartes.clear();
		this.setChanged();
		this.notifyObservers(this.nom + " rend ses cartes.");
		return copie;
	}

	public abstract void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle);

	protected abstract CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte);

	/**
	 * Getter of the property <tt>age</tt>
	 * 
	 * @return Returns the age.
	 * 
	 */

	public int getAge() {
		return age;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getNombreGraines() {
		return nombreGraines;
	}

	public void setNombreGraines(int nombreGraines) {
		this.nombreGraines = nombreGraines;
	}

	public boolean estProtege() {
		return this.nombreGrainesProteges > 0;
	}

	public int getNombreMenhirs() {
		return this.nombreMenhirs;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
		int nbCartes = 0;
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee()) nbCartes++;
		StringBuilder builder = new StringBuilder();
		String cartesStr = nbCartes > 1 ? "cartes" : "carte";
		String pointsStr = this.points > 1 ? "points" : "point";
		String grainesStr = this.nombreGraines > 1 ? "graines" : "graine";
		String menhirsStr = this.nombreMenhirs > 1 ? "menhirs" : "menhir";
		String guardStr = this.nombreGrainesProteges > 1 ? " guardées" : "guardée";

		builder.append("(" + this.age + " ans, " + nbCartes + " " + cartesStr + ")")
			   .append("\n  > ")
			   .append(this.points + " " + pointsStr)
			   .append(", ")
			   .append(this.nombreGraines + " " + grainesStr);

		if (this.nombreGrainesProteges > 0)
			builder.append(" (" + this.nombreGrainesProteges + guardStr + ")");

		builder.append(", ")
			   .append(this.nombreMenhirs + " " + menhirsStr);

		return builder.toString();
	}
}
