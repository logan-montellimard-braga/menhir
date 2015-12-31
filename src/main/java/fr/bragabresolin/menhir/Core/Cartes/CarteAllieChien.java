package fr.bragabresolin.menhir.Core.Cartes;

import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;

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
