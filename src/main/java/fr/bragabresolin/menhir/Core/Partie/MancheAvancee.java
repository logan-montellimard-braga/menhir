package fr.bragabresolin.menhir.Core.Partie;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe représentant le déroulement d'une manche avancée de jeu du Menhir.
 * Cette classe encapsule la logique et les règles d'une manche avancée d'un 
 * jeu du Menhir, en se basant sur les règles d'une manche normale.
 * Cette classe surdéfinit les fonctions nécessaires de manière à ajouter le
 * comportement particulier des manches avancées (utilisées dans les parties 
 * avancées).
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Partie.Manche
 */
public class MancheAvancee extends Manche {

	/**
	 * Représente le tas de cartes allié utilisé pendant la manche.
	 *
	 * Une valeur nulle signifie que la manche n'a pas encore été initialisée ou
	 * a mal été construite, et n'est pas dans un état utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 */
	protected Tas<CarteAllie> tasCartesAllies;

	/**
	 * Constructeur.
	 * 
	 * Appelle de façon transparente le constructeur de la classe mère, puis
	 * enregistre la référence vers le tas de cartes allié, utilisées lors d'une 
	 * manche avancée.
	 * 
	 * @param tasIngredients Le tas de cartes ingrédient à utiliser
	 * @param tasAllies Le tas de cartes allié à utiliser
	 * @param joueurs La liste des joueurs de la manche
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 * @see fr.bragabresolin.menhir.Core.Cartes.Tas
	 * @see fr.bragabresolin.menhir.Core.Partie.Manche
	 */
	public MancheAvancee(Tas<CarteIngredient> tasIngredients,
			Tas<CarteAllie> tasAllies, ArrayList<Joueur> joueurs) {
		super(tasIngredients, joueurs);
		this.tasCartesAllies = tasAllies;
		this.estRapide = false;
	}

	/**
	 * Fait se disputer la manche.
	 * 
	 * Une manche avancée se déroule comme une manche simple, mais les joueurs 
	 * peuvent choisir de piocher une carte allié ou deux graines en début de 
	 * manche, et peuvent jouer certaines cartes allié pendant le tour des 
	 * autres.
	 */
	public void jouer() {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.DEBUT_MANCHE));

		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.piocherCartes(this.tasCartesIngredients, 4);
			if (joueur.veutPiocherCarteAllie()) {
				joueur.piocherCartes(this.tasCartesAllies, 1);
			} else {
				joueur.augmenterGraines(2);
			}
		}

		for (Saison saison : Saison.values()) {
			this.saisonEnCours = saison;

			this.setChanged();
			this.notifyObservers(new Message(MessageType.DEBUT_SAISON, saison));

			jIt = this.joueurs.iterator();
			while (jIt.hasNext()) {
				Joueur joueur = jIt.next();
				this.faireJouer(joueur);
				
				Iterator<Joueur> autresJoueurs = this.joueurs.iterator();
				while (autresJoueurs.hasNext()) {
					Joueur autreJoueur = autresJoueurs.next();
					if (joueur != autreJoueur) autreJoueur.jouerDansTourAdverse(this.joueurs, this.saisonEnCours);
				}
			}

			this.setChanged();
			this.notifyObservers(new Message(MessageType.FIN_SAISON, saison));
		}

		this.setChanged();
		this.notifyObservers(new Message(MessageType.FIN_MANCHE));
	}

	/**
	 * Nettoye l'état de la manche.
	 *
	 * Surdéfinition pour décaler l'ordre des joueurs à la fin de la manche.
	 * On décale l'ordre des joueurs afin de faire en sorte que chaque manche 
	 * avancée ait un joueur différent au démarrage.
	 *
	 * @param destructif Vrai si on doit effacer la carte champ des joueurs, faux sinon
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void nettoyer(boolean destructif) {
		super.nettoyer(destructif);
		this.decalerJoueurs();
	}

	/**
	 * Décale la liste des joueurs.
	 *
	 * On décale les joueurs en mettant le joueur en queue de liste à la tête de 
	 * la liste.
	 */
	protected void decalerJoueurs() {
		Joueur shiftJoueur = this.joueurs.remove(this.joueurs.size() - 1);
		this.joueurs.add(0, shiftJoueur);
	}

	/**
	 * Remet les cartes fournies dans le tas de cartes ingredient ou de cartes 
	 * allié selon leur type.
	 * 
	 * Surdéfinition de la méthode de la classe mère afin de prendre en compte 
	 * les cartes allié utilisées dans une manche avancée.
	 *
	 * @param cartes La liste des cartes à récupérer
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 */
	protected void recupererCartes(List<Carte> cartes) {
		for (Carte carte : cartes) {
			carte.setDejaJouee(false);
			if (carte instanceof CarteIngredient)
				this.tasCartesIngredients.ajouterCarte((CarteIngredient) carte);
			else if (carte instanceof CarteAllie)
				this.tasCartesAllies.ajouterCarte((CarteAllie) carte);
		}
	}
}
