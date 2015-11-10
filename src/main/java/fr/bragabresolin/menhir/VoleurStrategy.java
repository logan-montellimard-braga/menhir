package fr.bragabresolin.menhir;

public class VoleurStrategy implements ComportementStrategy {
	public CarteAllie choisirCarteAllie(
			Joueur[] contexte, CarteAllie[] main) { return null; }

	public CarteIngredient choisirCarteIngredient(
			Joueur[] contexte, CarteIngredient[] main) { return null; }

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// voleur.
		return "voleur";
	}
}
