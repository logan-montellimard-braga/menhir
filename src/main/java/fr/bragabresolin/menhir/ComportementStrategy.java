package fr.bragabresolin.menhir;

import java.util.*;

public interface ComportementStrategy {

	public abstract CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle, ArrayList<Joueur> contexte,
			ArrayList<CarteAllie> cartes);

	public abstract CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes);

}
