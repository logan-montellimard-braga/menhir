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
	protected Tas<CarteAllie> tasCartesAllies;

	public MancheAvancee(Tas<CarteIngredient> tasIngredients,
			Tas<CarteAllie> tasAllies, ArrayList<Joueur> joueurs) {
		super(tasIngredients, joueurs);
		this.tasCartesAllies = tasAllies;
		this.estRapide = false;
	}

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

	public void nettoyer(boolean destructif) {
		super.nettoyer(destructif);
		this.decalerJoueurs();
	}

	protected void decalerJoueurs() {
		Joueur shiftJoueur = this.joueurs.remove(this.joueurs.size() - 1);
		this.joueurs.add(0, shiftJoueur);
	}

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
