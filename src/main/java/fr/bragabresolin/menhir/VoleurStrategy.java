package fr.bragabresolin.menhir;

import java.util.*;

public class VoleurStrategy implements ComportementStrategy {
	private static final int SEUIL_GRAINES = 4;

	public CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes) {
		
		CarteIngredient carteChoisie;
		ActionIngredient action;

		if (joueur.getNombreGraines() >= SEUIL_GRAINES)
			action = ActionIngredient.ENGRAIS;
		else
			action = ActionIngredient.FARFADET;

		CarteIngredient meilleurEffet = null;
		for (CarteIngredient carte : cartes) {
			if (meilleurEffet == null) meilleurEffet = carte;
			else if (carte.getMatrice().get(saisonActuelle).get(action)
					> meilleurEffet.getMatrice().get(saisonActuelle).get(action)) {
				 meilleurEffet = carte;
			}
		}

		carteChoisie = meilleurEffet;

		if (action == ActionIngredient.FARFADET) {
			Joueur meilleurJoueur = new JoueurVirtuel("", 8);
			for (Joueur j : contexte)
				if (j != joueur)
					if (j.getNombreMenhirs() >= meilleurJoueur.getNombreMenhirs()
							&& !j.estProtege())
						meilleurJoueur = j;

			if (meilleurJoueur == (new JoueurVirtuel("", 8))) {
				action = ActionIngredient.ENGRAIS;
			} else {
				carteChoisie.setCible(meilleurJoueur);
			}
		}

		carteChoisie.setAction(action);
		carteChoisie.setOrigine(joueur);
		return carteChoisie;
	}

	public CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteAllie> cartes) {

		// Comportement agressif : on joue tout, tout de suite !
		for (CarteAllie carte : cartes) {
			if (carte instanceof CarteAllieTaupe) {
				Joueur meilleurJoueur = new JoueurVirtuel("", 8);
				for (Joueur j : contexte)
					if (j != joueur)
						if (j.getNombreMenhirs() >= meilleurJoueur.getNombreMenhirs())
							meilleurJoueur = j;
				if (meilleurJoueur.getNombreMenhirs() > 0) {
					carte.setOrigine(joueur);
					carte.setCible(meilleurJoueur);
					return carte;
				}
			} else {
				if (joueur.getNombreGraines() > 0
						&& carte.getMatrice().get(saisonActuelle) > 0) {
					carte.setOrigine(joueur);
					return carte;
				}
			}
		}
		return null;
	}

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// voleur.
		return "voleur";
	}
}
