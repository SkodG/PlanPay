package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CalendarImpl implements CalendarP{

	private Set<Event> setEvents;
	private int day;
	
	private String path = "src/resource/events.txt";
		
	public CalendarImpl(int day) {
		this.setEvents = new TreeSet<>(new ComparatorEvents());
		this.day = day;
		
		setEvents = loadEventsFromFile();
	}
	
	public Event newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, InvalidParameterException{
		// Verifico se i parametri dell'evento non sono nulli o vuoti
	    if (newName == null || newName.trim().isEmpty() || newDaOra == null || newDaOra.trim().isEmpty() || newAora == null || newAora.trim().isEmpty()) {
	        throw new InvalidParameterException("I parametri dell'evento non possono essere nulli o vuoti.");
	    }
		
	    Optional<Event> existingEvent = getExistingEvent(newName, currentDate, newDaOra, newAora);
	    
		if (existingEvent.isPresent()) {	
            throw new EventAlreadyExistsException("Evento già esistente nell'intervallo di tempo specificato! Impossibile crearlo.");
        }

        Event newEvent = new EventImpl(newName, newDesc, currentDate, newDaOra, newAora, stato, identifier);
        setEvents.add(newEvent);
	    
	    return newEvent;
	}
	
	public boolean modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
							 String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora, State stato, String identifier) throws EventNotFoundException, EventAlreadyExistsException {
		
		List<EventImpl> eventsToModify = setEvents.stream()
	            .filter(e -> e instanceof EventImpl && ((EventImpl) e).getIdentifier().equals(identifier))
	            .map(e -> (EventImpl) e)
	            .collect(Collectors.toList());

	    if (eventsToModify.isEmpty()) {
	        throw new EventNotFoundException("Evento inesistente! Impossibile modificarlo.");
	    }
	    
	    for (EventImpl eventImpl : eventsToModify) {
	        if (newName != null && !newName.trim().isEmpty()) {
	            eventImpl.setName(newName);
	        }
	        if (newDesc != null && !newDesc.trim().isEmpty()) {
	            eventImpl.setDescription(newDesc);
	        }
	        if (newDaOra != null && !newDaOra.trim().isEmpty()) {
	            eventImpl.setDaOra(newDaOra);
	        }
	        if (newAora != null && !newAora.trim().isEmpty()) {
	            eventImpl.setAOra(newAora);
	        }
	        if (stato != null) {
	            eventImpl.setState(stato);
	        }
	    }
	    return true;
	}
	
	@Override
	public Event removeEvent(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException {
		Optional<Event> existingEvent = getExistingEvent(name, date, daOra, aOra);
		
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
	
	@Override
	public Set<Event> getAllEvents(){
		return this.setEvents;
	}
	
	@Override
	public boolean saveEventsToFile() {
        File file = new File(path);
		BufferedWriter writer = null;

        // Mi assicuro che la directory esista
        file.getParentFile().mkdirs();
		try {
			// Creo il writer in modo da sovrascrivere il file esistente			
			writer = new BufferedWriter(new FileWriter(file, false));
						
			// Scrivo gli eventi nel file
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
	
	@Override
	public Set<Event> loadEventsFromFile() {
	    BufferedReader reader = null;
		File file = new File(path);

	    if (!file.exists()) {
	        return new TreeSet<Event>(new ComparatorEvents());
	    }

	    try {
	         reader = new BufferedReader(new FileReader(file));
	         String line;
	         while ((line = reader.readLine()) != null) {
	             String[] parts = line.split("\\[,\\]");
	             if (parts.length == 6) {
	                 LocalDate date = LocalDate.parse(parts[0]);
	                 String daOra = parts[1];
	                 String aOra = parts[2];
	                 String name = parts[3];
	                 String description = parts[4];
	                 String stato = parts[5];
	                    
	                 EventImpl event = new EventImpl(name, description, date, daOra, aOra, State.valueOf(stato), name+date);
	                 setEvents.add(event);
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
	    return this.setEvents;
	  }
	
	private Optional<Event> getExistingEvent(String name, LocalDate currentDate, String daOra, String aOra) {
		LocalTime newStart = LocalTime.parse(daOra);
	    LocalTime newEnd = LocalTime.parse(aOra);
		
	    Set<Event> sameDateEvent = setEvents.stream()
								    		.filter(e -> {
							                    if (e instanceof EventImpl) {
							                        EventImpl eventImpl = (EventImpl) e;
							                        
							                        return eventImpl.getDate().equals(currentDate);
							                    }
							                    return false;
								    		})
								    		.map(e -> (EventImpl) e) 
								    	    .collect(Collectors.toSet());
		return sameDateEvent.stream()
						.filter(e -> {
		                    if (e instanceof EventImpl) {
		                        EventImpl eventImpl = (EventImpl) e;
		                        LocalTime existingStart = LocalTime.parse(eventImpl.getDaOra());
	                            LocalTime existingEnd = LocalTime.parse(eventImpl.getAOra());
		                        
	                            return (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) || 
	                                    newStart.equals(existingStart) || 
	                                    newEnd.equals(existingEnd);
		                    }
		                    return false;
		                })
		                .findFirst();
	}
	
	public void deleteAll() {
		if(getAllEvents().size() > 0) {
			getAllEvents().removeAll(getAllEvents());
		}
	}
	
	public void setPath(String strPath) {
		this.path = strPath;
	}
}
