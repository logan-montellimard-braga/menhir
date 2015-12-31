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
import fr.bragabresolin.menhir.Core.Cartes.ActionIngredient;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Joueurs.JoueurVirtuel;
import fr.bragabresolin.menhir.Core.Message.TamponCarte;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class VueCarteIngredient extends JPanel implements Observer, BlackTheme {
	
	private CarteIngredient carte;
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

	public VueCarteIngredient(CarteIngredient carte, JeuMenhir jeu) {
		carte.addObserver(this);
		this.carte = carte;
		this.jeu = jeu;
		this.estJouable = false;
		
		setBorder(new LineBorder(new Color(95, 95, 95)));
		setBackground(new Color(65, 65, 65));
		setLayout(new MigLayout("", "[100%,grow]", "[30px]10[100%-40px,grow]"));
		
		this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
            	if (VueCarteIngredient.this.estJouable) {
            		boolean carteEstOK = false;
            		
            		JPanel panel = new JPanel();
                	JLabel label = new JLabel("Veuillez choisir l'action à effectuer");
                	label.setForeground(LIGHT_FG);
                    panel.add(label, BorderLayout.NORTH);
                    DefaultComboBoxModel model = new DefaultComboBoxModel();
                    for (ActionIngredient ai : ActionIngredient.values()) {
                    	model.addElement(ai);
    				}
                    JComboBox comboBox = new JComboBox(model);
                    panel.add(comboBox, BorderLayout.CENTER);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Effet de la carte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (result) {
                        case JOptionPane.OK_OPTION:
                        	ActionIngredient ai = (ActionIngredient) comboBox.getSelectedItem();
                        	carteEstOK = true;
                        	
                        	if (ai == ActionIngredient.FARFADET) {
                        		carteEstOK = false;
                        		JPanel panelJoueurs = new JPanel();
                            	JLabel labelJoueurs = new JLabel("Veuillez choisir le joueur cible");
                            	labelJoueurs.setForeground(LIGHT_FG);
                                panelJoueurs.add(labelJoueurs, BorderLayout.NORTH);
                                DefaultComboBoxModel modelJoueurs = new DefaultComboBoxModel();
                                for (Joueur joueur : VueCarteIngredient.this.jeu.getJoueurs()) {
                                 	if (joueur instanceof JoueurVirtuel) {
                                   		modelJoueurs.addElement(joueur);
                                   	}
                				}
                                JComboBox comboBoxJoueurs = new JComboBox(modelJoueurs);
                                panelJoueurs.add(comboBoxJoueurs, BorderLayout.CENTER);

                                int joueurCible = JOptionPane.showConfirmDialog(null, panelJoueurs, "Cible de la carte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                switch (joueurCible) {
                                case JOptionPane.OK_OPTION:
                                	carteEstOK = true;
                                	Joueur j = (Joueur) comboBoxJoueurs.getSelectedItem();
                                	VueCarteIngredient.this.carte.setCible(j);
                                }
                        	}
                        	
                        	if (carteEstOK) {
                        		VueCarteIngredient.this.carte.setAction(ai);
                        		TamponCarte.getInstance().deposerCarte(VueCarteIngredient.this.carte);
                        	}
                            break;
                    }
            	} else {
            		JOptionPane.showMessageDialog(null, "Impossible de jouer cette carte maintenant.", "Attendez votre tour !", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
		
		JLabel label = new JLabel(carte.getNomCarte());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(230, 230, 230));
		label.setFont(new Font("SansSerif", Font.BOLD, 11));
		add(label, "cell 0 0,alignx center,aligny center");
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(65, 65, 65));
		add(panel, "cell 0 1,grow");
		panel.setLayout(new GridLayout(5, 4, 0, 0));
		
		JLabel labelVide = new JLabel("");
		panel.add(labelVide);
		
		for (ActionIngredient action : ActionIngredient.values()) {
			JLabel iconeAction = new JLabel("");
			iconeAction.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/" + action.toString().toLowerCase() + "_ico.png")));
			iconeAction.setToolTipText(action.toString());
			iconeAction.setFont(new Font("SansSerif", Font.BOLD, 11));
			iconeAction.setHorizontalAlignment(SwingConstants.CENTER);
			iconeAction.setForeground(new Color(204, 204, 204));
			panel.add(iconeAction);
		}
		
		for (Saison saison : Saison.values()) {
			JLabel icone = new JLabel();
			icone.setToolTipText(saison.toString());
			icone.setHorizontalAlignment(SwingConstants.CENTER);
			icone.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/" + saison.toString().toLowerCase() + "_ico.png")));
			icone.setForeground(new Color(204, 204, 204));
			panel.add(icone);
			
			for (ActionIngredient ai : ActionIngredient.values()) {
				int valeur = this.carte.getMatrice().get(saison).get(ai);
				JLabel labelValeur = new JLabel("" + valeur);
				labelValeur.setFont(new Font("SansSerif", Font.PLAIN, 11));
				labelValeur.setHorizontalAlignment(SwingConstants.CENTER);
				labelValeur.setForeground(new Color(204, 204, 204));
				panel.add(labelValeur);
			}
		}

	}

}
