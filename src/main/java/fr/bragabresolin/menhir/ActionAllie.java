package fr.bragabresolin.menhir;

public enum ActionAllie {
	CHIEN("Chien de garde"),
	TAUPE("Taupe géante");

	private String realName;
	private ActionAllie(String realName) {
		this.realName = realName;
	}

	public String toString() {
		return this.realName;
	}
}
