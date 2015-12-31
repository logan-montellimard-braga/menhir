package fr.bragabresolin.menhir.Vues.GUI;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.LineBorder;

import fr.bragabresolin.menhir.Core.JeuMenhir;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllieTaupe;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Joueurs.JoueurVirtuel;
import fr.bragabresolin.menhir.Core.Message.TamponCarte;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class VueCarteAllie extends JPanel implements Observer, BlackTheme {

	private CarteAllie carte;
	private JeuMenhir jeu;
	
	private boolean estJouable;
	
	public void setEstJouable(boolean estJouable) {
		this.estJouable = estJouable;
	}

	public void update(Observable o, Object message) {
		if (this.carte.getDejaJouee()) {
			this.setVisible(false);
		}
	}
	
	public VueCarteAllie(CarteAllie carte, JeuMenhir jeu) {
		carte.addObserver(this);
		this.carte = carte;
		this.jeu = jeu;
		this.estJouable = false;
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				boolean carteEstOK = false;
				
				if (VueCarteAllie.this.estJouable || VueCarteAllie.this.carte instanceof CarteAllieTaupe) {
					carteEstOK = true;
					if (VueCarteAllie.this.carte instanceof CarteAllieTaupe) {
						carteEstOK = false;
						JPanel panelJoueurs = new JPanel();
                    	JLabel labelJoueurs = new JLabel("Veuillez choisir le joueur cible");
                    	labelJoueurs.setForeground(LIGHT_FG);
                        panelJoueurs.add(labelJoueurs, BorderLayout.NORTH);
                        DefaultComboBoxModel modelJoueurs = new DefaultComboBoxModel();
                        for (Joueur joueur : VueCarteAllie.this.jeu.getJoueurs()) {
                         	if (joueur instanceof JoueurVirtuel) {
                           		modelJoueurs.addElement(joueur);
                           	}
        				}
                        JComboBox comboBoxJoueurs = new JComboBox(modelJoueurs);
                        panelJoueurs.add(comboBoxJoueurs, BorderLayout.CENTER);

                        int joueurCible = JOptionPane.showConfirmDialog(null, panelJoueurs, "Cible de la carte taupe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        switch (joueurCible) {
                        case JOptionPane.OK_OPTION:
                        	Joueur j = (Joueur) comboBoxJoueurs.getSelectedItem();
                        	VueCarteAllie.this.carte.setCible(j);
                        	carteEstOK = true;
                        }
					}
					
					if (VueCarteAllie.this.estJouable && carteEstOK) {
						TamponCarte.getInstance().deposerCarte(VueCarteAllie.this.carte);	
					} else if (carteEstOK) {
						VueCarteAllie.this.carte.setOrigine(VueCarteAllie.this.jeu.getJoueurPhysique());
						VueCarteAllie.this.carte.executer(VueCarteAllie.this.jeu.getMancheEnCours().getSaisonEnCours());	
					}
				} else {
            		JOptionPane.showMessageDialog(null, "Impossible de jouer cette carte maintenant.", "Attendez votre tour !", JOptionPane.ERROR_MESSAGE);
            	}
			}
		});

		setBorder(new LineBorder(new Color(95, 95, 95)));
		setBackground(new Color(65, 65, 65));
		setLayout(new MigLayout("", "[100%,grow]", "[30px]10[100%-40px,grow]"));
		
		JLabel label = new JLabel(this.carte instanceof CarteAllieTaupe ? "Taupe géante" : "Chien de garde");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(230, 230, 230));
		label.setFont(new Font("SansSerif", Font.BOLD, 11));
		add(label, "cell 0 0,alignx center,aligny center");
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(65, 65, 65));
		add(panel, "cell 0 1,grow");
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		for (Saison saison : Saison.values()) {
			JLabel labelSaison = new JLabel("");
			labelSaison.setToolTipText(saison.toString());
			labelSaison.setHorizontalAlignment(SwingConstants.CENTER);
			labelSaison.setIcon(new ImageIcon(VueCarteAllie.class.getResource("/images/" + saison.toString().toLowerCase() + "_ico.png")));
			labelSaison.setForeground(new Color(204, 204, 204));
			panel.add(labelSaison);
			
			JLabel labelValeur = new JLabel("" + carte.getMatrice().get(saison));
			labelValeur.setHorizontalAlignment(SwingConstants.CENTER);
			labelValeur.setForeground(new Color(204, 204, 204));
			panel.add(labelValeur);
		}
	}

}
