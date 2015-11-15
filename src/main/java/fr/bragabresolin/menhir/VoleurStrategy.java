package fr.bragabresolin.menhir;

import java.util.*;

public class VoleurStrategy implements ComportementStrategy {

	public CarteIngredient choisirCarteIngredient(
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes) {
		return null;
	}

	public CarteAllie choisirCarteAllie(
			ArrayList<Joueur> contexte, ArrayList<CarteAllie> cartes) {
		return null;
	}

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// voleur.
		return "voleur";
	}
}
