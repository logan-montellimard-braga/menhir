package fr.bragabresolin.menhir.Vues.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

public class VueJoueur extends JPanel {

	/**
	 * Create the panel.
	 */
	public VueJoueur(String text) {
		setBackground(new Color(51, 51, 51));
		setLayout(new MigLayout("", "15[20%]10[80%]", "15[25%][25%][25%][25%]"));
		
		JLabel lblHello = new JLabel(text);
		lblHello.setVerticalAlignment(SwingConstants.TOP);
		lblHello.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		lblHello.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblHello.setHorizontalAlignment(SwingConstants.LEFT);
		lblHello.setForeground(new Color(230, 230, 230));
		add(lblHello, "cell 0 0 2 1,alignx left,aligny top");
		
		JLabel lblP = new JLabel("");
		lblP.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/points_ico.png")));
		lblP.setToolTipText("Points");
		lblP.setHorizontalAlignment(SwingConstants.LEFT);
		lblP.setForeground(new Color(230, 230, 230));
		lblP.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(lblP, "cell 0 1,alignx center,aligny center");
		
		JLabel label = new JLabel("6");
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setForeground(new Color(230, 230, 230));
		add(label, "cell 1 1");
		
		JLabel lblM = new JLabel("");
		lblM.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/mehnir_ico.png")));
		lblM.setToolTipText("Menhirs");
		lblM.setHorizontalAlignment(SwingConstants.LEFT);
		lblM.setForeground(new Color(230, 230, 230));
		lblM.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(lblM, "cell 0 2,alignx center,aligny center");
		
		JLabel label_1 = new JLabel("2");
		label_1.setForeground(new Color(230, 230, 230));
		label_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		add(label_1, "cell 1 2");
		
		JLabel lblG = new JLabel("");
		lblG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/graines_ico.png")));
		lblG.setToolTipText("Graines (prot\u00E9g\u00E9es)");
		lblG.setHorizontalAlignment(SwingConstants.LEFT);
		lblG.setForeground(new Color(230, 230, 230));
		lblG.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(lblG, "cell 0 3,alignx center,aligny center");
		
		JLabel label_2 = new JLabel("4 (0)");
		label_2.setForeground(new Color(230, 230, 230));
		label_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		add(label_2, "cell 1 3");
	}

}
