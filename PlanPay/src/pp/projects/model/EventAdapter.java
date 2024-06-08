package pp.projects.model;

import java.time.LocalDate;

public class EventAdapter implements Data {
	private Event event;

	public EventAdapter(Event event) {
	   this.event = event;
	}
	
    @Override
    public String getName() {
        return event instanceof EventImpl ? ((EventImpl) event).getName() : "";
    }

    @Override
    public void setName(String name) {
    	if (event instanceof EventImpl) {
           ((EventImpl) event).setName(name);
        }
    }

    @Override
    public String getDescription() {
    	return event instanceof EventImpl ? ((EventImpl) event).getDescription() : "";
    }

    @Override
    public void setDescription(String description) {
    	if (event instanceof EventImpl) {
            ((EventImpl) event).setDescription(description);
        }
    }

    @Override
    public LocalDate getDate() {
    	return event instanceof EventImpl ? ((EventImpl) event).getDate() : null;
    }
}

