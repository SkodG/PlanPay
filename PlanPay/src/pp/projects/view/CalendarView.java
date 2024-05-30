package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.DayCellRenderer;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;

import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;

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
    private CalendarModel calendar;
    private JTable tblCalendario;
    private JLabel lbMese;
    
    private LocalDate today = LocalDate.now();
    private int actualMonth;
    
    private EventView eventView;

	/**
	 * Create the frame.
	 */
	public CalendarView(ConsoleControllerImpl controller) {
		this.calendar = new CalendarModel(today.getYear(), today.getMonthValue());
		this.actualMonth = today.getMonthValue();
		
		setTitle("CALENDARIO");
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
				calendar.setYearMonth(today.getYear(), actualMonth);		
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
				calendar.setYearMonth(today.getYear(), actualMonth);				
				
				updateMonthLable();
			}
		});
		btnAvanti.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnAvanti.setBounds(672, 10, 67, 29);
		contentPane.add(btnAvanti);
		
		lbMese = new JLabel(calendar.getMonth(calendar.getMonthValue()).name().toUpperCase());
		lbMese.setHorizontalAlignment(SwingConstants.CENTER);
		lbMese.setBackground(new Color(255, 255, 255));
		lbMese.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbMese.setBounds(10, 61, 729, 29);
		contentPane.add(lbMese);
		
		JButton btnNewevent = new JButton("Aggiungi evento");
		btnNewevent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creo un nuovo evento
				eventView = new EventView(true, selectDate(), controller, CalendarView.this);
				eventView.setVisible(true);
			}
		});
		btnNewevent.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewevent.setBounds(297, 545, 157, 49);
		contentPane.add(btnNewevent);
		
		tblCalendario = new JTable(calendar);
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
                    
                    LocalDate dateLocal = calendar.getDateAt(row, column);
                    System.out.println(dateLocal);
                    
                    List<EventImpl> events = calendar.getEventsAt(row, column);
                    System.err.println("entra quo!");
                    System.err.println(events.size());
                    // se l'evento esiste, passo i dati per visualizzarli sulla schermata
                    // altrimenti apro una nuova schermata vuota.
                    if(events.size() > 0) {
                    	// mostro tutti gli eventi che ci sono
                    	for(EventImpl event : events) {
                    		eventView = new EventView(false, dateLocal, controller, CalendarView.this);
                    		eventView.setEventDetail(event.getName(), event.getDate(), event.getDaOra(), event.getAOra(), event.getDescription());
                    	}
                    } else {
                    	eventView = new EventView(true, dateLocal, controller, CalendarView.this);
                    }   
                    eventView.setVisible(true);
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
		 lbMese.setText(calendar.getMonth(calendar.getMonthValue()).name().toUpperCase());
    }
	
	public LocalDate selectDate() {
		int indexRow = tblCalendario.getSelectedRow();
		int indexColumn = tblCalendario.getSelectedColumn();
		
		return calendar.getDateAt(indexRow, indexColumn);
	}
	
	public void updateUI(LocalDate daData, LocalDate aData, String daOra, String aOra, EventImpl event) {
		int daysEvent = 0;
		LocalDate currentDay = daData;
		
		if(!aData.equals(daData)) {
			daysEvent = aData.getDayOfMonth() - daData.getDayOfMonth();
			for(int i = 0; i <= daysEvent; i++) {
				currentDay = daData.plusDays(i);
				calendar.setValueAddEvent(currentDay, event);
			}
		}		
	}
}
