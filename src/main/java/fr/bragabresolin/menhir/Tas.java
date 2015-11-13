package fr.bragabresolin.menhir;

import java.util.*;
import java.io.*;
import java.util.Collections;

public class Tas<E extends Carte> extends ArrayDeque<E> {

	// FIXME: implémentation monstrueusement sale, à revoir
	public void melanger() {
		List<E> list = new ArrayList<E>();
		for (E c : this) list.add(c);
		this.clear();
		Collections.shuffle(list);
		for (E c : list) this.add(c);
	}

	public void genererContenu(int nombreDeCartes) {

	}

	public String toString() {
		return "Tas de " + this.size() + (this.size() > 1 ? " cartes" : " carte");
	}
}
