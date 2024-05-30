package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CalendarImpl implements CalendarP{

	private List<EventImpl> listEvents;
	private int day;
		
	public CalendarImpl(int day) {
		this.listEvents = new ArrayList<>();
		this.day = day;
	}
	
	/**
	 * Associo l'evento all'ora. (creazione di un nuovo evento)
	 * 
	 * @param e = nuovo evento da creare
	 */
	public EventImpl newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora) {
		Optional<EventImpl> existingEvent = getEvent(name, currentDate, daOra);
		if (existingEvent.isPresent()) {
	        throw new IllegalStateException("Evento già esistente! Impossibile crearlo!");
	    }
	    
	    EventImpl newEvent = new EventImpl(newName, newDesc, currentDate, newDaOra, newAora);
	    listEvents.add(newEvent);
	    
	    return newEvent;
	}
	
	/**
	 * 
	 * @param name
	 * @param currentDate
	 * @param daOra
	 * @return
	 */
	public Optional<EventImpl> getEvent(String name, LocalDate currentDate, String daOra) {
		return listEvents.stream()
						 .filter(e ->
							e.getName().equals(name) &&
							e.getDate().equals(currentDate) &&
							e.getDaOra().equals(daOra))
						 .findFirst();
	}
	
	/**
	 * 
	 * @param name
	 * @param desc
	 * @param daData
	 * @param aData
	 * @param daOra
	 * @param aOra
	 * @param newName
	 * @param newDesc
	 * @param currentDate
	 * @param newDaOra
	 * @param newAora
	 * @return
	 */
	public EventImpl modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
							 String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora) {
		EventImpl event = null;
		
		event = listEvents.stream()
				   .filter(e -> 
					   e.getName().equals(name) &&
					   e.getDaOra().equals(daOra) &&
					   e.getAOra().equals(daOra))
				   .findFirst()
				   .orElse(null);
		
		if (event != null) {
			 if (newName != null && !newName.trim().isEmpty()) {
			     event.setName(newName);
			 }
			 if (newDesc != null && !newDesc.trim().isEmpty()) {
			     event.setDescription(newDesc);
			 }
			 if (newDaOra != null && !newDaOra.trim().isEmpty()) {
			     event.setDaOra(newDaOra);
			 }
			 if (newAora != null && !newAora.trim().isEmpty()) {
			     event.setAOra(newAora);
			 }
		}
		
		return event;
	}
	
	/**
	 * elimino l'evento selezionato.
	 * 
	 * @param o = evento da eliminare.
	 */
	@Override
	public boolean removeEvent(String name, LocalDate date, String daOra) {
		Optional<EventImpl> existingEvent = getEvent(name, date, daOra);
		
		System.out.println(name + "-" + date + "-" + daOra);
		
		if (!existingEvent.isPresent()) {
	        throw new IllegalStateException("Evento inesistente! Impossibile cancellarlo.");
	    }
	   
		listEvents.remove(existingEvent.get());
		
		return true;
	}
	
	public int getDay() {
		return this.getDay();
	}
}
