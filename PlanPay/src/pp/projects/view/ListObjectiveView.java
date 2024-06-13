package pp.projects.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class ListObjectiveView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 * @param String name
	 */
	public ListObjectiveView(String name, double balance, LocalDate date, List<ObjectiveImpl> objectiveList,
								ConsoleControllerImpl controller, ConsolleObjectiveView consolleObj) {
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		Dimension preferredSize = new Dimension(100, 100);
        setPreferredSize(new Dimension(267, 47));
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);
        
        JLabel label = new JLabel("");
        add(label);
        
        JLabel label_1 = new JLabel("");
        add(label_1);
        
        JLabel lblnome = new JLabel(name);
        lblnome.setFont(new Font("Calibri", Font.PLAIN, 13));
        add(lblnome);
        
        JButton btnNewButton = new JButton("Apri");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ObjectiveView newObjective = new ObjectiveView(false, name, date, controller, consolleObj);
        		newObjective.setVisible(true);
        	}
        
        });
        
        JLabel lblBalance = new JLabel("0.00");
        lblBalance.setFont(new Font("Calibri", Font.PLAIN, 13));
        add(lblBalance);
        
        add(btnNewButton);
        
        JLabel label_2 = new JLabel("");
        add(label_2);
        
        
        JButton btnNewButton_1 = new JButton("Elimina");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//TODO: Eliminazione oggetto e obbiettivo collegato (View e Impl)-> serve metodo del controller!
        		controller.removeObjective(name);
        		consolleObj.updateUI(null);
        	}
        });
        add(btnNewButton_1);
        
        JLabel label_3 = new JLabel("");
        add(label_3);
        
        JLabel lblDate = new JLabel();
        lblDate.setFont(new Font("Calibri", Font.PLAIN, 13));
        add(lblDate);
        
	}
}
