package fr.bragabresolin.menhir.Core.Joueurs;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Joueurs.Comportements.*;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;

/**
 * Classe représentant un joueur virtuel, c'est-à-dire un joueur fictif contrôlé 
 * par un algorithme prédéfini.
 * Un joueur virtuel est concret, contrairement à un Joueur. L'implémentation 
 * de ses comportements de jeu repose sur l'utilisation d'algorithmes (et/ou de 
 * hasard) afin de déterminer le comportement à adopter selon le contexte.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
 */
public class JoueurVirtuel extends Joueur {

	/**
	 * Représente la stratégie de comportement utilisée par le joueur.
	 * 
	 * Une valeur nulle indique que l'objet est dans un état inconsistent et ne 
	 * peut pas être utilisé tel quel.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	private ComportementStrategy comportementStrategy = null;

	/**
	 * Retourne la stratégie du joueur.
	 * 
	 * @return La stratégie du joueur
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	public ComportementStrategy getComportementStrategy() {
		return comportementStrategy;
	}

	/**
	 * Constructeur simplifié.
	 *
	 * Appel transparent au constructeur parent, auquel s'ajoute la 
	 * détermination de la stratégie du joueur.
	 * On assigne une stratégie de façon aléatoire.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	public JoueurVirtuel() {
		super();
		int n = 1 + (int) (Math.random() * ((3 - 1) + 1));
		switch (n) {
			case 1:
				this.comportementStrategy = new TransformateurStrategy();
				break;
			case 2:
				this.comportementStrategy = new VoleurStrategy();
				break;
			case 3:
				this.comportementStrategy = new StockeurStrategy();
				break;
		}
	}

	/**
	 * Constructeur complet.
	 *
	 * Simple appel transparent au constructeur simplifié de cette classe, avec 
	 * spécification du nom et de l'âge.
	 * 
	 * @param nom Le nom du joueur
	 * @param age L'âge du joueur
	 */
	public JoueurVirtuel(String nom, int age) {
		this();
		this.nom = nom;
		this.age = age;
	}

	/**
	 * Mutateur de la stratégie du joueur.
	 *
	 * Permet de changer de stratégie en cours de jeu.
	 * 
	 * @param comportementStrategy La stratégie à adopter
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	public void setComportementStrategy(ComportementStrategy comportementStrategy) {
		this.comportementStrategy = comportementStrategy;
	}

	/**
	 * Fais jouer le joueur selon le contexte passé.
	 *
	 * Un joueur virtuel délègue entièrement le choix de sa carte ingrédient à 
	 * sa stratégie.
	 * Le joueur attends sans rien faire pendant un temps variable aléatoire 
	 * afin de simuler le temps de réflexion d'un vrai joueur.
	 * 
	 * @param contexte La liste des joueurs de la partie
	 * @param partieAvancee Si la partie est en mode avancé ou non
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	public void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle) {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_DEBUT_TOUR));
		
		int tempsAttenteMin = 1000;
		int tempsAttenteMax = 2500;
		try {
			Thread.sleep(tempsAttenteMin + (int) (Math.random() * 
						((tempsAttenteMax - tempsAttenteMin) + 1)));
		} catch (InterruptedException e) { e.printStackTrace(); }

		ArrayList<CarteIngredient> cartesIng = new ArrayList<CarteIngredient>();
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee() && carte instanceof CarteIngredient)
				cartesIng.add((CarteIngredient) carte);
		CarteIngredient carteChoisie = this.comportementStrategy.choisirCarteIngredient(this, saisonActuelle, contexte, cartesIng);
		carteChoisie.executer(saisonActuelle);

		if (partieAvancee) {
			CarteAllie carteAllie = this.choisirJouerAllie(saisonActuelle, contexte);
			if (carteAllie != null) carteAllie.executer(saisonActuelle);
		}
		
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_FIN_TOUR));
	}

	/**
	 * Demande au joueur de choisir de jouer une carte allié.
	 *
	 * Un joueur virtuel délègue entièrement le choix de jouer sa carte allié à 
	 * sa stratégie.
	 *
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte Les joueurs de la partie
	 * @return La carte allié choisie
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
	 */
	protected CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte) {
		ArrayList<CarteAllie> cartesAllie = new ArrayList<CarteAllie>();
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee() && carte instanceof CarteAllie)
				cartesAllie.add((CarteAllie) carte);

		if (cartesAllie.size() == 0) return null;

		return this.comportementStrategy.choisirCarteAllie(this, saisonActuelle, contexte, cartesAllie);
	}

	/**
	 * Demande au joueur s'il veut piocher une carte allié.
	 *
	 * Un joueur virtuel peut décider, selon sa stratégie ou d'autres facteurs, 
	 * de piocher ou non une carte allié.
	 *
	 * @return Vrai si le joueur veut piocher une carte allié
	 */
	public boolean veutPiocherCarteAllie() {
		// Pour l'instant, le choix de piocher 1 carte allié ou 2 graines est 
		// aléatoire.
		// A l'avenir, on pourra éventuellement déléguer cette décision au 
		// ComportementStrategy du joueur virtuel pour des comportements plus 
		// réfléchis.
		return (Math.random() > 0.5);
	}

	/**
	 * Invitation à effectuer une action autorisée pendant le tour de 
	 * l'adversaire.
	 *
	 * @param contexte La liste des joueurs de la partie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle) {
		// Méthode vide
		// Pour l'instant, les joueurs virtuels ne jouent jamais leur taupe
		// pendant le tour des autres.
	}

	/**
	 * Produit une représentation textuelle du joueur.
	 *
	 * Un joueur virtuel est un joueur dont on affiche le nom et le 
	 * comportement.
	 */
	public String toString() {
		String str = super.toString();
		return this.nom + " Joueur Virtuel <" + this.comportementStrategy.toString() +"> " + str;
	}
}
