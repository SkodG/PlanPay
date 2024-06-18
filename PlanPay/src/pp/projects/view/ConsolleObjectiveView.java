package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.ObjectiveImpl;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.Box;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;

public class ConsolleObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private List<ObjectiveImpl> objectiveList;
	private ConsoleControllerImpl consoleController;
	private LocalDate date;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ConsolleObjectiveView(ConsoleControllerImpl controller) {
		setTitle("OBBIETTIVI");
		this.consoleController = controller;
		this.date = LocalDate.now();
		objectiveList = consoleController.getObjectiveList();
		//provvisorio->  andrebbe nel testing della classe		
		System.out.println("LISTA OBBIETTIVI:");
		objectiveList.stream().map(o ->"Obbiettivo: "+ o.getName()).forEach(System.out::println);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewObjective = new JButton("Nuovo Obbiettivo");
		btnNewObjective.setBounds(10, 241, 198, 27);
		btnNewObjective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectiveView newObjective = new ObjectiveView(true, "", date, consoleController, ConsolleObjectiveView.this);
				newObjective.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		btnNewObjective.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnNewObjective);
	
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 412, 219);
		contentPane.add(scrollPane);
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 412, 219);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton btnDeleteAll = new JButton("Elimina Tutti");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectiveList = consoleController.getObjectiveList();
				//reset del panel e della lista
				if(objectiveList.isEmpty())
					JOptionPane.showMessageDialog(null, "Nessun elemento da eliminare", "Attenzione", JOptionPane.ERROR_MESSAGE);
				else {
					panel.removeAll();
					for(ObjectiveImpl o : objectiveList)
						controller.removeObjective(o.getName());
					updateUI();
				}					
			}
		});
		btnDeleteAll.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnDeleteAll.setBounds(224, 241, 198, 27);
		contentPane.add(btnDeleteAll);
	}
	
    // Metodo per aggiornare l'interfaccia utente
	public void updateUI() {
		//reset del panel (elimino tutti i ListObjective)
		panel.removeAll();
		//aggiorno la lista degli obbiettivi
		objectiveList = consoleController.getObjectiveList();
		//test
		System.out.println("LISTA OBBIETTIVI AGGIORNATA:");
		objectiveList.stream().map(o ->"Obbiettivo: "+ o.getName()+" Saldo:"+o.getBalance()).forEach(System.out::println);
		//reinserisco nel panel tutti gli elementi della lista obbiettivi
		objectiveList.stream().forEach(o -> panel.add(new ListObjectiveView(o.getName(), o.getBalance(), date, consoleController, this)));
	    // Richiamo il metodo revalidate() e repaint() per aggiornare l'interfaccia
	    contentPane.revalidate();
	    contentPane.repaint();		
    }
}
