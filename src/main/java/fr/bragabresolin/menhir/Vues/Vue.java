package fr.bragabresolin.menhir.Vues;

/**
 * L'interface Vue précise le contrat minimal qu'une vue doit implémenter afin 
 * d'être potentiellement utilisable par le démarreur du jeu.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Menhir
 */
public interface Vue {

	/**
	 * Démarre l'affichage de la vue en question.
	 */
	public void lancer();
}
