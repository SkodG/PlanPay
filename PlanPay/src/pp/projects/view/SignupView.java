package pp.projects.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.LoginControllerImpl;

import java.awt.TextField;
import java.awt.Label;
import java.awt.Font;
import java.awt.Button;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSignup;
	private JLabel lbNewUser;
	private JLabel lbNewPassword;
	private JTextField edNewUser;
	private JTextField edNewPassword;
	private JButton btnLoginY;
	private JLabel lbLoginY; 
	private JLabel lbName;
	private JTextField edName;
	private LoginControllerImpl controller;
	private String userName;
	private String user;
	private String password;

	/**
	 * Create the frame.
	 */
	public SignupView(LoginControllerImpl controller) {
		this.controller = controller;
		
		setTitle("SIGN UP");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbNewUser = new JLabel("User");
		lbNewUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbNewUser.setBounds(10, 94, 90, 21);
		contentPane.add(lbNewUser);
		
		edNewUser = new JTextField();
		edNewUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		edNewUser.setBounds(10, 119, 368, 33);
		contentPane.add(edNewUser);
		
		lbNewPassword = new JLabel("Password");
		lbNewPassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbNewPassword.setBounds(10, 174, 90, 21);
		contentPane.add(lbNewPassword);
		
		edNewPassword = new JTextField();
		edNewPassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		edNewPassword.setBounds(10, 201, 368, 33);
		contentPane.add(edNewPassword);
		
		lbLoginY = new JLabel("Ho un account! ");
		lbLoginY.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbLoginY.setBounds(10, 348, 115, 21);
		contentPane.add(lbLoginY);
		
		btnLoginY = new JButton("\r\nLogin");
		btnLoginY.setForeground(new Color(255, 255, 255));
		btnLoginY.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnLoginY.setBackground(SystemColor.activeCaption);
		btnLoginY.setBounds(123, 336, 108, 42);
		contentPane.add(btnLoginY);
		
		btnSignup = new JButton("\r\nSign Up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userName = edName.getText();
				user = edNewUser.getText();
				password = edNewPassword.getText();
				if(controller.signupButtonClick(user, password, userName)) {
					JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Info", JOptionPane.INFORMATION_MESSAGE);
					SignupView.this.setVisible(false);
				} else {
					 // Autenticazione fallita, mostra un messaggio di errore
					  JOptionPane.showMessageDialog(null, "ERRORE!. Credenziali mancanti o utente già registrato!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSignup.setForeground(new Color(255, 255, 255));
		btnSignup.setFont(new Font("Calibri", Font.PLAIN, 26));
		btnSignup.setBackground(new Color(50, 205, 50));
		btnSignup.setBounds(93, 260, 217, 51);
		contentPane.add(btnSignup);
		
		edName = new JTextField();
		edName.setFont(new Font("Calibri", Font.PLAIN, 16));
		edName.setBounds(10, 45, 368, 33);
		contentPane.add(edName);
		
		lbName = new JLabel("Account Name");
		lbName.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbName.setBounds(10, 18, 108, 21);
		contentPane.add(lbName);
	}
}
