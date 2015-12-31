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

	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		int nombreMenhirsEnleves = this.cible.diminuerMenhirs(forceEffet);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.CARTE_EXEC, nombreMenhirsEnleves));
		
		super.executer(saisonActuelle);
	}

	public String toString() {
		String template = super.toString();
		template = template.replace("{{_}}", "Taupe");

		return template;
	}
}
