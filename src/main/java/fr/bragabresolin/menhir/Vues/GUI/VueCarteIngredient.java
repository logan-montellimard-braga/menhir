package fr.bragabresolin.menhir.Vues.GUI;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class VueCarteIngredient extends JPanel {

	/**
	 * Create the panel.
	 */
	public VueCarteIngredient(String nom) {
		setBorder(new LineBorder(new Color(95, 95, 95)));
		setBackground(new Color(65, 65, 65));
		setLayout(new MigLayout("", "[100%,grow]", "[30px]10[100%-40px,grow]"));
		
		JLabel label = new JLabel(nom);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(230, 230, 230));
		label.setFont(new Font("SansSerif", Font.BOLD, 11));
		add(label, "cell 0 0,alignx center,aligny center");
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(65, 65, 65));
		add(panel, "cell 0 1,grow");
		panel.setLayout(new GridLayout(5, 4, 0, 0));
		
		JLabel label_2 = new JLabel("");
		label_2.setForeground(new Color(204, 204, 204));
		panel.add(label_2);
		
		JLabel lblE = new JLabel("");
		lblE.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/geant_ico.png")));
		lblE.setToolTipText("Géant");
		lblE.setFont(new Font("SansSerif", Font.BOLD, 11));
		lblE.setHorizontalAlignment(SwingConstants.CENTER);
		lblE.setForeground(new Color(204, 204, 204));
		panel.add(lblE);
		
		JLabel lblG = new JLabel("");
		lblG.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/engrais_ico.png")));
		lblG.setToolTipText("Engrais");
		lblG.setFont(new Font("SansSerif", Font.BOLD, 11));
		lblG.setHorizontalAlignment(SwingConstants.CENTER);
		lblG.setForeground(new Color(204, 204, 204));
		panel.add(lblG);
		
		JLabel lblF = new JLabel("");
		lblF.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/farfadet_ico.png")));
		lblF.setToolTipText("Farfadet");
		lblF.setFont(new Font("SansSerif", Font.BOLD, 11));
		lblF.setHorizontalAlignment(SwingConstants.CENTER);
		lblF.setForeground(new Color(204, 204, 204));
		panel.add(lblF);
		
		JLabel label_7 = new JLabel("");
		label_7.setToolTipText("Printemps");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/printemps_ico.png")));
		label_7.setForeground(new Color(204, 204, 204));
		panel.add(label_7);
		
		JLabel label_6 = new JLabel("0");
		label_6.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setForeground(new Color(204, 204, 204));
		panel.add(label_6);
		
		JLabel label_11 = new JLabel("1");
		label_11.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setForeground(new Color(204, 204, 204));
		panel.add(label_11);
		
		JLabel label_10 = new JLabel("0");
		label_10.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setForeground(new Color(204, 204, 204));
		panel.add(label_10);
		
		JLabel label_9 = new JLabel("");
		label_9.setToolTipText("Ete");
		label_9.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/ete_ico.png")));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setForeground(new Color(204, 204, 204));
		panel.add(label_9);
		
		JLabel label_16 = new JLabel("3");
		label_16.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_16.setHorizontalAlignment(SwingConstants.CENTER);
		label_16.setForeground(new Color(204, 204, 204));
		panel.add(label_16);
		
		JLabel label_15 = new JLabel("4");
		label_15.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_15.setHorizontalAlignment(SwingConstants.CENTER);
		label_15.setForeground(new Color(204, 204, 204));
		panel.add(label_15);
		
		JLabel label_14 = new JLabel("0");
		label_14.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setForeground(new Color(204, 204, 204));
		panel.add(label_14);
		
		JLabel label_17 = new JLabel("");
		label_17.setToolTipText("Automne");
		label_17.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/automne_ico.png")));
		label_17.setHorizontalAlignment(SwingConstants.CENTER);
		label_17.setForeground(new Color(204, 204, 204));
		panel.add(label_17);
		
		JLabel label_13 = new JLabel("2");
		label_13.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setForeground(new Color(204, 204, 204));
		panel.add(label_13);
		
		JLabel label_12 = new JLabel("1");
		label_12.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setForeground(new Color(204, 204, 204));
		panel.add(label_12);
		
		JLabel label_8 = new JLabel("2");
		label_8.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setForeground(new Color(204, 204, 204));
		panel.add(label_8);
		
		JLabel label_18 = new JLabel("");
		label_18.setToolTipText("Hiver");
		label_18.setIcon(new ImageIcon(VueCarteIngredient.class.getResource("/images/hiver_ico.png")));
		label_18.setHorizontalAlignment(SwingConstants.CENTER);
		label_18.setForeground(new Color(204, 204, 204));
		panel.add(label_18);
		
		JLabel label_19 = new JLabel("0");
		label_19.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_19.setHorizontalAlignment(SwingConstants.CENTER);
		label_19.setForeground(new Color(204, 204, 204));
		panel.add(label_19);
		
		JLabel label_1 = new JLabel("1");
		label_1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(new Color(204, 204, 204));
		panel.add(label_1);
		
		JLabel label_20 = new JLabel("3");
		label_20.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_20.setHorizontalAlignment(SwingConstants.CENTER);
		label_20.setForeground(new Color(204, 204, 204));
		panel.add(label_20);

	}

}
