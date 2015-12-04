package fr.bragabresolin.menhir;

import java.util.*;

public class JoueurPhysiqueLigneCommande extends JoueurPhysique {

	public JoueurPhysiqueLigneCommande() {
		super();
	}

	public JoueurPhysiqueLigneCommande(String nom, int age) {
		super(nom, age);
	}
	
	public void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle) {
		InterfaceLigneCommande display = InterfaceLigneCommande.getInstance();
		System.out.println("Rappel : " + saisonActuelle.toString().toUpperCase());
		System.out.println("Voici vos cartes en main :");

		List<CarteIngredient> cartesIng = new ArrayList<CarteIngredient>();

		for (int i = 0, j = 1; i < this.cartes.size(); i++) {
			if (!this.cartes.get(i).getDejaJouee() && this.cartes.get(i) instanceof CarteIngredient) {
				cartesIng.add((CarteIngredient) this.cartes.get(i));
				System.out.println(j + ")\n" + this.cartes.get(i) + "\n");
				j++;
			}
		}

		int choix = display.demanderNombre("Quelle carte voulez-vous jouer ?",
				1, cartesIng.size());
		CarteIngredient carteChoisie = cartesIng.get(choix - 1);
		System.out.println("Vous avez choisi la carte " + carteChoisie.getNomCarte() + ".");

		System.out.print("\nActions disponibles : ");
		for (ActionIngredient ai : ActionIngredient.values())
			System.out.print(" (" + (ai.ordinal() + 1) + ") " + ai + "  ");
		int choixAction = display.demanderNombre("Quelle action voulez-vous jouer ?", 1, ActionIngredient.values().length);
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
				numJoueur = display.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, contexte.size());
			}
			Joueur cible = contexte.get(numJoueur - 1);
			System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
			carteChoisie.setCible(cible);
		}

		carteChoisie.executer(saisonActuelle);

		if (partieAvancee) {
			CarteAllie carteAllie = this.choisirJouerAllie(saisonActuelle, contexte);
			if (carteAllie != null) carteAllie.executer(saisonActuelle);
		}
	}

	protected CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte) {
		InterfaceLigneCommande display = InterfaceLigneCommande.getInstance();
		List<CarteAllie> cartesAllie = new ArrayList<CarteAllie>();
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee() && carte instanceof CarteAllie)
				cartesAllie.add((CarteAllie) carte);

		if (cartesAllie.size() == 0) return null;

		System.out.println("Voici vos cartes allié en main :");
		for (Carte carte : cartesAllie)
			System.out.println((cartesAllie.indexOf(carte) + 1) + ")\n" + carte + "\n");
		boolean veutJouer = display.demanderBool("Voulez-vous jouer une carte allié ?");
		if (veutJouer) {
			int choix = display.demanderNombre("Quelle carte voulez-vous jouer ?",
					1, cartesAllie.size());
			CarteAllie carteChoisie = cartesAllie.get(choix - 1);
			carteChoisie.setOrigine(this);

			if (carteChoisie instanceof CarteAllieTaupe) {
				System.out.println("\n\nVoici les joueurs de cette partie :\n");
				for (int i = 0; i < contexte.size(); i++)
					System.out.println((i + 1) + ") " + contexte.get(i) + "\n");

				int numSelf = contexte.indexOf(this);
				int numJoueur = numSelf + 1;
				while (numSelf == (numJoueur - 1)) {
					numJoueur = display.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, contexte.size());
				}
				Joueur cible = contexte.get(numJoueur - 1);
				System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");
				carteChoisie.setCible(cible);
			}

			return carteChoisie;
		}
		return null;
	}

	public void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle) {
		InterfaceLigneCommande display = InterfaceLigneCommande.getInstance();
		List<CarteAllie> cartesAllieT = new ArrayList<CarteAllie>();
		for (Carte carte : this.cartes)
			if (!carte.getDejaJouee() && carte instanceof CarteAllieTaupe)
				cartesAllieT.add((CarteAllie) carte);

		if (cartesAllieT.size() == 0) return;

		CarteAllie carte = cartesAllieT.get(0);

		System.out.println("Vous avez une carte taupe en main disponible.");
		System.out.println("Vous pouvez la jouer pendant le tour de votre adversaire.");
		System.out.println(carte);

		boolean veutJouer = display.demanderBool("Voulez-vous la jouer ?");
		if (veutJouer) {
			System.out.println("\n\nVoici les joueurs de cette partie :\n");
			for (int i = 0; i < contexte.size(); i++)
				System.out.println((i + 1) + ") " + contexte.get(i) + "\n");

			int numSelf = contexte.indexOf(this);
			int numJoueur = numSelf + 1;
			while (numSelf == (numJoueur - 1)) {
				numJoueur = display.demanderNombre("Quel joueur voulez-vous cibler (pas vous-même !) ?", 1, contexte.size());
			}
			Joueur cible = contexte.get(numJoueur - 1);
			System.out.println("Vous avez ciblé le joueur " + cible.getNom() + ".");

			this.setChanged();
			this.notifyObservers(this.getNom() + " lance une taupe pendant le tour de son adversaire !");

			carte.setOrigine(this);
			carte.setCible(cible);
			carte.executer(saisonActuelle);
		}
	}

	public boolean veutPiocherCarteAllie() {
		return InterfaceLigneCommande.getInstance().demanderBool("Voulez-vous piocher une carte allié ?");
	}
}
