package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class ObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textAmount;
	private LocalDate date;
	private boolean	bNew;

	/**
	 * Create the frame.
	 */
	// quando nella view ConsolleObbiettivi inserisci il bottone "Nuovo" (x creare un nuovo obbiettivo).
	// al click del bottone instanzierai una nuova istanza di ObjectiveView, passandogli True, definendo quindi la creazione di una nuova istanza.
	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Apri", 
	//oppure apri l'obbiettivo con il doppio click sull'obbettivo (x modificare obbiettivo).
	// instanzi una nuova istanza di ObjectiveView, passandogli False, definendo quindi la modifica dell'istanza.
	public ObjectiveView(boolean bNew, int idCount, ConsoleControllerImpl controller, ConsolleObjectiveView contObj) {
		this.bNew = bNew;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(22, 11, 46, 14);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblName);
		
		JLabel lblDate = new JLabel("Data:");
		lblDate.setBounds(275, 11, 58, 14);
		lblDate.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblDate);
		
		JButton btnOperation = new JButton("Deposita/Preleva");
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Deposita/preleva da: "+ textName.getText());
				ServicesView serviceView = new ServicesView("OBBIETTIVO: "+ textName.getText(), controller);
				serviceView.setVisible(true);
			}
		});
		btnOperation.setBounds(239, 213, 139, 37);
		btnOperation.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnOperation);
		// TODO: al click creo il servizio e rendo visibile servicesView
		// avrai quindi new ServicesView("OBBIETTIVO: {nomeobbiettivo}"), il cui nome obbiettivo lo prendi dalla text box del nome obbiettivo
		// rendo visibile ServicessView
		
		
		JButton btnProjection = new JButton("Proiezione risparmio");
		btnProjection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO da implementare
			}
		});
		btnProjection.setBounds(39, 213, 154, 37);
		btnProjection.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnProjection);
		
		JLabel lblDisplayDate = new JLabel(/*date.toString()*/);
		lblDisplayDate.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayDate.setBounds(324, 11, 82, 14);
		contentPane.add(lblDisplayDate);
		
		textName = new JTextField();
		textName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textName.setBounds(151, 6, 99, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel lblDescr = new JLabel("Descrizione:");
		lblDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDescr.setBounds(22, 57, 69, 14);
		contentPane.add(lblDescr);
		
		JLabel lblThreshold = new JLabel("Soglia risparmio");
		lblThreshold.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblThreshold.setBounds(22, 133, 101, 14);
		contentPane.add(lblThreshold);
		
		textAmount = new JTextField();
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(151, 130, 99, 20);
		contentPane.add(textAmount);
		
		JTextArea textDescr = new JTextArea();
		textDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		textDescr.setBounds(151, 53, 208, 53);
		contentPane.add(textDescr);
		
		// da qui non si può dare una data a obbiettivo,
		//l'ideale sarebbe che la acquisisse da solo una volta istanziato!
		JButton btnSave = new JButton("Save changes");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(bNew) { 
					
				//}
				if(textAmount.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Soglia di risparmio non definita!", "Errore", JOptionPane.ERROR_MESSAGE);
				else {					
					controller.saveObjective(bNew, textName.getText(), textDescr.getText(), Double.parseDouble(textAmount.getText()));
					setVisible(false);
					contObj.updateUI();
				}
				
			}
		});
		btnSave.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnSave.setBounds(151, 176, 135, 27);
		contentPane.add(btnSave);
		
		JLabel lblCurrentAmount = new JLabel("Saldo:");
		lblCurrentAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblCurrentAmount.setBounds(275, 131, 46, 14);
		contentPane.add(lblCurrentAmount);
		
		JLabel lblDisplayAmount = new JLabel("...");
		lblDisplayAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayAmount.setBounds(324, 131, 82, 14);
		contentPane.add(lblDisplayAmount);
	}
}