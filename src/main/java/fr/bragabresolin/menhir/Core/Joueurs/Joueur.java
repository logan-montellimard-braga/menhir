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
	 * Constructeur raccourci.
	 *
	 * On initialise un joueur avec les données minimales requises et une main 
	 * de cartes vide.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
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

	/**
	 * Constructeur complet.
	 *
	 * On construit un joueur avec le constructeur raccourci, avec le nom et 
	 * l'âge fournis.
	 *
	 * @param nom Le nom du joueur
	 * @param age L'âge du joueur
	 */
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
	 * On prends en compte le nombre de graines de manière à ne transformer que 
	 * les graines dont on dispose. Le nombre de menhirs effectivement créés 
	 * peut donc être différent de l'argument.
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
	 * 
	 * Prend en compte les graines protégées par un éventuel chien actif.
	 * Si on subit un vol de graines, la carte chien éventuellement active est 
	 * annulée, on réduit donc le nombre de graines protégées du joueur à 0.
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

	/**
	 * Invitation à effectuer une action autorisée pendant le tour de 
	 * l'adversaire.
	 *
	 * Certaines cartes peuvent être jouées n'importe quand. Cette méthode est 
	 * appelée pour permettre cette possibilité.
	 * 
	 * @param contexte La liste des joueurs de la partie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public abstract void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle);

	/**
	 * Sauvegarde les points du joueur, c'est-à-dire transfert les menhirs de sa 
	 * carte champ vers son compteur de points.
	 *
	 * Les points sauvés dans le compteur de points ne peuvent plus être enlevés 
	 * pendant une manche.
	 */
	public void sauverPoints() {
		this.points += this.nombreMenhirs;
	}

	/**
	 * Réinitialise la carte champ du joueur en mettant ses graines (et graine 
	 * protégées) et menhirs à 0.
	 */
	public void reinitialiserChamp() {
		this.nombreGraines = 0;
		this.nombreMenhirs = 0;
		this.nombreGrainesProteges = 0;

		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_RESET_CHAMP));
	}

	/**
	 * Mutateur pour les graines protégées (carte Chien de Garde) du joueur.
	 *
	 * @param n Le nombre de graines à protéger
	 */
	public void setNombreGrainesProteges(int n) {
		this.nombreGrainesProteges = n;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Retourne le nombre de graines actuellement protégées par un chien.
	 * Cette valeur est à 0 si aucune carte chien n'est active pour ce joueur.
	 * 
	 * @return Le nombre de graines protégées du joueur
	 */
	public int getNombreGrainesProteges() {
		return this.nombreGrainesProteges;
	}

	/**
	 * Demande au joueur s'il veut piocher une carte allié.
	 * Utilisé en mode partie complexe, où un joueur peut choisir au début du 
	 * tour de piocher une carte allié ou récupérer deux graines.
	 *
	 * @return Vrai si le joueur veut piocher une carte allié
	 */
	public abstract boolean veutPiocherCarteAllie();

	/**
	 * Fais piocher des cartes au joueur, dans le tas fourni.
	 *
	 * @param tas Le tas dans lequel piocher
	 * @param nombreCartes Le nombre de cartes à piocher dans le tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
	 */
	public void piocherCartes(Tas<? extends Carte> tas, int nombreCartes) {
		for (int i = 0; i < nombreCartes; i++) {
			Carte carte = tas.donnerCarte();
			this.cartes.add(carte);

			this.setChanged();
			this.notifyObservers(new Message(MessageType.JOUEUR_PIOCHE_CARTE, carte));
		}
	}

	/**
	 * Fais rendre le joueur ses cartes.
	 * Les cartes sont supprimées de la main du joueur pour être rendues.
	 *
	 * @return La liste des cartes du joueur
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
	 */
	public List<Carte> rendreCartes() {
		List<Carte> copie = new ArrayList<Carte>(this.cartes);
		Collections.copy(copie, this.cartes);
		this.cartes.clear();

		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_REND_CARTES));

		return copie;
	}

	/**
	 * Fais jouer le joueur selon le contexte fourni.
	 *
	 * @param contexte La liste des joueurs de la partie
	 * @param partieAvancee Si la partie est en mode avancé ou non
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public abstract void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle);

	/**
	 * Demande au joueur de choisir de jouer une carte allié.
	 *
	 * Le joueur peut décider de ne pas jouer de carte allié durant son tour, 
	 * auquel cas null est renvoyé.
	 *
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte Les joueurs de la partie
	 * @return La carte allié choisie
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	protected abstract CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte);

	/**
	 * Retourne l'âge du joueur.
	 * 
	 * @return L'âge du joueur
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Retourne le nombre de points du joueur.
	 *
	 * @return Le nombre de points du joueur
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Mutateur pour le nombre de points du joueur.
	 *
	 * @param points Le nombre de points du joueur
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Retourne le nombre de graines sur la carte champ du joueur.
	 *
	 * @return Le nombre de graines du joueur
	 */
	public int getNombreGraines() {
		return nombreGraines;
	}

	/**
	 * Mutateur pour le nombre de graines sur la carte champ du joueur.
	 *
	 * @param nombreGraines Le nombre de graines du joueur
	 */
	public void setNombreGraines(int nombreGraines) {
		this.nombreGraines = nombreGraines;
	}

	/**
	 * Retourne vrai si le joueur est actuellement protégé par une carte chien 
	 * de garde qui le protège d'au moins 1 vol de graines.
	 *
	 * @return Si le joueur a un chien de garde actif ou non
	 */
	public boolean estProtege() {
		return this.nombreGrainesProteges > 0;
	}

	/**
	 * Retourne le nombre de menhirs que le joueur a sur sa carte champs.
	 *
	 * @return Le nombre de menhirs du joueur
	 */
	public int getNombreMenhirs() {
		return this.nombreMenhirs;
	}

	/**
	 * Retourne le nom du joueur.
	 *
	 * @return Le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Mutateur pour le nom du joueur.
	 *
	 * @param nom Le nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Retourne la main du joueur.
	 *
	 * @return Les cartes du joueur
	 */
	public List<Carte> getCartes() {
		return this.cartes;
	}
	
	/**
	 * Renvoit vrai si au moins une carte allié non jouée est disponible dans la 
	 * main du joueur.
	 *
	 * @return Si une carte allié est disponible
	 */
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
	 * Mutateur de l'âge du joueur.
	 * 
	 * @param age L'âge du joueur
	 * @throws IllegalArgumentException Si l'âge n'est pas autorisé par les règles
	 */
	public void setAge(int age) throws IllegalArgumentException {
		if (age >= 8)
			this.age = age;
		else
			throw new IllegalArgumentException("Le jeu est disponible à partir de 8 ans.");
	}

	/**
	 * Produit une représentation textuelle du joueur.
	 * 
	 * Un joueur est représenté en chaîne de caractères en affichant toutes ses 
	 * informations de partie.
	 *
	 * @return La représentation textuelle du joueur
	 */
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
