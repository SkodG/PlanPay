package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import pp.projects.model.CalendarImpl;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.State;

class CalendarTest {

private CalendarImpl calendar;
	
	@Test
    public void testCreateEvent() {
        Event event = new EventImpl("Test Event", "Description", LocalDate.now(), "10:00", "11:00", State.DA_AVVIARE);
        calendar.createEvent(event);
        
        Set<EventImpl> events = calendar.getEvents();
        assertTrue(events.contains(event));
    }

    @Test
    public void testDeleteEvent() {
        EventImpl event = new EventImpl("Test Event", "Description", LocalDate.now(), "10:00", "11:00", State.DA_AVVIARE);
        calendar.createEvent(event);
        calendar.deleteEvent(event);
        
        Set<EventImpl> events = calendar.getEvents();
        assertFalse(events.contains(event));
    }

    @Test
    public void testModifyEvent() {
        Event oldEvent = new EventImpl("Test Event", "Description", LocalDate.now(), "10:00", "11:00", State.DA_AVVIARE);
        Event newEvent = new EventImpl("Test Event Modified", "New Description", LocalDate.now(), "12:00", "13:00", State.IN_CORSO);
        calendar.createEvent(oldEvent);
        calendar.modifyEvent(oldEvent, newEvent);
        
        Set<EventImpl> events = calendar.getEvents();
        assertFalse(events.contains(oldEvent));
        assertTrue(events.contains(newEvent));
    }

}
