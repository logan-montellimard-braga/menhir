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

	public abstract CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle, ArrayList<Joueur> contexte,
			ArrayList<CarteAllie> cartes);

	public abstract CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes);

}
