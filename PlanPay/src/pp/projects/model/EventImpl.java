package pp.projects.model;

import java.time.LocalDate;

public class EventImpl implements Event, Data{
	
	private String name;
    private String description;
    private LocalDate daDate;
	private State eventState;
	private String daOra;
	private String aOra;
	
	public EventImpl(String name, String desc, LocalDate daData, String daOra, String aOra, State stato) {
		this.name = name;
		this.description = desc;
		this.daDate = daData;
		this.daOra = daOra;
		this.aOra = aOra;
		this.eventState = stato;
	}
	
	@Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getDate() {
        return daDate;
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
	
	@Override
	public String getDaOra() {
		return this.daOra;
	}
	
	@Override
	public String getAOra() {
		return this.aOra;
	}
	
	@Override
	public String getInfoEventToFile() {
		return this.getDate() + "[,]" + this.getDaOra() + "[,]" + this.getAOra() + "[,]" + this.getName() + "[,]" + this.getDescription() + "[,]" + this.getState().toString();
	}
	
	public void setDaOra(String da) {
		this.daOra = da;
	}
	
	public void setAOra(String a) {
		this.aOra = a;
	}
}
