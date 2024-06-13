package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	//TODO da eliminare
	//private DescObjectiveView descObjectiveView;
	private List<ObjectiveImpl> objectiveList;
	private ConsoleControllerImpl consoleController;
	private int idCount;
	private LocalDate date;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ConsolleObjectiveView(ConsoleControllerImpl controller) {
		setTitle("OBBIETTIVI");
		this.idCount = 0;
		this.consoleController = controller;
		this.date = LocalDate.now();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewObjective = new JButton("Nuovo Obbiettivo");
		btnNewObjective.setBounds(139, 241, 156, 27);
		btnNewObjective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectiveView newObjective = new ObjectiveView(true, "", date, consoleController, ConsolleObjectiveView.this);
				newObjective.setVisible(true);
				
				//provvisorio->  andrebbe nel testing della classe
				objectiveList = consoleController.getObjectiveList();
				objectiveList.stream().map(o ->"Obbiettivo: "+ o.getName()).forEach(System.out::println);
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		// 	TODO metterlo in verticale e sistemare la dimensione di panelObjective.
			
	}
	
    // Metodo per aggiornare l'interfaccia utente
	
	//fornire l'intera lista obbiettivi
	//per ogni obbiettivo, creare un pulsante che funzioni come un descObjectiveView
	//in un pane scrollabile
	//al doppio click si apre la view dell'objective view
	public void updateUI(ObjectiveView objectiveView) {

		Optional<ObjectiveImpl> objective = objectiveList.stream().reduce((first, second) -> second);
		if (objective.isPresent()) {
			//test per verificare che l'obbiettivo inserito per ultimo sia quello appena creato
			System.out.println("update UI: "+objective.get().getName());
			
			
			ListObjectiveView descObjectiveView = new ListObjectiveView(objective.get().getName(), objective.get().getBalance(),
													date, objectiveList, consoleController, this); 
			//inserisco il descObjectiveView come chiave nella Map insieme al valore corrisp. ObjectiveView
			//objectiveViewMap.put(descObjectiveView, objectiveView);//FORSE INUTILE
			
			// TODO aggiungere getImporto
			//objectiveList.add(descObjectiveView);  ???
	
	        // Creare un nuovo componente da aggiungere
	        // Aggiungi il nuovo componente al pannello principale
			panel.add(descObjectiveView);

	    	descObjectiveView.setVisible(true);
	    	System.out.println(contentPane.getComponentCount());
	    	// Richiama il metodo revalidate() e repaint() per aggiornare l'interfaccia
	    	contentPane.revalidate();
	    	contentPane.repaint();
		}
		
    }
}
