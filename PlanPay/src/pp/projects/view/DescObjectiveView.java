package pp.projects.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DescObjectiveView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 * @param String name, TODO: ObjectiveView objView
	 */
	public DescObjectiveView(String name, ConsoleControllerImpl controller, ConsolleObjectiveView consolleObj) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//JButton btnApri = new JButton("Apri");
		//btnApri.setBounds(312, 23, 114, 21);
		//contentPane.add(btnApri);
		
		//JButton btnElimina = new JButton("Elimina");
		//btnElimina.setBounds(312, 42, 114, 21);
		//contentPane.add(btnElimina);
		
		Dimension preferredSize = new Dimension(300, 100);
        setPreferredSize(new Dimension(397, 100));
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);
        setLayout(null);
        
        JLabel lbnome = new JLabel(name);
        lbnome.setBounds(10, 33, 160, 29);
        add(lbnome);
        
        JButton btnNewButton = new JButton("Apri");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ObjectiveView newObjective = new ObjectiveView(false, name, controller, consolleObj);
        		newObjective.setVisible(true);
        	}
        });
        btnNewButton.setBounds(273, 10, 85, 21);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Elimina");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//TODO: Eliminazione oggetto e obbiettivo collegato (View e Impl)-> serve metodo del controller!      		
        	}
        });
        btnNewButton_1.setBounds(273, 56, 85, 21);
        add(btnNewButton_1);
	}

}
