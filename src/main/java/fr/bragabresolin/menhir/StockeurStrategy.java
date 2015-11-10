package fr.bragabresolin.menhir;

public class StockeurStrategy implements ComportementStrategy {
	public CarteIngredient choisirCarteIngredient(
			Joueur[] contexte, CarteIngredient[] main) { return null; }

	public CarteAllie choisirCarteAllie(
			Joueur[] contexte, CarteAllie[] main) { return null; }

	public String toString() {
		// Représentation visuelle : cette stratégie est associée à un joueur
		// économe.
		return "économe";
	}
}
