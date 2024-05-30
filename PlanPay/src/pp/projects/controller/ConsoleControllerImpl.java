package pp.projects.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.State;
import pp.projects.model.TransactionImpl;
import pp.projects.view.CalendarView;
import pp.projects.view.ObjectiveView;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;
	private ServicesImpl services;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<ObjectiveImpl> listObjectives;		// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<TransactionImpl> listTransactions;
	private CalendarImpl calendario;
	private CalendarView calendarView;
	private Account account;
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

	@Override
	public EventImpl saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, State s, 
								String newName, String newdesc, String newDaOra, String newAora) {
		int daysEvents = 0;
		EventImpl events = new EventImpl(newName, newdesc, aData, newDaOra, newAora);
		LocalDate currentDate = daData;
		
		// Controllo se l'evento è già presente nella data/orario definita. Se non è presente lo creo. In caso contrario mostro messaggio di errore.
		if(bNew) {
			// creazione dell'evento in più date.
			if(!daData.equals(aData)) {
				daysEvents = aData.getDayOfMonth() - daData.getDayOfMonth();
				for(int i = 0; i <= daysEvents; i++) {
					if(i > 0) {
						currentDate = currentDate.plusDays(i);
					}
					events = calendario.newEvent(name, currentDate, daOra, newName, newdesc, newDaOra, newAora);
					if(events == null) {
						throw new IllegalStateException("Evento già esistente! Impossibile crearlo.");
					}
				}
			// creazione dell'evento nella data singola.
			} else {
					events = calendario.newEvent(name, currentDate, daOra, newName, newdesc, newDaOra, newAora);
					if(events == null) {
						JOptionPane.showMessageDialog(null, "Evento già esistente, impossibile crearlo.", "Errore", JOptionPane.ERROR_MESSAGE);
						return null;
					}
			}
		} else {
				events = calendario.modifyEvent(name, desc, daData, aData, daOra, aOra, newName, newdesc, currentDate, newDaOra, newAora);	
				if(events == null) {
					throw new IllegalStateException("Evento inesistente! Impossibile modificarlo.");
				}
		}		
		return events;
	}
	
	@Override
	public boolean removeEvent(String name, LocalDate date, String daOra) {
		return calendario.removeEvent(name, date, daOra);		
	}
	
	public String setNameController() {
		return this.controllerLogin.getUserName();
	}

	public void drawCalendar() {
		this.calendarView = new CalendarView(this);
	}
	
	public void saveObjective(boolean bNew, String nameObjective, String newNameOb, String newDescrOb, double savingTarget) {
		Optional<ObjectiveImpl> objective = getObjective(nameObjective);
	
		// Se sono su nuovo creo un nuovo obbiettivo > tanto l'id lo incremento alla creazione, quindi non può già esistere.
		if(bNew) {
			if(objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo esistente! Impossibile crearlo.");
			}
			listObjectives.add(new ObjectiveImpl(account, newNameOb, newDescrOb, savingTarget));//MODIFICATO: aggiunto param target per la soglia di risparmio da raggiungere nell'obbiettivo
		} else {
			if(!objective.isPresent()) {
				throw new IllegalStateException("Obbiettivo inesistente! Impossibile modificarlo.");
			}
			modifyObjective(objective.get(), newNameOb, newDescrOb, savingTarget);
		}		
	}
	
	private void modifyObjective(ObjectiveImpl objective, String newNameOb, String newDescrOb, double savingTarget) {
		objective.setDescription(newDescrOb);
		objective.setName(newNameOb);
		objective.setSavingTarget(savingTarget);
	}

	/**
	 * 
	 * @param importo
	 * @param tipo
	 * @param nome
	 * @return
	 */
	// T = withdraw, F = deposit
	public boolean updateConto(double importo, boolean tipo, String nome) {
		Optional<ObjectiveImpl> objective = null;
		
		if(tipo) {
			if(nome.toUpperCase().startsWith("OBBIETTIVO")) {
				objective = getObjective(nome.substring(12));
				if(!objective.isPresent()) {
					throw new IllegalStateException("Obbiettivo inesistente! Impossibile eseguire l'operazione.");
				}
				objective.get().deposit(importo);	// obbiettivo con WithDraw mi deposita i soldi sul conto.
			} else {
				return services.withdraw(importo);
			}
		} else {
			if(nome.toUpperCase().startsWith("OBBIETTIVO")) {
				objective = getObjective(nome.substring(12));
				if(!objective.isPresent()) {
					throw new IllegalStateException("Obbiettivo inesistente! Impossibile eseguire l'operazione.");
				}
				return objective.get().withdraw(importo);	// obbiettivo con WithDraw mi deposita i soldi sul conto.
			} else {
				services.deposit(importo);
			}
		}	
		
		controllerLogin.getConsolleView().updateUIconto();
		return true;
	}

	@Override
	public Optional<ObjectiveImpl> getObjective(String name) {
		return listObjectives.stream()
				.filter(o -> o.getName().equals(name))
				.findFirst();
	}
	
	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	@Override
	public List<ObjectiveImpl> getObjectiveList() {
		return listObjectives == null ? null : Collections.unmodifiableList(listObjectives);
	}
	
	@Override
	public void removeObjective(String name) {
		Optional<ObjectiveImpl> objective = getObjective(name);
		
		if(objective.isPresent()) {
			listObjectives.remove(objective.get());
		} else {
			throw new IllegalStateException("Obbiettivo inesistente! Impossibile cancellarlo.");
		}
	}
	
	public Account getAccount() {
		return this.account;
	}
}
