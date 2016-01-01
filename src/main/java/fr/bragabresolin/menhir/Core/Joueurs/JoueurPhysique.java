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

	/**
	 * Constructeur simplifié.
	 *
	 * Simple appel transparent au constructeur parent.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public JoueurPhysique() {
		super();
	}

	/**
	 * Constructeur complet.
	 *
	 * Simple appel transparent au constructeur parent équivalent
	 * 
	 * @param nom Le nom du joueur
	 * @param age L'âge du joueur
	 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
	 */
	public JoueurPhysique(String nom, int age) {
		super(nom, age);
	}
	
	/**
	 * Fais jouer le joueur selon le contexte passé.
	 *
	 * Un joueur physique joue en indiquant à ses observateurs qu'il a besoin de 
	 * cartes ou de données quelconques, puis en tentant de récupérer ces 
	 * données dans des tampons de communication.
	 * 
	 * @param contexte La liste des joueurs de la partie
	 * @param partieAvancee Si la partie est en mode avancé ou non
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Message.TamponCarte
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
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

	/**
	 * Demande au joueur de choisir de jouer une carte allié.
	 *
	 * Un joueur physique choisit s'il veut jouer une carte allié en indiquant à 
	 * ses observateurs qu'il a besoin d'une carte éventuellement. S'il reçoit 
	 * null, on considère que le joueur ne veut pas jouer de carte allié.
	 *
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @param contexte Les joueurs de la partie
	 * @return La carte allié choisie
	 * @see fr.bragabresolin.menhir.Core.Saison
	 * @see fr.bragabresolin.menhir.Core.Message.TamponCarte
	 */
	protected CarteAllie choisirJouerAllie(Saison saisonActuelle, ArrayList<Joueur> contexte) {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_CHOIX_JOUER_ALLIE));
		
		Carte carteAJouer = TamponCarte.getInstance().recupererCarte();
		if (carteAJouer != null) carteAJouer.setOrigine(this);
		
		return (CarteAllie) carteAJouer;
	}

	/**
	 * Demande au joueur s'il veut piocher une carte allié.
	 *
	 * Un joueur physique décide s'il veut piocher une carte allié en indiquant 
	 * à ses observateurs qu'il a besoin d'une réponse booléenne, puis la 
	 * récupère dans un tampon de communication.
	 *
	 * @return Vrai si le joueur veut piocher une carte allié
	 * @see fr.bragabresolin.menhir.Core.Message.TamponBooleen
	 */
	public boolean veutPiocherCarteAllie() {
		this.setChanged();
		this.notifyObservers(new Message(MessageType.JOUEUR_CHOIX_PIOCHER_ALLIE));
		
		boolean veutPiocherAllie = TamponBooleen.getInstance().recupererBool();
		return veutPiocherAllie;
	}

	/**
	 * Invitation à effectuer une action autorisée pendant le tour de 
	 * l'adversaire.
	 *
	 * Un joueur physique n'a pas d'actions particulières à exercer dans cette 
	 * méthode car l'interface lui permet de choisir de jouer ce qu'il veut au 
	 * moment où il veut, en vérifiant qu'il en a bien la possibilité.
	 * 
	 * @param contexte La liste des joueurs de la partie
	 * @param saisonActuelle La saison dans laquelle exécuter les actions
	 * @see fr.bragabresolin.menhir.Core.Saison
	 */
	public void jouerDansTourAdverse(ArrayList<Joueur> contexte, Saison saisonActuelle) {
		// Méthode vide
	}

	/**
	 * Produit une représentation textuelle du joueur.
	 *
	 * Un joueur physique est un joueur dont on affiche le nom.
	 * 
	 * @return String La représentation textuelle du joueur physique
	 */
	public String toString() {
		String str = super.toString();
		return this.nom + " " + str;
	}
}
