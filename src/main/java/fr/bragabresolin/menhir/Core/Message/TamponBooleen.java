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
	
	/**
	 * Point d'accès à l'instance unique (patron Singleton) du tampon.
	 *
	 * @return L'unique instance du TamponBooleen
	 */
	public static TamponBooleen getInstance() {
		if (TamponBooleen.instance == null)
			TamponBooleen.instance = new TamponBooleen();
		return TamponBooleen.instance;
	}
	
	/**
	 * Constructeur privé (patron Singleton).
	 *
	 * Crée le TamponBooleen en initialisant l'attribut de données comme étant 
	 * vide.
	 */
	private TamponBooleen() {
		this.bool = null;
	}
	
	/**
	 * Renvoit le booléen stocké dans le tampon.
	 *
	 * Le thread appelant est mis en attente si nécessaire jusqu'à ce qu'une 
	 * donnée soit disponible dans le tampon, auquel cas elle est renvoyée et le 
	 * tampon est nettoyé.
	 *
	 * @return Le booléen stocké
	 */
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
	
	/**
	 * Stocke un booléen dans le tampon.
	 *
	 * Le thread appelant est mis en attente si nécessaire jusqu'à ce que le 
	 * tampon soit vide, auquel cas les threads en attente du tampon sont 
	 * notifiés.
	 *
	 * @param bool Le booléen à enregistrer
	 */
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
