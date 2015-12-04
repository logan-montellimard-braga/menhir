package fr.bragabresolin.menhir;

public class CarteAllieChien extends CarteAllie {

	public void executer(Saison saisonActuelle) {
		int forceEffet = this.matriceForces.get(saisonActuelle);
		this.origine.setNombreGrainesProteges(forceEffet);
		this.setChanged();
		this.notifyObservers(this.origine.getNom() + " appelle un chien protégeant " + forceEffet + " graines.");

		super.executer(saisonActuelle);
	}

	public String toString() {
		String template = super.toString();
		template = template.replace("{{_}}", "Chien");

		return template;
	}
}
