package pp.projects.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

/**
 * Creazione di una JDialog per la capacità di essere modale. Usata per interazioni secondarie.
 */
public class EventView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField edTitolo;
	private JTextField edDaGiorno;
	private JTextField edAGiorno;
	private JTextField edAOra;
	private JTextField edDaOra;

	/**
	 * Create the dialog.
	 */
	public EventView(LocalDate date) {
		setTitle("EVENTO");
		setBounds(100, 100, 450, 506);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbTitolo = new JLabel("Titolo:");
		lbTitolo.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbTitolo.setBounds(10, 139, 84, 27);
		contentPanel.add(lbTitolo);
		
		JLabel lbDescrizione = new JLabel("Descrizione:");
		lbDescrizione.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDescrizione.setBounds(10, 176, 120, 27);
		contentPanel.add(lbDescrizione);
		
		edTitolo = new JTextField();
		edTitolo.setBounds(104, 138, 322, 27);
		contentPanel.add(edTitolo);
		edTitolo.setColumns(10);
		
		JTextArea txtDescrizione = new JTextArea();
		txtDescrizione.setBounds(10, 202, 416, 211);
		contentPanel.add(txtDescrizione);
		
		JButton btnSalva = new JButton("Salva");
		btnSalva.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnSalva.setActionCommand("OK");
		btnSalva.setBounds(224, 423, 95, 36);
		contentPanel.add(btnSalva);
		
		JButton btnCancella = new JButton("Cancella");
		btnCancella.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnCancella.setActionCommand("Cancel");
		btnCancella.setBounds(331, 423, 95, 36);
		contentPanel.add(btnCancella);
		
		JLabel lbDaGiorno = new JLabel("Da giorno:");
		lbDaGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDaGiorno.setBounds(10, 20, 95, 27);
		contentPanel.add(lbDaGiorno);
		
		JLabel lbAGiorno = new JLabel("A giorno:");
		lbAGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbAGiorno.setBounds(10, 58, 95, 27);
		contentPanel.add(lbAGiorno);
		
		edDaGiorno = new JTextField();
		edDaGiorno.setColumns(10);
		edDaGiorno.setBounds(104, 19, 120, 27);
		contentPanel.add(edDaGiorno);
		
		edAGiorno = new JTextField();
		edAGiorno.setColumns(10);
		edAGiorno.setBounds(104, 57, 120, 27);
		contentPanel.add(edAGiorno);
		
		edAOra = new JTextField();
		edAOra.setColumns(10);
		edAOra.setBounds(306, 57, 120, 27);
		contentPanel.add(edAOra);
		
		JLabel lbAOra = new JLabel("Ora:");
		lbAOra.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbAOra.setBounds(255, 58, 41, 27);
		contentPanel.add(lbAOra);
		
		JLabel lbDaOra = new JLabel("Ora:");
		lbDaOra.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDaOra.setBounds(255, 20, 41, 27);
		contentPanel.add(lbDaOra);
		
		edDaOra = new JTextField();
		edDaOra.setColumns(10);
		edDaOra.setBounds(306, 19, 120, 27);
		contentPanel.add(edDaOra);
	}
}
