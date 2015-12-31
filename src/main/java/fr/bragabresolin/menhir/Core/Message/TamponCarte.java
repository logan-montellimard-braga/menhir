package fr.bragabresolin.menhir.Core.Message;

import fr.bragabresolin.menhir.Core.Cartes.Carte;

public class TamponCarte {

	private Carte carte;
	private boolean ignorer;
	
	private static TamponCarte instance;
	
	public static TamponCarte getInstance() {
		if (TamponCarte.instance == null)
			TamponCarte.instance = new TamponCarte();
		return TamponCarte.instance;
	}
	
	private TamponCarte() {
		this.carte = null;
	}
	
	public synchronized void setIgnorer(boolean ignorer) {
		this.ignorer = ignorer;
		this.notifyAll();
	}
	
	public synchronized Carte recupererCarte() {
		while (this.carte == null && !this.ignorer) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Carte carteAJouer = this.carte;
		carte = null;
		this.ignorer = false;
		
		return carteAJouer;
	}
	
	public synchronized void deposerCarte(Carte carte) {
		while (this.carte != null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.carte = carte;
		this.notifyAll();
	}
}
