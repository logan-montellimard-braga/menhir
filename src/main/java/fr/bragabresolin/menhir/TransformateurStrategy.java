package fr.bragabresolin.menhir;

import java.util.*;

public class TransformateurStrategy implements ComportementStrategy {

	public CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes) {
		return null;
	}

	public CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteAllie> cartes) {
		return null;
	}

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// impatient.
		return "impatient";
	}
}
