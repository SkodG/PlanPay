package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import pp.projects.model.CalendarImpl;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.State;

class EventFileTest {
	private CalendarP calendar;
    private Path tempDir;
    private String tempFilePath;
	
    @Before
    public void setUp() throws IOException {
        // Crea una directory temporanea per i file di test
        tempDir = Files.createTempDirectory("test_events");
        tempFilePath = tempDir.resolve("events.txt").toString();
        calendar = new CalendarImpl();
        calendar.setPath(tempFilePath);
    }
    
    @Test
    public void testSaveEventsToFile() {
        // Crea alcuni eventi di test
        Event event1 = new EventImpl("Test Event 1", "Description 1", LocalDate.of(2024, 6, 21), "10:00", "11:00", State.IN_CORSO);
        Event event2 = new EventImpl("Test Event 2", "Description 2", LocalDate.of(2024, 6, 22), "12:00", "13:00", State.DA_AVVIARE);
        calendar.createEvent(event1);
        calendar.createEvent(event2);

        // Salva gli eventi su file
        boolean saveSuccess = calendar.saveEventsToFile();
        assertTrue(saveSuccess);

        // Verifica che il file esista
        File file = new File(tempFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testLoadEventsFromFile() {
        // Crea e salva alcuni eventi di test
        Event event1 = new EventImpl("Test Event 1", "Description 1", LocalDate.of(2024, 6, 21), "10:00", "11:00", State.IN_CORSO);
        Event event2 = new EventImpl("Test Event 2", "Description 2", LocalDate.of(2024, 6, 22), "12:00", "13:00", State.DA_AVVIARE);
        calendar.createEvent(event1);
        calendar.createEvent(event2);
        calendar.saveEventsToFile();

        // Carica gli eventi dal file
        Set<Event> loadedEvents = calendar.loadEventsFromFile();
        assertEquals(2, loadedEvents.size());
        assertTrue(loadedEvents.contains(event1));
        assertTrue(loadedEvents.contains(event2));
    }

    @Test
    public void testLoadEmptyFile() throws IOException {
        // Crea un file vuoto
        File file = new File(tempFilePath);
        assertTrue(file.createNewFile());

        // Carica gli eventi dal file vuoto
        Set<Event> loadedEvents = calendar.loadEventsFromFile();
        assertTrue(loadedEvents.isEmpty());
    }

}
