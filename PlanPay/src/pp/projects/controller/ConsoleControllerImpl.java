package pp.projects.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.Event;
import pp.projects.model.EventImpl;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.Transaction;
import pp.projects.model.TransactionImpl;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;
	private ServicesImpl services;					// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<ObjectiveImpl> listObjectives;		// Non uso interfaccia perchè ho dei metodi nella classe astratta, che devo richiamare.
	private List<Event> listEvents;
	private List<Transaction> listTransactions;
	private Account account;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		this.services = new ServicesImpl(this.account);
		this.listEvents = new ArrayList<Event>();
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
	
	/**
	 * @return la lista degli eventi inseriti dall'utente
	 */
	@Override
	public List<Event> getEvent() {
		return listEvents;
	}

	/**
	 * @return la lista degli obbiettivi inseriti dall'utente
	 */
	@Override
	public List<ObjectiveImpl> getObjective() {
		return listObjectives;
	}

	@Override
	public void newObject(String n, String d, String date, Double imp) {
		//ObjectiveImpl newObjective = new Objective(n, d, date, imp);
		
		
	}

	@Override
	public void removeObject(ObjectiveImpl o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newEvent(String n, String d, String date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEvent(EventImpl e) {
		// TODO Auto-generated method stub
		
	}
	
	public String setNameController() {
		return this.controllerLogin.getUserName();
	}


}
