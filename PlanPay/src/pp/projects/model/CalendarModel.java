package pp.projects.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CalendarModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private YearMonth yearMonth;	//anno mese combinati
    private List<LocalDate> days;	//rappresenta solo la data, senza informazioni sul tempo o sul fuso orario.
	
	public CalendarModel(int year, int month) {
		setYearMonth(year, month);
	}
	
	private void initializeDays() {
		this.days = new ArrayList<>();
		LocalDate firstDayOfMonth = yearMonth.atDay(1);	// ottengo il primo giorno del mese
		int daysInMonth = yearMonth.lengthOfMonth();
		int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();	// Giorno della settimana del primo giorno del mese: Lunedì 1, ..., Domenica 7
		
		System.out.println(daysInMonth);
		
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
		int dayIndex = rowIndex * getColumnCount() + columnIndex;
		LocalDate date = days.get(dayIndex);
        return (date != null) ? date.getDayOfMonth() : "";
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
}
