package pp.projects.model;

import java.time.LocalDate;

public interface CalendarP {

	EventImpl newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora);
	
	EventImpl modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
			 		  String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora);
	
	EventImpl removeEvent(String name, LocalDate date, String daOra);
}
