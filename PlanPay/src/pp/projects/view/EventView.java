package pp.projects.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.EventImpl;
import pp.projects.model.State;

import javax.swing.JLabel;

import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Creazione di una JDialog per la capacità di essere modale. Usata per interazioni secondarie.
 */
public class EventView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField edTitolo;
	private JDateChooser dateChooserDa;
	private JDateChooser dateChooserA;
	private JTextField edAOra;
	private JTextField edDaOra;
	private JTextArea txtDescrizione;
	
	private String name;
	private String desc;
	private State s;
	private String daOra;
	private String aOra;
	//private String formattedDateDa;
	//private String formattedDateA;
	private LocalDate localDateDa;
	private LocalDate localDateA;

	/**
	 * Create the dialog.
	 */
	public EventView(boolean bNew, LocalDate date, ConsoleControllerImpl c, CalendarView calendar) {		
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
		this.name = edTitolo.getText();
		
		txtDescrizione = new JTextArea();
		txtDescrizione.setBounds(10, 202, 416, 211);
		contentPanel.add(txtDescrizione);
		this.desc = txtDescrizione.getText();
			
		JLabel lbDaGiorno = new JLabel("Da giorno:");
		lbDaGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDaGiorno.setBounds(10, 20, 95, 27);
		contentPanel.add(lbDaGiorno);
		
		JLabel lbAGiorno = new JLabel("A giorno:");
		lbAGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbAGiorno.setBounds(10, 58, 95, 27);
		contentPanel.add(lbAGiorno);
        	
        dateChooserDa = new JDateChooser();
        dateChooserDa.setBounds(104, 20, 120, 30);
        dateChooserDa.setVisible(true); // Nascondi il JDateChooser inizialmente
        contentPanel.add(dateChooserDa);
        
        
        // Aggiungi l'ActionListener al JDateChooser
        dateChooserDa.addPropertyChangeListener("date", evt -> {
            Date selectedDate = dateChooserDa.getDate();
            if (selectedDate != null) {
                // Converti Date a LocalDate
                localDateDa = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        });
        
		// Crea il JDateChooser
        dateChooserA = new JDateChooser();
        dateChooserA.setBounds(104, 55, 120, 30);
        dateChooserA.setVisible(true); 
        contentPanel.add(dateChooserA);
        
        
        // Aggiungi l'ActionListener al JDateChooser
        dateChooserA.addPropertyChangeListener("date", evt -> {
            Date selectedDate = dateChooserA.getDate();
            if (selectedDate != null) {
                // Converti Date a LocalDate
                localDateA = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        });
		
		edAOra = new JTextField();
		edAOra.setColumns(10);
		edAOra.setBounds(306, 57, 120, 27);
		contentPanel.add(edAOra);
		this.aOra = edAOra.getText();
		
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
		this.daOra = edDaOra.getText();
		
		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				System.err.println(bNew);
				EventImpl event = c.saveEvent(bNew, name, desc, localDateDa, localDateA, daOra, aOra, s, 
										 		edTitolo.getText(), txtDescrizione.getText(), edDaOra.getText(), edAOra.getText());

				// Aggiungi sul calendario la scritta!
				if(event == null) {
					System.err.println("NULL");
				}
								
				if(event != null) {
					System.err.println("DEFISGN:" + event.getInfoEventToString());
					calendar.updateUI(localDateDa, localDateA, edDaOra.getText(), edAOra.getText(), event);
				}
				EventView.this.setVisible(false);
			}
		});
		btnSalva.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnSalva.setActionCommand("OK");
		btnSalva.setBounds(224, 423, 95, 36);
		contentPanel.add(btnSalva);
		
		JButton btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = false;
				LocalDate currentDay = calendar.selectDate();
				
				System.out.println(currentDay);
				
				result = c.removeEvent(edTitolo.getText(), currentDay, edDaOra.getText());
				if(!result) {
					
				}
			}
		});
		btnCancella.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnCancella.setActionCommand("Cancel");
		btnCancella.setBounds(331, 423, 95, 36);
		contentPanel.add(btnCancella);
	}
	
	public void setEventDetail(String name, LocalDate date, String daOra, String aOra, String desc) {
		Date dateD = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		edTitolo.setText(name);
		dateChooserDa.setDate(dateD);
		dateChooserA.setDate(dateD);
		edDaOra.setText(daOra);
		edAOra.setText(aOra);
		txtDescrizione.setText(desc);
	}
}
