package fr.bragabresolin.menhir;

import java.util.*;

public class Manche {

	protected Tas<CarteIngredient> tasCartesIngredients;
	protected ArrayList<Joueur> joueurs;
	protected Saison saisonEnCours;
	protected final boolean estRapide = true;

	public Manche(Tas<CarteIngredient> tasIngredients, ArrayList<Joueur> joueurs) {
		this.tasCartesIngredients = tasIngredients;
		this.joueurs = joueurs;
	}

	public void jouer() {
		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.piocherCartes(this.tasCartesIngredients, 4);
			joueur.augmenterGraines(2);
		}

		for (Saison saison : Saison.values()) {
			this.saisonEnCours = saison;
			jIt = this.joueurs.iterator();
			while (jIt.hasNext()) this.faireJouer(jIt.next());
		}

		this.nettoyer();
	}

	protected void faireJouer(Joueur joueur) {
		if (joueur instanceof JoueurVirtuel) {
			int tempsAttenteMin = 1000;
			int tempsAttenteMax = 2500;
			try {
				Thread.sleep(tempsAttenteMin + (int) (Math.random() * 
							((tempsAttenteMax - tempsAttenteMin) + 1)));
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
		joueur.jouer(this.joueurs, !this.estRapide, this.saisonEnCours);
	}

	protected void nettoyer() {
		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.sauverPoints();
			joueur.reinitialiserChamp();
			this.recupererCartes(joueur.rendreCartes());
		}
	}

	protected void recupererCartes(List<Carte> cartes) {
		for (Carte carte : cartes) {
			carte.setDejaJouee(false);
			this.tasCartesIngredients.ajouterCarte((CarteIngredient) carte);
		}
	}
}
