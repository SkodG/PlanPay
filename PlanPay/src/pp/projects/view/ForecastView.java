<<<<<<< HEAD
package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.IllegalInputException;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class ForecastView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private JLabel lblInserireMesi;
	private JTextField textYearlyFreq;
	private JComboBox<String> comboBox;
	private JTextField textYears;
	private JTextField textMonths;
	private int years;
	private int months;
	private double frequency;
	private double balance;
	private double result;



	/**
	 * Create the frame.
	 */
	public ForecastView(ConsoleControllerImpl controller, String objectiveName) {
		years = 0;
		months = 0;
		result = 0.00;
		balance = 0.00;
		String[] freqSelection = {"Nessuna","Mensile","Semestrale","Annuale", "Altro"};
		Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
		ObjectiveImpl objective = null;
		if(optObjective.isPresent())
			balance	= optObjective.get().getBalance();
		else
			JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
		//TODO chiudere la finestra


		setTitle("RISPARMIO PER OBBIETTIVO: "+ objectiveName);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSomma = new JLabel("Per raggiungere la somma di");
		lblSomma.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblSomma.setBounds(10, 11, 164, 22);
		contentPane.add(lblSomma);

		JLabel lblFreq = new JLabel("Seleziona la frequenza di versamento");
		lblFreq.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblFreq.setBounds(10, 105, 211, 22);
		contentPane.add(lblFreq);

		JLabel lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(341, 15, 33, 14);
		contentPane.add(lblNewLabel_1);

		textAmount = new JTextField();
		textAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 13));
		textAmount.setColumns(10);
		textAmount.setBounds(231, 12, 98, 20);
		contentPane.add(textAmount);

		JLabel lblNewLabel_2 = new JLabel("Quanto dovresti versare?");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 169, 189, 22);
		contentPane.add(lblNewLabel_2);

		comboBox = new JComboBox<>(freqSelection);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		comboBox.setBounds(231, 103, 143, 22);
		contentPane.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                handleComboBoxSelection(selected);
                System.out.println("frequency selected: "+frequency);
            }
        });

		textYearlyFreq = new JTextField();
		textYearlyFreq.setHorizontalAlignment(SwingConstants.RIGHT);
		textYearlyFreq.setFont(new Font("Calibri", Font.PLAIN, 13));
		textYearlyFreq.setColumns(10);
		textYearlyFreq.setBounds(231, 137, 98, 20);
		contentPane.add(textYearlyFreq);
		textYearlyFreq.setVisible(false);

		lblInserireMesi = new JLabel("inserire mesi versamento");
		lblInserireMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblInserireMesi.setBounds(10, 136, 164, 22);
		contentPane.add(lblInserireMesi);
		lblInserireMesi.setVisible(false);

		JLabel lblDisplayResult = new JLabel(Double.toString(result));
		lblDisplayResult.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayResult.setBounds(231, 203, 143, 20);
		contentPane.add(lblDisplayResult);

		JLabel lblNewLabel = new JLabel("entro un periodo di");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 44, 164, 14);
		contentPane.add(lblNewLabel);

		textYears = new JTextField();
		textYears.setHorizontalAlignment(SwingConstants.RIGHT);
		textYears.setFont(new Font("Calibri", Font.PLAIN, 13));
		textYears.setColumns(10);
		textYears.setBounds(231, 41, 98, 20);
		contentPane.add(textYears);

		JLabel lblAnni = new JLabel("anni");
		lblAnni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnni.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblAnni.setBounds(341, 45, 33, 14);
		contentPane.add(lblAnni);

		textMonths = new JTextField();
		textMonths.setHorizontalAlignment(SwingConstants.RIGHT);
		textMonths.setFont(new Font("Calibri", Font.PLAIN, 13));
		textMonths.setColumns(10);
		textMonths.setBounds(231, 72, 98, 20);
		contentPane.add(textMonths);

		JLabel lblMesi = new JLabel("mesi");
		lblMesi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblMesi.setBounds(339, 78, 35, 14);
		contentPane.add(lblMesi);

		JCheckBox ckboxNewCheckBox = new JCheckBox("Tieni conto del Saldo corrente");
		ckboxNewCheckBox.setSelected(true);
		ckboxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		ckboxNewCheckBox.setBounds(6, 200, 193, 23);
		contentPane.add(ckboxNewCheckBox);

		JButton btnCalculate = new JButton("Calcola");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(optObjective.isPresent()) {//TODO sostituire con un  excpetion? es ObjectiveNotFoundException
						years = Integer.parseInt(textYears.getText());
						months = Integer.parseInt(textMonths.getText());
						double targetAmount = Double.parseDouble(textAmount.getText());						
						if(comboBox.getSelectedItem().equals("Altro")) {
							frequency = 12 / Double.parseDouble(textYearlyFreq.getText());
							System.out.println("frequenza inserita:"+ frequency);
						}
						result = optObjective.get().savingForecast(targetAmount, frequency, years, months, ckboxNewCheckBox.isSelected());
						String truncatedResult = String.format("%.2f",result);
						lblDisplayResult.setText(truncatedResult);
					}
					else
						JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Uno o più campi errati!", "Errore", JOptionPane.ERROR_MESSAGE);					
				}
				catch(NullPointerException p){
					JOptionPane.showMessageDialog(null, "Uno o più campi vuoti!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalInputException i) {
					JOptionPane.showMessageDialog(null, i.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnCalculate.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnCalculate.setBounds(231, 169, 98, 23);
		contentPane.add(btnCalculate);

	}
	private void handleComboBoxSelection(String selected) {
		lblInserireMesi.setVisible(false);
    	textYearlyFreq.setVisible(false);

	        switch (selected) {
	        	case "Nessuna":
	        		this.frequency -= frequency;
	        		break;
	            case "Mensile":
	                this.frequency = 12.0;
	                break;
	            case "Semestrale":
	            	this.frequency = 2.0;
	                break;
	            case "Annuale":
	            	this.frequency = 1.0;
	            	break;
	            case "Altro":
	            	this.frequency = 0.0;
	        		lblInserireMesi.setVisible(true);
	            	textYearlyFreq.setVisible(true);
	            	break;
	        }
	}
}
=======
package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.IllegalInputException;
import pp.projects.model.ObjectiveImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class ForecastView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private JLabel lblInserireMesi;
	private JTextField textYearlyFreq;
	private JComboBox<String> comboBox;
	private JTextField textYears;
	private JTextField textMonths;
	private int years;
	private int months;
	private double frequency;
	private double balance;
	private double result;
	


	/**
	 * Create the frame.
	 */
	public ForecastView(ConsoleControllerImpl controller, String objectiveName) {
		
		years = 0;
		months = 0;
		result = 0.00;
		balance = 0.00;
		String[] freqSelection = {"Nessuna","Mensile","Semestrale","Annuale", "Altro"};
		Optional<ObjectiveImpl> optObjective = controller.getObjective(objectiveName);
		ObjectiveImpl objective = null;
		if(optObjective.isPresent())
			balance	= optObjective.get().getBalance();
		else
			JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
		//TODO chiudere la finestra
			
		
		setTitle("RISPARMIO PER OBBIETTIVO: "+ objectiveName);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSomma = new JLabel("Per raggiungere la somma di");
		lblSomma.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblSomma.setBounds(10, 11, 164, 22);
		contentPane.add(lblSomma);
		
		JLabel lblFreq = new JLabel("Seleziona la frequenza di versamento");
		lblFreq.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblFreq.setBounds(10, 105, 211, 22);
		contentPane.add(lblFreq);
		
		JLabel lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(341, 15, 33, 14);
		contentPane.add(lblNewLabel_1);
		
		textAmount = new JTextField();
		textAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 13));
		textAmount.setColumns(10);
		textAmount.setBounds(231, 12, 98, 20);
		contentPane.add(textAmount);
		
		JLabel lblNewLabel_2 = new JLabel("Quanto dovresti versare?");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 169, 189, 22);
		contentPane.add(lblNewLabel_2);
		
		comboBox = new JComboBox<>(freqSelection);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		comboBox.setBounds(231, 103, 143, 22);
		contentPane.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                handleComboBoxSelection(selected);
                System.out.println("frequency selected: "+frequency);
            }
        });
		
		textYearlyFreq = new JTextField();
		textYearlyFreq.setHorizontalAlignment(SwingConstants.RIGHT);
		textYearlyFreq.setFont(new Font("Calibri", Font.PLAIN, 13));
		textYearlyFreq.setColumns(10);
		textYearlyFreq.setBounds(231, 137, 98, 20);
		contentPane.add(textYearlyFreq);
		textYearlyFreq.setVisible(false);
		
		lblInserireMesi = new JLabel("inserire mesi versamento");
		lblInserireMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblInserireMesi.setBounds(10, 136, 164, 22);
		contentPane.add(lblInserireMesi);
		lblInserireMesi.setVisible(false);
		
		JLabel lblDisplayResult = new JLabel(Double.toString(result));
		lblDisplayResult.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayResult.setBounds(231, 203, 143, 20);
		contentPane.add(lblDisplayResult);
		
		JLabel lblNewLabel = new JLabel("entro un periodo di");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 44, 164, 14);
		contentPane.add(lblNewLabel);
		
		textYears = new JTextField();
		textYears.setHorizontalAlignment(SwingConstants.RIGHT);
		textYears.setFont(new Font("Calibri", Font.PLAIN, 13));
		textYears.setColumns(10);
		textYears.setBounds(231, 41, 98, 20);
		contentPane.add(textYears);
		
		JLabel lblAnni = new JLabel("anni");
		lblAnni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnni.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblAnni.setBounds(341, 45, 33, 14);
		contentPane.add(lblAnni);
		
		textMonths = new JTextField();
		textMonths.setHorizontalAlignment(SwingConstants.RIGHT);
		textMonths.setFont(new Font("Calibri", Font.PLAIN, 13));
		textMonths.setColumns(10);
		textMonths.setBounds(231, 72, 98, 20);
		contentPane.add(textMonths);
		
		JLabel lblMesi = new JLabel("mesi");
		lblMesi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblMesi.setBounds(339, 78, 35, 14);
		contentPane.add(lblMesi);
		
		JCheckBox ckboxNewCheckBox = new JCheckBox("Tieni conto del Saldo corrente");
		ckboxNewCheckBox.setSelected(true);
		ckboxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		ckboxNewCheckBox.setBounds(6, 200, 193, 23);
		contentPane.add(ckboxNewCheckBox);
		
		JButton btnCalculate = new JButton("Calcola");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(optObjective.isPresent()) {//TODO sostituire con un  excpetion? es ObjectiveNotFoundException
						years = Integer.parseInt(textYears.getText());
						months = Integer.parseInt(textMonths.getText());
						double targetAmount = Double.parseDouble(textAmount.getText());						
						if(comboBox.getSelectedItem().equals("Altro")) {
							frequency = 12 / Double.parseDouble(textYearlyFreq.getText());
							System.out.println("frequenza inserita:"+ frequency);
						}
						result = optObjective.get().savingForecast(targetAmount, frequency, years, months, ckboxNewCheckBox.isSelected());
						String truncatedResult = String.format("%.2f",result);
						lblDisplayResult.setText(truncatedResult);
					}
					else
						JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Uno o più campi errati!", "Errore", JOptionPane.ERROR_MESSAGE);					
				}
				catch(NullPointerException p){
					JOptionPane.showMessageDialog(null, "Uno o più campi vuoti!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalInputException i) {
					JOptionPane.showMessageDialog(null, i.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnCalculate.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnCalculate.setBounds(231, 169, 98, 23);
		contentPane.add(btnCalculate);
		
	}
	private void handleComboBoxSelection(String selected) {
		lblInserireMesi.setVisible(false);
    	textYearlyFreq.setVisible(false);
    	
	        switch (selected) {
	        	case "Nessuna":
	        		this.frequency -= frequency;
	        		break;
	            case "Mensile":
	                this.frequency = 12.0;
	                break;
	            case "Semestrale":
	            	this.frequency = 2.0;
	                break;
	            case "Annuale":
	            	this.frequency = 1.0;
	            	break;
	            case "Altro":
	            	this.frequency = 0.0;
	        		lblInserireMesi.setVisible(true);
	            	textYearlyFreq.setVisible(true);
	            	break;
	        }
	}
}
>>>>>>> refs/remotes/origin/main
