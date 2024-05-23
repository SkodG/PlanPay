package pp.projects.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class DescObjectiveView extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public DescObjectiveView(String name) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//JButton btnApri = new JButton("Apri");
		//btnApri.setBounds(312, 23, 114, 21);
		//contentPane.add(btnApri);
		
		//JButton btnElimina = new JButton("Elimina");
		//btnElimina.setBounds(312, 42, 114, 21);
		//contentPane.add(btnElimina);
		
		Dimension preferredSize = new Dimension(300, 100);
        setPreferredSize(new Dimension(500, 100));
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);
        setLayout(null);
        
        JLabel lbnome = new JLabel(name);
        lbnome.setBounds(10, 33, 160, 29);
        add(lbnome);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(273, 10, 85, 21);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("New button");
        btnNewButton_1.setBounds(273, 56, 85, 21);
        add(btnNewButton_1);
	}

}
