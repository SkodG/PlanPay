package pp.projects.model;

public interface Event {
	/**
	 * @return dello stato
	 */
	State getState();
	
	/**
	 * modifica lo stato
	 * 
	 * @param s = stato
	 */
	void setState(State s);
	
	/**
	 * 
	 * @return l'evento in stringa da visualizzare sul calendario.
	 */
	String getInfoEventToString();
	
	/**
	 * 
	 * @return l'orario impostato sull'evento.
	 */
	String getDaOra();
	
	/**
	 * 
	 * @return l'orario impostato sull'evento.
	 */
	String getAOra();
	
	/**
	 * 
	 * @return il formato per la scrittura nel file
	 */
	String getInfoEventToFile();
	
	String getIdentifier();
	
	void setIdentifier(String identifier);
}
