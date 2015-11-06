package fr.bragabresolin.menhir;

import java.util.EnumMap;

public abstract class Carte {

	/*
	 * (non-javadoc)
	 */
	private Joueur origine;

	/*
	 * (non-javadoc)
	 */
	private Joueur cible;

	/*
	 * (non-javadoc)
	 */
	private boolean dejaJouee;

	public abstract void executer();

	/**
	 * Getter of the property <tt>origine</tt>
	 * 
	 * @return Returns the origine.
	 * 
	 */

	public Joueur getOrigine() {
		return origine;
	}

	/**
	 * Setter of the property <tt>origine</tt>
	 * 
	 * @param origine
	 *            The origine to set.
	 * 
	 */
	public void setOrigine(Joueur origine) {
		this.origine = origine;
	}

	/**
	 * Getter of the property <tt>cible</tt>
	 * 
	 * @return Returns the cible.
	 * 
	 */

	public Joueur getCible() {
		return cible;
	}

	/**
	 * Setter of the property <tt>cible</tt>
	 * 
	 * @param cible
	 *            The cible to set.
	 * 
	 */
	public void setCible(Joueur cible) {
		this.cible = cible;
	}

	/**
	 * Getter of the property <tt>dejaJouee</tt>
	 * 
	 * @return Returns the dejaJouee.
	 * 
	 */

	public boolean getDejaJouee() {
		return dejaJouee;
	}

	/**
	 * Setter of the property <tt>dejaJouee</tt>
	 * 
	 * @param dejaJouee
	 *            The dejaJouee to set.
	 * 
	 */
	public void setDejaJouee(boolean dejaJouee) {
		this.dejaJouee = dejaJouee;
	}

}
