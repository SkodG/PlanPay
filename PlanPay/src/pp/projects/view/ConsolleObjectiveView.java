package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.ObjectiveImpl;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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

public class ConsolleObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] objectives = {"Obbiettivo_1", "Obbiettivo_2", "Obbiettivo_3", "Obbiettivo_4"};//ho bisogno che questi oggetti siano creati in base a quanti obbettivi sono istanziati
	JPanel panelObjective;
	
	private List<DescObjectiveView> listObjective = new ArrayList<DescObjectiveView>();
	private DescObjectiveView descObjectiveView;
	private List<ObjectiveImpl> list;
	private int idCount;

	/**
	 * Create the frame.
	 */
	public ConsolleObjectiveView(ConsoleControllerImpl consoleController) {
		this.idCount = 0;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

				//TODO inserire elementi in una lista scrollabile
		//ottengo la lista di elementi dal controller e istanzio un oggetto DescObjectiveView per ogni Objective nella lista
		//e li salvo in un array (devo fare cast)->controllare slide
		
		 /*objectives = (DescObjectiveView[]) consoleController.getObjectiveList().stream().
				map(objective -> new DescObjectiveView()).toArray();*/
		//uso di toString() sugli Objective invece di usare DescObjectiveView???
		//TEST objectives = new DescObjectiveView[100];
		
		//JList list = new JList(listObjective.toArray());
		//list.setFont(new Font("Calibri", Font.PLAIN, 12));
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		//JScrollPane scrollPane = new JScrollPane(list);
		//scrollPane.setBounds(10, 11, 414, 160);
		//contentPane.add(scrollPane);
		//list.setVisible(true);
		//ListCellRenderer renderer = new DescObjectiveView();
		//list.setCellRenderer(renderer);
		
		//list.getCellRenderer();
		
	    JButton btnNewObjective = new JButton("Nuovo Obbiettivo");
			btnNewObjective.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					idCount += 1;
					ObjectiveView newObjective = new ObjectiveView(true, idCount, consoleController, ConsolleObjectiveView.this);
					newObjective.setVisible(true);
					list = consoleController.getObjectiveList();
					System.out.println(list);
				}
			});
		btnNewObjective.setBounds(155, 218, 135, 32);
		btnNewObjective.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewObjective);
		
		panelObjective = new JPanel();
		panelObjective.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelObjective.setLayout(new BoxLayout(panelObjective, BoxLayout.Y_AXIS));
		
		// 	TODO metterlo in verticale e sistemare la dimensione di panelObjective.
        JScrollPane scrollPane = new JScrollPane(panelObjective);
        contentPane.add(scrollPane, BorderLayout.CENTER);
				
	}
	
    // Metodo per aggiornare l'interfaccia utente
	public void updateUI() {

		Optional<ObjectiveImpl> objective = list.stream().reduce((first, second) -> second);
		if (objective.isPresent()) {
			System.out.println(objective.get());
			descObjectiveView = new DescObjectiveView(objective.get().getName()); // TODO aggiungere getImporto
			//listObjective.add(descObjectiveView);
	
	        // Creare un nuovo componente da aggiungere
	        // Aggiungi il nuovo componente al pannello principale
	    	panelObjective.add(descObjectiveView);
	    	descObjectiveView.setVisible(true);
	    	System.out.println(panelObjective.getComponentCount());
	
	        // Richiama il metodo revalidate() e repaint() per aggiornare l'interfaccia
	    	panelObjective.revalidate();
	    	panelObjective.repaint();
		}
    }
}
