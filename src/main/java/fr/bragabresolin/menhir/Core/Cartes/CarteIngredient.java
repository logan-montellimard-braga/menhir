package fr.bragabresolin.menhir.Core.Cartes;

import java.util.EnumMap;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;

/**
 * Classe représentant une carte ingrédient du jeu du Menhir.
 * 
 * Une carte ingrédient est une carte qui dispose en plus d'un nom, d'une 
 * action/facette (eg Farfadet, Engrais, Géant) et d'une matrice de forces à 
 * deux dimensions, spécifiant la force de l'effet de la carte selon la saison  
 * dans laquelle elle est exécutée et l'action choisie.
 * 
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
 */
public class CarteIngredient extends Carte {
	
	/**
	 * Représente les forces des actions de la carte en fonction de la saison. 
	 * 
	 * Une valeur nulle n'est possible que lorsque la carte est en cours 
	 * d'initialisation (par exemple par un générateur extérieur). Elle n'est 
	 * pas utilisable en l'état.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	private EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matriceForces;

	/**
	 * Nom de la carte, qui sert uniquement à l'affichage (ex : chant de sirène).
	 * Le nom n'est pas unique ; plusieurs cartes peuvent avoir le même nom 
	 * (elles n'ont généralement alors pas la même matrice des forces, mais rien 
	 * ne l'interdit).
	 */
	private String nomCarte;

	/**
	 * Une action choisie avant de jouer la carte parmi les actions possibles 
	 * des cartes ingredients : géant, farfadet, engrais.
	 * 
	 * Une valeur nulle est possible avant l'exécution de la carte, mais 
	 * provoquera la levée d'une exception à l'exécution si l'action n'est 
	 * toujours pas spécifiée.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
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
			this.notifyObservers(new Message(MessageType.CARTE_EXEC, forceEffet));
			break;
			
		case ENGRAIS:
			int grainesTransformees = joueurOrigine.augmenterMehnirs(forceEffet);
			this.setChanged();
			this.notifyObservers(new Message(MessageType.CARTE_EXEC, grainesTransformees));
			joueurOrigine.diminuerGraines(grainesTransformees);
			break;
			
		case FARFADET:
			int grainesVolees = this.cible.subirVolGraines(forceEffet);
			joueurOrigine.augmenterGraines(grainesVolees);
			this.setChanged();
			this.notifyObservers(new Message(MessageType.CARTE_EXEC, grainesVolees));
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
