package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.*;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;

/**
 * Classe de stratégie pour joueur virtuel, définissant un comportement de 
 * "stockeur" (économe) pour le joueur qui l'utilise.
 * 
 * Un joueur économe est un joueur qui cherche à économiser le plus possible de 
 * graines, jusqu'à un seuil donné, avant de les faire pousser en menhirs. Dans 
 * cette logique, il cherche à protéger ses graines et jouera donc en priorité 
 * les Chiens de Garde dont il disposera.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
 */
public class StockeurStrategy implements ComportementStrategy {

	/**
	 * Représente le seuil à partir duquel la stratégie pousse le joueur à faire 
	 * pousser ses graines en menhirs.
	 */
	private static final int SEUIL_GRAINES = 6;

	/**
	 * Représente le seuil à partir duquel la stratégie pousse le joueur à 
	 * devenir un peu plus agressif.
	 */
	private static final int SEUIL_MENHIRS = 5;

	/**
	 * Demande à la stratégie économe de déterminer quelle carte ingrédient jouer,
	 * selon le contexte passé.
	 * 
	 * La stratégie économe pousse le joueur à accumuler ses graines pour les 
	 * faire pousser au dernier moment, avec la carte la plus avantageuse. La 
	 * stratégie ne vole (effet Farfadet) jamais de graines.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes ingrédient parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
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

	/**
	 * Demande à la stratégie de déterminer quelle carte allié jouer, selon le 
	 * contexte passé.
	 * 
	 * La stratégie économe pousse à protéger les graines (carte chien) 
	 * uniquement si on en a un nombre intéressant à protéger. Elle devient un 
	 * peu agressive en cas de carte taupe si elle juge que la partie est assez 
	 * avancée, en ciblant le joueur le plus avancé, s'il a assez de menhirs 
	 * pour que la carte ne soit pas gâchée.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes allié parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
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

	/**
	 * Représentation textuelle de la stratégie.
	 * On affiche juste le nom folklorique de la stratégie.
	 * 
	 * @return Le nom de la stratégie
	 */
	public String toString() {
		return "économe";
	}
}
