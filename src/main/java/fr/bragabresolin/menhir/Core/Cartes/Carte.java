package fr.bragabresolin.menhir.Core.Cartes;

import java.util.Observable;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe abstraite représentant une carte quelconque du jeu du Menhir.
 * 
 * Une carte est caractérisée par son origine (qui la joue), sa cible éventuelle 
 * (sur qui elle fait effet), et son statut (déjà jouée (défaussée) ou non),
 * ainsi que la possibilité de s'exécuter.
 * 
 * Cette classe est émettrice de messages.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 */
public abstract class Carte extends Observable {

	/**
	 * Defini le joueur qui lance la carte.
	 * 
	 * Une valeur nulle est possible avant de jouer la carte, mais résultera en 
	 * une exception à l'exécution de celle-ci si l'origine n'est toujours pas 
	 * spécifiée à ce moment-là.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	protected Joueur origine;

	/**
	 * Defini le joueur sur qui la carte agit négativement (optionnel).
	 * 
	 * Une valeur nulle est possible et parfaitement acceptable pour la plupart 
	 * des situations. Les actions qui requierent une cible (eg Farfadet) 
	 * provoqueront une exception à l'exécution de celle-ci si la cible n'est 
	 * toujours pas spécifiée à ce moment-là.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	protected Joueur cible;

	/**
	 * Spécifie si la carte à déjà été jouée pour ne pas la proposer à nouveau 
	 * au tour suivant (symbolise la défausse du joueur). 
	 */
	protected boolean dejaJouee;

	/**
	 * Réalise l'action de la carte.
	 * 
	 * L'effet des cartes est entièrement dépendant de l'implémentation de 
	 * celles-ci et doit donc être implémenté séparément dans toutes les classes 
	 * filles.
	 * 
	 * @param saisonActuelle La saison danslaquelle exécuter l'effet de la carte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public abstract void executer(Saison saisonActuelle);

	/**
	 * Retourne le joueur d'origine de la carte.
	 *
	 * @return Le joueur d'origine
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public Joueur getOrigine() {
		return origine;
	}

	/**
	 * Retourne le joueur d'origine de la carte.
	 *
	 * Le joueur d'origine doit toujours être spécifié afin que la carte sache 
	 * quel joueur l'a lancé lorsqu'elle s'exécute.
	 * 
	 * @param origine Le joueur d'origine à enregistrer
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void setOrigine(Joueur origine) {
		this.origine = origine;
	}

	/**
	 * Retourne la cible de la carte.
	 *
	 * @return La cible.
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public Joueur getCible() {
		return cible;
	}

	/**
	 * Mutateur de la cible de la carte.
	 *
	 * La cible de la carte doit être spécifiée pour exécuter certains effets, 
	 * selon les types de cartes.
	 * 
	 * @param cible La cible à enregistrer
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public void setCible(Joueur cible) {
		this.cible = cible;
		this.setChanged();
		this.notifyObservers(new Message(MessageType.CARTE_SET_CIBLE, cible));
	}

	/**
	 * Retourne le statut de jeu de la carte.
	 * 
	 * @return Si la carte a déjà été jouée ou non.
	 */
	public boolean getDejaJouee() {
		return dejaJouee;
	}

	/**
	 * Mutateur du statut dejaJouee de la carte.
	 * 
	 * @param dejaJouee Le statut de jeu de la carte
	 */
	public void setDejaJouee(boolean dejaJouee) {
		this.dejaJouee = dejaJouee;
	}

	/**
	 * Produit une représentation en chaîne de caractère de la carte.
	 *
	 * La représentation textuelle d'une carte généraliste ne peut pas être très 
	 * détaillée ; elle comprend uniquement le statut de la carte (déjà jouée ou 
	 * non).
	 * 
	 * @return La représentation textuelle de la carte
	 */
	public String toString() {
		String str = "";
		if (this.dejaJouee)
			str += " (défausse) ";
		str += "Carte";

		return str;
	}

	/**
	 * Construit une représentation graphique de boîte autour des données de la 
	 * carte.
	 *
	 * La représentation se sert de la taille des données de chaque ligne afin 
	 * de produire un affichage en boîte uniforme.
	 * 
	 * @param tailleMin La largeur minimale requise pour afficher la carte
	 * @param carte La représentation textuelle non formatée de la carte
	 * @return La représentation textuelle formatée de la carte
	 */
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
