package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.CalendarP;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventImpl;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.State;

class CalendarTest {

	private CalendarP calendario;
	private CalendarModel calendarModel;
	private Event meeting1;
	private Event meeting2;
	private Event meeting3;
	private Event presentaz1;
	private Event presentaz2;
	private Event viaggio1;
	private Event viaggio2;
	private Event viaggio3;
	
	@BeforeEach
	 public void setUp() throws EventAlreadyExistsException, InvalidParameterException {
		// rimuovo tutti gli eventi che ho precdentemente salvato
		calendario = new CalendarImpl(0, "");
		calendarModel = new CalendarModel(2024, 7);
		CalendarImpl cale = (CalendarImpl) calendario;
		cale.deleteAll();
		
		meeting1 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    meeting2 = calendario.newEvent("", LocalDate.now().plusDays(2), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    meeting3 = calendario.newEvent("", LocalDate.now().plusDays(3), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    presentaz1 = calendario.newEvent("", LocalDate.now(), "00:00", "Preprazione presentazione meeting", "", "08:00", "09:00", State.IN_CORSO, "Preprazione presentazione meeting " + LocalDate.now());
	    presentaz2 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Preprazione presentazione meeting", "", "08:00", "09:00", State.IN_CORSO, "Preprazione presentazione meeting " + LocalDate.now());
	    viaggio1 = calendario.newEvent("", LocalDate.of(2024, 04, 25), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	    viaggio2 = calendario.newEvent("", LocalDate.of(2024, 04, 26), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	    viaggio3 = calendario.newEvent("", LocalDate.of(2024, 04, 27), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	}
	
	@Test
    public void testCreateEvent() throws EventAlreadyExistsException, InvalidParameterException {
        Set<Event> events = calendario.getAllEvents();
        assertTrue(events.contains(meeting1));
        assertTrue(events.contains(meeting2));
        assertTrue(events.contains(meeting3));
        assertTrue(events.contains(presentaz2));
        assertTrue(events.contains(presentaz1));
        assertTrue(events.contains(viaggio1));
        assertTrue(events.contains(viaggio2));
        assertTrue(events.contains(viaggio3));
    }

	@Test
	public void testModifyEventDesc() throws EventAlreadyExistsException, InvalidParameterException, EventNotFoundException {
	    // Creazione eventi direttamente come EventImpl
	    EventImpl meeting11 = (EventImpl) meeting1;
	    EventImpl meeting21 = (EventImpl) meeting2;
	    EventImpl meeting31 = (EventImpl) meeting3;
	    boolean success = false;

	    // Modifica la descrizione degli eventi
	    success = calendario.modifyEvent(meeting11.getName(), meeting11.getDescription(), meeting11.getDate(), meeting11.getDate(),
	    								 meeting11.getDaOra(), meeting11.getAOra(), meeting11.getName(), "In questa giornata è stato presentato ....", 
	                                     meeting11.getDate(), meeting11.getDaOra(), meeting11.getAOra(), meeting11.getState(), meeting11.getIdentifier());

	    // Verifica che l'operazione è andata a buon fine
	    assertTrue(success);

	    // Verifica che gli eventi originali siano stati aggiornati
	    assertEquals("In questa giornata è stato presentato ....", meeting11.getDescription());
	    assertNotEquals("In questa giornata è stato presentato ....", meeting21.getDescription());
	    assertEquals("Metting di 3 giorni presso il palacongressi di Riccione.", meeting21.getDescription());
	    
	    success = calendario.modifyEvent(meeting21.getName(), meeting21.getDescription(), meeting21.getDate(), meeting21.getDate(),
							    		 meeting21.getDaOra(), meeting21.getAOra(), meeting21.getName(), "Workshop relativo a....", 
							    		 meeting21.getDate(), meeting21.getDaOra(), meeting21.getAOra(), meeting21.getState(), meeting21.getIdentifier());

		// Verifica che l'operazione è andata a buon fine
		assertTrue(success);
		
	    assertNotEquals("In questa giornata è stato presentato ....", meeting31.getDescription());
	    assertNotEquals("Workshop relativo a....", meeting31.getDescription());
	    assertEquals("Metting di 3 giorni presso il palacongressi di Riccione.", meeting31.getDescription());
	}
	
	@Test
	public void testModifyEventTime() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
	    // Creazione eventi direttamente come EventImpl
	    EventImpl presentaz11 = (EventImpl) presentaz1;
	    EventImpl presentaz21 = (EventImpl) presentaz2;
	   
	    // Modifica l'ora dell'evento
	    boolean success = calendario.modifyEvent(presentaz11.getName(), presentaz11.getDescription(), presentaz11.getDate(), presentaz11.getDate(), 
	    					   					 presentaz11.getDaOra(), presentaz11.getAOra(), presentaz11.getName(), presentaz11.getDescription(), 
	    					   					 presentaz11.getDate(), "09:00", "10:00", presentaz11.getState(), presentaz11.getIdentifier());
	    
	    // Verifica che l'operazione è andata a buon fine
	    assertTrue(success);
	    
	    // Verifica che l'ora sia stata modificata per presentaz1 e presentaz2
	    assertEquals("09:00", presentaz11.getDaOra());
	    assertEquals("10:00", presentaz11.getAOra());
	    assertEquals("09:00", presentaz21.getDaOra());
	    assertEquals("10:00", presentaz21.getAOra());
	}
	
	@Test
	public void testModifyEventState() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
	    // Creazione eventi direttamente come EventImpl
	    EventImpl presentaz = (EventImpl) presentaz1;
	   
	    // Modifica l'ora dell'evento
	    calendario.modifyEvent(presentaz.getName(), presentaz.getDescription(), presentaz.getDate(), presentaz.getDate(), 
	    					   presentaz.getDaOra(), presentaz.getAOra(), presentaz.getName(), presentaz.getDescription(), 
	    					   presentaz.getDate(), presentaz.getDaOra(), presentaz.getAOra(), State.CONCLUSO, presentaz.getIdentifier());
	    
	    // Verifica che l'ora sia stata modificata per presentaz1 e presentaz2
	    assertEquals(State.CONCLUSO, presentaz1.getState());
	    assertEquals(State.CONCLUSO, presentaz1.getState());
	}
	
    @Test
    public void testDeleteActivity() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
    	EventImpl event = (EventImpl) viaggio1;
        calendario.removeActivity(event.getName(), event.getDate(), viaggio1.getDaOra(), viaggio1.getAOra());
        
        Set<Event> events = calendario.getAllEvents();

        // Verifico che viaggio1 sia stato rimosso
        assertFalse(events.contains(viaggio1));

        // Verifico che gli altri eventi siano ancora presenti
        assertTrue(events.contains(meeting1));
        assertTrue(events.contains(meeting2));
        assertTrue(events.contains(meeting3));
        assertTrue(events.contains(presentaz1));
        assertTrue(events.contains(presentaz2));
        assertTrue(events.contains(viaggio2));
        assertTrue(events.contains(viaggio3));
    }
    public void testDeleteEvent() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
    	// Rimuovi tutti gli eventi con lo stesso identificatore
    	Event event1 = calendario.newEvent("", LocalDate.now(), "00:00", "evento 1", "", "20:00", "21:00", State.IN_CORSO, "evento1 " + LocalDate.now().toString());
    	Event event2 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "evento 2", "", "20:00", "21:00", State.IN_CORSO, "evento2 " + LocalDate.now().toString());
    	Event event3 = calendario.newEvent("", LocalDate.now().plusDays(2), "00:00", "evento 3", "", "20:00", "21:00", State.IN_CORSO, "evento3 " + LocalDate.now().toString());
	    
    	EventImpl eventImpl = (EventImpl) event1;
    	
	    Set<Event> eventsToRemove = calendario.removeEvents(eventImpl.getName(), eventImpl.getDate(), eventImpl.getDaOra(), eventImpl.getAOra());
	    Set<Event> events = calendario.getAllEvents();
	
	    // Verifico che tutti gli eventi con lo stesso identificatore siano stati rimossi
	    for (Event evt : eventsToRemove) {
	        assertFalse(events.contains(evt));
	    }
	
	    // Verifico che gli altri eventi non siano stati rimossi
	    assertTrue(events.contains(meeting1));
	    assertTrue(events.contains(meeting2));
	    assertTrue(events.contains(meeting3));
	    assertTrue(events.contains(presentaz1));
	    assertTrue(events.contains(presentaz2));
    }
    
	@Test
    public void testException() {
		
		try {
			// NUOVO EVENTO
			calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Ferie", "", "17:00", "18:00", State.DA_AVVIARE, "Ferie " + LocalDate.now().plusDays(1).toString());
		} catch(Exception ex) {
			
		}
		
		// Test per EventAlreadyExistsException
		assertThrows(EventAlreadyExistsException.class, () -> calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Ferie", "", "17:00", "18:00", State.DA_AVVIARE, "Ferie " + LocalDate.now().plusDays(1).toString()));
		
		// Test per InvalidParameterException
		assertThrows(InvalidParameterException.class, () -> calendario.newEvent("", LocalDate.now(), "00:00", "", "Ultima sessione di esami", "08:00", "09:00", State.IN_CORSO, "" + LocalDate.now().toString()));
		assertThrows(InvalidParameterException.class, () -> calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Esame PMO", "Ultimo appello.", "", "", State.IN_CORSO, "Esame PMO " + LocalDate.now().plusDays(1).toString()));
		
		// Test per EventNotFoundException durante la modifica di un evento non esistente
		assertThrows(EventNotFoundException.class, () ->
        				calendario.modifyEvent("Laurea", "", LocalDate.of(2024, 10, 25), LocalDate.now().plusDays(1), "08:00", "20:00", "Malattia", 
        									   "Proclamazione tesi", LocalDate.now().plusDays(1), "00:00", "18:00", State.DA_AVVIARE, "Laurea " + LocalDate.of(2024, 10, 25).toString()));
		
		// Test per EventNotFoundException durante la modifica di un evento non esistente
		assertThrows(EventNotFoundException.class, () ->
        				calendario.removeActivity("Discussione progetto", LocalDate.now(), "00:00", "01:00"));		
	}
	   
    @Test
    public void testGetEventsInDate() {
        LocalDate date = LocalDate.now().plusDays(1);
        calendarModel.setValueAddEvent(date, meeting1);
        calendarModel.setValueAddEvent(date, presentaz2);

        Set<Event> eventsInDate = calendarModel.getEventsInDate(date);

        // Verifico che gli eventi aggiunti siano presenti
        assertNotNull(eventsInDate);
        assertTrue(eventsInDate.contains(meeting1));
        assertTrue(eventsInDate.contains(presentaz2));
    }

    @Test
    public void testGetValueAtDate() {
        LocalDate date = LocalDate.now().plusDays(1);

        Set<Event> events = new HashSet<>();
        events.add(meeting1);
        events.add(presentaz1);

        String event1Html = calendarModel.toHtml(meeting1) + meeting1.getInfoEventToString().replace("\n", " ").replace("\r", " ");
        String event2Html = calendarModel.toHtml(presentaz1) + presentaz1.getInfoEventToString().replace("\n", " ").replace("\r", " ");

        String expectedHtml = "<html>4<br>" + event1Html + "<br>" + event2Html + "</html>";
        Object actualHtml = calendarModel.getValueAtDate(date, events);
        
        assertEquals(expectedHtml, actualHtml);
    }
}
