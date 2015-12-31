package fr.bragabresolin.menhir.Core.Message;


public class TamponBooleen {
	private Boolean bool;
	
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
