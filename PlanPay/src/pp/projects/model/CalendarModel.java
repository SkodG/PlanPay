package pp.projects.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class CalendarModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private YearMonth yearMonth;	//anno mese combinati
    private List<LocalDate> days;	//rappresenta solo la data, senza informazioni sul tempo o sul fuso orario.
    private Map<LocalDate, List<EventImpl>> cellData;
    
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
	
	@Override
	public int getRowCount() {
		return days.size() / 7;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	/**
	 * @return il contenuto della cella. (il giorno)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {	      
		LocalDate date = getDateAt(rowIndex, columnIndex);
		List<EventImpl> events = getEventsAt(rowIndex, columnIndex);
        
		if (date == null) {
	        return "";
	    }
		
        // Creo una rappresentazione testuale formattata di una cella del calendario, 
        // utilizzando HTML per supportare la visualizzazione di più righe di testo all'interno della cella della JTable.
        StringBuilder cellContent = new StringBuilder();

        cellContent.append("<html>").append(date.getDayOfMonth());
        for (Event event : events) {        	
        	String eventText = event.getInfoEventToString().replace("\n", " ").replace("\r", " ");
            cellContent.append("<br>").append(eventText);
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
	
	public List<EventImpl> getEventsAt(int rowIndex, int columnIndex) {
		LocalDate date = getDateAt(rowIndex, columnIndex);
		
		if (date == null) {
	            return Collections.emptyList();
	     }
	     return cellData.getOrDefault(date, new ArrayList<>());
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
    
    public int getYear() {
    	return this.yearMonth.getYear();
    }
    
    public int getMonthValue() {
    	return this.yearMonth.getMonthValue();
    }
    
    public Month getMonth(int monthValue) {
    	return Month.values()[monthValue - 1];
    }
    
    public void setValueAddEvent(LocalDate date, EventImpl event) {
    	// verifico se k esiste, in caso negativa creo un nuovo arraylist,
    	// in caso positivo restituisce il valore (la lista degli eventi) associato a quella chiave. 
    	// poi con .add(event) aggiungo l'evento alla lista associato a k.
    	cellData.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
    	
    	// trovo l'indice per notificare tramite il metodo fireTableCellUpdated (messo a disposizione da AbstractTableModel)
    	// che i dati in una specifica cella sono stati aggiornati, aggiornando la visualizzazione della cella.
    	int[] index = findDateIndices(date);
    	if(index != null) {
    		fireTableCellUpdated(index[0], index[1]);
    	}
    }
    
    public int[] findDateIndices(LocalDate date) {
        for (int rowIndex = 0; rowIndex < getRowCount(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
                int dayIndex = rowIndex * getColumnCount() + columnIndex;
                LocalDate currentDate = days.get(dayIndex);
                if (date.equals(currentDate)) {
                    return new int[]{rowIndex, columnIndex};
                }
            }
        }
        return null; // Data non trovata
    }
    
}
