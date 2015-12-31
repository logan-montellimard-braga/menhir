package fr.bragabresolin.menhir.Core.Joueurs;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;
import fr.bragabresolin.menhir.Core.Message.TamponBooleen;
import fr.bragabresolin.menhir.Core.Message.TamponCarte;

public class JoueurPhysique extends Joueur {

	public JoueurPhysique() {
		super();
	}

	public JoueurPhysique(String nom, int age) {
		super(nom, age);
	}
	
	public void jouer(ArrayList<Joueur> contexte, boolean partieAvancee, Saison saisonActuelle) {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_DEBUT_TOUR));
		
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_CHOIX_JOUER_ING));
		
		Carte carteAJouer = TamponCarte.getInstance().recupererCarte();
		carteAJouer.setOrigine(this);
		carteAJouer.executer(saisonActuelle);
		
		if (partieAvancee) {
			CarteAllie carteAllie = this.choisirJouerAllie(saisonActuelle, contexte);
			if (carteAllie != null) carteAllie.executer(saisonActuelle);
		}
		
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_FIN_TOUR));
	}

	protected CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte) {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_CHOIX_JOUER_ALLIE));
		
		Carte carteAJouer = TamponCarte.getInstance().recupererCarte();
		if (carteAJouer != null) carteAJouer.setOrigine(this);
		
		return (CarteAllie) carteAJouer;
	}

	public boolean veutPiocherCarteAllie() {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_CHOIX_PIOCHER_ALLIE));
		
		boolean veutPiocherAllie = TamponBooleen.getInstance().recupererBool();
		return veutPiocherAllie;
	}

	public void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle) {
	}

	public String toString() {
		String str = super.toString();
		return this.nom + " " + str;
	}
}
