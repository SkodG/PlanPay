package pp.projects.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.LoginControllerImpl;

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
	private LoginControllerImpl controller;
	private String username;
	private String password;

	/**
	 * Create the frame.
	 */
	public LoginView(LoginControllerImpl controller) {
		setForeground(UIManager.getColor("activeCaption"));
		this.username = "";
		this.password = "";	
		this.controller = controller;
		
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
				controller.loginButtonClick(username, password);
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
