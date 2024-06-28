package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.CalendarModel;
import pp.projects.model.DayCellRenderer;
import pp.projects.model.Event;
import pp.projects.model.EventAdapter;
import pp.projects.model.EventImpl;
import pp.projects.model.Data;
import pp.projects.model.State;

import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class CalendarView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;    
    private CalendarModel calendarModel;
    private JTable tblCalendario;
    private JLabel lbMese;
    private JLabel lbNeventi_concluso;
    private JLabel lbNeventi_incorso;
    private JLabel lbNeventi_daAvviare;
    
    private EventView eventView;
    private SelectedEventView selectedEventView;
    private boolean isSelectingEvent = false;
	private int eventiDaAvviare = 0;
	private int eventiIncorso = 0;
	private int eventiConclusi = 0;
	private ConsoleController c;
	
    
	/**
	 * Create the frame.
	 */
	public CalendarView(ConsoleController controller, CalendarModel model) {
		this.c = controller;
		
		setTitle("CALENDARIO");
		this.calendarModel = model;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1201, 790);
		setResizable(false); // Impedisce l'ingrandimento della finestra
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIndietro = new JButton("<<");
		btnIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarModel.previousMonth();
				updateMonthLable();
			}
		});
		btnIndietro.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnIndietro.setBounds(10, 10, 82, 41);
		contentPane.add(btnIndietro);
		
		JButton btnAvanti = new JButton(">>");
		btnAvanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarModel.nextMonth();
				updateMonthLable();
			}
		});
		btnAvanti.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnAvanti.setBounds(1086, 10, 82, 41);
		contentPane.add(btnAvanti);
		
		lbMese = new JLabel(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase() + "  " + calendarModel.getYear());
		lbMese.setHorizontalAlignment(SwingConstants.CENTER);
		lbMese.setBackground(new Color(255, 255, 255));
		lbMese.setFont(new Font("Calibri", Font.PLAIN, 30));
		lbMese.setBounds(10, 49, 1158, 41);
		contentPane.add(lbMese);
		
		JButton btnNewevent = new JButton("Aggiungi evento");
		btnNewevent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creo un nuovo evento
				eventView = new EventView(true, LocalDate.now(), controller, CalendarView.this);
				eventView.setVisible(true);
			}
		});
		btnNewevent.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewevent.setBounds(560, 675, 157, 49);
		contentPane.add(btnNewevent);
		
		tblCalendario = new JTable(calendarModel);
		tblCalendario.setBounds(10, 150, 1167, 494);		
		// imposto la dimensione delle colonne e delle righe
		tblCalendario.setRowHeight(82);
		tblCalendario.setDefaultRenderer(Object.class, new DayCellRenderer()); // Imposta il renderer personalizzato
		contentPane.add(tblCalendario);
		
		// EVENTI CALENDARIO, gestione del doppio click.
		tblCalendario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
                    int row = tblCalendario.rowAtPoint(e.getPoint());
                    int column = tblCalendario.columnAtPoint(e.getPoint());                    
                    
                    LocalDate dateLocal = calendarModel.getDateAt(row, column);
                    
                    Set<Event> events = calendarModel.getEventsInDate(dateLocal);
                    
                    if(events != null) {
                    	// se ci sono più di un evento li visualizzo sulla finestra selectedEventView
	                    if(events.size() > 1) {
	                    	isSelectingEvent = false;
	                    	selectedEventView = new SelectedEventView(controller, CalendarView.this, dateLocal, events);
	                    	selectedEventView.setVisible(true);
	                    } else if(events.size() == 1) {
	                    	// se c'è un evento lo visualizzo su eventView
	                    	for(Event event : events) {
	                    		EventAdapter eventAdapter = new EventAdapter(event);
	                    		isSelectingEvent = true; 
	                    		eventView = new EventView(false, dateLocal, controller, CalendarView.this);
	                    		// passo i valori che mi serviranno per la modifica/cancellazione dell'evento.
	                    		eventView.setEventDetail(eventAdapter.getName(), dateLocal, event.getDaOra(), event.getAOra(), eventAdapter.getDescription(), event.getState(), event.getIdentifier());
	                    		eventView.setVisible(true);
	                    		isSelectingEvent = false;
	                    	}
	                    }   
                    } else {
                    	eventView = new EventView(true, dateLocal, controller, CalendarView.this);
                    	eventView.setVisible(true);
                    }
				}
			}
		});
		
		JLabel lbLunedì = new JLabel("Lunedì");
		lbLunedì.setBackground(new Color(137, 168, 203));
		lbLunedì.setHorizontalAlignment(SwingConstants.CENTER);
		lbLunedì.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbLunedì.setBounds(40, 111, 105, 29);
		contentPane.add(lbLunedì);
		
		JLabel lbMartedi = new JLabel("Martedì");
		lbMartedi.setBackground(new Color(137, 168, 203));
		lbMartedi.setHorizontalAlignment(SwingConstants.CENTER);
		lbMartedi.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbMartedi.setBounds(205, 111, 105, 29);
		contentPane.add(lbMartedi);
		
		JLabel lbMercole = new JLabel("Mercoledì");
		lbMercole.setBackground(new Color(137, 168, 203));
		lbMercole.setHorizontalAlignment(SwingConstants.CENTER);
		lbMercole.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbMercole.setBounds(377, 111, 105, 29);
		contentPane.add(lbMercole);
		
		JLabel lbGiove = new JLabel("Giovedì");
		lbGiove.setBackground(new Color(137, 168, 203));
		lbGiove.setHorizontalAlignment(SwingConstants.CENTER);
		lbGiove.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbGiove.setBounds(542, 111, 105, 29);
		contentPane.add(lbGiove);
		
		JLabel lbVenerdi = new JLabel("Venerdì");
		lbVenerdi.setBackground(new Color(137, 168, 203));
		lbVenerdi.setHorizontalAlignment(SwingConstants.CENTER);
		lbVenerdi.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbVenerdi.setBounds(703, 111, 105, 29);
		contentPane.add(lbVenerdi);
		
		JLabel lbSabato = new JLabel("Sabato");
		lbSabato.setBackground(new Color(137, 168, 203));
		lbSabato.setHorizontalAlignment(SwingConstants.CENTER);
		lbSabato.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbSabato.setBounds(866, 111, 105, 29);
		contentPane.add(lbSabato);
		
		JLabel lbDomenica = new JLabel("Domenica");
		lbDomenica.setBackground(new Color(137, 168, 203));
		lbDomenica.setHorizontalAlignment(SwingConstants.CENTER);
		lbDomenica.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbDomenica.setBounds(1037, 111, 105, 29);
		contentPane.add(lbDomenica);
			
		JPanel pnLegenda = new JPanel();
		pnLegenda.setBounds(959, 652, 218, 99);
		contentPane.add(pnLegenda);
		pnLegenda.setLayout(null);
		
		JPanel pnState_inCorso = new JPanel();
		pnState_inCorso.setBackground(new Color(255, 255, 51));
		pnState_inCorso.setBounds(10, 38, 25, 25);
		pnLegenda.add(pnState_inCorso);
		
		JPanel pnState_concluso = new JPanel();
		pnState_concluso.setBackground(new Color(0, 204, 0));
		pnState_concluso.setBounds(10, 66, 25, 25);
		pnLegenda.add(pnState_concluso);
		
		JPanel pnState_daAvviare = new JPanel();
		pnState_daAvviare.setBackground(new Color(255, 51, 0));
		pnState_daAvviare.setBounds(10, 10, 25, 25);
		pnLegenda.add(pnState_daAvviare);
		
		lbNeventi_concluso = new JLabel("0 attività concluse");
		lbNeventi_concluso.setBounds(45, 10, 168, 25);
		pnLegenda.add(lbNeventi_concluso);
		
		lbNeventi_incorso = new JLabel("0 attività in corso");
		lbNeventi_incorso.setBounds(45, 38, 168, 25);
		pnLegenda.add(lbNeventi_incorso);
		
		lbNeventi_daAvviare = new JLabel("0 attività da avviare");
		lbNeventi_daAvviare.setBounds(45, 66, 168, 25);
		pnLegenda.add(lbNeventi_daAvviare);
		
        updateLegenda(controller.loadEvents());
		
		// WindowListener per gestire l'evento "windowOpened"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // creazione di una nuova istanza di Calendario
				controller.drawCalendar();
                calendarModel.loadEvents(controller.loadEvents());
            }
        });
	}
	
	private void updateMonthLable() {
		lbMese.setText(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase() + "  " + calendarModel.getYear());
    }
	
	public LocalDate selectDate() {
		int indexRow = tblCalendario.getSelectedRow();
		int indexColumn = tblCalendario.getSelectedColumn();
		
		return calendarModel.getDateAt(indexRow, indexColumn);
	}
	
	public void updateUI(LocalDate daData, LocalDate aData, String daOra, String aOra, Set<Event> events, boolean bDelete) {
		if(!bDelete) {
			Set<Event> eventsCopy = new HashSet<>(events); 
	        for (Event ev : eventsCopy) {
	            Data data = new EventAdapter(ev);
	            EventImpl eventImpl = (EventImpl) ev;
	            System.out.println("DESCRIZIONE DOPO MODIFICA: " + eventImpl.getDescription());
	            if (eventView.isbNew()) {
	                if (!(daData.equals(aData))) {
	                    calendarModel.setValueEvent(data.getDate(), ev);
	                } else {
	                    calendarModel.setValueEvent(daData, ev);
	                }
	            } else {
	                calendarModel.setValueModifyEvent(c.loadEvents(), ev);
	            }
	        }
	    } else {
	        eventView.setVisible(false);
	        calendarModel.setValueEvent(aData, null);
	    }

	    contentPane.revalidate();
	    contentPane.repaint();		
	}
	
	public void updateUIRemoveEvent(LocalDate date, Event event) {
		calendarModel.removeEvent(date, event);
		
	    contentPane.revalidate();
	    contentPane.repaint();	
	}
	
	public void updateLegenda(Set<Event> events) {
		Set<String> uniqueEvents = new HashSet<>();
		
	       for (Event event : events) {
	    	   EventAdapter ev = new EventAdapter(event);
	            String key = ev.getName() + event.getDaOra() + event.getAOra() + event.getState();

	            if (!uniqueEvents.contains(key)) {
	                uniqueEvents.add(key);

	                if (event.getState().equals(State.DA_AVVIARE)) {
	                    eventiDaAvviare++;
	                } else if (event.getState().equals(State.IN_CORSO)) {
	                    eventiIncorso++;
	                } else if (event.getState().equals(State.CONCLUSO)) {
	                    eventiConclusi++;
	                }
	            }
	        }

		lbNeventi_concluso.setText(eventiDaAvviare + " attività da avviare");
		lbNeventi_incorso.setText(eventiIncorso + " attività in corso");
		lbNeventi_daAvviare.setText(eventiConclusi + " attività conclus" + (eventiConclusi == 1 ? "a" : "e"));
	}
	
	public boolean getSelectingEvent() {
		return isSelectingEvent;
	}
}