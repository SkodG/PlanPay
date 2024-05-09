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
}
