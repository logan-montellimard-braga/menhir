package fr.bragabresolin.menhir;

import java.util.EnumMap;

public abstract class Carte {

	/**
	 * Defini le joueur qui lance la carte.
	 */
	protected Joueur origine;

	/**
	 * Defini le joueur sur qui la carte agit négativement (optionnel).
	 */
	protected Joueur cible;

	/**
	 * Spécifie si la carte à déjà été jouée pour ne pas la proposer à nouveau 
	 * au tour suivant (symbolise la défausse du joueur). 
	 */
	protected boolean dejaJouee;

	/**
	 * Réalise l'action de la carte.
	 * @param saisonActuelle La saison en cours pour l'effet de la carte.
	 */
	public abstract void executer(Saison saisonActuelle);

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

	public String toString() {
		String str = "";
		if (this.dejaJouee)
			str += " (défausse) ";
		str += "Carte";

		return str;
	}

	public String toString(int tailleMin, String carte) {
		String[] lignes = carte.split("\n");
		for (int i = 0; i < lignes.length; i++)
			if (lignes[i].length() > tailleMin) tailleMin = lignes[i].length();
		tailleMin += 2;

		for (int i = 0; i < lignes.length; i++)
			lignes[i] = "│ " + lignes[i]
				+ String.format("%" + (tailleMin - lignes[i].length()) + "s", "")
				+ "│";

		StringBuilder sb = new StringBuilder();
		for (String s : lignes) sb.append(s + "\n");

		StringBuilder separatorTop = new StringBuilder();
		StringBuilder separatorBot = new StringBuilder();
		StringBuilder separatorMid = new StringBuilder();
		separatorTop.append("┌");
		separatorBot.append("└");
		separatorMid.append("├");
		for (int i = 0; i < tailleMin; i++) {
			separatorTop.append("─");
			separatorMid.append("─");
			separatorBot.append("─");
		}
		separatorTop.append("─┐");
		separatorBot.append("─┘");
		separatorMid.append("─┤");

		sb.insert(0, separatorTop.toString() + "\n");
		sb.insert(2 * separatorTop.toString().length() + 1, "\n" + separatorMid.toString());
		sb.append(separatorBot.toString());

		return sb.toString();
	}
}
