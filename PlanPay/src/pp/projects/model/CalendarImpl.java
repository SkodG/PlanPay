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
	
	public EventImpl newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora) throws EventAlreadyExistsException, InvalidParameterException{
		Optional<EventImpl> existingEvent = getEvent(name, currentDate, daOra);
		if (existingEvent.isPresent()) {
			 throw new EventAlreadyExistsException("Evento già esistente! Impossibile crearlo!");
        }

        if (newName == null || newName.trim().isEmpty() || newDesc == null || newDesc.trim().isEmpty() ||
            newDaOra == null || newDaOra.trim().isEmpty() || newAora == null || newAora.trim().isEmpty()) {
            throw new InvalidParameterException("I parametri dell'evento non possono essere nulli o vuoti.");
        }
	    
	    EventImpl newEvent = new EventImpl(newName, newDesc, currentDate, newDaOra, newAora);
	    listEvents.add(newEvent);
	    
	    return newEvent;
	}
	
	public Optional<EventImpl> getEvent(String name, LocalDate currentDate, String daOra) {
		return listEvents.stream()
						 .filter(e ->
							e.getName().equals(name) &&
							e.getDate().equals(currentDate) &&
							e.getDaOra().equals(daOra))
						 .findFirst();
	}
	
	public EventImpl modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
							 String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora) throws EventNotFoundException {
		EventImpl event = null;
		
		event = listEvents.stream()
				   .filter(e -> e.getName().equals(name))
				   .findFirst()
				   .orElseThrow(() -> new EventNotFoundException("Evento inesistente! Impossibile modificarlo."));
		
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
	
	@Override
	public EventImpl removeEvent(String name, LocalDate date, String daOra) throws EventNotFoundException {
		Optional<EventImpl> existingEvent = getEvent(name, date, daOra);
		
		System.out.println(name + "-" + date + "-" + daOra);
		
		if (!existingEvent.isPresent()) {
	        throw new EventNotFoundException("Evento inesistente! Impossibile cancellarlo.");
	    }
	   
		listEvents.remove(existingEvent.get());
		
		return existingEvent.get();
	}
	
	public int getDay() {
		return this.day;
	}
}
