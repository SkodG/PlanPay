package pp.projects.model;

import java.util.Comparator;

public class ComparatorEvents implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		EventAdapter adapter1 = new EventAdapter(o1);
        EventAdapter adapter2 = new EventAdapter(o2);
		
		int dateComparison = adapter1.getDate().compareTo(adapter2.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }
        // se le date sono uguali gli eventi vengono confrontati per orario
        int startTimeComparison = o1.getDaOra().compareTo(o2.getDaOra());
        if (startTimeComparison != 0) {
            return startTimeComparison;
        }
        // se sia le date che gli orari di inizio sono uguali, gli eventi vengono confrontati per nome.
        return adapter1.getName().compareTo(adapter2.getName());
	}
}
