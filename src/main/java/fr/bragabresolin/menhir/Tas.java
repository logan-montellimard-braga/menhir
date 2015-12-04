package fr.bragabresolin.menhir;

import java.util.*;
import java.io.*;
import java.util.Collections;

public class Tas<E extends Carte> {
	private LinkedList<E> cartes;

	public Tas() {
		this.cartes = new LinkedList<E>();
	}

	public void melanger() {
		Collections.shuffle(this.cartes);
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
