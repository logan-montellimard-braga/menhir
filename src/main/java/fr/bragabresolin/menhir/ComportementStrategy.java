package fr.bragabresolin.menhir;

public interface ComportementStrategy {

	public abstract CarteAllie choisirCarteAllie(Joueur[] contexte,
			CarteAllie[] main);

	public abstract CarteIngredient choisirCarteIngredient(
			Joueur[] contexte, CarteIngredient[] main);

}
