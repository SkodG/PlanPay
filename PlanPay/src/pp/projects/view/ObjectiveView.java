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
import javax.swing.SwingConstants;

public class ObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textAmount;
	private LocalDate date;
	private boolean	bNew;


	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Nuovo" (x creare un nuovo obbiettivo).
	// al click del bottone instanzierai una nuova istanza di ObjectiveView, passandogli True, definendo quindi la creazione di una nuova istanza.
	
	// quando nella view ConsolleObbiettivi inserisci il bottone "Apri", 
	//oppure apri l'obbiettivo con il doppio click sull'obbettivo (x modificare obbiettivo).
	// instanzi una nuova istanza di ObjectiveView, passandogli False, definendo quindi la modifica dell'istanza.
	/**
	 * Create the frame.
	 */
	public ObjectiveView(boolean bNew, String nomeObbiettivo, LocalDate date, ConsoleControllerImpl controller, ConsolleObjectiveView consObj) {
		
		setTitle("OBBIETTIVO "+nomeObbiettivo+" - Data:"+date.toString());		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(22, 25, 46, 14);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblName);
		
		JButton btnOperation = new JButton("Deposita/Preleva");
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textName.getText().isBlank())
					;
				else {
					ServicesView serviceView = new ServicesView("OBBIETTIVO: "+ textName.getText(), controller);
					serviceView.setVisible(true);
				}
					
				System.out.println("Deposita/preleva da: "+ textName.getText());
			}
		});
		btnOperation.setBounds(285, 218, 139, 27);
		btnOperation.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnOperation);
		
		JButton btnProjection = new JButton("Previsione");
		btnProjection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO da implementare
			}
		});
		btnProjection.setBounds(7, 218, 123, 27);
		btnProjection.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnProjection);
		
		textName = new JTextField(nomeObbiettivo);
		textName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textName.setBounds(151, 22, 208, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel lblDescr = new JLabel("Descrizione:");
		lblDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDescr.setBounds(22, 57, 92, 14);
		contentPane.add(lblDescr);
		
		JLabel lblThreshold = new JLabel("Soglia risparmio");
		lblThreshold.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblThreshold.setBounds(22, 133, 101, 14);
		contentPane.add(lblThreshold);
		
		double savingAmount;
		if(bNew) 
			savingAmount = 0.0;
		else 
			savingAmount = controller.getObjective(nomeObbiettivo).get().getSavingTarget();
		
		System.out.println("NOME VIEW APPENA CREATA: "+nomeObbiettivo);
		
		
		textAmount = new JTextField(Double.toString(savingAmount));
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(151, 130, 99, 20);
		contentPane.add(textAmount);
		
		JTextArea textDescr = new JTextArea();
		textDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		textDescr.setBounds(151, 53, 208, 53);
		contentPane.add(textDescr);
		
		JButton btnSave = new JButton("Salva modifiche");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Double.parseDouble(textAmount.getText());
					
					if(textAmount.getText().isBlank() || textName.getText().isBlank())
						JOptionPane.showMessageDialog(null, "Inserire nome obbiettivo"+
								" e ammontare da risparmiare", "Errore", JOptionPane.ERROR_MESSAGE);					
					else if(controller.getObjective(textName.getText()).isEmpty()){//TODO  MODIFICARE DIRETTAMENTE METODI IN CONSOLE CONTROLLER
						//controllo che il nome non sia già preso
						System.out.println("vecchio nome obbiettivo: "+nomeObbiettivo);
						System.out.println("nuovo nome obbiettivo: "+textName.getText());//Optional.Empty -> non ha trovato l'obbiettivo
						controller.saveObjective(bNew, nomeObbiettivo, textName.getText(), textDescr.getText(), Double.parseDouble(textAmount.getText()));
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Obbiettivo già presente!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Inserire cifra numerica per la soglia di risparmio", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalStateException l) {
					JOptionPane.showMessageDialog(null, l.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				finally {
					consObj.updateUI(ObjectiveView.this);
				}
			}
		});
		btnSave.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnSave.setBounds(140, 218, 135, 27);
		contentPane.add(btnSave);
		
		JLabel lblCurrentAmount = new JLabel("Saldo:");
		lblCurrentAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblCurrentAmount.setBounds(275, 131, 46, 14);
		contentPane.add(lblCurrentAmount);
		
		Double accBalance = controller.getAccount().getBalance();
		
		JLabel lblDisplayAmount = new JLabel(accBalance.toString()+ " €");
		lblDisplayAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayAmount.setBounds(324, 131, 82, 14);
		contentPane.add(lblDisplayAmount);
	}
}