package fr.bragabresolin.menhir.Core;

public class JeuMenhirThread extends JeuMenhir implements Runnable {
	
	public JeuMenhirThread(int nombreJoueurs, String nomJoueur, int ageJoueur, boolean modeAvance) {
		super(nombreJoueurs, nomJoueur, ageJoueur, modeAvance);
	}
	
	public void lancerPartie() {
		Thread t = new Thread(this, "jeu");
		t.start();
	}
	
	public void run() {
		super.lancerPartie();
	}
}
