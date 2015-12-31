package fr.bragabresolin.menhir.Core.Partie;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.*;

public class Manche extends java.util.Observable {

	protected Tas<CarteIngredient> tasCartesIngredients;
	protected ArrayList<Joueur> joueurs;
	protected Saison saisonEnCours;
	protected boolean estRapide;

	public Manche(Tas<CarteIngredient> tasIngredients, ArrayList<Joueur> joueurs) {
		this.tasCartesIngredients = tasIngredients;
		this.joueurs = joueurs;
		this.estRapide = true;
		this.saisonEnCours = Saison.values()[0];
	}

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

	protected void faireJouer(Joueur joueur) {
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
		java.util.Collections.sort(this.joueurs, new ScoreComparator());
		return this.joueurs;
	}

	protected class ScoreComparator implements java.util.Comparator<Joueur> {
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
	
	public Saison getSaisonEnCours() {
		return this.saisonEnCours;
	}
}
