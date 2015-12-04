package fr.bragabresolin.menhir;

import java.util.*;

public class Manche {

	protected Tas<CarteIngredient> tasCartesIngredients;
	protected ArrayList<Joueur> joueurs;
	protected Saison saisonEnCours;
	protected boolean estRapide;

	public Manche(Tas<CarteIngredient> tasIngredients, ArrayList<Joueur> joueurs) {
		this.tasCartesIngredients = tasIngredients;
		this.joueurs = joueurs;
		this.estRapide = true;
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

		this.nettoyer(true);
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

	public void nettoyer(boolean destructif) {
		Iterator<Joueur> jIt = this.joueurs.iterator();
		while (jIt.hasNext()) {
			Joueur joueur = jIt.next();
			joueur.sauverPoints();
			if (destructif) joueur.reinitialiserChamp();
			this.recupererCartes(joueur.rendreCartes());
		}
	}

	protected void recupererCartes(List<Carte> cartes) {
		for (Carte carte : cartes) {
			carte.setDejaJouee(false);
			this.tasCartesIngredients.ajouterCarte((CarteIngredient) carte);
		}
	}

	public List<Joueur> classerJoueurs() {
		Collections.sort(this.joueurs, new ScoreComparator());
		return this.joueurs;
	}

	protected class ScoreComparator implements Comparator<Joueur> {
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
}
