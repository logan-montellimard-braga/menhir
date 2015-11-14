package fr.bragabresolin.menhir;

import java.util.EnumMap;
import java.util.Arrays;

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
			this.origine.nombreGrainesProteges = forceEffet;
			break;
			
		case TAUPE:
			this.cible.diminuerMenhirs(forceEffet);
			break;
		}
		this.dejaJouee = true;
	}

	public void setMatrice(EnumMap<Saison, Integer> m) {
		this.matriceForces = m;
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

	public String toString() {
		StringBuilder builder = new StringBuilder();

		int offset = 0;
		for (Saison s : Saison.values())
			if (s.toString().length() > offset)
				offset = s.toString().length();

		builder.append(super.toString());

		builder.append(" " + this.action + " (allié)")
			   .append("\n");

		for (Saison saison : this.matriceForces.keySet()) {
			builder.append(saison)
				   .append(String.format("%" + (offset + 2 - saison.toString().length()) + "s", ""))
				   .append(String.format("%5d", this.matriceForces.get(saison)))
				   .append("\n");
		}

		String resultat = builder.toString();
		return super.toString(30, resultat);
	}
}
