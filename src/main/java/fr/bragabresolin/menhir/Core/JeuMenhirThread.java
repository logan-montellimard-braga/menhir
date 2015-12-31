package fr.bragabresolin.menhir.Core;

/**
 * Classe wrapper permettant de lancer un jeu du Menhir dans un nouveau thread.
 * Cette classe est simplement un wrapper autour de la classe JeuMenhir afin de 
 * proposer une interface transparente, par la méthode lancerPartie, au 
 * démarrage du jeu dans un thread séparé.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.JeuMenhir
 */
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
