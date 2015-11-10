package fr.bragabresolin.menhir;

public class JoueurVirtuel extends Joueur {

	/*
	 * (non-javadoc)
	 */
	private ComportementStrategy comportementStrategy = null;

	/**
	 * Getter of the property <tt>comportementStrategy</tt>
	 * 
	 * @return Returns the comportementStrategy.
	 * 
	 */

	public ComportementStrategy getComportementStrategy() {
		return comportementStrategy;
	}

	/**
	 * Setter of the property <tt>comportementStrategy</tt>
	 * 
	 * @param comportementStrategy
	 *            The comportementStrategy to set.
	 * 
	 */
	public void setComportementStrategy(
			ComportementStrategy comportementStrategy) {
		this.comportementStrategy = comportementStrategy;
			}

	public void jouer(Joueur[] contexte, Saison saisonActuelle) {

	}

	protected CarteAllie choisirJouerAllie() {
		return null;
	}

	public String toString() {
		String str = super.toString();
		return "Joueur Virtuel <" + this.comportementStrategy.toString() +"> " + str;
	}
}
