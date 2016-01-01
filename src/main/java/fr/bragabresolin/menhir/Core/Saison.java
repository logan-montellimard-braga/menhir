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

	/**
	 * Nom d'affichage de la saison
	 */
	private String realName;

	/**
	 * Constructeur de l'énumération, avec le nom d'affichage de la saison.
	 *
	 * @param realName Le nom d'affichage de la saison
	 */
	private Saison(String realName) {
		this.realName = realName;
	}

	/**
	 * Retourne le nom d'affichage de la saison.
	 *
	 * @return Le nom d'affichage de la saison
	 */
	public String toString() {
		return this.realName;
	}
}
