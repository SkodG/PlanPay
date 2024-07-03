package pp.projects.model;

import java.util.Comparator;

public class ComparatorEvents implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		EventImpl event1 = (EventImpl) o1;
        EventImpl event2 = (EventImpl) o2;
		
		int dateComparison = event1.getDate().compareTo(event2.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }
        // se le date sono uguali gli eventi vengono confrontati per orario
        int startTimeComparison = o1.getDaOra().compareTo(o2.getDaOra());
        if (startTimeComparison != 0) {
            return startTimeComparison;
        }
        // se sia le date che gli orari di inizio sono uguali, gli eventi vengono confrontati per nome.
        return event1.getName().compareTo(event2.getName());
	}
}
