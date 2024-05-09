package pp.projects.model;

public class EventImpl implements Data, Event{
	
	private String eventName;
	private String eventDescr;
	private String eventData;
	private State eventState;
	
	public EventImpl(String name, String desc, String data, State s) {
		this.eventName = name;
		this.eventDescr = desc;
		this.eventData = data;
		this.eventState = s;
	}
	
	@Override
	public String getName() {
		return this.eventName;
	}

	@Override
	public void setName(String name) {
		this.eventName = name;
		
	}

	@Override
	public String getDescription() {
		return this.eventDescr;
	}

	@Override
	public void setDescription(String d) {
		this.eventDescr = d;
	}

	@Override
	public String getDate() {
		return this.eventData;
	}
	
	@Override
	public State getState() {
		return this.eventState;
	}

	@Override
	public void setState(State s) {
		this.eventState = s;
	}

}
