package pp.projects.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

public class CalendarModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private YearMonth yearMonth;	//anno mese combinati
    private List<LocalDate> days;	//rappresenta solo la data, senza informazioni sul tempo o sul fuso orario.
    private Map<LocalDate, Set<Event>> cellData;
    
	public CalendarModel(int year, int month) {
		setYearMonth(year, month);
		this.cellData = new HashMap<>(); // Inizializza la mappa
	}
	
	private void initializeDays() {
		this.days = new ArrayList<>();
		LocalDate firstDayOfMonth = yearMonth.atDay(1);	// ottengo il primo giorno del mese
		int daysInMonth = yearMonth.lengthOfMonth();
		int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();	// Giorno della settimana del primo giorno del mese: Lunedì 1, ..., Domenica 7
		
		// Aggiungo giorni vuoti se il mese non inizia con il lunedì
		for(int i = 1; i < firstDayOfWeek; i++) {
			days.add(null);
		}
		
		// Aggiungo tutti i giorni al mese
		for(int i = 1; i <= daysInMonth; i++) {
			// creo il giorno specifico del mese
			days.add(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), i));
		}
		
		// mi assicuro che la lista abbia lunghezza multipla di 7, aggiungo null alla fine per la rappresentazione
		while(days.size() % 7 != 0) {
			days.add(null);
		}		
	}
	
	public void setYearMonth(int year, int month) {
		this.yearMonth = YearMonth.of(year, month);
		initializeDays();
		// chiamo il metodo della classe AstractModel per notificare alla tabella che i dati sono stati modificati
		fireTableDataChanged();
	}
	
    public int getYear() {
    	return this.yearMonth.getYear();
    }
    
    public int getMonthValue() {
    	return this.yearMonth.getMonthValue();
    }
    
    public Month getMonth(int monthValue) {
    	return Month.values()[monthValue - 1];
    }
	
	public void nextMonth() {
        yearMonth = yearMonth.plusMonths(1);
        initializeDays();
        fireTableDataChanged();
    }

    public void previousMonth() {
        yearMonth = yearMonth.minusMonths(1);
        initializeDays();
        fireTableDataChanged();
    }
	
	@Override
	public int getRowCount() {
		return days.size() / 7;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	private Set<Event> getEventsAt(int rowIndex, int columnIndex) {
		LocalDate date = getDateAt(rowIndex, columnIndex);
		
		if (date == null) {
	            return Collections.emptySet();
	     }
	     return cellData.getOrDefault(date, new TreeSet<>(new ComparatorEvents()));
	}
	
	/**
	 * @return il contenuto della cella. (il giorno)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {	      
		LocalDate date = getDateAt(rowIndex, columnIndex);
		Set<Event> events = getEventsAt(rowIndex, columnIndex);
        
		if (date == null) {
	        return "";
	    }
		
        return getValueAtDate(date, events);
	}
	
	/* Ogni qualvolta viene richiamata garantisce la scrittura tramite HTML della cella, rispetti agli eventi in un determinato giorno. */
	public Object getValueAtDate(LocalDate date, Set<Event> events) {
		// Creo una rappresentazione testuale formattata di una cella del calendario, 
        // utilizzando HTML per supportare la visualizzazione di più righe di testo all'interno della cella della JTable.
        StringBuilder cellContent = new StringBuilder();

        cellContent.append("<html>").append(date.getDayOfMonth());
        for (Event event : events) {   
        	String eventText = event.getInfoEventToString().replace("\n", " ").replace("\r", " ");
        	String eventHtml = toHtml(event) + eventText;        	
            cellContent.append("<br>").append(eventHtml);
        }
        cellContent.append("</html>");
        
        return cellContent.toString();
	}
    
    public LocalDate getDateAt(int rowIndex, int columnIndex) {
        int dayIndex = rowIndex * getColumnCount() + columnIndex;
        if (dayIndex >= days.size()) {
            return null;
        }
        return days.get(dayIndex);
    }
	
	/**
	 * @return il nome della colonna in base all'indice passato;
	 * @param columnIndex, ovvero l'indice del nome della colonna.
	 */
    public String getColumName(int columnIndex) {
    	switch (columnIndex) {
    		case 0 : return "Lunedì";
    		case 1 : return "Martedì";
    		case 2 : return "Mercoledì";
    		case 3 : return "Giovedì";
    		case 4 : return "Venerdì";
    		case 5 : return "Sabato";
    		case 6 : return "Domenica";
    		default : return "";    	
    	}
    }

    private int[] findDateIndices(LocalDate date) {
    	int dayIndex = days.indexOf(date);
    	if (dayIndex == -1) {
    	   return null;
    	}
    	int row = dayIndex / getColumnCount();
    	int column = dayIndex % getColumnCount();
    	return new int[] {row, column};
    }
    
    private void setValueEvent(LocalDate date) {
    	// trovo l'indice per notificare tramite il metodo fireTableCellUpdated (messo a disposizione da AbstractTableModel)
    	// che i dati in una specifica cella sono stati aggiornati, aggiornando la visualizzazione della cella.
    	int[] index = findDateIndices(date);
    	if(index != null) {
    		fireTableCellUpdated(index[0], index[1]);
    	}
    }
    
    public void setValueAddEvent(LocalDate date, Event addEvent) {
    	// verifico se k esiste, in caso negativa creo un nuovo arraylist,
    	// in caso positivo restituisce il valore (la lista degli eventi) associato a quella chiave. 
    	// poi con .add(event) aggiungo l'evento alla lista associato a k.
    	cellData.computeIfAbsent(date, k -> new TreeSet<>(new ComparatorEvents())).add(addEvent);
    	
    	Set<Event> eventsInDay = cellData.get(date);
    	getValueAtDate(date, eventsInDay);
    	
    	setValueEvent(date);
    }
    
    public Set<Event> getEventsInDate(LocalDate date){
    	return cellData.get(date);
    }
    
    private Set<Event> eventIdentifier(String identifier, Set<Event> setEvents) {
        return setEvents.stream()
                        .filter(e -> ((EventImpl) e).getIdentifier().equals(identifier))
                        .collect(Collectors.toSet());
    }
    
    public void setValueModifyEvent(Set<Event> events, Event updateEvent) {
    	String identifier = updateEvent.getIdentifier();
    	
    	Set<Event> relatedEvents = eventIdentifier(identifier, events);
    	
    	for(Event even : relatedEvents) {
    		EventImpl ev = (EventImpl) even;
    		// devo prendere gli eventi con stesso identificatore dell'evento modificato e poi modificarli per ogni data
    		Set<Event> eventInDate = getEventsInDate(ev.getDate());
            
    		if (eventInDate != null) {
                // Trova e modifica gli eventi con lo stesso identificatore nella data specifica
    			eventInDate.stream()
				    		.forEach(existingEvent -> {
				                EventImpl existingEventImpl = (EventImpl) existingEvent;
				                // Aggiorna i campi dell'evento esistente
				                existingEventImpl.setName(((EventImpl) updateEvent).getName());
				                existingEventImpl.setDescription(((EventImpl) updateEvent).getDescription());
				                existingEventImpl.setDaOra(((EventImpl) updateEvent).getDaOra());
				                existingEventImpl.setAOra(((EventImpl) updateEvent).getAOra());
				                existingEventImpl.setState(((EventImpl) updateEvent).getState());
				                existingEventImpl.setIdentifier(((EventImpl) updateEvent).getIdentifier());
				            });
	        // Aggiorna la mappa cellData con gli eventi modificati
	        cellData.put(ev.getDate(), eventInDate);
    		}
    	}
        
    }
    
    public boolean removeEvent(LocalDate date, Event event) {
    	// Rimuovi l'evento anche dalla mappa cellData
        Set<Event> events = getEventsInDate(date);
        if (events != null) {     
        	//' devo rimuovere l'evento
        	events.remove(event);
            if (events.isEmpty()) {
                cellData.remove(date); // Rimuovi la chiave se la lista è vuota
            }
        } else {
        	return false;
        }
    	
        setValueEvent(date);        
        return true;
    }
    
    // carico gli aventi sul calendario
    public void loadEvents(Set<Event> events) {
        for (Event event : events) {
        	EventAdapter eventad = new EventAdapter(event);
            LocalDate date = eventad.getDate();
            setValueAddEvent(date, event);
        }
        fireTableDataChanged();
    }
    
	public String toHtml(Event event) {
        String color;
        switch (event.getState()) {
            case DA_AVVIARE:
                color = "red";
                break;
            case IN_CORSO:
                color = "yellow";
                break;
            case CONCLUSO:
                color = "green";
                break;
            default:
                color = "transparent"; // Default color
        }
        return "<span style='color:" + color + "; font-size:20px;'>&#8226;</span> ";
	}
	
    
}
