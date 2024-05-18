package pp.projects.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.event.ListSelectionEvent;

import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.CalendarImpl;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.Transaction;
import pp.projects.model.TransactionImpl;
import pp.projects.view.CalendarView;
import pp.projects.view.EventView;
import pp.projects.view.ObjectiveView;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;
	private ServicesImpl services;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<ObjectiveImpl> listObjectives;		// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<Transaction> listTransactions;
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
		this.listTransactions = new ArrayList<Transaction>();
	}
	
	@Override
	public List<Transaction> getAllTransactions() {
		// Ottengo lo stream dei servizi
		Stream<Transaction> serviceTransaction = services != null ? services.getList().stream() : Stream.empty();
		
		// Ottengo lo stream degli obbiettivi
		Stream<Transaction> objectiveTransaction = 
				listObjectives != null ? listObjectives.stream()
										 				.flatMap(objective -> objective.getList().stream()) : Stream.empty();
		return Stream.concat(serviceTransaction, objectiveTransaction).collect(Collectors.toList());
	}
	
	public List<String> getDatiTransazione() {
		String data = "";
		String testo = "";
		String importo = "";
		
		List<Transaction> listTransactions = this.getAllTransactions();
		
		// ciclo ogni transazione e aggiungo i dati alla lista restituendolo
		for(Transaction t : listTransactions) {
			
			
			datiTransazione.add(data + "  " + testo + "  " + importo + " €");
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
	public void newEvent(LocalDate date) {
		eventView = new EventView(date);
		eventView.setVisible(true);
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
	
	public void saveObjective(boolean bNew, String nameObjective, String descObjective, LocalDate dateObjective) {
		// controllo che l'obbiettivo non sia già presente nella lista. Come lo controllo?
		// nome deve essere diverso. (OBBIETTIVO BIUNIVOCO)
		// se non sto modificando l'obbiettivo lo aggiungo alla lista
		if(!bNew) {
			listObjectives.add(new ObjectiveImpl(account, nameObjective, descObjective, dateObjective));
		} else {
			// TODO: prendo l'obbiettivo nella lista e setto i campi modificati
			
		}
		
	}
	
	// TODO inserire un metodo per controllare che se serviceview.bGetNegative() è false > do messaggio di errore.
}
