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
		Joueur joueurOrigine = this.getOrigine();
		
		switch (this.action) {
		case GEANT:
			joueurOrigine.augmenterGraines(forceEffet);
			
		case ENGRAIS:
			int grainesTransformees = joueurOrigine.augmenterMehnirs(forceEffet);
			joueurOrigine.diminuerGraines(grainesTransformees);
			
		case FARFADET:
			int grainesVolees = this.getCible().subirVolGraines(forceEffet);
			joueurOrigine.augmenterGraines(grainesVolees);
		}

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

}
