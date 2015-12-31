package fr.bragabresolin.menhir.Vues.GUI;

import fr.bragabresolin.menhir.Core.JeuMenhir;
import fr.bragabresolin.menhir.Core.JeuMenhirThread;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllieChien;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllieTaupe;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Message.Message;
import fr.bragabresolin.menhir.Core.Partie.Manche;
import fr.bragabresolin.menhir.Core.Joueurs.*;
import fr.bragabresolin.menhir.Vues.Vue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class VueMenhir implements Vue, BlackTheme {
	
	private JeuMenhir jeu;
	
	private JFrame frame;
	private JPanel panelJoueurs;
	private LinkedList<VueJoueur> vuesJoueurs;
	private JPanel panelCartes;
	private JPanel panelSuiviEffets;
	
	private JLabel lblSuiviEffets;
	private JLabel lblManche;
	private JLabel lblSaisonencours;
	private JLabel lblInformations;
	
	private int mancheActuelle;


	public VueMenhir() {
		this.mancheActuelle = 0;
		
		this.vuesJoueurs = new LinkedList<VueJoueur>();

		frame = new JFrame();
		frame.setIconImage(frame.getToolkit().getImage(getClass().getResource("/images/ico.png")));
		frame.setTitle("Jeu du Menhir - Braga & Bresolin");
		frame.setResizable(true);
		frame.getContentPane().setBackground(DARK_BG);
		frame.setBackground(DARK_BG);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		UIManager.put("OptionPane.background", DARK_BG);
		UIManager.put("Panel.background",DARK_BG);
		UIManager.put("OptionPane.messageForeground",LIGHT_FG);
    }

	private void initialize() {
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(new MigLayout("", "0[100%,grow]0", "0[50px,fill]0[35px,fill]0[40%-20px,grow]0[60%-65px,grow]0"));

		this.mancheActuelle = 0;
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
		panel.setBackground(ACCENT_1);
		frame.getContentPane().add(panel, "cell 0 0,growx,aligny top");
		panel.setLayout(new MigLayout("", "20[10%]10[80%]10[10%]20", "[100%]"));

		this.lblSaisonencours = new JLabel("Saison".toUpperCase());
		this.lblSaisonencours.setToolTipText("Saison en cours");
		this.lblSaisonencours.setFont(TITLE_FONT);
		this.lblSaisonencours.setForeground(LIGHT_FG);
		panel.add(this.lblSaisonencours, "cell 0 0,alignx left,growy");

		this.lblInformations = new JLabel("<...>");
		this.lblInformations.setFont(UIManager.getFont("Table.font"));
		this.lblInformations.setForeground(LIGHT_FG);
		panel.add(this.lblInformations, "cell 1 0,alignx center,aligny center");

		this.lblManche = new JLabel("MANCHE x");
		this.lblManche.setToolTipText("Manche en cours");
		this.lblManche.setForeground(LIGHT_FG);
		this.lblManche.setFont(TITLE_FONT);
		panel.add(this.lblManche, "cell 2 0,alignx right,aligny center");
		
		this.panelJoueurs = new JPanel();
		this.panelJoueurs.setBackground(DARK_BG);
		frame.getContentPane().add(this.panelJoueurs, "cell 0 2,grow");
		this.panelJoueurs.setLayout(new GridLayout(1, 6, 0, 0));
		this.remplirPanelJoueurs();
		
		this.panelSuiviEffets = new JPanel();
		this.panelSuiviEffets.setBackground(ACCENT_2);
		this.panelSuiviEffets.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
		frame.getContentPane().add(this.panelSuiviEffets, "cell 0 1,grow");
		this.lblSuiviEffets = new JLabel("En attente d'actions à afficher");
		this.lblSuiviEffets.setForeground(ACCENT_FG);
		this.lblSuiviEffets.setFont(DEFAULT_FONT);
		this.lblSuiviEffets.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblSuiviEffets.setBorder(new MatteBorder(4, 0, 0, 0, ACCENT_2)); // padding
		this.panelSuiviEffets.add(this.lblSuiviEffets);

		this.panelCartes = new VueMainJoueur(this.jeu);
		this.frame.getContentPane().add(this.panelCartes, "cell 0 3,grow");
		
		this.jeu.addObserver(new Observer() {
			public void update(Observable o, Object message) {
				switch (((Message) message).getType()) {
				case DEBUT_PARTIE:
					VueMenhir.this.initTopPanel();
					break;
				case FIN_PARTIE:
					VueMenhir.this.afficherClassement();
					break;
				default:
					break;
				}
			}
		});
		
		Iterator<CarteIngredient> itc = this.jeu.getTasIng().iterator();
		while (itc.hasNext()) {
			CarteIngredient carte = itc.next();
			carte.addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						Message mes = (Message) message;
						switch (mes.getType()) {
						case CARTE_EXEC:
							CarteIngredient c = (CarteIngredient) o;
							switch (c.getAction()) {
							case GEANT:
								VueMenhir.this.lblSuiviEffets.setText(c.getOrigine().getNom() + " récupère " + (Integer) mes.getBody() + " graines.");
								break;
							case ENGRAIS:
								VueMenhir.this.lblSuiviEffets.setText(c.getOrigine().getNom() + " fait pousser " + (Integer) mes.getBody() + " menhirs.");
								break;
							case FARFADET:
								VueMenhir.this.lblSuiviEffets.setText(c.getOrigine().getNom() + " vole " + (Integer) mes.getBody() + " graines à " + c.getCible().getNom() + ".");
								break;
							}
							break;
						default:
							break;
						}
					}
				}
			});
		}
		
		Iterator<CarteAllie> itca = this.jeu.getTasAllie().iterator();
		while(itca.hasNext()) {
			CarteAllie carte = itca.next();
			carte.addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						Message mes = (Message) message;
						switch (mes.getType()) {
						case CARTE_EXEC:
							CarteAllie c = (CarteAllie) o;
							if (c instanceof CarteAllieChien) {
								VueMenhir.this.lblSuiviEffets.setText(c.getOrigine().getNom() + " appelle un chien qui protège " + (Integer) mes.getBody() + " graines.");
							} else if (c instanceof CarteAllieTaupe) {
								VueMenhir.this.lblSuiviEffets.setText(c.getOrigine().getNom() + " appelle une taupe géante qui détruit " + (Integer) mes.getBody() + " menhirs de " + c.getCible().getNom());
							}
						default:
							break;
						}
					}
				}
			});
		}
		
		Iterator<Joueur> itj = this.jeu.getJoueurs().iterator();
		while (itj.hasNext()) {
			itj.next().addObserver(new Observer() {
				public void update(Observable o, Object message) {
					if (message instanceof Message) {
						switch (((Message) message).getType()) {
						case JOUEUR_DEBUT_TOUR:
							Joueur j = (Joueur) o;
							VueMenhir.this.lblInformations.setText("Tour de " + j.getNom());
							break;
						default:
							break;
						}
					}
				}
			});
		}
		
		this.frame.pack();
	}
	
	private void afficherClassement() {
		this.panelCartes.removeAll();
		this.panelCartes.setLayout(new BorderLayout());
		
		this.lblManche.setText("");
		this.lblSaisonencours.setText("");
		this.lblInformations.setText("Partie terminée !");
		this.lblSuiviEffets.setText("Voici le classement :");
		
		this.remplirPanelJoueurs();
		
		JButton boutonRejouer = new JButton("REJOUER");
		boutonRejouer.setBackground(DARK_BG);
		boutonRejouer.setForeground(LIGHT_FG);
		boutonRejouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VueMenhir.this.demarrerJeu();
			}
		});
		this.panelCartes.add(boutonRejouer, BorderLayout.CENTER);
		
		this.frame.pack();
	}
	
	private void initTopPanel() {
		lblSaisonencours.setText(this.jeu.getMancheEnCours().getSaisonEnCours().toString());
		this.jeu.getMancheEnCours().addObserver(new Observer() {
			public void update(Observable o, Object message) {
				Message mes = (Message) message;
				switch (mes.getType()) {
				case DEBUT_SAISON:
					lblSaisonencours.setText(((Manche) o).getSaisonEnCours().toString());
					lblInformations.setText("Changement de saison !");
					break;
				case DEBUT_MANCHE:
					remplirPanelJoueurs();
					mancheActuelle++;
					lblManche.setText("Manche " + mancheActuelle);
					lblInformations.setText("Changement de manche !");
				default:
				}
			}
		});
	}
	
	private void remplirPanelJoueurs() {
		Iterator<VueJoueur> itv = this.vuesJoueurs.iterator();
		while (itv.hasNext()) {
			itv.next().nettoyer();
		}
		this.panelJoueurs.removeAll();
		Iterator<Joueur> it = this.jeu.getJoueurs().iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			VueJoueur vueJoueur = new VueJoueur(j);
			this.vuesJoueurs.add(vueJoueur);
			if (it.hasNext()) {
				vueJoueur.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
			}
			this.panelJoueurs.add(vueJoueur);
		}
	}
	
	private void confirmerDemarrage() {
		String texte = "Bienvenue dans le Jeu du Menhir !" + "\n" +
					   "La partie va démarrer !";
		int result = JOptionPane.showConfirmDialog(this.frame, texte, "Bienvenue - Jeu du Menhir", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (result != 0) {
			System.exit(0);
		}
	}
	
	private String demanderNom() {
		String nomJoueur = null;
		while (nomJoueur == null || (nomJoueur != null && nomJoueur.equals(""))) {
			nomJoueur = (String) JOptionPane.showInputDialog(
	                this.frame,
	                "Quel est votre nom ?",
	                "Choix du nom - Menhir",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                null);
			if (nomJoueur == null) System.exit(0);
		}
		return nomJoueur;
	}
	
	private boolean demanderModePartie() {
		String texte = "Voulez-vous jouer en partie avancée ?";
		int result = JOptionPane.showConfirmDialog(this.frame, texte, "Choix du mode de jeu - Menhir", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		return result == 0;
	}
	
	private int demanderAge() {
		int age = 0;
		while (age < 1) {
			String reponse = (String) JOptionPane.showInputDialog(
	                this.frame,
	                "Quel âge avez-vous ?",
	                "Choix de l'âge - Menhir",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                null);
			if (reponse == null) System.exit(0);
			try {
				age = Integer.parseInt(reponse, 10);
			} catch (NumberFormatException e) {
				age = 0;
			}
		}
		if (age < 8) {
			JOptionPane.showMessageDialog(this.frame, "Vous êtes trop jeune pour jouer (- de 8 ans) !", "Joueur trop jeune", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return age;
	}
	
	private int demanderNombreJoueurs() {
		int nombreJoueurs = 0;
		while (nombreJoueurs < 1 || nombreJoueurs > 5) {
			String reponse = (String) JOptionPane.showInputDialog(
	                this.frame,
	                "Avec combien de joueurs voulez-vous jouer ?" + "\n" + "Choisissez un nombre entre 1 et 5 compris.",
	                "Choix du nombre de joueurs - Menhir",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                null);
			if (reponse == null) System.exit(0);
			try {
				nombreJoueurs = Integer.parseInt(reponse, 10);
			} catch (NumberFormatException e) {
				nombreJoueurs = 0;
			}
		}
		return nombreJoueurs;
	}
	
	private void demarrerJeu() {
		String nomJoueur = this.demanderNom();
		int ageJoueur = this.demanderAge();
		int nombreJoueurs = this.demanderNombreJoueurs();
		boolean partieAvancee = this.demanderModePartie();
		this.jeu = new JeuMenhirThread(nombreJoueurs, nomJoueur, ageJoueur, partieAvancee);
		this.initialize();
		this.jeu.lancerPartie();
	}
	
	private void afficherSplashScreen() {
		frame.getContentPane().removeAll();
		JLabel splash = new JLabel("");
		splash.setIcon(new ImageIcon(VueMenhir.class.getResource("/images/splash.png")));
		frame.getContentPane().add(splash, BorderLayout.CENTER);
	}

	public void lancer() {
		this.afficherSplashScreen();
		this.frame.setVisible(true);
		
		this.confirmerDemarrage();
		this.demarrerJeu();
	}
}
