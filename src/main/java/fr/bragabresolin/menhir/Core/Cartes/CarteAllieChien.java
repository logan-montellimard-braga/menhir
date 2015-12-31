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

	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		this.origine.setNombreGrainesProteges(forceEffet);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.CARTE_EXEC, forceEffet));

		super.executer(saisonActuelle);
	}

	public String toString() {
		String template = super.toString();
		template = template.replace("{{_}}", "Chien");

		return template;
	}
}
