package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;

/**
 * Classe de stratégie pour joueur virtuel, définissant un comportement de 
 * "transformateur" (impatient) pour le joueur qui l'utilise.
 * 
 * Un joueur impatient est un joueur qui cherche à transformer ses graines en 
 * menhir le plus rapidement possible, dès qu'il en dispose. Dans cette optique,
 * il peut se permettre d'être plus agressif avec ses cartes allié et donc de 
 * jouer la Taupe Géante lorsque possible.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
 */
public class TransformateurStrategy implements ComportementStrategy {

	/**
	 * Demande à la stratégie impatient de déterminer quelle carte ingrédient
	 * jouer, selon le contexte passé.
	 * 
	 * La stratégie impatient pousse le joueur à jouer l'engrais pour faire 
	 * pousser ses graines dès qu'il en a, sinon, choisir le géant pour en 
	 * récupérer.
	 * Il choisit ensuite la carte avec la meilleure valeur d'action sur le 
	 * moment.
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

	/**
	 * Demande à la stratégie impatient de déterminer quelle carte allié jouer,
	 * selon le contexte passé.
	 * 
	 * La stratégie impatient pousse le joueur à être agressif et jouer ses 
	 * taupes sur le joueur le plus avancé. S'il a un chien et assez de graines 
	 * pour que la carte ne soit pas gaspillée, il le joue.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes allié parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
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

	/**
	 * Représentation textuelle de la stratégie.
	 * On affiche juste le nom folklorique de la stratégie.
	 * 
	 * @return Le nom de la stratégie
	 */
	public String toString() {
		return "impatient";
	}
}
