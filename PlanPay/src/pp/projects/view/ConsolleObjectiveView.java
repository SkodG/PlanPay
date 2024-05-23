package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class ConsolleObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] objectives = {"Obbiettivo_1", "Obbiettivo_2", "Obbiettivo_3", "Obbiettivo_4"};//ho bisogno che questi oggetti siano creati in base a quanti obbettivi sono istanziati
	//quindi devo poter prendere la lista dal controller o da consoleview!
	//una volta ottenuta la lista, istanzio un DescObjView per ognuno(stream o loop?)
	//per far ciò non è necessaria laclasse  DescObjectiveView!!!
	//dopodichè  li metto nella lista di questo JFrame
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsolleObjectiveView frame = new ConsolleObjectiveView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public ConsolleObjectiveView(ConsoleControllerImpl consoleController) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				//TODO inserire elementi in una lista scrollabile
		//ottengo la lista di elementi dal controller e istanzio un oggetto DescObjectiveView per ogni Objective nella lista
		//e li salvo in un array (devo fare cast)->controllare slide
		
		 /*objectives = (DescObjectiveView[]) consoleController.getObjectiveList().stream().
				map(objective -> new DescObjectiveView()).toArray();*/
		//uso di toString() sugli Objective invece di usare DescObjectiveView???
		//TEST objectives = new DescObjectiveView[100];
		
		JList list = new JList(objectives);
		list.setFont(new Font("Calibri", Font.PLAIN, 12));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 11, 414, 160);
		contentPane.add(scrollPane);
		list.setVisible(true);
		ListCellRenderer renderer = new DescObjectiveView();
		list.setCellRenderer(renderer);
		
		list.getCellRenderer();
		
		JButton btnNewObjective = new JButton("Nuovo Obbiettivo");
		btnNewObjective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectiveView newObjective = new ObjectiveView(true, consoleController);
				newObjective.setVisible(true);
			}
		});
		btnNewObjective.setBounds(155, 218, 135, 32);
		btnNewObjective.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewObjective);
		


		
	}
}
