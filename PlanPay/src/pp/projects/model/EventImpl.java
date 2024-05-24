package pp.projects.model;

import java.time.LocalDate;
import java.util.Date;

public class EventImpl implements Data, Event{
	
	private String eventName;
	private String eventDescr;
	private LocalDate eventData;
	private State eventState;
	private String daOra;
	private String aOra;
	
	public EventImpl(String name, String desc, LocalDate data, String daOra, String aOra) {
		this.eventName = name;
		this.eventDescr = desc;
		this.eventData = data;
		this.daOra = daOra;
		this.aOra = aOra;
		//this.eventState = s;
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
	public LocalDate getDate() {
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
	
	@Override
	public String getInfoEventToString() {
		return this.getDaOra() + " - " + this.getAOra() + " : '" + this.getName() + "'";
	}
	
	/**
	 * 
	 * @return orario modificato
	 */
	public LocalDate setDateTime() {
		return this.eventData;
	}
	
	public String getDaOra() {
		return this.daOra;
	}
	
	public void setDaOra(String da) {
		this.daOra = da;
	}
	
	public String getAOra() {
		return this.aOra;
	}
	
	public void setAOra(String a) {
		this.daOra = a;
	}

}
