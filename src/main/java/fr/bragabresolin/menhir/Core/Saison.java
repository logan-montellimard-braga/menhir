package fr.bragabresolin.menhir.Core;

/**
 * Enumération pour les saisons utilisées dans le jeu.
 * Chaque membre de cette énumération a un champ String realName, permettant 
 * d'encoder le nom visuel de la saison (qui peut varier de son nom 
 * d'énumération : majuscules, accents, ...) pour utilisation en String.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 */
public enum Saison {
	PRINTEMPS("Printemps"),
	ETE("Été"),
	AUTOMNE("Automne"),
	HIVER("Hiver");

	private String realName;
	private Saison(String realName) {
		this.realName = realName;
	}

	public String toString() {
		return this.realName;
	}
}
