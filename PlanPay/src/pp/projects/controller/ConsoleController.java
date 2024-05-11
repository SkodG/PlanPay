package pp.projects.controller;

import java.util.List;

import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.Transaction;
import pp.projects.model.TransactionImpl;

public interface ConsoleController {
	
	List<Transaction> getAllTransactions();
	
	List<Event> getEvent();	
	
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
