package fr.bragabresolin.menhir.Vues.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Joueurs.JoueurPhysique;
import fr.bragabresolin.menhir.Core.Message.Message;

import javax.swing.BorderFactory;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

public class VueJoueur extends JPanel implements Observer, BlackTheme {
	
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
				this.labelNom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, LIGHT_FG));
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
		
		if (joueur instanceof JoueurPhysique) setBackground(new Color(65, 65, 65));
		else setBackground(new Color(51, 51, 51));
		setLayout(new MigLayout("", "15[20%]10[80%]", "15[25%][25%][25%][25%]"));
		
		// Label du nom + âge
		this.labelNom = new JLabel(this.joueur.getNom() + " (" + this.joueur.getAge() + " ans)");
		this.labelNom.setVerticalAlignment(SwingConstants.TOP);
		this.labelNom.setFont(new Font("SansSerif", Font.BOLD, 14));
		this.labelNom.setHorizontalAlignment(SwingConstants.LEFT);
		this.labelNom.setForeground(new Color(230, 230, 230));
		this.add(this.labelNom, "cell 0 0 2 1,alignx left,aligny top");
		
		// Label des points 
		JLabel lblP = new JLabel("Points");
		//lblP.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/points_ico.png")));
		lblP.setToolTipText("Points");
		lblP.setHorizontalAlignment(SwingConstants.LEFT);
		lblP.setForeground(new Color(230, 230, 230));
		lblP.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblP, "cell 0 1,alignx center,aligny center");
		
		this.labelPoints = new JLabel("" + this.joueur.getPoints());
		this.labelPoints.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.labelPoints.setForeground(new Color(230, 230, 230));
		this.add(this.labelPoints, "cell 1 1");
		
		JLabel lblM = new JLabel("Menhirs");
		//lblM.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/mehnir_ico.png")));
		lblM.setToolTipText("Menhirs");
		lblM.setHorizontalAlignment(SwingConstants.LEFT);
		lblM.setForeground(new Color(230, 230, 230));
		lblM.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblM, "cell 0 2,alignx center,aligny center");
		
		this.labelMenhirs = new JLabel("" + this.joueur.getNombreMenhirs());
		this.labelMenhirs.setForeground(new Color(230, 230, 230));
		this.labelMenhirs.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(this.labelMenhirs, "cell 1 2");
		
		JLabel lblG = new JLabel("Graines");
		//lblG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/graines_ico.png")));
		lblG.setToolTipText("Graines (prot\u00E9g\u00E9es)");
		lblG.setHorizontalAlignment(SwingConstants.LEFT);
		lblG.setForeground(new Color(230, 230, 230));
		lblG.setFont(new Font("SansSerif", Font.BOLD, 12));
		this.add(lblG, "cell 0 3,alignx center,aligny center");
		
		this.labelGraines = new JLabel("" + this.joueur.getNombreGraines() + " (" + this.joueur.getNombreGrainesProteges() + ")");
		this.labelGraines.setForeground(new Color(230, 230, 230));
		this.labelGraines.setFont(new Font("SansSerif", Font.PLAIN, 12));
		this.add(this.labelGraines, "cell 1 3");
	}
	
	public void nettoyer() {
		this.joueur.deleteObserver(this);
	}

}
