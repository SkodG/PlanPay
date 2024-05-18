package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;

import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.Transaction;

public interface ConsoleController {
	
	List<Transaction> getAllTransactions();
	
	List<ObjectiveImpl> getObjectiveList();
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeObjective(ObjectiveImpl o);
	
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
