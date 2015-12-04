package fr.bragabresolin.menhir;

import java.util.*;

public class JoueurPhysique extends Joueur {

	public JoueurPhysique() {
		super();
	}

	public JoueurPhysique(String nom, int age) {
		super(nom, age);
	}
	
	public void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle) {
		// Pour l'instant, on utilise uniquement l'implémentation de la classe 
		// fille JoueurPhysiqueLigneCommande.
		// Lorsque l'on fera un vrai MVC avec interface, cette méthode sera 
		// utilisée et exécutera la carte choisie.
	}

	protected CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte) {
		return null;
	}

	public boolean veutPiocherCarteAllie() {
		return true;
	}

	public void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle) {
	}

	public String toString() {
		String str = super.toString();
		return this.nom + " " + str;
	}
}
