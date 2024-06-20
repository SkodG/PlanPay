package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class EventImpl implements Event, Data{
	
	private String name;
    private String description;
    private LocalDate date;
	private State eventState;
	private String daOra;
	private String aOra;
	
	public EventImpl(String name, String desc, LocalDate data, String daOra, String aOra) {
		this.name = name;
		this.description = desc;
		this.date = data;
		this.daOra = daOra;
		this.aOra = aOra;
		//this.eventState = s;
		
		//this.inputStream = this.getClass().getResourceAsStream("/resource/events.txt");
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
        return date;
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
		return this.getDate() + "," + this.getDaOra() + "," + this.getAOra() + "," + this.getName() + "," + this.getDescription();
	}
	
	public void setDaOra(String da) {
		this.daOra = da;
	}
	
	public void setAOra(String a) {
		this.aOra = a;
	}
}
