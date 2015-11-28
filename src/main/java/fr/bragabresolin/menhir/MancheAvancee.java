package fr.bragabresolin.menhir;

import java.util.*;

public class MancheAvancee extends Manche {
	protected Tas<CarteAllie> tasCartesAllies;
	protected final boolean estRapide = false;

	public MancheAvancee(Tas<CarteIngredient> tasIngredients,
			Tas<CarteAllie> tasAllies, ArrayList<Joueur> joueurs) {
		super(tasIngredients, joueurs);
		this.tasCartesAllies = tasAllies;
	}

	public void jouer() {
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
			jIt = this.joueurs.iterator();
			while (jIt.hasNext()) {
				Joueur joueur = jIt.next();
				this.faireJouer(joueur);
				
				Iterator<Joueur> autresJoueurs = this.joueurs.iterator();
				while (autresJoueurs.hasNext()) {
					Joueur autreJoueur = autresJoueurs.next();
					if (joueur != autreJoueur) autreJoueur.jouerDansTourAdverse();
				}
			}
		}

		this.nettoyer();
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
