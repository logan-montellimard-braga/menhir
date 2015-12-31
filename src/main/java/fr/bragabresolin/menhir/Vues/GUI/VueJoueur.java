package fr.bragabresolin.menhir.Vues.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Joueurs.JoueurPhysique;
import fr.bragabresolin.menhir.Core.Message.Message;

import javax.swing.BorderFactory;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import net.miginfocom.swing.MigLayout;

/**
 * Composant graphique représentant un joueur, qu'il soit physique ou virtuel.
 * L'intégralité des informations du joueur (points, graines, menhirs, ...) est
 * mise à jour à chaque notification du joueur suivi.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Vues.GUI.VueMenhir
 * @see fr.bragabresolin.menhir.Core.Joueurs.Joueur
 */
public class VueJoueur extends JPanel implements Observer, BlackTheme {
	
	public static final long serialVersionUID = 1l;
	
	private Joueur joueur;
	
	private JLabel labelNom;
	private JLabel labelPoints;
	private JLabel labelGraines;
	private JLabel labelMenhirs;
	
	public void update (Observable o, Object message) {
		this.labelNom.setText(this.joueur.getNom() + " (" + this.joueur.getAge() + " ans)");
		this.labelPoints.setText("" + this.joueur.getPoints());
		this.labelGraines.setText("" + this.joueur.getNombreGraines() + " (" + this.joueur.getNombreGrainesProteges() + ")");
		this.labelMenhirs.setText("" + this.joueur.getNombreMenhirs());
		
		if (message instanceof Message) {
			Message mes = (Message) message;
			switch(mes.getType()) {
			case JOUEUR_DEBUT_TOUR:
				this.labelNom.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, LIGHT_FG));
				break;
			case JOUEUR_FIN_TOUR:
				this.labelNom.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, DARK_BG));
				break;
			default:
				break;
			}
		}
	}

	public VueJoueur(Joueur joueur) {
		this.joueur = joueur;
		this.joueur.addObserver(this);
		
		if (joueur instanceof JoueurPhysique) setBackground(ACCENT_2);
		else setBackground(DARK_BG);
		setLayout(new MigLayout("", "15[20%]10[80%]", "15[25%][25%][25%][25%]"));
		
		// Label du nom + âge
		this.labelNom = new JLabel(this.joueur.getNom() + " (" + this.joueur.getAge() + " ans)");
		this.labelNom.setVerticalAlignment(SwingConstants.TOP);
		this.labelNom.setFont(new Font("SansSerif", Font.BOLD, 14));
		this.labelNom.setHorizontalAlignment(SwingConstants.LEFT);
		this.labelNom.setForeground(LIGHT_FG);
		this.add(this.labelNom, "cell 0 0 2 1,alignx left,aligny top");
		
		// Label des points 
		JLabel lblP = new JLabel("Points");
		lblP.setToolTipText("Points");
		lblP.setHorizontalAlignment(SwingConstants.LEFT);
		lblP.setForeground(LIGHT_FG);
		lblP.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblP, "cell 0 1,alignx center,aligny center");
		
		this.labelPoints = new JLabel("" + this.joueur.getPoints());
		this.labelPoints.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.labelPoints.setForeground(LIGHT_FG);
		this.add(this.labelPoints, "cell 1 1");
		
		JLabel lblM = new JLabel("Menhirs");
		lblM.setToolTipText("Menhirs");
		lblM.setHorizontalAlignment(SwingConstants.LEFT);
		lblM.setForeground(LIGHT_FG);
		lblM.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblM, "cell 0 2,alignx center,aligny center");
		
		this.labelMenhirs = new JLabel("" + this.joueur.getNombreMenhirs());
		this.labelMenhirs.setForeground(LIGHT_FG);
		this.labelMenhirs.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(this.labelMenhirs, "cell 1 2");
		
		JLabel lblG = new JLabel("Graines");
		lblG.setToolTipText("Graines (prot\u00E9g\u00E9es)");
		lblG.setHorizontalAlignment(SwingConstants.LEFT);
		lblG.setForeground(LIGHT_FG);
		lblG.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblG, "cell 0 3,alignx center,aligny center");
		
		this.labelGraines = new JLabel("" + this.joueur.getNombreGraines() + " (" + this.joueur.getNombreGrainesProteges() + ")");
		this.labelGraines.setForeground(LIGHT_FG);
		this.labelGraines.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(this.labelGraines, "cell 1 3");
	}
	
	public void nettoyer() {
		this.joueur.deleteObserver(this);
	}

}
