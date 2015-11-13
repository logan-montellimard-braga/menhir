package fr.bragabresolin.menhir;

public class JoueurPhysique extends Joueur {

	public JoueurPhysique() {
		super();
	}

	public JoueurPhysique(String nom, int age) {
		super(nom, age);
	}
	
	public void jouer(Joueur[] contexte, Saison saisonActuelle) {
		return;
	}

	protected CarteAllie choisirJouerAllie() {
		return null;
	}

	public String toString() {
		String str = super.toString();
		return this.nom + " " + str;
	}
}
