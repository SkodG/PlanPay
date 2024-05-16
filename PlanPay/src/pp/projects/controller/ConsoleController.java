package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;

import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.Transaction;

public interface ConsoleController {
	
	List<Transaction> getAllTransactions();
	
	List<ObjectiveImpl> getObjective();
	
	/**
	 * Aggiunge un obbiettivo alla lista degli obbiettivi
	 * 
	 * @param n = nome obbiettivo
	 * @param d = descrizione obbiettivo
	 * @param date = data.... 
	 * @param imp = importo dell'obbiettivo
	 */
	void newObject(ObjectiveImpl o);
	
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
	void newEvent(LocalDate date);
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeEvent(Event e);
}
