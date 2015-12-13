package fr.bragabresolin.menhir.Vues.GUI;

import fr.bragabresolin.menhir.Vues.Vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.GridLayout;
import javax.swing.JOptionPane;

public class VueMenhir implements Vue {

	private static final int WIDTH = 850;
	private static final int HEIGHT = 500;
	
	private static final Color DARK_BG = new Color(51, 51, 51);
	private static final Color LIGHT_FG = new Color(230, 230, 230);
	private static final Color BORDER_COLOR = new Color(95, 95, 95);
	private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 11);
	
	private JFrame frame;

	public VueMenhir() {
		frame = new JFrame();
		frame.setIconImage(frame.getToolkit().getImage(getClass().getResource("/images/ico.png")));
		frame.setTitle("Jeu du Menhir - Braga & Bresolin");
		frame.setResizable(true);
		frame.getContentPane().setBackground(DARK_BG);
		frame.setBackground(DARK_BG);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setSize(WIDTH, HEIGHT);
		
		UIManager.put("OptionPane.background", DARK_BG);
		UIManager.put("Panel.background",DARK_BG);
		UIManager.put("OptionPane.messageForeground",LIGHT_FG);
    }

	private void initialize() {
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(new MigLayout("", "0[100%,grow]0", "0[50px,fill]0[40%,grow]0[60%-50px,grow]0"));

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
		panel.setBackground(new Color(85, 85, 85));
		frame.getContentPane().add(panel, "cell 0 0,growx,aligny top");
		panel.setLayout(new MigLayout("", "20[10%]10[80%]10[10%]20", "[100%]"));

		JLabel lblSaisonencours = new JLabel("Hiver".toUpperCase());
		lblSaisonencours.setToolTipText("Saison en cours");
		lblSaisonencours.setFont(TITLE_FONT);
		lblSaisonencours.setForeground(LIGHT_FG);
		panel.add(lblSaisonencours, "cell 0 0,alignx left,growy");

		JLabel lblInformations = new JLabel("Tour du joueur J2");
		lblInformations.setFont(UIManager.getFont("Table.font"));
		lblInformations.setForeground(LIGHT_FG);
		panel.add(lblInformations, "cell 1 0,alignx center,aligny center");

		JLabel lblManche = new JLabel("MANCHE 1/6");
		lblManche.setToolTipText("Manche en cours");
		lblManche.setForeground(LIGHT_FG);
		lblManche.setFont(TITLE_FONT);
		panel.add(lblManche, "cell 2 0,alignx right,aligny center");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(DARK_BG);
		frame.getContentPane().add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new GridLayout(1, 6, 0, 0));

		VueJoueur j1 = new VueJoueur("J1");
		j1.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
		VueJoueur j2 = new VueJoueur("J2");
		j2.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
		VueJoueur j3 = new VueJoueur("J3");
		j3.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
		VueJoueur j4 = new VueJoueur("J4");
		j4.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
		VueJoueur j5 = new VueJoueur("J5");

		VueJoueur j = new VueJoueur("Logan");
		j.setBackground(new Color(65, 65, 65));
		j.setBorder(new MatteBorder(0, 0, 0, 1, (Color) BORDER_COLOR));
		panel_1.add(j);
		panel_1.add(j1);
		panel_1.add(j2);
		panel_1.add(j3);
		panel_1.add(j4);
		panel_1.add(j5);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(DARK_BG);
		panel_2.setBorder(new MatteBorder(1, 0, 0, 0, (Color) BORDER_COLOR));
		frame.getContentPane().add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new MigLayout("", "0[80%-15px,grow]40[20%-15px]0", "0[30px][100%-30px,grow]0"));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(DARK_BG);
		panel_2.add(panel_3, "cell 0 1,grow");
		panel_3.setLayout(new GridLayout(1, 4, 0, 0));

		VueCarteIngredient btnFour = new VueCarteIngredient("Larmes de Dryade");
		panel_3.add(btnFour);

		VueCarteIngredient btnThree = new VueCarteIngredient("Racine d'arc-en-ciel");
		panel_3.add(btnThree);

		VueCarteIngredient btnTwo = new VueCarteIngredient("Esprit du Dolmen");
		panel_3.add(btnTwo);

		VueCarteIngredient btnOne = new VueCarteIngredient("Rayon de lune");
		panel_3.add(btnOne);

		VueCarteAllie btnFive = new VueCarteAllie("Chien de garde");
		panel_2.add(btnFive, "cell 1 1,grow");
		
		this.frame.pack();
		this.frame.setSize(WIDTH, HEIGHT);
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


	public void lancer() {
		JLabel splash = new JLabel("HEJEKJKJ");
		splash.setIcon(new ImageIcon(VueMenhir.class.getResource("/images/splash.png")));
		frame.getContentPane().add(splash, BorderLayout.CENTER);
		this.frame.setVisible(true);
		
		this.confirmerDemarrage();
		String nomJoueur = this.demanderNom();
		int ageJoueur = this.demanderAge();
		int nombreJoueurs = this.demanderNombreJoueurs();
		this.initialize();
	}
}
