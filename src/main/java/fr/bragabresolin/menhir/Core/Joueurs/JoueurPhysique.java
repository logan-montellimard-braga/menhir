package fr.bragabresolin.menhir.Core.Joueurs;

import java.util.*;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.*;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.MessageType;
import fr.bragabresolin.menhir.Core.Message.TamponBooleen;
import fr.bragabresolin.menhir.Core.Message.TamponCarte;

/**
 * Classe représentant un joueur physique, c'est-à-dire un joueur réel devant 
 * son écran.
 * Un joueur physique est concret, contrairement à un Joueur. L'implémentation 
 * de ses comportements de jeu repose sur l'utilisation de Messages pour 
 * informer une quelconque vue qui écoute que le joueur a besoin de données, 
 * ainsi que des Tampons pour les récupérer de façon transparente et 
 * synchronisée.
 *
 * Cette classe est productrice de messages.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
 * @see fr.bragabresolin.menhir.Core.Message.Message
 * @see fr.bragabresolin.menhir.Core.Message.MessageType
 * @see fr.bragabresolin.menhir.Core.Message.TamponBooleen
 * @see fr.bragabresolin.menhir.Core.Message.TamponCarte
 */
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
