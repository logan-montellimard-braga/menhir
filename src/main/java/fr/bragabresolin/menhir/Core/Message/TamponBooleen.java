package fr.bragabresolin.menhir.Core.Message;


/**
 * Classe servant de tampon (buffer) de stockage d'une valeur booléenne, 
 * généralement pour échanger des informations entre le joueur et le coeur du 
 * jeu.
 * Cette classe fonctionne à la manière d'une "boîte aux lettres" synchronisée.
 * Cette classe implémente le patron de conception Singleton.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 */
public class TamponBooleen {

	/**
	 * Représente le booléen stocké dans le tampon.
	 * Il peut être lu et écrit de manière synchrone.
	 *
	 * Une valeur nulle signifie qu'aucune donnée n'est disponible.
	 */
	private Boolean bool;
	
	/**
	 * Attribut statique contenant l'unique instance de la classe (patron de 
	 * conception Singleton).
	 *
	 * Une valeur nulle est possible et signifie que la classe n'a pas encore 
	 * été utilisée, mais la nullité de cette valeur n'est jamais apparente à 
	 * l'extérieur de la classe.
	 */
	private static TamponBooleen instance;
	
	public static TamponBooleen getInstance() {
		if (TamponBooleen.instance == null)
			TamponBooleen.instance = new TamponBooleen();
		return TamponBooleen.instance;
	}
	
	private TamponBooleen() {
		this.bool = null;
	}
	
	public synchronized Boolean recupererBool() {
		while (this.bool == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Boolean boolRetour = this.bool;
		this.bool = null;
		
		return boolRetour;
	}
	
	public synchronized void deposerBool(Boolean bool) {
		while (this.bool != null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.bool = bool;
		this.notifyAll();
	}
}
