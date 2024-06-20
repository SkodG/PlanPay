package pp.projects.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import pp.projects.model.AbstractOperations;
import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.ComparatorEvents;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.OperationType;
import pp.projects.model.ServicesImpl;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.view.CalendarView;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private Account account;
	private List<AbstractOperations> operationsList;
	
	// gestione calendario
	private CalendarImpl calendario;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		this.operationsList = new ArrayList<>();
        this.operationsList.add(new ServicesImpl(account));
		
        // Inizializza il modello con l'anno e il mese corrente
        this.calendario = new CalendarImpl(0);
	}
		
	@Override
	public boolean updateConto(double importo, boolean tipo, String nome, OperationType operType) throws IllegalArgumentException {
		boolean bRes = false;
		
		// Tipo: true indica un deposito (deposit)
		// Tipo false indica un prelievo (withdraw)
		if (operType == OperationType.OBIETTIVO) {
		    for (AbstractOperations operation : operationsList) {
		         if (operation instanceof ObjectiveImpl) {
		        	 // prendo la lista di tutti gli obbiettivi
		        	 if(operation.nome().equals(nome)) {
			              if (tipo) {		            	  
			            	  operation.deposit(importo, "");
			                  bRes = true;
			              } else {
			                  bRes = operation.withdraw(importo, "");
			              }
		              }
		          }
		     }
		  } else if (operType == OperationType.SERVIZIO) {
		      for (AbstractOperations operation : operationsList) {
		            if (operation instanceof ServicesImpl) {
		                if (tipo) {
		                    operation.deposit(importo, nome);
		                    bRes = true;
		                } else {
		                    bRes = operation.withdraw(importo, nome);
		                }
		            }
		        }
		    }
		    if (!bRes) {
		        throw new IllegalArgumentException("Operazione non valida o fondi insufficienti.");
		    }
	    
	    // Aggiorna la vista del conto dopo l'operazione
         controllerLogin.getConsolleView().updateUIconto();
         return bRes;
	}
	
	// Metodo per aggiungere un nuovo obiettivo
    private void addObjective(String name, String description, double savingTarget) {
         this.operationsList.add(new ObjectiveImpl(account, name, description, savingTarget));
    }
    
    // Metodo per modificare un obbiettivo esistente
	private void modifyObjective(ObjectiveImpl objective, String newNameOb, String newDescrOb, double savingTarget) {
		objective.setDescription(newDescrOb);
		objective.setName(newNameOb);
		objective.setSavingTarget(savingTarget);
	}
	
	@Override
	public Optional<ObjectiveImpl> getObjective(String name) {
		return operationsList.stream()
			   .filter(oper -> oper instanceof ObjectiveImpl)
			   // converto ogni operazione in ObjectiveImpl
	           .map(oper -> (ObjectiveImpl) oper)
	           .filter(o -> o.getName().equals(name))
	           .findFirst();

	}
	
	@Override
	public void saveObjective(boolean bNew, String nameObjective, String newDescrOb, double savingTarget) throws IllegalStateException {
		Optional<ObjectiveImpl> objective = getObjective(nameObjective);
		// Se sono su nuovo creo un nuovo obbiettivo > tanto l'id lo incremento alla creazione, quindi non può già esistere.
		if(bNew) {
			if(objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo esistente! Impossibile crearlo.");
			}
			addObjective(nameObjective, newDescrOb, savingTarget);//MODIFICATO: aggiunto param target per la soglia di risparmio da raggiungere nell'obbiettivo
		} else {
			if(!objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo inesistente! Impossibile modificarlo.");
			}
			modifyObjective(objective.get(), nameObjective, newDescrOb, savingTarget);
		}		
	}
	
	@Override
	public void removeObjective(String name) throws IllegalStateException {
		Optional<ObjectiveImpl> objective = getObjective(name);
		
		if (!objective.isPresent()) {
	        throw new IllegalStateException("Obbiettivo inesistente! Impossibile cancellarlo.");
	    }

	    operationsList.remove(objective.get());
	}
	
	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	@Override
	public List<ObjectiveImpl> getObjectiveList() {
		return operationsList.stream()
					.filter(oper -> oper instanceof ObjectiveImpl)
					.map(oper -> (ObjectiveImpl) oper)
					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}
	
	/**
	 * @return la lista di tutte le transazioni da mostrare nell view
	 */
	@Override
	public List<Transaction> getAllTransactions() {
		return operationsList.stream()
				.flatMap(operat -> operat.getList().stream())
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return La stringa della singola transazione
	 */
	@Override
	public List<String> getDatiTransazione() {
		return getAllTransactions().stream()
                .map(t ->  t.getDescription())
                .collect(Collectors.toList());
	}
	
	@Override
	public Account getAccount() {
		return this.account;
	}
	
	@Override
	public String setNameController() {
		return this.controllerLogin.getUserName();
	}
	
	// calendario
	@Override
	public CalendarModel getCalendarModel() {
    	LocalDate today = LocalDate.now();
    	int anno = today.getYear();
    	int mese = today.getMonthValue();
		
		return new CalendarModel(anno, mese);
	}
	
	@Override
	public CalendarView drawCalendar() {
    	/*LocalDate today = LocalDate.now();
    	int anno = today.getYear();
    	int mese = today.getMonthValue();*/
		
        return new CalendarView(this, getCalendarModel());
    }
	
	@Override
	public Set<Event> loadEvents() {
        return calendario.loadEventsFromFile();
    }
	
	@Override
	public Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, State s, 
								String newName, String newdesc, String newDaOra, String newAora) throws EventAlreadyExistsException, EventNotFoundException, InvalidParameterException {
		int daysEvents = 0;
		LocalDate currentDate = daData;
		Event event = null;
		Set<Event> events = new TreeSet<>(new ComparatorEvents());
		
		// Controllo se l'evento è già presente nella data/orario definita. Se non è presente lo creo. In caso contrario mostro messaggio di errore.
		if(bNew) {
			// creazione dell'evento in più date.
			if(!daData.equals(aData)) {
				daysEvents = (int) ChronoUnit.DAYS.between(daData, aData);
				for(int i = 0; i <= daysEvents; i++) {
					if(i > 0) {
						currentDate = currentDate.plusDays(1);
					}
					event = calendario.newEvent(name, currentDate, daOra, newName, newdesc, newDaOra, newAora);
				}
			// creazione dell'evento nella data singola.
			} else {
					event = calendario.newEvent(name, daData, daOra, newName, newdesc, newDaOra, newAora);
			}
		} else {
				event = calendario.modifyEvent(name, desc, daData, aData, daOra, aOra, newName, newdesc, currentDate, newDaOra, newAora);	
		}

	    // Salvataggio degli eventi su file
	    if (!calendario.saveEventsToFile()) {
	        return Collections.emptySet(); // Restituisce un insieme vuoto se il salvataggio fallisce
	    }
	    // se il salvataggio va a buon fine lo aggiungo al set
		events.add(event);		
		return events;
	}
	
	@Override
	public Event removeEvent(String name, LocalDate date, String daOra) throws EventNotFoundException  {
	    // Salvataggio degli eventi su file
		return calendario.removeEvent(name, date, daOra);
	}
	
	public void updateUIevents() {
		controllerLogin.getConsolleView().updateEventsUI();
	}
	
	public Set<Event> getAllEventToFile(){
		return calendario.getAllEvents();
	}
	
}
