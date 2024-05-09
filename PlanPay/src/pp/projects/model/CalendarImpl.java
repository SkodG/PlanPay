package pp.projects.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarImpl implements Calendar{

	List<Event> eventList;
	
	public CalendarImpl() {
		this.eventList = new ArrayList<>();
	}
	
	/**
	 * Aggiunge un evento alla lista degli eventi
	 * 
	 * @param e = evento
	 */
	@Override
	public void newEvent(EventImpl e) {
		this.eventList.add(e);
	}
	
	/**
	 * elimina l'evento che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	@Override
	public void removeEvent(EventImpl e) {
		this.eventList.remove(e);
	}

}
