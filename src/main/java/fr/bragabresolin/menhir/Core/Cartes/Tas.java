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

	public Tas() {
		this.cartes = new LinkedList<E>();
	}

	public void melanger() {
		Collections.shuffle(this.cartes);
		this.setChanged();
		this.notifyObservers(new Message(MessageType.TAS_MELANGE, this.cartes));
	}

	public E donnerCarte() {
		return this.cartes.poll();
	}

	public void ajouterCarte(E carte) {
		this.cartes.push(carte);
	}

	public Iterator<E> iterator() {
		return this.cartes.iterator();
	}

	public String toString() {
		return "Tas de " + this.cartes.size() + (this.cartes.size() > 1 ? " cartes" : " carte");
	}
}
