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
		System.out.println("Voici vos cartes en main :");

		List<CarteIngredient> cartesIng = new ArrayList<CarteIngredient>();

		for (int i = 0, j = 1; i < this.cartes.size(); i++) {
			if (!this.cartes.get(i).getDejaJouee() && this.cartes.get(i) instanceof CarteIngredient) {
				cartesIng.add((CarteIngredient) this.cartes.get(i));
				System.out.println(j + ")\n" + this.cartes.get(i) + "\n");
				j++;
			}
		}

		int choix = CLIUtils.demanderNombre("Quelle carte voulez-vous jouer ?",
				1, cartesIng.size());
		CarteIngredient carteChoisie = cartesIng.get(choix - 1);
		System.out.println("Vous avez choisi la carte " + carteChoisie.getNomCarte() + ".");

		System.out.print("\nActions disponibles : ");
		for (ActionIngredient ai : ActionIngredient.values())
			System.out.print(" (" + (ai.ordinal() + 1) + ") " + ai + "  ");
		int choixAction = CLIUtils.demanderNombre("Quelle action voulez-vous jouer ?", 1, ActionIngredient.values().length);
		ActionIngredient action = ActionIngredient.values()[choixAction - 1];
		System.out.println("Vous avez choisi l'effet " + action + ".");

		carteChoisie.setOrigine(this);
		carteChoisie.setAction(action);

		if (action == ActionIngredient.FARFADET) {
			System.out.println("\n\nVoici les joueurs de cette partie :\n");
			for (int i = 0; i < contexte.size(); i++)
				System.out.println((i + 1) + ") " + contexte.get(i) + "\n");

			int numSelf = contexte.indexOf(this);
			int numJoueur = numSelf + 1;
			while (numSelf == (numJoueur - 1)) {
				numJoueur = CLIUtils.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, contexte.size());
			}
			Joueur cible = contexte.get(numJoueur - 1);
			System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
			carteChoisie.setCible(cible);
		}

		carteChoisie.executer(saisonActuelle);

		if (partieAvancee) {
			CarteAllie carteAllie = this.choisirJouerAllie(contexte);
			if (carteAllie != null) carteAllie.executer(saisonActuelle);
		}
	}

	protected CarteAllie choisirJouerAllie(ArrayList<Joueur> contexte) {
		List<CarteAllie> cartesAllie = new ArrayList<CarteAllie>();
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee() && carte instanceof CarteAllie)
				cartesAllie.add((CarteAllie) carte);

		if (cartesAllie.size() == 0) return null;

		System.out.println("Voici vos cartes allié en main :");
		for (Carte carte : cartesAllie)
			System.out.println((cartesAllie.indexOf(carte) + 1) + ")\n" + carte + "\n");
		boolean veutJouer = CLIUtils.demanderBool("Voulez-vous jouer une carte allié ?");
		if (veutJouer) {
			int choix = CLIUtils.demanderNombre("Quelle carte voulez-vous jouer ?",
					1, cartesAllie.size());
			CarteAllie carteChoisie = cartesAllie.get(choix - 1);
			carteChoisie.setOrigine(this);

			if (carteChoisie.getAction() == ActionAllie.TAUPE) {
				System.out.println("\n\nVoici les joueurs de cette partie :\n");
				for (int i = 0; i < contexte.size(); i++)
					System.out.println((i + 1) + ") " + contexte.get(i) + "\n");

				int numSelf = contexte.indexOf(this);
				int numJoueur = numSelf + 1;
				while (numSelf == (numJoueur - 1)) {
					numJoueur = CLIUtils.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, contexte.size());
				}
				Joueur cible = contexte.get(numJoueur - 1);
				System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
				carteChoisie.setCible(cible);
			}

			return carteChoisie;
		}
		return null;
	}

	public boolean veutPiocherCarteAllie() {
		return CLIUtils.demanderBool("Voulez-vous piocher une carte allié ?");
	}

	public String toString() {
		String str = super.toString();
		return this.nom + " " + str;
	}
}
