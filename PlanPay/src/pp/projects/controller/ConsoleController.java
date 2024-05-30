package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;

import pp.projects.model.Event;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.State;
import pp.projects.model.TransactionImpl;

public interface ConsoleController {
	
	List<TransactionImpl> getAllTransactions();
	
	List<ObjectiveImpl> getObjectiveList();
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeObjective(ObjectiveImpl o);
	
	/**
	 * @return l'evento che è stato aggiunto/modificato
	 * 
	 * @param bNew = mi tiene traccia per sapere se creare l'evento o modificarlo
	 * @param name = nome dell'evento
	 * @param desc = descrizione dell'evento
	 * @param daData = Da data dell'evento
	 * @param aData = A data dell'evento
	 * @param daOra = da ora dell'evento
	 * @param aOra = a ora dell'evento
	 * @param s = stato dell'evento
	 * @param newName = nome dell'evento in modifica
	 * @param newdesc = descrizione dell'evento in modifica
	 * @param newDaOra = da ora dell'evento in modifica
	 * @param newAora = a ora dell'evento in modifica
	 * @param newS = stato dell'evento in modifica
	 */
	Event saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, State s, 
					    String newName, String newdesc, String newDaOra, String newAora);
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	boolean removeEvent(String name, LocalDate date, String daOra);
}
