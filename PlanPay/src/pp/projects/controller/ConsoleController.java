package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pp.projects.model.Account;
import pp.projects.model.CalendarModel;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.IllegalOperationException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.Objective;
import pp.projects.model.OperationType;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.view.CalendarView;

public interface ConsoleController {
	
	/**
	 * 
	 * @param importo
	 * @param tipo
	 * @param nome
	 * @param op
	 * @return
	 * @throws IllegalOperationException
	 */
	boolean updateConto(double importo, boolean tipo, String nome, OperationType op) throws IllegalOperationException;
	
	/**
	 * 
	 * @return
	 */
	List<Objective> getObjectiveList();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	Optional<Objective> getObjective(String name);
	
	/**
	 * 
	 * @param bNew
	 * @param nameObjective
	 * @param newDescrOb
	 * @param savingTarget
	 * @throws IllegalStateException
	 */
	void saveObjective(boolean bNew, String nameObjective, String newDescrOb, double savingTarget) throws IllegalStateException;
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 */
	void removeObjective(String name) throws IllegalStateException;
	
	/**
	 * 
	 * @return
	 */
	List<Transaction> getAllTransactions();
	
	/**
	 * 
	 * @return
	 */
	List<String> getDatiTransazione();
	
	/**
	 * 
	 * @return
	 */
	Account getAccount();
	
	/**
	 * 
	 * @return
	 */
	String setNameController();
	
	/**
	 * 
	 * @return
	 */
	CalendarModel getCalendarModel();
	
	/**
	 * 
	 * @return
	 */
	CalendarView drawCalendar();
	
	/**
	 * 
	 * @return
	 */
	Set<Event> loadEvents();
	
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
	 * @param stato = stato dell'evento in modifica
	 * @param identifier = identificatore per eventi collegati
	 * @throws EventNotFoundException 
	 * @throws EventAlreadyExistsException 
	 * @throws InvalidParameterException 
	 */
	 Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, 
					    String newName, String newdesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, EventNotFoundException, InvalidParameterException;
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 * 
	 * @throws EventNotFoundException 
	 */
	Event removeActivity(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException;
	
	/**
	 * 
	 * @param name
	 * @param date
	 * 
	 * 
	 * 
	 * @return
	 * @throws EventNotFoundException
	 */
	Set<Event> removeEvents(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException;

	/**
	 * 
	 */
	void updateUIevents();
	
	/**
	 * 
	 * @return
	 */
	Set<Event> getAllEventToFile();
}
