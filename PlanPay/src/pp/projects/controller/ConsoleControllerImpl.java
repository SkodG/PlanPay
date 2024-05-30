package pp.projects.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	@Override
	public List<ObjectiveImpl> getObjectiveList() {
		return listObjectives == null ? null : Collections.unmodifiableList(listObjectives);
	}
	
	@Override
	public void removeObjective(ObjectiveImpl o) {
		// TODO Auto-generated method stub
		
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
						JOptionPane.showMessageDialog(null, "Evento già esistente, impossibile crearlo.", "Errore", JOptionPane.ERROR_MESSAGE);
						return null;
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
					JOptionPane.showMessageDialog(null, "Evento inesistente, impossibile modificarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
					return null;
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
	
	public void saveObjective(boolean bNew, String nameObjective, String descObjective, double savingTarget) {
		// Se sono su nuovo creo un nuovo obbiettivo > tanto l'id lo incremento alla creazione, quindi non può già esistere.
		if(bNew) {
			listObjectives.add(new ObjectiveImpl(account, nameObjective, descObjective, savingTarget));//MODIFICATO: aggiunto param target per la soglia di risparmio da raggiungere nell'obbiettivo
		} else {
			// Nel caso di modifica, controllo che l'obbiettivo esista e in caso positivo modifico i campi passati.
			listObjectives.stream()
						  .filter(o -> o.getName().equals(nameObjective))
						  .findFirst()
						  .ifPresent(o -> {
							  o.setDescription(descObjective);
						  	  o.setName(nameObjective);
						  });
		}		
	}
	
	//TODO: aggiungere metodi per il deposito e il prelievo di denaro dall'account per conto di ServicesView,
	//il controller farà quindi da tramite tra ServiceView e ServiceImpl
	public void updateConto(double importo, boolean tipo, String nome) {
		ObjectiveImpl objective = null;
		
		//tutto rispetto al tipo booleano passato.
		services.deposit(importo);
		
		if(nome.toUpperCase().startsWith("OBBIETTIVO")) {
			objective = getObjective(nome.substring(12));
			objective.deposit(importo);
		}
	}
	
	//TODO ricerca obbiettivo (nome)
	public ObjectiveImpl getObjective(String name) {
		return new ObjectiveImpl(account, name, name, 0);
	}
	
	public Account getAccount() {
		return this.account;
	}
}
