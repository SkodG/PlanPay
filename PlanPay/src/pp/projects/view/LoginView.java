package pp.projects.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.LoginController;
import pp.projects.model.AuthenticationException;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogin;
	private JLabel lbUser;
	private JLabel lbPassword;
	private JTextField edUser;
	private JTextField edPassword;
	private JButton btnNew;
	private JLabel lbNew; 
	private String username;
	private String password;

	public LoginView(LoginController controller) {
		setForeground(UIManager.getColor("activeCaption"));
		this.username = "";
		this.password = "";	
		
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbUser = new JLabel(" User");
		lbUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbUser.setBounds(10, 35, 90, 21);
		contentPane.add(lbUser);
		
		lbPassword = new JLabel(" Password");
		lbPassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbPassword.setBounds(10, 129, 90, 21);
		contentPane.add(lbPassword);
		
		edUser = new JTextField();
		edUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		edUser.setBounds(10, 64, 361, 33);
		contentPane.add(edUser);
		
		edPassword = new JTextField();
		edPassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		edPassword.setBounds(10, 156, 361, 33);
		contentPane.add(edPassword);
		
		btnLogin = new JButton("\r\nLogin");
		btnLogin.setBackground(new Color(41, 235, 20));
		btnLogin.setFont(new Font("Calibri", Font.PLAIN, 28));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = edUser.getText();
				password = edPassword.getText();
				try {
					if(controller.loginButtonClick(username, password)) {
						LoginView.this.setVisible(false);
					} 
				} catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(null, "Nome utente e password non possono essere vuoti.", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (AuthenticationException e1) {
					JOptionPane.showMessageDialog(null, "Autenticazione fallita. Credenziali non valide. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		btnLogin.setBounds(99, 219, 207, 52);
		contentPane.add(btnLogin);
		
		btnNew = new JButton("\r\nSign Up");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edUser.setText("");
				edPassword.setText("");
				controller.newSignupButtonClick();
			}
		});
		btnNew.setForeground(new Color(255, 255, 255));
		btnNew.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnNew.setBackground(SystemColor.activeCaption);
		btnNew.setBounds(146, 311, 108, 42);
		contentPane.add(btnNew);
		
		lbNew = new JLabel("Non ho un account! ");
		lbNew.setFont(new Font("Calibri", Font.PLAIN, 16));
		lbNew.setBounds(10, 334, 153, 21);
		contentPane.add(lbNew);
	}
}
