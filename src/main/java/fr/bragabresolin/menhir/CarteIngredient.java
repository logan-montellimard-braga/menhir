package fr.bragabresolin.menhir;

import java.util.EnumMap;

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
	 * @see action
	 * @see super.origine
	 * @see super.cible 
	 * 
	 */
	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle).get(this.action);
		Joueur joueurOrigine = this.origine;
		
		switch (this.action) {
		case GEANT:
			joueurOrigine.augmenterGraines(forceEffet);
			
		case ENGRAIS:
			int grainesTransformees = joueurOrigine.augmenterMehnirs(forceEffet);
			joueurOrigine.diminuerGraines(grainesTransformees);
			
		case FARFADET:
			int grainesVolees = this.cible.subirVolGraines(forceEffet);
			joueurOrigine.augmenterGraines(grainesVolees);
		}
		this.dejaJouee = true;
	}

	public void setMatrice(EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matrice) {
		this.matriceForces = matrice;
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
			builder.append("  " + saison);
			builder.append(String.format("%" + (offset + 2 - saison.toString().length()) + "s", ""));
			int i = 0;
			for (ActionIngredient act : matriceForces.get(saison).keySet()) {
				builder.append(String.format("%" + (i + act.toString().length()) + "s", matriceForces.get(saison).get(act)));
				i = (i * 2 + 2) % 4;
			}
			builder.append("\n");
		}

		String resultat = builder.toString();
		return super.toString(35, resultat);
	}
}
