package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;

/**
 * Interface définissant le contrat qu'une classe doit implémenter pour être 
 * utilisée en tant que fournisseur de comportement par une classe 
 * consommatrice, par exemple un JoueurVirtuel.
 * 
 * Cette interface stipule qu'un comportement est au moins défini par sa 
 * capacité à choisir une carte allié et une carte ingrédient, selon le 
 * contexte.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.JoueurVirtuel
 */
public interface ComportementStrategy {

	/**
	 * Demande à la stratégie de déterminer quelle carte allié jouer, selon le 
	 * contexte passé.
	 * 
	 * Renvoit null si la stratégie décide de ne pas jouer de carte allié pour 
	 * le moment.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes allié parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
	public abstract CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle, ArrayList<Joueur> contexte,
			ArrayList<CarteAllie> cartes);

	/**
	 * Demande à la stratégie de déterminer quelle carte ingrédient jouer, selon le 
	 * contexte passé.
	 * 
	 * Renvoit forcément une carte ingrédient.
	 *
	 * @param joueur Le joueur virtuel qui appelle la stratégie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte La liste des joueurs de la partie
	 * @param cartes Les cartes ingrédient parmi lesquels choisir une carte
	 * @return La carte choisie
	 */
	public abstract CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes);

}
