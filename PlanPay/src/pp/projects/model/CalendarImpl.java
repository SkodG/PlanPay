package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

public class CalendarImpl implements CalendarP{

	// Lista di eventi che accadono in quella specifica data
	private Map<LocalDate, List<EventImpl>> eventsMap;
	private List<EventImpl> listToDate;
	private int day;
		
	public CalendarImpl(int day) {
		// ordino le date in ordine cronologico.
		// quindi quando inserisco un elemento nella mappa, le date saranno subito ordinate dal meno recente al più recente.
		this.eventsMap = new TreeMap<>(LocalDate::compareTo);
		this.listToDate = new ArrayList<>();
		this.day = day;
	}
	
	/**
	 * Associo l'evento all'ora. (creazione di un nuovo evento)
	 * 
	 * @param e = nuovo evento da creare
	 */
	public void newEvent(EventImpl e) {
		// Normalizzo la data per togliere orario, minuti, sec...
		LocalDate dateNoOrario = e.getDate();
		// recupero la lista degli eventi associati alla data
		List<EventImpl> events = eventsMap.get(dateNoOrario);
		if(events == null) {
			events = new ArrayList<>();
		}
		events.add(e);
		// aggiungo la lista di eventi alla mappa
		eventsMap.put(dateNoOrario, events);
	}
	
	/**
	 * 
	 * @param d = data utilizzata come chiave di ricerca.
	 * @return la lista degli eventi in base alla data passata.
	 */
	public List<EventImpl> getEventsForDate(LocalDate d) {
		LocalDate date = d;
		LocalDate dateNoOrario = date;
		// recupero la lista degli eventi associati alla data
		return eventsMap.get(dateNoOrario);
	}
	
	/**
	 * elimino l'evento selezionato.
	 * 
	 * @param o = evento da eliminare.
	 */
	@Override
	public void removeEvent(EventImpl e) {
		// Normalizzo la data
		LocalDate dateNoOrario = e.getDate();
		// recupero la lista degli eventi associati alla data
		List<EventImpl> events = eventsMap.get(dateNoOrario);
		
		if (events != null) {
			// per eliminazione meglio usare iterator
			Iterator<EventImpl> it = events.iterator();
			while(it.hasNext()) {
				EventImpl ev = it.next();
				if (ev.getName().equals(e.getName())) {
					it.remove();
					break;
				}
			}
		}
		
		// se ho eliminato tutti gli eventi associati alla data, rimuovo la data dalla mappa
		if (events.isEmpty()) {
			eventsMap.remove(dateNoOrario);
		}
	}
	
	public int getDay() {
		return this.getDay();
	}
}
