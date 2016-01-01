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
	
	/**
	 * Constructeur.
	 * 
	 * Le constructeur JeuMenhirThread fait simplement appel de façon 
	 * transparente au constructeur JeuMehnir.
	 * 
	 * @param nombreJoueurs Le nombre de joueurs virtuels avec qui jouer
	 * @param nomJoueur Le nom du joueur réel
	 * @param ageJoueur L'âge du joueur réel
	 * @param modeAvance Vrai si la partie est en mode avancée, faux si en mode rapide
	 * @see fr.bragabresolin.menhir.Core.JeuMenhir
	 */
	public JeuMenhirThread(int nombreJoueurs, String nomJoueur, int ageJoueur, boolean modeAvance) {
		super(nombreJoueurs, nomJoueur, ageJoueur, modeAvance);
	}
	
	/**
	 * Démarre la partie du jeu du Menhir.
	 * 
	 * L'implémentation JeuMehnirThread lance la partie dans un nouveau thread 
	 * séparé.
	 */
	public void lancerPartie() {
		Thread t = new Thread(this, "jeu");
		t.start();
	}
	
	/**
	 * Lance la partie dans un Thread.
	 * Cette méthode n'est pas appelée directement.
	 */
	public void run() {
		super.lancerPartie();
	}
}
