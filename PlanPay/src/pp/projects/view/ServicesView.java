package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServicesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField depAmount;
	private JTextField wthAmount;

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
	public ServicesView(String tipo, ConsoleControllerImpl controller) {//TODO modifica come nello screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		depAmount = new JTextField();
		depAmount.setBounds(214, 59, 86, 20);
		depAmount.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(depAmount);
		depAmount.setColumns(10);
		
		wthAmount = new JTextField();
		wthAmount.setBounds(214, 93, 86, 20);
		wthAmount.setFont(new Font("Calibri", Font.PLAIN, 12));
		wthAmount.setColumns(10);
		contentPane.add(wthAmount);
		
		JButton btnDeposit = new JButton("Deposita nel conto");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO effettuare l'operazione di deposito
				
				
			}
		});
		btnDeposit.setBounds(62, 58, 149, 23);
		btnDeposit.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Preleva dal conto");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO effettuare l'operazione di prelievo
				
			}
		});
		btnWithdraw.setBounds(62, 92, 149, 23);
		btnWithdraw.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnWithdraw);
		
		JLabel lblBalance = new JLabel("Saldo attuale");
		lblBalance.setBounds(123, 21, 81, 14);
		lblBalance.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblBalance);
		
		JLabel lblCurrency_1 = new JLabel("EUR");
		lblCurrency_1.setBounds(276, 21, 37, 14);
		lblCurrency_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblCurrency_1);
		
		JLabel lblCurrency_2 = new JLabel("EUR");
		lblCurrency_2.setBounds(302, 62, 37, 14);
		lblCurrency_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblCurrency_2);
		
		JLabel lblCurrency_3 = new JLabel("EUR");
		lblCurrency_3.setBounds(302, 96, 37, 14);
		lblCurrency_3.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblCurrency_3);
		
		JLabel lblDisplayBalance = new JLabel("...");
		lblDisplayBalance.setBounds(214, 21, 46, 14);
		lblDisplayBalance.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblDisplayBalance.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblDisplayBalance);
	}
}
