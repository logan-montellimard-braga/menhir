package fr.bragabresolin.menhir.Core.Cartes;

import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;

/**
 * Classe représentant une carte allié "Chien de Garde" du jeu du Menhir.
 * 
 * Une carte allié chien est une carte allié dont l'effet d'exécution est 
 * d'augmenter le nombre de graines protégées du joueur qui lance la carte.
 * 
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
 */
public class CarteAllieChien extends CarteAllie {

	/**
	 * Exécute l'effet de la carte.
	 * 
	 * Lorsqu'elle est exécutée, la carte Chien de Garde change le nombre de 
	 * graines protégées du joueur qui l'a lancée, puis on délègue le reste des 
	 * actions standards à la méthode de la classe mère.
	 *
	 * @param saisonActuelle La saison en cours dans laquelle exécuter la carte
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		this.origine.setNombreGrainesProteges(forceEffet);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.CARTE_EXEC, forceEffet));

		super.executer(saisonActuelle);
	}

	/**
	 * Produit une représentation en chaîne de caractère de la carte.
	 *
	 * La représentation est simplement composée du squelette d'affichage de la 
	 * classe mère, dont les variables de nom sont remplacées par le nom de 
	 * cette carte.
	 * 
	 * @return La représentation textuelle de la carte
	 */
	public String toString() {
		String template = super.toString();
		template = template.replace("{{_}}", "Chien");

		return template;
	}
}
