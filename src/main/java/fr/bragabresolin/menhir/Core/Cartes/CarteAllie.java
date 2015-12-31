package fr.bragabresolin.menhir.Core.Cartes;

import java.util.EnumMap;
import fr.bragabresolin.menhir.Core.Saison;

/**
 * Classe abstraite représentant une carte allié du jeu du Menhir.
 * 
 * Une carte allié est une carte disposant d'une matrice de forces de son effet,
 * encodant dans l'objet la puissance numérique de l'effet de la carte en 
 * fonction de la saison dans laquelle est est exécutée.
 * 
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
 */
public abstract class CarteAllie extends Carte {
	
	/**
	 * Représente la force de l'action de la carte en fonction de la saison. 
	 * 
	 * Une valeur nulle n'est possible que lorsque la carte est en cours 
	 * d'initialisation (par exemple par un générateur extérieur). Elle n'est 
	 * pas utilisable en l'état.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	protected EnumMap<Saison, Integer> matriceForces;
	
	/**
	 * Réalise l'action de la carte;
	 * @param saisonActuelle La saison en cours pour l'effet de la carte.
	 */
	public void executer(Saison saisonActuelle) {
		this.dejaJouee = true;
		this.setChanged();
		this.notifyObservers();
	}

	public void setMatrice(EnumMap<Saison, Integer> m) {
		this.matriceForces = m;
	}

	public EnumMap<Saison, Integer> getMatrice() {
		return this.matriceForces;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		int offset = 0;
		for (Saison s : Saison.values())
			if (s.toString().length() > offset)
				offset = s.toString().length();

		builder.append(super.toString());

		builder.append(" {{_}} (allié)")
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
