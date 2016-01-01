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
	 * Retourne le nom de cette carte.
	 * 
	 * @return Le nom de la carte
	 */
	public String getNomCarte() {
		return nomCarte;
	}

	/**
	 * Mutateur pour le nom de cette carte.
	 * 
	 * @param nomCarte Le nom de la carte
	 */
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	
	/**
	 * Exécute l'effet de la carte.
	 *
	 * L'effet de la carte dépend conjointement de l'action choisie et de la 
	 * saison en cours ; la force de l'effet est alors obtenue par croisement de
	 * ces variables dans la matrice des forces de la carte.
	 * On fait attention à stocker, lorsque nécessaire, les nombres effectifs 
	 * des effets produits pour les communiquer aux observateurs, car ces 
	 * nombres peuvent être différents de la force de l'effet.
	 * Une fois la carte jouée, elle est marquée comme telle avec son attribut 
	 * dejaJouee et ne pourra plus être proposée avant d'être réinitialisée.
	 * 
	 * @param saisonActuelle La saison en cours dans laquelle exécuter la carte
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 * @see fr.bragabresolin.menhir.Core.Saison
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

	/**
	 * Mutateur de la matrice des forces de la carte.
	 * 
	 * La matrice des forces est généralement spécifiée une fois à la génération 
	 * de la carte, et n'est pas amenée à varier au cours du jeu.
	 *
	 * @param matrice La matrice à deux dimensions des forces de la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	public void setMatrice(EnumMap<Saison, EnumMap<ActionIngredient, Integer>> matrice) {
		this.matriceForces = matrice;
	}

	/**
	 * Retourne la matrice des forces de la carte.
	 *
	 * @return La matrice à deux dimensions des forces de la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	public EnumMap<Saison, EnumMap<ActionIngredient, Integer>> getMatrice() {
		return this.matriceForces;
	}

	/**
	 * Retourne l'action de la carte.
	 * 
	 * @return L'action de la carte
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	public ActionIngredient getAction() {
		return action;
	}

	/**
	 * Mutateur de l'action (facette) de la carte.
	 * 
	 * Spécifier l'action de la carte est nécessaire avant de l'exécuter, car 
	 * une carte s'exécute forcément dans le contexte d'une facette.
	 * 
	 * @param action L'action à enregistrer
	 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
	 */
	public void setAction(ActionIngredient action) {
		this.action = action;
	}

	/**
	 * Produit une représentation en chaîne de caractère de la carte.
	 *
	 * La représentation entoure les informations de la carte d'une boîte UTF-8,
	 * et formate en tableau la matrice des forces de la carte.
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
