package fr.bragabresolin.menhir.Vues.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import fr.bragabresolin.menhir.Core.JeuMenhir;
import fr.bragabresolin.menhir.Core.Cartes.Carte;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Message.TamponBooleen;
import fr.bragabresolin.menhir.Core.Message.TamponCarte;
import net.miginfocom.swing.MigLayout;

/**
 * Composant graphique représentant la main du joueur, c'est-à-dire l'ensemble 
 * des cartes qu'il a en main.
 * Ce composant est mis à jour lorsque le joueur réel qu'il suit pioche ou rend 
 * des cartes.
 * Ce composant est composé des vues des cartes (ingrédient et allié) du joueur, 
 * et c'est lui qui indique à chacune d'entre elle si elle peut être utiliée 
 * selon les besoins du joueur suivi.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Vues.GUI.VueMenhir
 * @see fr.bragabresolin.menhir.Vues.GUI.VueCarteAllie
 * @see fr.bragabresolin.menhir.Vues.GUI.VueCarteIngredient
 */
public class VueMainJoueur extends JPanel implements Observer, BlackTheme {
	
	/**
	 * Constante d'identification pour la sérialisation.
	 */
	public static final long serialVersionUID = 1l;
	
	/**
	 * Représente le panneau contenant uniquement les cartes ingrédients.
	 * 
	 * Les autres cartes (cartes allié) sont directement ajoutées au JPanel 
	 * courant ; les cartes ingrédient ayant besoin d'un panel supplémentaire 
	 * pour utiliser un layout imbriqué plus favorable à leur nombre, 
	 * contrairement aux cartes allié.
	 */
	private JPanel panelCartesIng;

	/**
	 * Référence vers les vues des cartes ingrédient du panneau.
	 * 
	 * Cette référence est nécessaire afin d'effectuer les actions individuelles 
	 * sur chaque vues, comme les rendre jouables/cliquables.
	 */
	private LinkedList<VueCarteIngredient> listeCartes;

	/**
	 * Référence vers les vues des cartes allié du panneau.
	 * 
	 * Cette référence est nécessaire afin d'effectuer les actions individuelles 
	 * sur chaque vues, comme les rendre jouables/cliquables.
	 */
	private LinkedList<VueCarteAllie> listeCartesAllie;

	/**
	 * Référence vers le jeu en train d'être joué.
	 * Cette référence est utilisée pour être passée aux vues des cartes, qui 
	 * s'en servent directement.
	 * 
	 * Une valeur nulle signifie que le composant est dans un état non 
	 * utilisable.
	 * 
	 * @see fr.bragabresolin.menhir.Core.JeuMenhir
	 * @see fr.bragabresolin.menhir.Vues.GUI.VueCarteAllie
	 * @see fr.bragabresolin.menhir.Vues.GUI.VueCarteIngredient
	 */
	private JeuMenhir jeu;
	
	public VueMainJoueur(JeuMenhir jeu) {
		this.jeu = jeu;
		this.jeu.getJoueurPhysique().addObserver(this);
		this.listeCartes = new LinkedList<VueCarteIngredient>();
		this.listeCartesAllie = new LinkedList<VueCarteAllie>();
		
		this.setBackground(DARK_BG);
		this.setBorder(new MatteBorder(1, 0, 0, 0, (Color) BORDER_COLOR));
		this.setLayout(new MigLayout("", "0[80%-15px,grow]40[20%-15px]0", "0[30px][100%-30px,grow]0"));
		this.construirePanel();
	}
	
	public void update(Observable o, Object message) {
		if (message instanceof Message) {
			Message mes = (Message) message;
			switch(mes.getType()) {
			case JOUEUR_PIOCHE_CARTE:
				Carte carte = (Carte) mes.getBody();
				this.ajouterCarte(carte);
				break;
			case JOUEUR_REND_CARTES:
				this.supprimerCartes();
				break;
			case JOUEUR_CHOIX_JOUER_ING:
				JOptionPane.showMessageDialog(null, "C'est à votre tour. Sélectionnez une carte ingrédient à jouer.", "A vous de jouer !", JOptionPane.INFORMATION_MESSAGE);
				Iterator<VueCarteIngredient> it = this.listeCartes.iterator();
				while (it.hasNext()) {
					it.next().setEstJouable(true);
				}
				break;
			case JOUEUR_CHOIX_JOUER_ALLIE:
				if (!this.jeu.getJoueurPhysique().carteAllieDispo()) {
					TamponCarte.getInstance().setIgnorer(true);
					break;
				}
				
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous jouer une carte allié ? Si OK, sélectionnez-là.", "A vous de jouer !", JOptionPane.YES_NO_OPTION);
				switch (result) {
				case JOptionPane.OK_OPTION:
					Iterator <VueCarteAllie> ital = this.listeCartesAllie.iterator();
					while (ital.hasNext()) ital.next().setEstJouable(true);
					break;
				default:
					TamponCarte.getInstance().setIgnorer(true);
					break;
				}
				// Absence de break volontaire
			case JOUEUR_FIN_TOUR:
				it = this.listeCartes.iterator();
				while (it.hasNext()) it.next().setEstJouable(false);
				break;
			case JOUEUR_CHOIX_PIOCHER_ALLIE:
				result = JOptionPane.showConfirmDialog(null, "Voulez-vous piocher une carte allié ? Si non, vous obtiendrez 2 graines.", "Distribution des cartes", JOptionPane.YES_NO_OPTION);
				switch (result) {
				case JOptionPane.OK_OPTION:
					TamponBooleen.getInstance().deposerBool(true);
					break;
				default:
					TamponBooleen.getInstance().deposerBool(false);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void construirePanel() {
		this.panelCartesIng = new JPanel();
		this.panelCartesIng.setBackground(DARK_BG);
		this.add(this.panelCartesIng, "cell 0 1,grow");
		this.panelCartesIng.setLayout(new GridLayout(1, 4, 0, 0));
	}
	
	private void ajouterCarte(Carte carte) {
		JPanel vueCarte = null;
		if (carte instanceof CarteIngredient) {
			vueCarte = new VueCarteIngredient((CarteIngredient) carte, this.jeu);
			this.listeCartes.add((VueCarteIngredient) vueCarte);
			this.panelCartesIng.add(vueCarte);
		} else if (carte instanceof CarteAllie){
			vueCarte = new VueCarteAllie((CarteAllie) carte, this.jeu);
			this.listeCartesAllie.add((VueCarteAllie) vueCarte);
			this.add(vueCarte);
		}
	}
	
	private void supprimerCartes() {
		this.removeAll();
		this.listeCartes.clear();
		this.construirePanel();
	}
}
