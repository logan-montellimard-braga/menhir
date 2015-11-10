package fr.bragabresolin.menhir;

import java.util.EnumMap;

public class CarteAllie extends Carte {
	
	
	/**
	 * 
	 * @param action
	 */
	public CarteAllie(ActionAllie action) {
		super();
		this.action = action;
	}

	/**
	 * Représente la force de l'action de la carte en fonction de la saison. 
	 */
	private EnumMap<Saison, Integer> matriceForces;
	
	/**
	 * Defini l'action de la carte (taupe ou chien). Ce champs est spécifié à 
	 * la création et n'est pas modifiable par la suite.
	 */
	private ActionAllie action;
	
	/**
	 * Réalise l'action de la carte selon son attribut action.
	 * @param saisonActuelle La saison en cours pour l'effet de la carte.
	 */
	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		switch (this.action){
		case CHIEN:
			
		case TAUPE:
			
		}
	}

	/**
	 * Getter of the property <tt>action</tt>
	 * 
	 * @return Returns the action.
	 * 
	 */

	public ActionAllie getAction() {
		return action;
	}

}
