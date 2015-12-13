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

public class VueCarteAllie extends JPanel {

	/**
	 * Create the panel.
	 */
	public VueCarteAllie(String nom) {
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
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel label_7 = new JLabel("");
		label_7.setToolTipText("Printemps");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setIcon(new ImageIcon(VueCarteAllie.class.getResource("/images/printemps_ico.png")));
		label_7.setForeground(new Color(204, 204, 204));
		panel.add(label_7);
		
		JLabel lblNewLabel = new JLabel("0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(204, 204, 204));
		panel.add(lblNewLabel);
		
		JLabel label_9 = new JLabel("");
		label_9.setToolTipText("Ete");
		label_9.setIcon(new ImageIcon(VueCarteAllie.class.getResource("/images/ete_ico.png")));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setForeground(new Color(204, 204, 204));
		panel.add(label_9);
		
		JLabel lblNewLabel_1 = new JLabel("3");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(204, 204, 204));
		panel.add(lblNewLabel_1);
		
		JLabel label_17 = new JLabel("");
		label_17.setToolTipText("Automne");
		label_17.setIcon(new ImageIcon(VueCarteAllie.class.getResource("/images/automne_ico.png")));
		label_17.setHorizontalAlignment(SwingConstants.CENTER);
		label_17.setForeground(new Color(204, 204, 204));
		panel.add(label_17);
		
		JLabel lblNewLabel_2 = new JLabel("2");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(204, 204, 204));
		panel.add(lblNewLabel_2);
		
		JLabel label_2 = new JLabel("");
		label_2.setToolTipText("Hiver");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setIcon(new ImageIcon(VueCarteAllie.class.getResource("/images/hiver_ico.png")));
		label_2.setForeground(new Color(204, 204, 204));
		panel.add(label_2);
		
		JLabel lblNewLabel_3 = new JLabel("0");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(204, 204, 204));
		panel.add(lblNewLabel_3);

	}

}
