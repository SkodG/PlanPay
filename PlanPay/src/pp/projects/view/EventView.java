package pp.projects.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;

import pp.projects.controller.ConsoleController;
import pp.projects.model.ComparatorEvents;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.State;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/**
 * Creazione di una JDialog per la capacità di essere modale. Usata per interazioni secondarie.
 */
public class EventView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField edTitolo;
	private JDateChooser dateChooserDa;
	private JDateChooser dateChooserA;
	private JFormattedTextField timeFieldDaOra;
	private JFormattedTextField timeFieldAora;
	private JTextArea txtDescrizione;
	private JComboBox<String> cmbStato;
	private JButton btnCancellaAttivita;
	private JButton btnCancellaTuttoEvento;
	
	private String name;
	private String desc;
	private State stato;
	private String identifierEvent;
	private LocalDate selectedDateDa;
	private LocalDate selectedDateA;
	private boolean bNew;
	private ConsoleController controller;
	
	/**
	 * Create the dialog.
	 */
	public EventView(boolean bNew, LocalDate date, ConsoleController c, CalendarView calendar) {		
		this.name = new String();
		this.bNew = bNew;
		this.controller = c;
		
		setTitle("EVENTO");
		setBounds(100, 100, 500, 526);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbTitolo = new JLabel("Titolo:");
		lbTitolo.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbTitolo.setBounds(10, 168, 84, 27);
		contentPanel.add(lbTitolo);
		
		JLabel lbDescrizione = new JLabel("Descrizione:");
		lbDescrizione.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDescrizione.setBounds(10, 205, 120, 27);
		contentPanel.add(lbDescrizione);
		
		edTitolo = new JTextField();
		edTitolo.setFont(new Font("Calibri", Font.PLAIN, 15));
		edTitolo.setBounds(104, 158, 372, 36);
		contentPanel.add(edTitolo);
		edTitolo.setColumns(10);
		this.name = edTitolo.getText();
		
		txtDescrizione = new JTextArea();
		txtDescrizione.setFont(new Font("Calibri", Font.PLAIN, 15));
		txtDescrizione.setBounds(10, 242, 466, 171);
		contentPanel.add(txtDescrizione);		
        this.desc = txtDescrizione.getText();
        
		JLabel lbAOra = new JLabel("Ora:");
		lbAOra.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbAOra.setBounds(305, 58, 41, 27);
		contentPanel.add(lbAOra);
		
		JLabel lbDaOra = new JLabel("Ora:");
		lbDaOra.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDaOra.setBounds(305, 20, 41, 27);
		contentPanel.add(lbDaOra);
		
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormatter timeFormatter = new DateFormatter(timeFormat);
        timeFormatter.setAllowsInvalid(false);  // Non permette valori invalidi
        timeFormatter.setOverwriteMode(true);   // Sovrascrive il testo durante la digitazione
                
        timeFieldDaOra = new JFormattedTextField(timeFormatter);
		timeFieldDaOra.setFont(new Font("Calibri", Font.PLAIN, 16));
		timeFieldDaOra.setBounds(356, 20, 120, 30);
		timeFieldDaOra.setColumns(5);
	    contentPanel.add(timeFieldDaOra);  
	        
		timeFieldAora = new JFormattedTextField(timeFormatter);
		timeFieldAora.setFont(new Font("Calibri", Font.PLAIN, 16));
		timeFieldAora.setBounds(356, 53, 120, 30);
		timeFieldAora.setColumns(5);
	    contentPanel.add(timeFieldAora);     

        try {
            Date midnight = timeFormat.parse("00:00");
            timeFieldAora.setValue(midnight);  // Imposta mezzanotte come valore predefinito
            timeFieldDaOra.setValue(midnight);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		JLabel lbDaGiorno = new JLabel("Da giorno:");
		lbDaGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbDaGiorno.setBounds(10, 20, 95, 27);
		contentPanel.add(lbDaGiorno);
		
		JLabel lbAGiorno = new JLabel("A giorno:");
		lbAGiorno.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbAGiorno.setBounds(10, 58, 95, 27);
		contentPanel.add(lbAGiorno);
        	
        dateChooserDa = new JDateChooser();
        dateChooserDa.setFont(new Font("Calibri", Font.PLAIN, 15));
        dateChooserDa.setBounds(104, 20, 120, 30);
        dateChooserDa.setVisible(true); // Nascondi il JDateChooser inizialmente
        contentPanel.add(dateChooserDa);
        
        dateChooserA = new JDateChooser();
        dateChooserA.setFont(new Font("Calibri", Font.PLAIN, 15));
        dateChooserA.setBounds(104, 55, 120, 30);
        dateChooserA.setVisible(true); 
        contentPanel.add(dateChooserA);
                
        selectedDateDa = date;
        selectedDateA = date;
        
        // Setta la data sul JDateChooser per la creazione di un nuovo evento
        dateChooserDa.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateChooserA.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        /* -----  ActionListener per il JDateChooser serve a catturare e gestire i cambiamenti di data selezionati dall'utente.
        	è utile per aggiornare automaticamente altre parti dell' applicazione. ------- */
        dateChooserDa.addPropertyChangeListener("date", evt -> {
        	if (!calendar.getSelectingEvent()) {
	            Date selectedDate = dateChooserDa.getDate();
	            if (selectedDate != null) {
	                // Converti Date a LocalDate
	            	selectedDateDa = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            }
        	}
        });
        
        dateChooserA.addPropertyChangeListener("date", evt -> {
        	if (!calendar.getSelectingEvent()) {
	            Date selectedDate = dateChooserA.getDate();
	            if (selectedDate != null) {
	                // Converti Date a LocalDate
	            	selectedDateA = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            }
        	}
        });
        
		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {					
					timeFieldAora.commitEdit();
	                timeFieldDaOra.commitEdit();
	                
	                String newDaOra = timeFieldDaOra.getText();
	                String newAora = timeFieldAora.getText();

	                // Controlla che aData sia maggiore di daData
	                if (!selectedDateA.isAfter(selectedDateDa) && !selectedDateA.equals(selectedDateDa)) {
	                    JOptionPane.showMessageDialog(EventView.this, 
	                        "La data di fine deve essere successiva alla data di inizio.",
	                        "Errore di validazione",
	                        JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                
	                Date daOraDate = timeFormat.parse(newDaOra);
	                Date aOraDate = timeFormat.parse(newDaOra);
	                
	                // Se le date sono uguali, controlla che l'ora di fine sia successiva all'ora di inizio
	                if (selectedDateA.equals(selectedDateDa) && daOraDate.after(aOraDate)) {
	                    JOptionPane.showMessageDialog(EventView.this, 
	                        "L'ora di fine deve essere successiva all'ora di inizio.",
	                        "Errore di validazione",
	                        JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                
	                // Alla creazione del nuovo evento devo definire identifier, uguale al primo valore + dalla data.
	                // In tal modo, posso identificare quali sono gli eventi da modificare, collegati all'evento stesso.
					if(bNew)
						identifierEvent = edTitolo.getText() + " " + selectedDateDa;
	                
					try {
		                Set<Event> events;
						events = c.saveEvent(bNew, name, desc, selectedDateDa, selectedDateA, newDaOra, newAora,
						        			 edTitolo.getText(), saveDescription(), newDaOra, newAora, stato, identifierEvent);
						edTitolo.setEditable(false);
						//calendar.updateUI(selectedDateDa, selectedDateA, newDaOra, newAora, events, false);	
						calendar.updateUI(events);	
						c.updateUIevents();
		                calendar.updateLegenda(events);
						EventView.this.setVisible(false);
					} catch (EventAlreadyExistsException e1) {
						JOptionPane.showMessageDialog(null, "Evento già esistente! Impossibile crearlo.", "Errore", JOptionPane.ERROR_MESSAGE);
					} catch (EventNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Evento inesistente! Impossibile modificarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
					} catch (InvalidParameterException e1) {
						JOptionPane.showMessageDialog(null, "Parametro non valido! Controlla i dati inseriti.", "Errore", JOptionPane.ERROR_MESSAGE);
					} catch (RuntimeException e1) {
					    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ParseException ex) {
					Date daOraDate = (Date) timeFieldAora.getValue();
		            Date aOraDate = (Date) timeFieldDaOra.getValue();
		            String errorMessage = "Orario non valido: Da " + (daOraDate != null ? timeFormat.format(daOraDate) : "N/A") + 
		                                  " a " + (aOraDate != null ? timeFormat.format(aOraDate) : "N/A");
		            JOptionPane.showMessageDialog(EventView.this, errorMessage, "Errore", JOptionPane.INFORMATION_MESSAGE);
	            }
			}
		});
		btnSalva.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnSalva.setActionCommand("OK");
		btnSalva.setBounds(10, 423, 152, 56);
		contentPanel.add(btnSalva);
		
		btnCancellaAttivita = new JButton("<html><div style='text-align: center;'>Cancella singola<br>attività</div></html>");
		btnCancellaAttivita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Event event = null;
				LocalDate currentDay = calendar.selectDate();
				
				try {
					event = c.removeActivity(edTitolo.getText(), currentDay, timeFieldDaOra.getText(), timeFieldAora.getText());
				} catch (EventNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Evento inesistente! Impossibile cancellarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
				EventView.this.setVisible(false);
				if(event != null) {
					 Set<Event> eventSet = new TreeSet<>(new ComparatorEvents());
					 eventSet.add(event);
					 calendar.updateUIRemoveEvent(currentDay, eventSet);
					 controller.updateUIevents();
				}
			}
		});
		btnCancellaAttivita.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnCancellaAttivita.setActionCommand("Cancel");
		btnCancellaAttivita.setBounds(324, 423, 152, 56);
		contentPanel.add(btnCancellaAttivita);
		
		JLabel lbStato = new JLabel("Stato:");
		lbStato.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbStato.setBounds(10, 110, 95, 27);
		contentPanel.add(lbStato);
		
		cmbStato = new JComboBox<>();
		cmbStato.setBounds(104, 107, 120, 30);
		cmbStato.addItem(" ");
        cmbStato.addItem("Da avviare");
        cmbStato.addItem("In corso");
        cmbStato.addItem("Concluso");
		contentPanel.add(cmbStato);
		
		btnCancellaTuttoEvento = new JButton("Cancella evento");
		btnCancellaTuttoEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Set<Event> eventRemove = null;
				LocalDate currentDay = calendar.selectDate();
				
				try {
					// DEVO RIMUOVERE L'EVENTO TOTALE. AVVISO PRIMA DI CANCELLARE.
					int response = JOptionPane.showConfirmDialog(
							    null, 
							    "Stai cancellando tutte le attività collegate all'evento. Continuare?", 
							    "", 
							    JOptionPane.YES_NO_OPTION, 
							    JOptionPane.QUESTION_MESSAGE
					);

					if (response == JOptionPane.YES_OPTION) {
						eventRemove = c.removeEvents(edTitolo.getText(), currentDay, timeFieldDaOra.getText(), timeFieldAora.getText());
						controller.updateUIevents();
					} 
				} catch (EventNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Evento inesistente! Impossibile cancellarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
				EventView.this.setVisible(false);
				if(eventRemove != null && eventRemove.size() > 0) {
					calendar.updateUIRemoveEvent(currentDay, eventRemove);
				}
			}
		});
		btnCancellaTuttoEvento.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnCancellaTuttoEvento.setActionCommand("Cancel");
		btnCancellaTuttoEvento.setBounds(168, 423, 152, 56);
		contentPanel.add(btnCancellaTuttoEvento);
		
		// Imposto un valore di default
		stato = State.V;
		
		cmbStato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String statoString = (String) cmbStato.getSelectedItem();
                if(statoString.equals("Da avviare"))
                	stato = State.DA_AVVIARE;
                else if(statoString.equals("In corso"))
                	stato = State.IN_CORSO;
                else if(statoString.equals("Concluso"))
                	stato = State.CONCLUSO;
                else
                	stato = State.V;
            }
        });
		
		if(bNew) {
			btnCancellaAttivita.setVisible(false);
			btnCancellaTuttoEvento.setVisible(false);
			btnSalva.setBounds(168, 423, 152, 56);
		}
	}
	
	public void setEventDetail(String nameEvent, LocalDate date, String daOra, String aOra, String desc, State state, String identifier) {
		Date dateD = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		edTitolo.setText(nameEvent);
		// assegno alla variabile 'nome', il nome dell'evento aperto.
		name = nameEvent;
		identifierEvent = identifier;
		dateChooserDa.setDate(dateD);
		dateChooserA.setDate(dateD);
		 try {
		        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		        Date daOraDate = timeFormat.parse(daOra);
		        Date aOraDate = timeFormat.parse(aOra);
		        
		        timeFieldDaOra.setValue(daOraDate);
		        timeFieldAora.setValue(aOraDate);
		    } catch (ParseException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Errore nella formattazione dell'ora: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		    }
		
		 loadDescription(desc);
		 
		 String stateToString = "";
		 if(state.equals(State.IN_CORSO)) {
			 stateToString = "In corso";
		 } else if(state.equals(State.DA_AVVIARE)) {
			 stateToString = "Da avviare";
		 } else if(state.equals(State.CONCLUSO)) {
			 stateToString = "Concluso";
		 }
		 cmbStato.setSelectedItem(stateToString);		 
			
		 setCancelButton(identifier);
	}
	
	public boolean isbNew() {
		return this.bNew;
	}
	
	private String saveDescription() {
		StringBuilder sb = new StringBuilder();
        String[] lines = txtDescrizione.getText().split("\n");
        for (String line : lines) {
            if (sb.length() > 0) {
                sb.append("[|]");
            }
            sb.append(line);
        }        
        return sb.toString();
	}
	
    private void loadDescription(String descr) {
    	String loadedText = "";
        if (descr != null) {
            loadedText = descr.replace("[|]", "\n");
            txtDescrizione.setText(loadedText);
        }
    }
    
    private void setCancelButton(String identifier) {
    	Set<Event> eventsToFile = controller.getAllEventToFile();	
    	
    	Set<Event> identifierEvents = eventsToFile.stream()
												  .filter(e -> e.getIdentifier().equals(identifier))
												  .collect(Collectors.toSet());
    	
    	if(identifierEvents != null && identifierEvents.size() > 1) {
    		btnCancellaTuttoEvento.setVisible(true);
    		btnCancellaAttivita.setBounds(324, 423, 152, 56);
    		btnCancellaTuttoEvento.setBounds(168, 423, 152, 56);
    	} else if (identifierEvents.size() == 1) {
    		btnCancellaTuttoEvento.setVisible(false);
    		btnCancellaTuttoEvento.setBounds(302, 423, 152, 56);
    	}
    }
}
