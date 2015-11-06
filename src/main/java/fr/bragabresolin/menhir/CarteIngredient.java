package fr.bragabresolin.menhir;

import java.util.EnumMap;

public class CarteIngredient extends menhirUmlClass.Carte {

	private EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matriceForces;

	/*
	 * (non-javadoc)
	 */
	private String nomCarte;

	/*
	 * (non-javadoc)
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

	public void executer() {

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
