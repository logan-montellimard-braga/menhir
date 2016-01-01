package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Cartes.*;

/**
 * Classe de stratégie pour joueur virtuel, définissant un comportement de 
 * "voleur" pour le joueur qui l'utilise.
 * 
 * Un joueur voleur est un joueur agressif qui cherche à voler les autres 
 * joueurs pour obtenir ses graines (et les transformer une fois un seuil 
 * atteint). Il tente également de détruire les menhirs des meilleurs joueurs 
 * lorsque possible, avec ses Taupes Géantes.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.Comportements.ComportementStrategy
 */
public class VoleurStrategy implements ComportementStrategy {

	/**
	 * Représente le seuil à partir duquel la stratégie pousse le joueur à faire 
	 * pousser ses graines en menhirs.
	 */
	private static final int SEUIL_GRAINES = 4;

	/**
	 * Demande à la stratégie voleur de déterminer quelle carte ingrédient jouer,
	 * selon le contexte passé.
	 * 
	 * La stratégie voleur pousse le joueur à obtenir toutes ses graines par le 
	 * biais des farfadets, en les volant aux autres. Il ne joue jamais le 
	 * géant. Quand il a atteint un nombre satisfaisant de graines, il tente de 
	 * jouer l'engrais pour les faire pousser.
	 * S'il joue le farfadet, il cible toujours le joueur avec le meilleur 
	 * nombre de graines, et sans chien de garde les protégeant.
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

	/**
	 * Demande à la stratégie voleur de déterminer quelle carte allié jouer,
	 * selon le contexte passé.
	 * 
	 * La stratégie voleur pousse le joueur à être agressif et jouer ses taupes 
	 * sur le joueur le plus avancé.
	 * Si la carte allié en main est un chien, on la joue si elle protège au 
	 * moins une graine, quitte à louper un meilleur effet plus tard.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes allié parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
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

	/**
	 * Représentation textuelle de la stratégie.
	 * On affiche juste le nom folklorique de la stratégie.
	 * 
	 * @return Le nom de la stratégie
	 */
	public String toString() {
		return "voleur";
	}
}
