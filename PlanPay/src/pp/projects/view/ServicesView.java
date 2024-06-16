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
	private JTextField textName;
	private LocalDate date;
	private String name;

	/**
	 * Create the frame.
	 */
	public ServicesView(OperationType operationType, String tipo, ConsoleControllerImpl controller) {
		date = LocalDate.now();
		if(operationType == OperationType.OBIETTIVO)
			setTitle("OBBIETTIVO - Data: " + date);
		else
			setTitle("SERVIZIO CONTO - Data: " + date);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCurrency = new JLabel("€");
		lblCurrency.setBounds(173, 35, 37, 14);
		lblCurrency.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblCurrency);
		
		JLabel lblAmount = new JLabel("Importo:");
		lblAmount.setBounds(10, 38, 62, 14);
		lblAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblAmount);
		
		textAmount = new JTextField("0.00");
		textAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(82, 32, 86, 20);
		contentPane.add(textAmount);
		
		JLabel lblTitle = new JLabel("Causale");
		lblTitle.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblTitle.setBounds(10, 63, 62, 14);
		contentPane.add(lblTitle);
		
		textName = new JTextField();
		textName.setFont(new Font("Calibri", Font.PLAIN, 13));
		textName.setBounds(83, 60, 331, 20);
		textName.setColumns(10);	
		contentPane.add(textName);
		
		JButton btnDeposit = new JButton("Deposita");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double dAmount;
				//leggo la cifra inserita, se non è stata inserita alcuna cifra
				//do messaggio di errore
				try {
					dAmount = Double.parseDouble(textAmount.getText());
					if(operationType == OperationType.SERVIZIO) {
						controller.updateConto(dAmount, true, textName.getText());
					}
					else if(operationType == OperationType.OBIETTIVO) {
						String objectiveName = tipo.substring(12);
						//test
						System.out.println(objectiveName);
						//cerco nella lista degli obbiettivi 
						List<ObjectiveImpl> objectiveList = controller.getObjectiveList();
						Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
						//se trovo l'obbiettivo posso eseguire l'operazione
						if(optObjective.isPresent()) {
							controller.updateConto(dAmount, true, "");
							setVisible(false);
						}
						else {
							//messaggio di errore 
							JOptionPane.showMessageDialog(null, 
								"Obbiettivo non trovato! creare obbiettivo "+ objectiveName +" prima di effettuare un'operazione!",
								"Errore", JOptionPane.ERROR_MESSAGE);
						}				
					} 
				}catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Inserire cifra numerica  per l'operazione!", "Errore", JOptionPane.ERROR_MESSAGE);
					
				} finally {
					//Pulisci i campi una volta finito
				textAmount.setText("0.00");
				textName.setText("");
				}			
			}
		});
		btnDeposit.setBounds(10, 101, 149, 35);
		btnDeposit.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Preleva");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				boolean result = false;
				
				//TODO effettuare l'operazione di prelievo
				//Se questa view è stata chiamata dalla consoleView
				try {
					double dAmount = Double.parseDouble(textAmount.getText());
					if(operationType == OperationType.SERVIZIO) {
						result = controller.updateConto(dAmount, false, textName.getText());
						System.out.println(""+result);
					}
					else if(operationType == OperationType.OBIETTIVO) {
						String objectiveName = tipo.substring(12);
						System.out.println("Nome Obbiettivo:"+objectiveName);
						//cerco nella lista degli obbiettivi 
						Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
						//se trovo l'obbiettivo posso eseguire l'operazione
						if(optObjective.isPresent()) {
							result = controller.updateConto(dAmount, false, "");
						}
						else {
							//messaggio di errore 
							JOptionPane.showMessageDialog(null, 
									"Obbiettivo non trovato! creare obbiettivo "+ objectiveName +" prima di effettuare un'operazione!",
									"Errore", JOptionPane.ERROR_MESSAGE);
						}										
					}
					//Controllo che l'operazione sia avvenuta
					if(!result) 
						JOptionPane.showMessageDialog(null, "Operazione non riuscita! Fondi non sufficienti per prelievo",
								"Errore", JOptionPane.ERROR_MESSAGE);
					else
						setVisible(false);
				} catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Inserire un valore numerico  per l'operazione!", "Errore", JOptionPane.ERROR_MESSAGE);
				} finally {
					//Pulisci i campi una volta finito
				textAmount.setText("0.00");
				textName.setText("");
				}	
				
			} //TODO Si può riutilizzare il codice con un singolo actionListener?
		});
		btnWithdraw.setBounds(264, 101, 149, 35);
		btnWithdraw.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnWithdraw);
		
				

	}
}
