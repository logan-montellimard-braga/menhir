package fr.bragabresolin.menhir;

import java.util.EnumMap;

public class CarteAllie extends Carte {

	private EnumMap<Saison, Integer> matriceForces;
	/*
	 * (non-javadoc)
	 */
	private ActionAllie action;
	
	/**
	 * @param saisonActuelle La saison en cours pour l'effet de la carte.
	 */
	public void executer(Saison saisonActuelle) {}

	/**
	 * Getter of the property <tt>action</tt>
	 * 
	 * @return Returns the action.
	 * 
	 */

	public ActionAllie getAction() {
		return action;
	}

	/**
	 * Setter of the property <tt>action</tt>
	 * 
	 * @param action
	 *            The action to set.
	 * 
	 */
	public void setAction(ActionAllie action) {
		this.action = action;
	}
}
