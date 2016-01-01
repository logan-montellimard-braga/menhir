package fr.bragabresolin.menhir.Core.Cartes;

import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;

/**
 * Classe représentant une carte allié "Taupe Géante" du jeu du Menhir.
 * 
 * Une carte allié taupe est une carte allié dont l'effet d'exécution est 
 * de détruire les mehnirs du joueur ciblé.
 * 
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
 */
public class CarteAllieTaupe extends CarteAllie {

	/**
	 * Exécute l'effet de la carte.
	 * 
	 * Lorsqu'elle est exécutée, la carte Taupe Géante détruit les menhirs de 
	 * son joueur cible, puis délègue les actions standards à la méthode de la
	 * classe mère.
	 * On fait attention à stocker le nombre effectifs de menhirs détruits pour 
	 * le communiquer aux observateurs, car ce nombre peut être différent du 
	 * nombre de l'effet de la carte, par exemple si la cible a moins de 
	 * menhirs que ce que la carte peut lui détruire.
	 *
	 * @param saisonActuelle La saison en cours dans laquelle exécuter la carte
	 * @see fr.bragabresolin.menhir.Core.Cartes.CarteAllie
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		int nombreMenhirsEnleves = this.cible.diminuerMenhirs(forceEffet);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.CARTE_EXEC, nombreMenhirsEnleves));
		
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
		template = template.replace("{{_}}", "Taupe");

		return template;
	}
}
