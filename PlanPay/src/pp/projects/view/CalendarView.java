package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.CalendarModel;
import pp.projects.model.DayCellRenderer;
import pp.projects.model.Event;
import pp.projects.model.EventAdapter;
import pp.projects.model.Data;
import pp.projects.model.EventImpl;

import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    
    private LocalDate today = LocalDate.now();
    private int actualMonth;
    
    private EventView eventView;
    private SelectedEventView selectedEventView;
    private boolean isSelectingEvent = false;


	/**
	 * Create the frame.
	 */
	public CalendarView(ConsoleController controller, CalendarModel model) {
		setTitle("CALENDARIO");
		this.calendarModel = model;
		this.actualMonth = today.getMonthValue();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 763, 641);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIndietro = new JButton("<<");
		btnIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// carica nuovo mese + carica nuovi eventi
				actualMonth -= 1;
				calendarModel.setYearMonth(today.getYear(), actualMonth);		
				updateMonthLable();
			}
		});
		btnIndietro.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnIndietro.setBounds(10, 10, 67, 29);
		contentPane.add(btnIndietro);
		
		JButton btnAvanti = new JButton(">>");
		btnAvanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// carica nuovo mese + carica nuovi eventi
				actualMonth += 1;
				//calendar = new CalendarModel(today.getYear(), actualMonth);
				calendarModel.setYearMonth(today.getYear(), actualMonth);				
				
				updateMonthLable();
			}
		});
		btnAvanti.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnAvanti.setBounds(672, 10, 67, 29);
		contentPane.add(btnAvanti);
		
		lbMese = new JLabel(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase());
		lbMese.setHorizontalAlignment(SwingConstants.CENTER);
		lbMese.setBackground(new Color(255, 255, 255));
		lbMese.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbMese.setBounds(10, 61, 729, 29);
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
		btnNewevent.setBounds(297, 545, 157, 49);
		contentPane.add(btnNewevent);
		
		tblCalendario = new JTable(calendarModel);
		tblCalendario.setBounds(10, 150, 734, 375);		
		// imposto la dimensione delle colonne e delle righe
		tblCalendario.setRowHeight(75);
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
	                    		eventView.setEventDetail(eventAdapter.getName(), dateLocal, ((EventImpl) event).getDaOra(), ((EventImpl) event).getAOra(), eventAdapter.getDescription());
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
		lbLunedì.setBounds(10, 111, 105, 29);
		contentPane.add(lbLunedì);
		
		JLabel lbMartedi = new JLabel("Martedì");
		lbMartedi.setBackground(new Color(137, 168, 203));
		lbMartedi.setHorizontalAlignment(SwingConstants.CENTER);
		lbMartedi.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbMartedi.setBounds(113, 111, 105, 29);
		contentPane.add(lbMartedi);
		
		JLabel lbMercole = new JLabel("Mercoledì");
		lbMercole.setBackground(new Color(137, 168, 203));
		lbMercole.setHorizontalAlignment(SwingConstants.CENTER);
		lbMercole.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbMercole.setBounds(217, 111, 105, 29);
		contentPane.add(lbMercole);
		
		JLabel lbGiove = new JLabel("Giovedì");
		lbGiove.setBackground(new Color(137, 168, 203));
		lbGiove.setHorizontalAlignment(SwingConstants.CENTER);
		lbGiove.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbGiove.setBounds(322, 111, 105, 29);
		contentPane.add(lbGiove);
		
		JLabel lbVenerdi = new JLabel("Venerdì");
		lbVenerdi.setBackground(new Color(137, 168, 203));
		lbVenerdi.setHorizontalAlignment(SwingConstants.CENTER);
		lbVenerdi.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbVenerdi.setBounds(426, 111, 105, 29);
		contentPane.add(lbVenerdi);
		
		JLabel lbSabato = new JLabel("Sabato");
		lbSabato.setBackground(new Color(137, 168, 203));
		lbSabato.setHorizontalAlignment(SwingConstants.CENTER);
		lbSabato.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbSabato.setBounds(531, 111, 105, 29);
		contentPane.add(lbSabato);
		
		JLabel lbDomenica = new JLabel("Domenica");
		lbDomenica.setBackground(new Color(137, 168, 203));
		lbDomenica.setHorizontalAlignment(SwingConstants.CENTER);
		lbDomenica.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbDomenica.setBounds(634, 111, 105, 29);
		contentPane.add(lbDomenica);
		
		// WindowListener per gestire l'evento "windowOpened"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // creazione di una nuova istanza di Calendario
            	controller.drawCalendar();
            }
        });
	}
	
	private void updateMonthLable() {
		lbMese.setText(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase());
    }
	
	public LocalDate selectDate() {
		int indexRow = tblCalendario.getSelectedRow();
		int indexColumn = tblCalendario.getSelectedColumn();
		
		return calendarModel.getDateAt(indexRow, indexColumn);
	}
	
	public void updateUI(LocalDate daData, LocalDate aData, String daOra, String aOra, Set<Event> events, boolean bDelete) {
		if(!bDelete) {
			for(Event ev : events) {
				Data data = new EventAdapter(ev);
				if(eventView.isbNew()) {
					if(!(daData.equals(aData))) {
						calendarModel.setValueAddEvent(data.getDate(), ev);
					} else {
						calendarModel.setValueAddEvent(daData, ev);
					}
				}else {
					calendarModel.setValueModifyEvent(daData, ev);
				}
			}			
		} else {
			eventView.setVisible(false);
			calendarModel.setValueAddEvent(aData, null);
		}

	    contentPane.revalidate();
	    contentPane.repaint();		
	}
	
	public void updateUIRemoveEvent(LocalDate date, Event event) {
		calendarModel.removeEvent(date, event);
		
	    contentPane.revalidate();
	    contentPane.repaint();	
	}
	
	public boolean getSelectingEvent() {
		return isSelectingEvent;
	}
}
