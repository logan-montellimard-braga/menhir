package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;

public class TransformateurStrategy implements ComportementStrategy {

	public CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes) {

		CarteIngredient carteChoisie;
		ActionIngredient action;

		// Si on a des graines, on les fait pousser
		if (joueur.getNombreGraines() >= 1)
			action = ActionIngredient.ENGRAIS;
		// Sinon on en prends d'autres
		else
			action = ActionIngredient.GEANT;

		// On choisit la carte avec le meilleur effet voulu
		// c'est-à-dire la carte la plus proche du nombre de graines si c'est un
		// engrais
		CarteIngredient meilleurEffet = null;
		// Si on prends des graines, on en veut le plus possible
		for (CarteIngredient carte : cartes) {
			if (meilleurEffet == null) meilleurEffet = carte;
			else if (carte.getMatrice().get(saisonActuelle).get(action)
					> meilleurEffet.getMatrice().get(saisonActuelle).get(action)) {
				meilleurEffet = carte;
				if (action == ActionIngredient.GEANT
						&& meilleurEffet.getMatrice().get(saisonActuelle).get(action) == joueur.getNombreGraines())
					break;
			}
		}
		carteChoisie = meilleurEffet;
		carteChoisie.setAction(action);
		carteChoisie.setOrigine(joueur);

		return carteChoisie;
	}

	public CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteAllie> cartes) {
		// Vu que cette stratégie transforme les graines le plus tôt possible
		// on a rarement besoin de les protéger avec un chien.
		// On donne donc la priorité aux taupes géantes.
		for (CarteAllie carte : cartes) {
			if (carte instanceof CarteAllieChien) {
				if (carte.getMatrice().get(saisonActuelle) > 2
						&& carte.getMatrice().get(saisonActuelle) <= joueur.getNombreGraines()) {
					carte.setOrigine(joueur);
					return carte;
				}
			} else {
				Joueur meilleurJoueur = new JoueurVirtuel("", 8);
				for (Joueur j : contexte)
					if (j != joueur)
						if (j.getNombreMenhirs() >= meilleurJoueur.getNombreMenhirs())
							meilleurJoueur = j;
				if (carte.getMatrice().get(saisonActuelle) > 1
						&& meilleurJoueur.getNombreMenhirs() >= carte.getMatrice().get(saisonActuelle)) {
					carte.setOrigine(joueur);
					carte.setCible(meilleurJoueur);
					return carte;
				}
			}
		}
		return null;
	}

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// impatient.
		return "impatient";
	}
}
