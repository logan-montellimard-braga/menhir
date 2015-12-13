package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.*;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;

public class StockeurStrategy implements ComportementStrategy {
	private static final int SEUIL_GRAINES = 6;
	private static final int SEUIL_MENHIRS = 5;

	public CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes) {

		CarteIngredient carteChoisie;
		ActionIngredient action;
		// On récolte un maximum de graines
		// Quand on en a un certain nombre, on les transforme
		if (joueur.getNombreGraines() >= SEUIL_GRAINES) {
			// On fait évoluer les graines en menhirs
			action = ActionIngredient.ENGRAIS;
		} else {
			// On prend des graines
			action = ActionIngredient.GEANT;
		}

		// On choisit la carte avec le meilleur effet voulu
		CarteIngredient meilleurEffet = null;
		for (CarteIngredient carte : cartes) {
			if (meilleurEffet == null) meilleurEffet = carte;
			else if (carte.getMatrice().get(saisonActuelle).get(action)
					> meilleurEffet.getMatrice().get(saisonActuelle).get(action))
				meilleurEffet = carte;
		}
		carteChoisie = meilleurEffet;
		carteChoisie.setAction(action);
		carteChoisie.setOrigine(joueur);
		return carteChoisie;
	}

	public CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteAllie> cartes) {
		// On protège toujours ses graines tant que possible
		// si on en a un nombre intéressant
		if (joueur.getNombreGraines() >= SEUIL_GRAINES / 2) {
			// si carte chien, on la joue
			for (CarteAllie carte : cartes)
				if (carte instanceof CarteAllieChien) {
					carte.setOrigine(joueur);
					return carte;
				}
		} else if (joueur.getNombreMenhirs() >= SEUIL_MENHIRS) {
			// si carte taupe, on la joue
			// si on a déjà quelques menhirs (on s'assure de ne pas tout jouer 
			// tout de suite)
			// On s'assure également que le joueur a assez de menhirs à détruire
			for (CarteAllie carte : cartes)
				if (carte instanceof CarteAllieTaupe) {
					carte.setOrigine(joueur);

					Joueur meilleurJoueur = new JoueurVirtuel("", 8);
					for (Joueur j : contexte)
						if (j != joueur)
							if (j.getNombreMenhirs() >= meilleurJoueur.getNombreMenhirs())
								meilleurJoueur = j;
					if (carte.getMatrice().get(saisonActuelle) > 1 && meilleurJoueur.getNombreMenhirs() >= carte.getMatrice().get(saisonActuelle)) {
						carte.setOrigine(joueur);
						carte.setCible(meilleurJoueur);
						return carte;
					}
				}
		}

		// Sinon, on ne joue rien
		return null;
	}

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// économe.
		return "économe";
	}
}
