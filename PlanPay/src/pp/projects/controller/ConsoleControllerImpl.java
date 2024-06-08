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
import java.util.stream.Stream;

import pp.projects.model.AbstractOperations;
import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.ComparatorEvents;
import pp.projects.model.Event;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.view.CalendarView;
import pp.projects.view.ObjectiveView;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	//private List<ObjectiveImpl> listObjectives;		// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<Transaction> listTransactions;
	private Account account;
	private ObjectiveView objectiveView;	
	private List<String> datiTransazione;
	private List<AbstractOperations> operationsList;
	//private AbstractOperations serviceOperations;
	
	// gestione calendario
	private CalendarImpl calendario;
	private CalendarModel calendarModel;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		//this.listObjectives = new ArrayList<>();
		this.listTransactions = new ArrayList<>();
		//this.serviceOperations = new ServicesImpl(account);
		this.operationsList = new ArrayList<>();
        this.operationsList.add(new ServicesImpl(account));
		
        // Inizializza il modello con l'anno e il mese corrente
        this.calendario = new CalendarImpl(0);
	}
		
	@Override
	public boolean updateConto(double importo, boolean tipo, String nome) {
		Optional<ObjectiveImpl> objective = null;
		
		// Itera su tutte le operazioni per trovare quella corretta
	    for (AbstractOperations operation : operationsList) {
	    	if (tipo) {
		    	if(operation instanceof Objective) {
		    		operation.deposit(importo);				// Obbiettivo con WithDraw mi deposita i soldi sul conto.
				} else {
					return operation.withdraw(importo);		// Servizio che preleva soldi dal conto
				}
	    	} else {
                // Tipo false indica un deposito (deposit)
                if (operation instanceof Objective) {
                    return operation.withdraw(importo); 	// Obiettivo con Withdraw che preleva soldi dal conto
                } else {
                    operation.deposit(importo); 			// Servizio che deposita soldi sul conto
                }
            }
	    	
	    	// Aggiorna la vista del conto dopo l'operazione
            controllerLogin.getConsolleView().updateUIconto();
            return true;
	    }
	    // Se nessuna operazione è stata trovata con il nome specificato, lancia un'eccezione
	    throw new IllegalStateException("Operazione inesistente! Impossibile eseguire l'operazione.");
	    
		/*if(tipo) {
			if(nome.toUpperCase().startsWith("OBBIETTIVO")) {
				objective = getObjective(nome.substring(12));
				if(!objective.isPresent()) {
					throw new IllegalStateException("Obbiettivo inesistente! Impossibile eseguire l'operazione.");
				}
				objective.get().deposit(importo);	// obbiettivo con WithDraw mi deposita i soldi sul conto.
			} else {
				return serviceOperations.withdraw(importo);
			}
		} else {
			if(nome.toUpperCase().startsWith("OBBIETTIVO")) {
				objective = getObjective(nome.substring(12));
				if(!objective.isPresent()) {
					throw new IllegalStateException("Obbiettivo inesistente! Impossibile eseguire l'operazione.");
				}
				return objective.get().withdraw(importo);	// obbiettivo con WithDraw mi deposita i soldi sul conto.
			} else {
				serviceOperations.deposit(importo);
			}
		}	
		
		controllerLogin.getConsolleView().updateUIconto();
		return true;*/
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
	public void saveObjective(boolean bNew, String nameObjective, String newNameOb, String newDescrOb, double savingTarget) {
		Optional<ObjectiveImpl> objective = getObjective(nameObjective);
	
		// Se sono su nuovo creo un nuovo obbiettivo > tanto l'id lo incremento alla creazione, quindi non può già esistere.
		if(bNew) {
			if(objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo esistente! Impossibile crearlo.");
			}
			addObjective(newNameOb, newDescrOb, savingTarget);//MODIFICATO: aggiunto param target per la soglia di risparmio da raggiungere nell'obbiettivo
		} else {
			if(!objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo inesistente! Impossibile modificarlo.");
			}
			modifyObjective(objective.get(), newNameOb, newDescrOb, savingTarget);
		}		
	}
	
	@Override
	public void removeObjective(String name) {
		Optional<ObjectiveImpl> objective = getObjective(name);
		
		if(objective.isPresent()) {
			operationsList.remove(objective.get());
		} else {
			throw new IllegalStateException("Obbiettivo inesistente! Impossibile cancellarlo.");
		}
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
		/*// Ottengo lo stream dei servizi
		Stream<TransactionImpl> serviceTransaction = serviceOperations != null ? services.getList().stream() : Stream.empty();
		
		// Ottengo lo stream degli obbiettivi
		Stream<TransactionImpl> objectiveTransaction = 
				listObjectives != null ? listObjectives.stream()
										 				.flatMap(objective -> objective.getList().stream()) : Stream.empty();*/
		//return Stream.concat(serviceTransaction, objectiveTransaction).collect(Collectors.toList());
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
                .map(t -> t.getName() + "  " + t.getDescription() + "  " + t.getAmount() + " €")
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
	public CalendarView drawCalendar() {
		// Inizializzazione calendario
    	LocalDate today = LocalDate.now();
    	int anno = today.getYear();
    	int mese = today.getMonthValue();
		
		this.calendarModel = new CalendarModel(anno, mese);
        return new CalendarView(this, calendarModel);
	}
	
	@Override
	public Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, State s, 
								String newName, String newdesc, String newDaOra, String newAora) {
		int daysEvents = 0;
		LocalDate currentDate = daData;
		Set<Event> events = new TreeSet<>(new ComparatorEvents());
		
		// Controllo se l'evento è già presente nella data/orario definita. Se non è presente lo creo. In caso contrario mostro messaggio di errore.
		if(bNew) {
			// creazione dell'evento in più date.
			if(!daData.equals(aData)) {
				daysEvents = (int) ChronoUnit.DAYS.between(daData, aData);
				System.out.println("daysEvents: " + daysEvents);
				for(int i = 0; i <= daysEvents; i++) {
					if(i > 0) {
						currentDate = currentDate.plusDays(1);
					}
					Event event = calendario.newEvent(name, currentDate, daOra, newName, newdesc, newDaOra, newAora);
					if(event == null) {
						throw new IllegalStateException("Evento già esistente! Impossibile crearlo.");
					}
					events.add(event);
				}
				System.out.println("events size: " + events.size());
			// creazione dell'evento nella data singola.
			} else {
					Event event = calendario.newEvent(name, daData, daOra, newName, newdesc, newDaOra, newAora);
					if(event == null) {
						throw new IllegalStateException("Evento già esistente! Impossibile crearlo.");
					}
					events.add(event);
			}
		} else {
				Event event = calendario.modifyEvent(name, desc, daData, aData, daOra, aOra, newName, newdesc, currentDate, newDaOra, newAora);	
				if(event == null) {
					throw new IllegalStateException("Evento inesistente! Impossibile modificarlo.");
				}
				events.add(event);
		}		
		return events;
	}
	
	@Override
	public Event removeEvent(String name, LocalDate date, String daOra) {
		return calendario.removeEvent(name, date, daOra);		
	}
}
