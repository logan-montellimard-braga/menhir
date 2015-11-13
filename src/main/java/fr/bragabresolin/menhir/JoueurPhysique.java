package fr.bragabresolin.menhir;

public class JoueurPhysique extends Joueur {

	public JoueurPhysique() {
		super();
	}

	public JoueurPhysique(int age) {
		super(age);
	}
	
	public void jouer(Joueur[] contexte, Saison saisonActuelle) {
		return;
	}

	protected CarteAllie choisirJouerAllie() {
		return null;
	}

	public String toString() {
		String str = super.toString();
		return "Joueur Physique " + str;
	}
}
