package pp.projects.controller;

import java.util.List;

import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.TransactionImpl;

public interface ConsoleController {
	/**
	 * @return la lista delle transazioni effettuate dall'utente in ordine di data
	 */
	List<TransactionImpl> getTransaction();	
	
	/**
	 * @return la lista degli eventi inseriti dall'utente
	 */
	List<EventImpl> getEvent();		/// MI SERVE?????
	
	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	List<ObjectiveImpl> getObjective();
	
	/**
	 * Aggiunge un obbiettivo alla lista degli obbiettivi
	 * 
	 * @param n = nome obbiettivo
	 * @param d = descrizione obbiettivo
	 * @param date = data.... 
	 * @param imp = importo dell'obbiettivo
	 */
	void newObject(String n, String d, String date, Double imp);
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeObject(ObjectiveImpl o);
	
	/**
	 * Aggiunge un obbiettivo alla lista degli obbiettivi
	 * 
	 * @param n = nome obbiettivo
	 * @param d = descrizione obbiettivo
	 * @param date = data.... 
	 */
	void newEvent(String n, String d, String date);
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeEvent(EventImpl e);

}
