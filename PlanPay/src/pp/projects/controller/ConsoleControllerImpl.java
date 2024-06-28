package pp.projects.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import pp.projects.model.AbstractOperations;
import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.IllegalOperationException;
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
	private CalendarImpl calendario;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		this.operationsList = new ArrayList<>();
        this.operationsList.add(new ServicesImpl(account));
	}
		
	@Override
	public boolean updateConto(double importo, boolean tipo, String nome, OperationType operType) throws IllegalOperationException {
		boolean bRes = doOperation(importo, tipo, nome, operType);
		
		 if (!bRes) {
		     throw new IllegalOperationException("Operazione non valida o fondi insufficienti.");
		 }
	    
	     // Aggiorna la vista del conto dopo l'operazione
         controllerLogin.getConsolleView().updateUIconto();
         return bRes;
	}
	
	private boolean doOperation(double importo, boolean tipo, String nome, OperationType operType) {
		boolean bRes = false;
		
		// Tipo: true indica un deposito (deposit)
		// Tipo false indica un prelievo (withdraw)
		if (operType == OperationType.OBIETTIVO) {
		    for (AbstractOperations operation : operationsList) {
		         if (operation instanceof ObjectiveImpl) {
		        	 // prendo la lista di tutti gli obbiettivi
		        	 if(operation.nome().equals(nome)) {
			              if (tipo) {    	
			                  bRes = operation.deposit(importo, "");
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
		                    bRes = operation.deposit(importo, nome);
		                } else {
		                    bRes = operation.withdraw(importo, nome);
		                }
		            }
		       }
		  }
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
	
	@Override
	public List<ObjectiveImpl> getObjectiveList() {
		return operationsList.stream()
							 .filter(oper -> oper instanceof ObjectiveImpl)
							 .map(oper -> (ObjectiveImpl) oper)
							 .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}
	
	@Override
	public List<Transaction> getAllTransactions() {
		return operationsList.stream()
							  .flatMap(operat -> operat.getList().stream())
							  .collect(Collectors.toList());
	}
	
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

	@Override
	public CalendarModel getCalendarModel() {
		LocalDate today = LocalDate.now();
        return new CalendarModel(today.getYear(), today.getMonthValue());
	}
	
	@Override
	public CalendarView drawCalendar() {		
        return new CalendarView(this, getCalendarModel());
    }
	
	@Override
	public Set<Event> loadEvents() {
        return calendario.loadEventsFromFile();
    }
	
	@Override
    public Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
                                String newName, String newdesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, EventNotFoundException, InvalidParameterException {
        if (bNew) {
            createNewEvents(name, desc, daData, aData, daOra, aOra, newName, newdesc, newDaOra, newAora, stato, identifier);
        } else {
            modifyExistingEvent(name, desc, daData, aData, daOra, aOra, newName, newdesc, newDaOra, newAora, stato, identifier);
        }

        if (!calendario.saveEventsToFile()) {
            throw new RuntimeException("Errore durante il salvataggio degli eventi.");
        }

        return calendario.getAllEvents();
    }

    private void createNewEvents(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
                                 String newName, String newdesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, InvalidParameterException {
        int daysEvents = (int) ChronoUnit.DAYS.between(daData, aData);
        for (int i = 0; i <= daysEvents; i++) {
            LocalDate currentDate = daData.plusDays(i);
            calendario.newEvent(name, currentDate, daOra, newName, newdesc, newDaOra, newAora, stato, identifier);
        }
    }
    
    private void modifyExistingEvent(String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra,
    								 String newName, String newdesc, String newDaOra, String newAora, State stato, String identifier) throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
		System.out.println("NEW DESC CONTROLLER : " + newdesc);
    	boolean modified = calendario.modifyEvent(name, desc, daData, aData, daOra, aOra, newName, newdesc, daData, newDaOra, newAora, stato, identifier);
		if (!modified) {
			throw new EventNotFoundException("Evento inesistente! Impossibile modificarlo.");
		}
	}
	
	@Override
	public Event removeEvent(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException  {
		return calendario.removeEvent(name, date, daOra, aOra);
	}
	
	public void updateUIevents() {
		controllerLogin.getConsolleView().updateEventsUI();
	}
	
	public Set<Event> getAllEventToFile(){
		this.calendario = new CalendarImpl(0, setNameController());
		return calendario.getAllEvents();
	}
}
