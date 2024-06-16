package pp.projects.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.Account;
import pp.projects.model.OperationType;

import java.awt.Font;
import java.awt.Color;
import java.util.List;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ConsolleView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCalendar;
	private JButton btnServices;
	private JButton btnTransactions;
	private JLabel lbTransactions;
	private JLabel lbUsername;
	private JLabel lbImp;
	private JLabel lblmporto;
	private JButton btnObjectives;
	private DefaultListModel<String> transactionListModel;
	private JList<String> transactionList;
	private JScrollPane scrollPane;
	private int count;
	
	private ConsoleControllerImpl controller;
	private CalendarView calendarView;
	private ServicesView servicesView;
	private ConsolleObjectiveView consolleObjectiveView;
	private Account account;
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ConsolleView(ConsoleControllerImpl c, Account account) throws IOException {
		setTitle("CONSOLLE");
		this.controller = c;
		this.servicesView = new ServicesView(OperationType.SERVIZIO, "", c);//modificato costruttore
		this.consolleObjectiveView = new ConsolleObjectiveView(c);		
		this.account = account;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 490);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbUsername = new JLabel("");
		lbUsername.setFont(new Font("Calibri", Font.PLAIN, 38));
		lbUsername.setBounds(31, 24, 461, 42);
		contentPane.add(lbUsername);
		
		btnServices = new JButton("SERVIZI");
		btnServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicesView.setVisible(true);
			}
		});
		btnServices.setFont(new Font("Calibri", Font.PLAIN, 36));
		btnServices.setBounds(31, 165, 307, 176);
		contentPane.add(btnServices);
		
		lbImp = new JLabel("Importo:");
		lbImp.setFont(new Font("Calibri", Font.PLAIN, 33));
		lbImp.setBounds(31, 76, 134, 42);
		contentPane.add(lbImp);
		
		lblmporto = new JLabel("0,00 €");
		lblmporto.setFont(new Font("Calibri", Font.PLAIN, 34));
		lblmporto.setBounds(175, 76, 178, 42);
		contentPane.add(lblmporto);
		       
		lbTransactions = new JLabel("Transazioni");
		lbTransactions.setFont(new Font("Calibri", Font.PLAIN, 36));
		lbTransactions.setBounds(31, 385, 597, 42);
		contentPane.add(lbTransactions);
		
		btnCalendar = new JButton();
		try {
			btnCalendar.setIcon(new ImageIcon(this.getClass().getResource("/images/calendar.png")));
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarView = c.drawCalendar();
				calendarView.setVisible(true);
			}
		});
		btnCalendar.setBounds(558, 24, 134, 94);	
		contentPane.add(btnCalendar);
		
		btnTransactions = new JButton();
		try {
		    btnTransactions.setIcon(new ImageIcon(this.getClass().getResource("/images/freccia.png")));
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		btnTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count += 1;
				if (count % 2 == 0) {
					if(updateUI()) {
						transactionList.setVisible(false);
				        scrollPane.setVisible(false);
						setBounds(100, 100, 736, 490);
					}
				} else {
					if(updateUI()) {
						transactionList.setVisible(true);
				        scrollPane.setVisible(true);
						setBounds(100, 100, 736, 722);
					}
				}
			}
		});
		btnTransactions.setBounds(630, 385, 62, 42);
		contentPane.add(btnTransactions);
		
		btnObjectives = new JButton("OBBIETTIVI");
		btnObjectives.setFont(new Font("Calibri", Font.PLAIN, 36));
		btnObjectives.setBounds(385, 165, 307, 176);
		contentPane.add(btnObjectives);
		
		btnObjectives.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consolleObjectiveView.setVisible(true);
			}
		});
		
        // Inizializza la JList e lo JScrollPane
        transactionListModel = new DefaultListModel<>();
        transactionList = new JList<>(transactionListModel);
        transactionList.setFont(new Font("Calibri", Font.PLAIN, 19));
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(transactionList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(31, 433, 661, 249);
        contentPane.add(scrollPane);

        // Nascondi la JList e lo JScrollPane all'inizio
        transactionList.setVisible(false);
        scrollPane.setVisible(false);
		
		// WindowListener per gestire l'evento "windowOpened"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                lbUsername.setText(c.setNameController().toUpperCase());
            }
        });
	}
	
	public boolean updateUI() {
		List<String> transactions = controller.getDatiTransazione();	
		
		if(transactions != null) {	        
            transactionListModel.clear(); 	// Pulisce il modello prima di aggiungere nuovi elementi
            transactionListModel.addElement("<html><div style='height:1px;'></div></html>"); 
            
            for (String transaction : transactions) {
                transactionListModel.addElement(transaction);
            }

            return !transactions.isEmpty(); // Restituisce true se ci sono transazioni
        }
		
		return false;
	}
	
	public void updateUIconto() {
		lblmporto.setText(account.getBalance().toString());
		consolleObjectiveView.updateUI();
	}

}
