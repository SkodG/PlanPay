package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class ServicesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private LocalDate date; //TODO gestire la data!

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServicesView frame = new ServicesView();
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
	public ServicesView(String tipo, ConsoleControllerImpl controller /*TODO Gestire la data!*/) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLocalDate = new JLabel("Data:");
		lblLocalDate.setBounds(78, 21, 81, 14);
		lblLocalDate.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblLocalDate);
		
		JLabel lblDisplayLocalDate = new JLabel(/*date.toString()*/);
		lblDisplayLocalDate.setBounds(182, 21, 101, 14);
		lblDisplayLocalDate.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayLocalDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblDisplayLocalDate);
		
		JButton btnDeposit = new JButton("Deposita");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double dAmount = Double.parseDouble(textAmount.getText());
				if(tipo.equals("SERVIZIO: ")) {
					controller.updateConto(dAmount, false, tipo);
					
				}
				else if(tipo.startsWith("OBBIETTIVO: ")) {
					String objectiveName = tipo.substring(12);
					//test
					System.out.println(objectiveName);
					//cerco nella lista degli obbiettivi 
					List<ObjectiveImpl> objectiveList = controller.getObjectiveList();
					Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
					//se trovo l'obbiettivo posso eseguire l'operazione
					if(optObjective.isPresent()) {
						controller.updateConto(dAmount, false, tipo);
						JOptionPane.showMessageDialog(null, "Operazione riuscita!", "Successo", JOptionPane.ERROR_MESSAGE);
					}
					else {
						//messaggio di errore 
						JOptionPane.showMessageDialog(null, 
								"Obbiettivo non trovato! creare obbiettivo "+ objectiveName +" prima di effettuare un'operazione!",
								"Errore", JOptionPane.ERROR_MESSAGE);
					}					
				}
				//Pulisci i campi una volta finito
				textAmount.setText("");
				
			}
		});
		btnDeposit.setBounds(32, 140, 149, 35);
		btnDeposit.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Preleva");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				boolean result = false;
				//TODO effettuare l'operazione di prelievo
				//Se questa view è stata chiamata dalla consoleView
				double dAmount = Double.parseDouble(textAmount.getText());
				if(tipo.equals("SERVIZIO:")) {
					
					result = controller.updateConto(dAmount, true, tipo);
				}
				else if(tipo.startsWith("OBBIETTIVO: ")) {
					String objectiveName = tipo.substring(12);
					System.out.println(objectiveName);
					//cerco nella lista degli obbiettivi 
					List<ObjectiveImpl> objectiveList = controller.getObjectiveList();
					Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
					//se trovo l'obbiettivo posso eseguire l'operazione
					if(optObjective.isPresent()) {
						result = controller.updateConto(dAmount, true, tipo);
					}
					else {
						//messaggio di errore 
						JOptionPane.showMessageDialog(null, 
								"Obbiettivo non trovato! creare obbiettivo "+ objectiveName +" prima di effettuare un'operazione!",
								"Errore", JOptionPane.ERROR_MESSAGE);
					}										
				}
				//Controllo che l'operazione sia avvenuta
				if(result) {
					JOptionPane.showMessageDialog(null,	"Operazione riuscita!", "Successo", JOptionPane.ERROR_MESSAGE);
					//Pulisci i campi una volta finito
					textAmount.setText("");
				} else {
					JOptionPane.showMessageDialog(null, 
							"Operazione non riuscita! Fondi non sufficienti per prelievo",
							"Errore", JOptionPane.ERROR_MESSAGE);
				}
			} //TODO Si può riutilizzare il codice con un singolo actionListener?
		});
		
		btnWithdraw.setBounds(241, 140, 149, 35);
		btnWithdraw.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnWithdraw);
		
				
		JLabel lblCurrency = new JLabel("EUR");
		lblCurrency.setBounds(262, 84, 37, 14);
		lblCurrency.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblCurrency);
		
		JLabel lblAmount = new JLabel("Importo:");
		lblAmount.setBounds(78, 84, 62, 14);
		lblAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblAmount);
		
		textAmount = new JTextField();
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(166, 81, 86, 20);
		contentPane.add(textAmount);
		
		JLabel lblTitle = new JLabel("Causale");
		lblTitle.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblTitle.setBounds(78, 51, 62, 14);
		contentPane.add(lblTitle);
		
		JLabel lblDisplayTitle = new JLabel(tipo);
		lblDisplayTitle.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayTitle.setBounds(182, 49, 70, 14);
		contentPane.add(lblDisplayTitle);
	}
}
