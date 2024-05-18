package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ObjectiveView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ObjectiveView frame = new ObjectiveView();
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
	public ObjectiveView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(24, 54, 46, 14);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Descrizione:");
		lblNewLabel_1.setBounds(24, 100, 76, 14);
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Data creazione:");
		lblNewLabel_2.setBounds(21, 11, 89, 14);
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Deposita");
		btnNewButton.setBounds(238, 16, 101, 23);
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Preleva");
		btnNewButton_1.setBounds(238, 50, 101, 23);
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Proiezione risparmio");
		btnNewButton_2.setBounds(238, 128, 162, 23);
		btnNewButton_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		contentPane.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(349, 49, 63, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(349, 19, 63, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 125, 197, 125);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(24, 68, 119, 22);
		contentPane.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(23, 30, 120, 22);
		contentPane.add(textArea_2);
	}
}