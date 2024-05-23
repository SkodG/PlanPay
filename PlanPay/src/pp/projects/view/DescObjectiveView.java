package pp.projects.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class DescObjectiveView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DescObjectiveView(String name) {
		setBounds(100, 100, 450, 113);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(name);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 22, 216, 27);
		contentPane.add(lblNewLabel);
		
		JButton btnApri = new JButton("Apri");
		btnApri.setBounds(312, 23, 114, 21);
		contentPane.add(btnApri);
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.setBounds(312, 42, 114, 21);
		contentPane.add(btnElimina);
	}

}
