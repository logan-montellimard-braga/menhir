package fr.bragabresolin.menhir;

import java.util.*;

public interface ComportementStrategy {

	public abstract CarteAllie choisirCarteAllie(ArrayList<Joueur> contexte,
			ArrayList<CarteAllie> cartes);

	public abstract CarteIngredient choisirCarteIngredient(
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes);

}
