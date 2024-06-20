package pp.projects.model;

import java.time.LocalDate;

public interface CalendarP {

	/**
	 * Creazione di un nuovo evento.
	 * 
	 * @param name 
	 * @param currentDate = data corrente.
	 * @param daOra = ora dell'evento.
	 * @param newName
	 * @param newDesc = descrizione dell'evento.
	 * @param newDaOra = da ora evento.
	 * @param newAora = a ora evento.
	 * @return nuovo evento.
	 * @throws InvalidParameterException 
	 * @throws EventAlreadyExistsException 
	 */
	Event newEvent(String name, LocalDate currentDate, String daOra, String newName, String newDesc, String newDaOra, String newAora) throws EventAlreadyExistsException, InvalidParameterException;
	
	/**
	 * Modifica di un evento già esistente.
	 * 
	 * @param name
	 * @param desc
	 * @param daData
	 * @param aData
	 * @param daOra
	 * @param aOra
	 * @param newName
	 * @param newDesc
	 * @param currentDate
	 * @param newDaOra
	 * @param newAora
	 * @return l'evento modificato.
	 * @throws EventNotFoundException 
	 */
	Event modifyEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
			 		  String newName, String newDesc, LocalDate currentDate, String newDaOra, String newAora) throws EventNotFoundException;
	
	/**
	 * Rimozione di un evento.
	 * 
	 * @param name
	 * @param date
	 * @param daOra
	 * @return l'evento rimosso.
	 * @throws EventNotFoundException 
	 */
	Event removeEvent(String name, LocalDate date, String daOra) throws EventNotFoundException;
}
