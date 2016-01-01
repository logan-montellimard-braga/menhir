package fr.bragabresolin.menhir.Core.Cartes;

/**
 * Enumération représentant les différentes facettes jouables d'une carte
 * ingrédient.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.CarteIngredient
 */
public enum ActionIngredient {
	GEANT("Géant"),
	ENGRAIS("Engrais"),
	FARFADET("Farfadet");

	/**
	 * Nom d'affichage de l'action
	 */
	private String realName;

	/**
	 * Constructeur de l'énumération, prenant le nom d'affichage formaté de 
	 * l'action
	 * 
	 * @param realName Le nom d'affichage de l'action
	 */
	private ActionIngredient(String realName) {
		this.realName = realName;
	}

	/**
	 * Retourne le nom d'affichage de l'action.
	 *
	 * @return Le nom d'affichage de l'action
	 */
	public String toString() {
		return this.realName;
	}
}
