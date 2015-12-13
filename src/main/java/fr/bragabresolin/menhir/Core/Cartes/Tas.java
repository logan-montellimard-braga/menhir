package fr.bragabresolin.menhir.Core.Cartes;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Collections;
import fr.bragabresolin.menhir.Core.Message.*;

public class Tas<E extends Carte> extends Observable {
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

	public E peek() {
		return this.cartes.getFirst();
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
