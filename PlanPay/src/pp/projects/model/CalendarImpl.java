package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class CalendarImpl implements CalendarP{

	private Set<Event> setEvents;
	private int day;
	
	// event
	private String path = "src/resource/events.txt";
		
	public CalendarImpl(int day) {
		this.setEvents = new TreeSet<>(new ComparatorEvents());
		this.day = day;
		
		setEvents = loadEventsFromFile();
	}
	
	public Event newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora) throws EventAlreadyExistsException, InvalidParameterException{
		Optional<Event> existingEvent = getEvent(name, currentDate, daOra);
		if (existingEvent.isPresent()) {
			 throw new EventAlreadyExistsException("Evento già esistente! Impossibile crearlo!");
        }

        if (newName == null || newName.trim().isEmpty() || newDaOra == null || newDaOra.trim().isEmpty() || 
        	newAora == null || newAora.trim().isEmpty()) {
            throw new InvalidParameterException("I parametri dell'evento non possono essere nulli o vuoti.");
        }
	    
        Event newEvent = new EventImpl(newName, newDesc, currentDate, newDaOra, newAora);
        setEvents.add(newEvent);
	    
	    return newEvent;
	}
	
	public Optional<Event> getEvent(String name, LocalDate currentDate, String daOra) {
		return setEvents.stream()
						.filter(e -> {
		                    if (e instanceof EventImpl) {
		                        EventImpl eventImpl = (EventImpl) e;
		                        return eventImpl.getName().equals(name) &&
		                                eventImpl.getDate().equals(currentDate) &&
		                                eventImpl.getDaOra().equals(daOra);
		                    }
		                    return false;
		                })
		                .findFirst();
	}
	
	public Event modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
							 String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora) throws EventNotFoundException {
		EventImpl event = null;
		
		event = setEvents.stream()
		         		 .map(e -> (EventImpl) e) // Cast a EventImpl
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
	public Event removeEvent(String name, LocalDate date, String daOra) throws EventNotFoundException {
		Optional<Event> existingEvent = getEvent(name, date, daOra);
		
		if (!existingEvent.isPresent()) {
	        throw new EventNotFoundException("Evento inesistente! Impossibile cancellarlo.");
	    }
	   
		setEvents.remove(existingEvent.get());		
		
	    if (!saveEventsToFile()) {
	        return null; // Restituisce un insieme vuoto se il salvataggio fallisce
	    }	    
		
		return existingEvent.get();
	}
	
	public int getDay() {
		return this.day;
	}
	
	public Set<Event> getAllEvents(){
		return this.setEvents;
	}
	
	public boolean saveEventsToFile() {
        File file = new File(path);
		BufferedWriter writer = null;
        System.out.println("SIZE EVENTS: " + setEvents);

        // Mi assicuro che la directory esista
        file.getParentFile().mkdirs();
		try {
			writer = new BufferedWriter(new FileWriter(file, false));
			 for (Event ev : setEvents) {
	             writer.write(ev.getInfoEventToFile());
	             writer.newLine();
	          }
		} catch (IOException e) {
            System.out.print("Problemi al salvataggio del file: " + e);
            return false;
        } finally {
        	// Chiusura del writer, se è stato aperto con successo
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Errore durante la chiusura del writer: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }	
		return true;
	}
	
	public Set<Event> loadEventsFromFile() {
	    BufferedReader reader = null;
		File file = new File(path);
		Set<Event> eventToLoad = new TreeSet<Event>(new ComparatorEvents());

	    if (!file.exists()) {
	        return Collections.emptySet();
	    }

	    try {
	         reader = new BufferedReader(new FileReader(file));
	         String line;
	         while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length == 5) {
	                    LocalDate date = LocalDate.parse(parts[0]);
	                    String daOra = parts[1];
	                    String aOra = parts[2];
	                    String name = parts[3];
	                    String description = parts[4];
	                    
	                    EventImpl event = new EventImpl(name, description, date, daOra, aOra);
	                    eventToLoad.add(event);
	                }
	         }
	     } catch (IOException e) {
	         System.out.println("Errore nel caricamento degli eventi dal file: " + e.getMessage());
	     } finally {
	         if (reader != null) {
	             try {
	                  reader.close();
	              } catch (IOException e) {
	                  System.out.println("Errore durante la chiusura del reader: " + e.getMessage());
	              }
	          }
	     }
	    return eventToLoad;
	  }
}
