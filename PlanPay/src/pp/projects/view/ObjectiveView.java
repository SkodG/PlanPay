package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.OperationType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class ObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textAmount;
	private ConsoleControllerImpl controller;
	private JLabel lblDisplayBalance;
	private double savingAmount;
	private double balance;
	private boolean	bNew;
	private boolean	hasSaved;
	private String description;
	private String nomeObbiettivo;
	
	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Nuovo" (x creare un nuovo obbiettivo).
	// al click del bottone instanzierai una nuova istanza di ObjectiveView, passandogli True, definendo quindi la creazione di una nuova istanza.
	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Apri", 
	//oppure apri l'obbiettivo con il doppio click sull'obbettivo (x modificare obbiettivo).
	// instanzi una nuova istanza di ObjectiveView, passandogli False, definendo quindi la modifica dell'istanza.
	/**
	 * Create the frame.
	 */
	public ObjectiveView(boolean bNew, String nomeObbiettivo, LocalDate date, ConsoleControllerImpl controller, ConsolleObjectiveView consObj) {
		
		this.controller = controller;
		this.nomeObbiettivo = nomeObbiettivo;
		this.bNew  = bNew;
		savingAmount = updateSavingAmount(nomeObbiettivo);
		description = updateDescription(nomeObbiettivo);
		balance = updateBalance(nomeObbiettivo);
		System.out.println("soglia  risparmio =" +savingAmount);
		System.out.println("descrizione =" +description);
		hasSaved = false;
		setTitle("OBBIETTIVO "+nomeObbiettivo+" - Data: "+date.toString());		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 476, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(22, 25, 46, 14);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblName);
		
		JButton btnProjection = new JButton("Previsione");
		btnProjection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO da implementare
			}
		});
		btnProjection.setBounds(10, 154, 123, 27);
		btnProjection.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnProjection);
		
		textName = new JTextField(nomeObbiettivo);
		textName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textName.setBounds(151, 22, 208, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		textName.setEditable(bNew);
		
		JLabel lblDescr = new JLabel("Descrizione:");
		lblDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDescr.setBounds(22, 57, 92, 14);
		contentPane.add(lblDescr);
		
		JLabel lblThreshold = new JLabel("Soglia risparmio");
		lblThreshold.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblThreshold.setBounds(22, 120, 101, 14);
		contentPane.add(lblThreshold);
				
		textAmount = new JTextField(Double.toString(savingAmount));
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(151, 117, 99, 20);
		contentPane.add(textAmount);
		
		JTextArea textDescr = new JTextArea(description);
		textDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		textDescr.setBounds(151, 53, 208, 53);
		contentPane.add(textDescr);
		
		JButton btnSave = new JButton("Salva modifiche");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					savingAmount = Double.parseDouble(textAmount.getText());
					if(textAmount.getText().isBlank() || textName.getText().isBlank())
						JOptionPane.showMessageDialog(null, "Inserire nome obbiettivo"+
								" e ammontare da risparmiare", "Errore", JOptionPane.ERROR_MESSAGE);					
					else if(controller.getObjective(textName.getText()).isEmpty()){
						//l'obbiettivo ha un nome nuovo
						//controllo che il nome non sia già preso
						System.out.println("vecchio nome obbiettivo: "+nomeObbiettivo);
						System.out.println("nuovo nome obbiettivo: "+textName.getText());//Optional.Empty -> non ha trovato l'obbiettivo
						description = textDescr.getText().isEmpty()? updateDescription(textName.getText()): textDescr.getText();
						System.out.println("nuova descrizione obbiettivo: "+description);
						controller.saveObjective(bNew, nomeObbiettivo, textName.getText(), description, savingAmount);
						hasSaved = true;
						textName.setEditable(false);
					}//altrimenti se l'obbiettivo è già stato creato ma viene modificato
					else if((controller.getObjective(textName.getText()).get().getSavingTarget() != savingAmount) ||
							 (controller.getObjective(textName.getText()).get().getDescription().equals(description))) {
						description = textDescr.getText().isEmpty()? updateDescription(textName.getText()): textDescr.getText();
						controller.saveObjective(bNew, nomeObbiettivo, textName.getText(), description, savingAmount);
					}
					else if(bNew && hasSaved == false){
						JOptionPane.showMessageDialog(null, "Obbiettivo già presente!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Inserire cifra numerica per la soglia di risparmio", "Errore", JOptionPane.ERROR_MESSAGE);
					textAmount.setText("0.00");
				}
				catch(IllegalStateException l) {
					JOptionPane.showMessageDialog(null, l.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				finally {
					consObj.updateUI();
				}
			}
		});
		btnSave.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnSave.setBounds(143, 154, 139, 27);
		contentPane.add(btnSave);
		
		JButton btnOperation = new JButton("Deposita/Preleva");
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.doClick();
				//apro il form di ServicesView solo se l'obbettivo è stato salvato e non sto creando un nuovo obbiettivo con lo stesso nome di un altro
				if(!bNew || hasSaved) {
					ServicesView serviceView = new ServicesView(OperationType.OBIETTIVO, textName.getText(), controller);
					serviceView.setVisible(true);
					setVisible(false);
					System.out.println("Deposita/preleva da: "+ textName.getText());
				}
				
			}		
		});
		btnOperation.setBounds(292, 154, 139, 27);
		btnOperation.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnOperation);
		
		JLabel lblCurrentAmount = new JLabel("Saldo:");
		lblCurrentAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblCurrentAmount.setBounds(272, 120, 46, 14);
		contentPane.add(lblCurrentAmount);
		
		JLabel lblDisplayBalance = new JLabel(Double.toString(balance)+" €");
		lblDisplayBalance.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayBalance.setBounds(331, 120, 82, 14);
		contentPane.add(lblDisplayBalance);
		
		JProgressBar progressBar = new JProgressBar(0, (int)savingAmount);
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setBounds(414, 22, 17, 115);
		contentPane.add(progressBar);
	}
	
	public void updateUIObjective() {
		double newBalance = updateBalance(this.nomeObbiettivo);
		lblDisplayBalance.setText(Double.toString(newBalance));
	}
	
	private double updateBalance(String nomeObbiettivo) {
		return (controller.getObjective(nomeObbiettivo).isPresent()) ?
				controller.getObjective(nomeObbiettivo).get().getBalance() : 0.00;
	}
	
	private double updateSavingAmount(String nomeObbiettivo) {
			return controller.getObjective(nomeObbiettivo).isPresent()?
					controller.getObjective(nomeObbiettivo).get().getSavingTarget(): 0.00;
	}
	
	private String updateDescription(String nomeObbiettivo) {
		return controller.getObjective(nomeObbiettivo).isPresent()? 
				controller.getObjective(nomeObbiettivo).get().getDescription():"";
	}
}