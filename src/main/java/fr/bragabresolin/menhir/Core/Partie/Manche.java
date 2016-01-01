package fr.bragabresolin.menhir.Core.Partie;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe représentant le déroulement d'une manche de jeu du Menhir.
 * Cette classe encapsule la logique et les règles d'une manche classique d'un 
 * jeu du Menhir : nombre de tours, qui joue et quand, classement des joueurs, 
 * etc...
 * Cette classe a besoin de référence vers les composants d'une manche (joueurs 
 * et tas) pour les faire jouer.
 * 
 * Cette classe est productrice de messages.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Partie.MancheAvancee
 */
public class Manche extends java.util.Observable {

	/**
	 * Représente le tas de cartes ingrédient utilisé pendant la manche.
	 *
	 * Une valeur nulle signifie que la manche n'a pas encore été initialisée ou
	 * a mal été construite, et n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
	 */
	protected Tas<CarteIngredient> tasCartesIngredients;

	/**
	 * Représente la liste des joueurs participant à la manche.
	 *
	 * Une valeur nulle signifie que la manche n'a pas encore été initialisée ou
	 * a mal été construite, et n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	protected ArrayList<Joueur> joueurs;

	/**
	 * Représente la saison actuellement active de la manche.
	 *
	 * Une valeur nulle est impossible ; l'attribut est toujours au moins rempli 
	 * par la première saison disponible dans l'énumération Saison.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	protected Saison saisonEnCours;

	/**
	 * Représente le type de jeu (manche avancée ou rapide).
	 */
	protected boolean estRapide;

	/**
	 * Constructeur.
	 * 
	 * Créé une manche simple à partir d'un tas de cartes ingrédient à utiliser 
	 * durant la manche, et la liste des joueurs participant.
	 * 
	 * @param tasIngredients Le tas de cartes à utiliser
	 * @param joueurs La liste des joueurs de la manche
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 */
	public Manche(Tas<CarteIngredient> tasIngredients, ArrayList<Joueur> joueurs) {
		this.tasCartesIngredients = tasIngredients;
		this.joueurs = joueurs;
		this.estRapide = true;
		this.saisonEnCours = Saison.values()[0];
	}

	/**
	 * Fait se disputer la manche.
	 * 
	 * Une manche simple se déroule en faisant piocher tous les joueurs leurs 
	 * cartes et leurs graines, puis, pour chaque saison, on fait jouer le 
	 * joueur dans le contexte de la saison.
	 */
	public void jouer() {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.DEBUT_MANCHE));

		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.piocherCartes(this.tasCartesIngredients, 4);
			joueur.augmenterGraines(2);
		}

		for (Saison saison : Saison.values()) {
			this.saisonEnCours = saison;

			this.setChanged();
			this.notifyObservers(new Message(MessageType.DEBUT_SAISON, saison));

			jIt = this.joueurs.iterator();
			while (jIt.hasNext()) this.faireJouer(jIt.next());

			this.setChanged();
			this.notifyObservers(new Message(MessageType.FIN_SAISON, saison));
		}

		this.nettoyer(true);

		this.setChanged();
		this.notifyObservers(new Message(MessageType.FIN_MANCHE));
	}

	/**
	 * Fait jouer le joueur passé en argument.
	 * 
	 * Fait jouer le joueur en lui passant le contexte de la manche en cours.
	 *
	 * @param joueur Le joueur à faire jouer
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	protected void faireJouer(Joueur joueur) {
		joueur.jouer(this.joueurs, !this.estRapide, this.saisonEnCours);
	}

	/**
	 * Nettoye l'état de la manche.
	 *
	 * Nettoye les "restes" du jeu de la manche en faisant sauvegarder leurs 
	 * points aux joueurs et en récupérant les cartes pour les remettre dans le 
	 * tas de cartes.
	 * Le paramètre destructif permet de préciser si on veut réinitialiser les 
	 * champs des joueurs ; lors de la dernière manche, on ne le fait pas, afin 
	 * de garder une trace des graines restantes pour trier les joueurs en cas 
	 * d'égalité sur les points.
	 *
	 * @param destructif Vrai si on doit effacer la carte champ des joueurs, faux sinon
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void nettoyer(boolean destructif) {
		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.sauverPoints();
			if (destructif) joueur.reinitialiserChamp();
			this.recupererCartes(joueur.rendreCartes());
		}
	}

	/**
	 * Remet les cartes fournies dans le tas de cartes ingredient
	 *
	 * @param cartes La liste des cartes à récupérer
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
	 */
	protected void recupererCartes(List<Carte> cartes) {
		for (Carte carte : cartes) {
			carte.setDejaJouee(false);
			this.tasCartesIngredients.ajouterCarte((CarteIngredient) carte);
		}
	}

	/**
	 * Classe les joueurs participant à la manche selon les règles du jeu.
	 * 
	 * Les joueurs sont classés selon leur score et les subtilités précisées par 
	 * les règles en cas d'égalité.
	 *
	 * @return La liste des joueurs classés
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public List<Joueur> classerJoueurs() {
		java.util.Collections.sort(this.joueurs, new ScoreComparator());
		return this.joueurs;
	}

	/**
	 * Classe interne permettant de trier les joueurs selon les règles du jeu.
	 * 
	 * On trie un joueur selon ses points, puis selon le nombre de graînes 
	 * restantes en cas d'égalité.
	 * 
	 * @author  Logan Braga
	 * @author  Simon Bresolin
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	protected class ScoreComparator implements java.util.Comparator<Joueur> {

		/**
		 *
		 * @param a Le premier joueur à comparer
		 * @param b Le second joueur avec qui le comparer
		 * @return Un entier de comparaison : -1 si a &lt; b, 0 si a = b et 1 si a &gt; b
		 */
		public int compare(Joueur a, Joueur b) {
			int scoreA = a.getPoints();
			int scoreB = b.getPoints();

			if (scoreA > scoreB) {
				return -1;
			} else if (scoreA < scoreB) {
				return 1;
			} else {
				int grainesA = a.getNombreGraines();
				int grainesB = b.getNombreGraines();
				return grainesA > grainesB ? -1 : grainesA == grainesB ? 0 : 1;
			}
		}
	}
	
	/**
	 * Retourne la saison en cours de la manche.
	 *
	 * @return La saison actuellement en application
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public Saison getSaisonEnCours() {
		return this.saisonEnCours;
	}
}
