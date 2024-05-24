package pp.projects.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.model.TransactionImpl;
import pp.projects.view.CalendarView;
import pp.projects.view.EventView;
import pp.projects.view.ObjectiveView;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;
	private ServicesImpl services;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<ObjectiveImpl> listObjectives;		// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<TransactionImpl> listTransactions;
	private List<EventImpl> listEvents;
	private CalendarImpl calendario;
	private CalendarView calendarView;
	private Account account;
	private EventView eventView;
	private ObjectiveView objectiveView;
	
	private List<String> datiTransazione;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		this.services = new ServicesImpl(this.account);
		this.calendario = new CalendarImpl(0);
		this.listObjectives = new ArrayList<ObjectiveImpl>();
		this.listTransactions = new ArrayList<TransactionImpl>();
		this.listEvents = new ArrayList<EventImpl>();
	}
	
	/**
	 * @return la lista di tutte le transazioni da mostrare nell view
	 */
	@Override
	public List<TransactionImpl> getAllTransactions() {
		// Ottengo lo stream dei servizi
		Stream<TransactionImpl> serviceTransaction = services != null ? services.getList().stream() : Stream.empty();
		
		// Ottengo lo stream degli obbiettivi
		Stream<TransactionImpl> objectiveTransaction = 
				listObjectives != null ? listObjectives.stream()
										 				.flatMap(objective -> objective.getList().stream()) : Stream.empty();
		return Stream.concat(serviceTransaction, objectiveTransaction).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return La stringa della singola transazione
	 */
	public List<String> getTransactionToString() {
		List<TransactionImpl> listTransactions = this.getAllTransactions();
		
		// ciclo ogni transazione e aggiungo i dati alla lista restituendolo
		for(TransactionImpl t : listTransactions) {			
			datiTransazione.add(t.getName() + "  " + t.getDescription() + "  " + t.getAmount() + " €");
		}		
		return this.datiTransazione;
	}

	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	@Override
	public List<ObjectiveImpl> getObjectiveList() {
		return listObjectives;
	}
	
	@Override
	public void removeObjective(ObjectiveImpl o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public EventImpl saveEvent(boolean bNew, String name, String desc, LocalDate daData, 
								LocalDate aData, String daOra, String aOra, State s, 
								String newName, String newdesc, String newDaOra, String newAora) {
		int daysEvents = 0;
		LocalDate currentDate = daData;
		boolean bExist = false;
		EventImpl event = null;
		
		// Controllo se l'evento è già presente nella data/orario definita.
		// Se non è presente lo creo. In caso contrario mostro messaggio di errore.
		if(bNew) {
			if(!daData.equals(aData)) {
				System.err.println("confronto data");
				daysEvents = aData.getDayOfMonth() - daData.getDayOfMonth();
				for(int i = 0; i <= daysEvents; i++) {
					if(i > 0) {
						currentDate = currentDate.plusDays(i);
					}
	
					// controllo se esiste un evento con la stessa data/ora prima di aggiungerlo
					bExist = eventExist(name, currentDate, daOra);
					
					if(bExist) {
						// Autenticazione fallita, mostra un messaggio di errore
						JOptionPane.showMessageDialog(null, "Evento già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
						return null;
						//resultCreation += "Non è stato creato l'evento '" + name + "' per il giorno " + currentDate.toString() + " nell'orario: " + daOra + "-" + aOra + ", in quanto nella fascia oraria specificata è già presente un altro evento!\n";
					} else {
						event = new EventImpl(newName, newdesc, currentDate, newDaOra, newAora);
						listEvents.add(event);
					}
				}
			} else {
				// controllo se esiste un evento con la stessa data/ora prima di aggiungerlo
				bExist = eventExist(name, currentDate, daOra);
				
				if(bExist) {
					// Autenticazione fallita, mostra un messaggio di errore
					JOptionPane.showMessageDialog(null, "Evento già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
					return null;
					//resultCreation += "Non è stato creato l'evento '" + name + "' per il giorno " + currentDate.toString() + " nell'orario: " + daOra + "-" + aOra + ", in quanto nella fascia oraria specificata è già presente un altro evento!\n";
				} else {
					event = new EventImpl(newName, newdesc, currentDate, newDaOra, newAora);
					listEvents.add(event);
				}
			}
		} else {
			// Nel caso di modifica, prendo l'obbiettivo da modificare e setto i campi.
			bExist = eventExist(name, daData, daOra);
			if(bExist) {
				event = listEvents.stream()
								   .filter(e -> 
									   e.getName().equals(name) &&
									   e.getDate().equals(daOra) &&
									   e.getDaOra().equals(daOra))
								   .findFirst()
								   .orElse(null);
				if (event != null) {
	                if (newName != null && !newName.trim().isEmpty()) {
	                    event.setName(newName);
	                }
	                if (newdesc != null && !newdesc.trim().isEmpty()) {
	                    event.setDescription(newdesc);
	                }
	                if (newDaOra != null && !newDaOra.trim().isEmpty()) {
	                    event.setDaOra(newDaOra);
	                }
	                if (newAora != null && !newAora.trim().isEmpty()) {
	                    event.setAOra(newAora);
	                }
				}
			}
						   
		}		
		return event;
	}
	
	public boolean eventExist(String name, LocalDate currentDate, String daOra) {
		return listEvents.stream()
						 .anyMatch(e ->
							e.getName().equals(name) &&
							e.getDate().equals(currentDate) &&
							e.getDaOra().equals(daOra) 
						);
	}

	@Override
	public void removeEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
	
	public String setNameController() {
		return this.controllerLogin.getUserName();
	}

	public void drawCalendar() {
		this.calendarView = new CalendarView(this);
	}
	
	public void saveObjective(boolean bNew, int id, String nameObjective, String descObjective) {
		// Se sono su nuovo creo un nuovo obbiettivo > tanto l'id lo incremento alla creazione, quindi non può già esistere.
		if(bNew) {
			listObjectives.add(new ObjectiveImpl(account, id, nameObjective, descObjective));
		} else {
			// Nel caso di modifica, controllo che l'obbiettivo esista e in caso positivo modifico i campi passati.
			listObjectives.stream()
						  .filter(o -> o.getId() == id)
						  .findFirst()
						  .ifPresent(o -> {
							  o.setDescription(descObjective);
						  	  o.setName(nameObjective);
						  });
		}		
	}
	
	// TODO inserire un metodo per controllare che se serviceview.bGetNegative() è false > do messaggio di errore.
	//public void testOperation() {
	//	if(services.withdraw())
	//}
}
