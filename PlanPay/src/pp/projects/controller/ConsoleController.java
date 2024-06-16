package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pp.projects.model.Account;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.OperationType;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.view.CalendarView;

public interface ConsoleController {
	
	boolean updateConto(double importo, boolean tipo, String nome, OperationType op) throws IllegalStateException;
	
	List<ObjectiveImpl> getObjectiveList();
	Optional<ObjectiveImpl> getObjective(String name);
	void saveObjective(boolean bNew, String nameObjective, String newNameOb, String newDescrOb, double savingTarget) throws IllegalStateException;
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 */
	void removeObjective(String name) throws IllegalStateException;
	
	
	List<Transaction> getAllTransactions();
	List<String> getDatiTransazione();
	
	Account getAccount();
	String setNameController();
	
	// gestione calendario
	CalendarView drawCalendar();
	
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
	 * @throws EventNotFoundException 
	 * @throws EventAlreadyExistsException 
	 * @throws InvalidParameterException 
	 */
	 Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, State s, 
					    String newName, String newdesc, String newDaOra, String newAora) throws EventAlreadyExistsException, EventNotFoundException, InvalidParameterException;
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * @param o = obbiettivo da eliminare
	 * @throws EventNotFoundException 
	 */
	Event removeEvent(String name, LocalDate date, String daOra) throws EventNotFoundException;
}
