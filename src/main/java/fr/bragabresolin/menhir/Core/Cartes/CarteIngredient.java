package fr.bragabresolin.menhir.Core.Cartes;

import java.util.EnumMap;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;

public class CarteIngredient extends Carte {
	
	/**
	 * Représente les forces des actions de la carte en fonction de la saison. 
	 */
	private EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matriceForces;

	/**
	 * Nom de la carte qui sert uniquement à l'affichage (ex : chant de sirène).
	 */
	private String nomCarte;

	/**
	 * Une action choisie avant de jouer la carte  parmi les actions possibles 
	 * des cartes ingredients : géant, farfadet, engrais.
	 */
	private ActionIngredient action;

	public CarteIngredient(String nomCarte) {
		this.nomCarte = nomCarte;
		this.matriceForces = new EnumMap<Saison, EnumMap<ActionIngredient, Integer>>(Saison.class);
	}

	/**
	 * Getter of the property <tt>nomCarte</tt>
	 * 
	 * @return Returns the nomCarte.
	 * 
	 */
	public String getNomCarte() {
		return nomCarte;
	}

	/**
	 * Setter of the property <tt>nomCarte</tt>
	 * 
	 * @param nomCarte
	 *            The nomCarte to set.
	 * 
	 */
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	
	/**
	 * Réalise l'action de la carte selon son attribut action.
	 * @param saisonActuelle La saison en cours pour l'effet de la carte.
	 * @see ActionIngredient
	 * 
	 */
	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle).get(this.action);
		Joueur joueurOrigine = this.origine;
		
		switch (this.action) {
		case GEANT:
			joueurOrigine.augmenterGraines(forceEffet);
			this.setChanged();
			this.notifyObservers(joueurOrigine.getNom() + " récupère " + forceEffet + " graines.");
			break;
			
		case ENGRAIS:
			int grainesTransformees = joueurOrigine.augmenterMehnirs(forceEffet);
			this.setChanged();
			this.notifyObservers(joueurOrigine.getNom() + " fait pousser " + grainesTransformees + " menhirs.");
			joueurOrigine.diminuerGraines(grainesTransformees);
			break;
			
		case FARFADET:
			int grainesVolees = this.cible.subirVolGraines(forceEffet);
			joueurOrigine.augmenterGraines(grainesVolees);
			this.setChanged();
			this.notifyObservers("Le joueur " + this.origine.getNom() + " vole " + grainesVolees + " graines au joueur " + this.cible.getNom() + ".");
			break;
		}
		this.dejaJouee = true;
		
		this.setChanged();
		this.notifyObservers();
	}

	public void setMatrice(EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matrice) {
		this.matriceForces = matrice;
	}

	public EnumMap<Saison, EnumMap<ActionIngredient, Integer>> getMatrice() {
		return this.matriceForces;
	}

	/**
	 * Getter of the property <tt>action</tt>
	 * 
	 * @return Returns the action.
	 * 
	 */

	public ActionIngredient getAction() {
		return action;
	}

	/**
	 * Setter of the property <tt>action</tt>
	 * 
	 * @param action
	 *            The action to set.
	 * 
	 */
	public void setAction(ActionIngredient action) {
		this.action = action;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		int offset = 0;
		for (Saison s : Saison.values())
			if (s.toString().length() > offset)
				offset = s.toString().length();

		builder.append(super.toString());

		builder.append(" " + this.nomCarte + " (ingrédient)")
			   .append("\n");

		String header = String.format("%" + offset + "s    ", "");
		for (ActionIngredient ag : ActionIngredient.values())
			header += String.format("%s  ", ag);
		builder.append(header)
			   .append("\n");

		for (Saison saison : matriceForces.keySet()) {
			builder.append(saison);
			builder.append(String.format("%" + (offset + 4 - saison.toString().length()) + "s", ""));
			int i = 0;
			for (ActionIngredient act : matriceForces.get(saison).keySet()) {
				builder.append(String.format("%" + (i + act.toString().length()) + "s", matriceForces.get(saison).get(act)));
				i = (i * 2 + 2) % 4;
			}
			builder.append("\n");
		}

		String resultat = builder.toString();
		return super.toString(40, resultat);
	}
}
