package fr.bragabresolin.menhir.Core.Joueurs;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe abstraite représentant un joueur, réel comme virtuel, du jeu du Menhir.
 * Cette classe encapsule les informations sur le joueur (nom, âge, ...) ainsi
 * que ses variables en cours de jeu (cartes, points, ...).
 *
 * Cette classe est productrice de messages.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.JoueurVirtuel
 * @see fr.bragabresolin.menhir.Core.Joueurs.JoueurPhysique
 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
 * @see fr.bragabresolin.menhir.Core.Message.Message
 * @see fr.bragabresolin.menhir.Core.Message.MessageType
 */
public abstract class Joueur extends Observable {

	/**
	 * Représente le nom du joueur.
	 * Utilisé pour un affichage un peu plus chaleureux.
	 * 
	 * Une valeur nulle signifie que l'objet n'a pas encore fini d'être 
	 * initialisé. Il peut quand même être utilisé dans cet état.
	 */
	protected String nom;

	/**
	 * Représente l'âge du joueur.
	 * Doit être plus grand que 7 selon les règles du jeu.
	 */
	protected int age;
	
	/**
	 * Représente le nombre de points présents sur la carte point du joueur.
	 * Ce sont les points calculés à la fin de chaque manche, ils ne peuvent pas
	 * être enlevés par un autre joueur durant une manche et sont forcément 
	 * positifs ou nuls.
	 */
	protected int points;
	
	/**
	 * Représente le nombre de graines sur la carte champ.
	 * Ce nombre est forcément positif ou nul ; il varie au cours d'une manche.
	 */
	protected int nombreGraines;
	
	/**
	 * Représente le nombre de menhirs adultes sur la carte champ.
	 * Ce nombre est forcément positif ou nul ; il varie au cours d'une manche.
	 */
	protected int nombreMenhirs;
	
	/**
	 * Représente le nombre de graines protégées par une carte chien active.
	 * Si aucune carte chien n'est active, ce champ est à 0.
	 */
	protected int nombreGrainesProteges;

	/**
	 * Représente la main du joueur.
	 * Cette main contient à la fois ses cartes ingrédients et alliés.
	 * 
	 * Une valeur nulle indique que l'objet est dans un état inconsistent et ne 
	 * peut pas être utilisé tel quel ; si le joueur n'a plus de cartes en main,
	 * la List est vide.
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

		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_PIOCHE_GRAINE, n));
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
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_PERDS_GRAINES, grainesAvant - this.nombreGraines));
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

		if (augmentation != 0) this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_GAGNE_MENHIRS, augmentation));

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

		int effet = menhirsAvant - this.nombreMenhirs;

		if (effet != 0) this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_PERDS_MENHIR, effet));

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

		if (effet != 0) this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_PERDS_GRAINES, effet));

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

		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_RESET_CHAMP));
	}

	public void setNombreGrainesProteges(int n) {
		this.nombreGrainesProteges = n;
		this.setChanged();
		this.notifyObservers();
	}

	public int getNombreGrainesProteges() {
		return this.nombreGrainesProteges;
	}

	public abstract boolean veutPiocherCarteAllie();

	public void piocherCartes(Tas<? extends Carte> tas, int nombreCartes) {
		for (int i = 0; i < nombreCartes; i++) {
			Carte carte = tas.donnerCarte();
			this.cartes.add(carte);

			this.setChanged();
			this.notifyObservers(new Message(MessageType.JOUEUR_PIOCHE_CARTE, carte));
		}
	}

	public List<Carte> rendreCartes() {
		List<Carte> copie = new ArrayList<Carte>(this.cartes);
		Collections.copy(copie, this.cartes);
		this.cartes.clear();

		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_REND_CARTES));

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
	
	public List<Carte> getCartes() {
		return this.cartes;
	}
	
	public boolean carteAllieDispo() {
		Iterator<Carte> it = this.cartes.iterator();
		while (it.hasNext()) {
			Carte carte = it.next();
			if (carte instanceof CarteAllie &&
					!carte.getDejaJouee()) return true;
		}
		return false;
	}

	/**
	 * Setter of the property <tt>age</tt>
	 * 
	 * @param age The age to set.
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
