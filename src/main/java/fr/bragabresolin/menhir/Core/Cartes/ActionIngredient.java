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

	private String realName;
	private ActionIngredient(String realName) {
		this.realName = realName;
	}

	public String toString() {
		return this.realName;
	}
}
