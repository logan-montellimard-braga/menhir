package fr.bragabresolin.menhir.Core.Cartes;

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
