package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_3;
	private boolean	bNew;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ObjectiveView frame = new ObjectiveView();
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
	// quando nella view ConsolleObbiettivi inserisci il bottone "Nuovo" (x creare un nuovo obbiettivo).
	// al click del bottone instanzierai una nuova istanza di ObjectiveView, passandogli True, definendo quindi la creazione di una nuova istanza.
	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Apri", oppure apri l'obbiettivo con il doppio click sull'obbettivo (x modificare obbiettivo).
	// instanzi una nuova istanza di ObjectiveView, passandogli False, definendo quindi la modifica dell'istanza.
	public ObjectiveView(boolean bNew, ConsoleControllerImpl controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.bNew = bNew;
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(22, 11, 46, 14);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblName);
		
		JLabel lblNewLabel_2 = new JLabel("Date:");
		lblNewLabel_2.setBounds(260, 11, 58, 14);
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Deposita");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServicesView serviceView = new ServicesView("OBBIETTIVO:"+ lblName.getText(), controller);
				serviceView.setVisible(true);
			}
		});
		btnNewButton.setBounds(182, 227, 101, 23);
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton);
		// TO DO: al click creo il servizio
		// avrai quindi new ServiceView("OBBIETTIVO: {nomeobbiettivo}"), il cui nome obbiettivo lo prendi dalla text box del nome obbiettivo
		// rendo visibile servicesView
		
		JButton btnNewButton_1 = new JButton("Preleva");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServicesView serviceView = new ServicesView("OBBIETTIVO:"+ lblName.getText(), controller);
			}
		});
		btnNewButton_1.setBounds(304, 227, 101, 23);
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton_1);
		// TO DO: al click creo il servizio e rendo visibile servicesView
		// avrai quindi new ServiceView("OBBIETTIVO: {nomeobbiettivo}"), il cui nome obbiettivo lo prendi dalla text box del nome obbiettivo
		// rendo visibile servicesView
		
		
		JButton btnNewButton_2 = new JButton("Proiezione risparmio");
		btnNewButton_2.setBounds(10, 227, 154, 23);
		btnNewButton_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel_4 = new JLabel("Transaction title:");
		lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(20, 36, 121, 23);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3_1 = new JLabel("...");
		lblNewLabel_3_1.setBounds(339, 9, 46, 14);
		contentPane.add(lblNewLabel_3_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField.setBounds(151, 6, 99, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblDescr = new JLabel("Description:");
		lblDescr.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblDescr.setBounds(22, 79, 69, 14);
		contentPane.add(lblDescr);
		
		JLabel lblNewLabel_5 = new JLabel("Beneficiary:");
		lblNewLabel_5.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(22, 148, 77, 14);
		contentPane.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(151, 145, 99, 20);
		contentPane.add(textField_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(151, 33, 208, 35);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(151, 83, 208, 53);
		contentPane.add(textArea_1);
		// TO DO: creare un bottone salva. Al click chiama il metodo controller.salvaObbiettivo
		//(dove gli passi la variabile bNew, istanziata nel costruttore)
		
		//NON E' CHIARO chi debba istanziare l'obbiettivo, da qui non si può dargli una data,
		//l'ideale sarebbe che la acquisisse da solo una volta istanziato!
		JButton btnNewButton_3 = new JButton("Save changes");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveObjective(bNew, lblName.getText(), lblDescr.getText(), null);
			}
		});
		btnNewButton_3.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton_3.setBounds(151, 176, 115, 23);
		contentPane.add(btnNewButton_3);
	}
}