package fr.bragabresolin.menhir;

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
