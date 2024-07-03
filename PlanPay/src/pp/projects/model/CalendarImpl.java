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
	private String pathEvents;
	private Login login;
			
	public CalendarImpl(int day, String user) {
		this.setEvents = new TreeSet<>(new ComparatorEvents());
		this.day = day;
		this.login = new LoginImpl();
		this.pathEvents = login.getEventsFilePath(user);
		
		if(pathEvents != null)		
			setEvents = loadEventsFromFile();
	}
	
	public Event newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, InvalidParameterException {
        validateEventParameters(newName, newDaOra, newAora);

        Optional<Event> existingEvent = getExistingEvent(newName, currentDate, newDaOra, newAora);
        
        if (existingEvent.isPresent()) {
        	System.out.println("evento esistente : " + existingEvent.get().getIdentifier() + " - " + existingEvent.get().getDaOra() + " " + existingEvent.get().getAOra());
            throw new EventAlreadyExistsException("Evento già esistente nell'intervallo di tempo specificato! Impossibile crearlo.");
        }

        Event newEvent = new EventImpl(newName, newDesc, currentDate, newDaOra, newAora, stato, identifier);
        setEvents.add(newEvent);

        return newEvent;
    }

    public boolean modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
                               String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora, State stato, String identifier) throws EventNotFoundException, EventAlreadyExistsException {
        List<EventImpl> eventsToModify = getEventsByIdentifier(identifier);

        if (eventsToModify.isEmpty()) {
            throw new EventNotFoundException("Evento inesistente! Impossibile modificarlo.");
        }

        for (EventImpl eventImpl : eventsToModify) {
            if (isValidString(newName)) eventImpl.setName(newName);
            if (isValidString(newDaOra)) eventImpl.setDaOra(newDaOra);
            if (isValidString(newAora)) eventImpl.setAOra(newAora);
            if (stato != null) eventImpl.setState(stato);
        }

        updateEventDescription(eventsToModify, currentDate, newDesc);
        saveEventsToFile();
        return true;
    }

    @Override
    public Event removeActivity(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException {
        Event eventToRemove = getExistingEvent(name, date, daOra, aOra)
                .orElseThrow(() -> new EventNotFoundException("Evento inesistente! Impossibile cancellarlo."));

        setEvents.remove(eventToRemove);
        saveEventsToFile();
        return eventToRemove;
    }
    
	@Override
	public Set<Event> removeEvents(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException {
		Event eventToRemove = getExistingEvent(name, date, daOra, aOra)
                .orElseThrow(() -> new EventNotFoundException("Evento inesistente! Impossibile cancellarlo."));

		String identifierRemove = eventToRemove.getIdentifier();
		
	    Set<Event> eventsToRemove = setEvents.stream()
								             .filter(e -> e.getIdentifier().equals(identifierRemove))
								             .collect(Collectors.toSet());

	    // Rimuovi gli eventi dal set originale
	    setEvents.removeAll(eventsToRemove);
	    saveEventsToFile();
	    return eventsToRemove;
	}

    public int getDay() {
        return this.day;
    }

    @Override
    public Set<Event> getAllEvents() {
        return this.setEvents;
    }

    @Override
    public boolean saveEventsToFile() {
        if (pathEvents == null) return false;

        File file = new File(pathEvents);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            file.getParentFile().mkdirs();
            for (Event ev : setEvents) {
                writer.write(ev.getInfoEventToFile());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Problemi al salvataggio del file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Set<Event> loadEventsFromFile() {
        if (pathEvents == null) return new TreeSet<>(new ComparatorEvents());

        File file = new File(pathEvents);
        if (!file.exists()) return new TreeSet<>(new ComparatorEvents());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\[,\\]");
                if (parts.length == 7) {
                    EventImpl event = new EventImpl(parts[3], parts[4], LocalDate.parse(parts[0]), parts[1], parts[2], State.valueOf(parts[5]), parts[6]);
                    setEvents.add(event);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nel caricamento degli eventi dal file: " + e.getMessage());
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
        setEvents.clear();
    }

    @Override
    public void setPathEvents(String path) {
        this.pathEvents = path;
    }

    private void validateEventParameters(String newName, String newDaOra, String newAora) throws InvalidParameterException {
        if (!isValidString(newName) || !isValidString(newDaOra) || !isValidString(newAora)) {
            throw new InvalidParameterException("I parametri dell'evento non possono essere nulli o vuoti.");
        }
    }

    private boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private List<EventImpl> getEventsByIdentifier(String identifier) {
        return setEvents.stream()
                .filter(e -> ((EventImpl) e).getIdentifier().equals(identifier))
                .map(e -> (EventImpl) e)
                .collect(Collectors.toList());
    }

    private void updateEventDescription(List<EventImpl> events, LocalDate currentDate, String newDesc) {
        events.stream()
                .filter(e -> e.getDate().equals(currentDate))
                .findFirst()
                .ifPresent(event -> {
                    if (isValidString(newDesc)) {
                        event.setDescription(newDesc);
                    }
                });
    }
}
