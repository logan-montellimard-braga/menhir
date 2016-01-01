package fr.bragabresolin.menhir.Core.Cartes;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Collections;
import fr.bragabresolin.menhir.Core.Message.*;

/**
 * Classe représentant un tas de cartes du jeu du Menhir.
 * 
 * Un tas de cartes est un regroupement de cartes de même type. Un tas permet de
 * piocher la carte du dessus du tas, d'ajouter une carte au tas, et d'être 
 * mélangé.
 * 
 * Cette classe est émettrice de messages.
 * 
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
 * @see fr.bragabresolin.menhir.Core.Cartes.ActionIngredient
 */
public class Tas<E extends Carte> extends Observable {

	/**
	 * Contient les cartes contenues dans le tas.
	 * 
	 * Si le tas est vide, la LinkedList est également vide (pas de valeur nulle).
	 * 
	 * @see fr.bragabresolin.menhir.Core.Cartes.Carte
	 */
	private LinkedList<E> cartes;

	/**
	 * Constructeur.
	 *
	 * Initialise la liste de cartes du tas en tant que liste vide.
	 */
	public Tas() {
		this.cartes = new LinkedList<E>();
	}

	/**
	 * Mélange les cartes présentes dans le tas.
	 */
	public void melanger() {
		Collections.shuffle(this.cartes);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.TAS_MELANGE, this.cartes));
	}

	/**
	 * Renvoit (en supprimant du tas) la carte située sur le dessus.
	 *
	 * @return La carte du dessus du tas
	 */
	public E donnerCarte() {
		return this.cartes.poll();
	}

	/**
	 * Ajoute une carte sur le haut du tas (début de la liste).
	 * 
	 * @param carte La carte à ajouter
	 */
	public void ajouterCarte(E carte) {
		this.cartes.push(carte);
	}

	/**
	 * Renvoit un iterateur sur les cartes du tas.
	 *
	 * Simple wrapper autour de la collection utilisée internalement.
	 *
	 * @return L'iterateur sur la collection de cartes interne
	 */
	public Iterator<E> iterator() {
		return this.cartes.iterator();
	}

	/**
	 * Produit une représentation textuelle du tas de cartes.
	 *
	 * On affiche le nombre de cartes présentes dans le tas.
	 *
	 * @return La chaîne représentant le tas.
	 */
	public String toString() {
		return "Tas de " + this.cartes.size() + (this.cartes.size() > 1 ? " cartes" : " carte");
	}
}
