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
	 * Réalise l'action de la carte.
	 * 
	 * L'effet est entièrement dépendant du type de carte allié, et donc des 
	 * classes filles qui offriront une surdéfinition de cette méthode.
	 * Les actions standards à toutes les cartes allié sont de marquer la carte 
	 * comme déjà jouée, et de notifier les observateurs.
	 *
	 * @param saisonActuelle La saison en cours dans laquelle exécuter la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public void executer(Saison saisonActuelle) {
		this.dejaJouee = true;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Mutateur de la matrice des forces de la carte.
	 * 
	 * La matrice des forces est généralement spécifiée une fois à la génération 
	 * de la carte, et n'est pas amenée à varier au cours du jeu.
	 *
	 * @param m La matrice à une dimension des forces de la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public void setMatrice(EnumMap<Saison, Integer> m) {
		this.matriceForces = m;
	}

	/**
	 * Retourne la matrice des forces de la carte.
	 *
	 * @return La matrice à une dimension des forces de la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public EnumMap<Saison, Integer> getMatrice() {
		return this.matriceForces;
	}

	/**
	 * Produit une représentation en chaîne de caractère de la carte.
	 *
	 * La représentation entoure les informations de la carte d'une boîte UTF-8,
	 * et formate la matrice des forces de la carte en un tableau à 
	 * une dimension.
	 * 
	 * @return La représentation textuelle de la carte
	 */
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
