package pp.projects.controller;

import java.util.ArrayList;
import java.util.List;

import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.EventImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.TransactionImpl;

public class ConsoleControllerImpl implements ConsoleController{

	private LoginControllerImpl controllerLogin;
	private ServicesImpl services;
	private List<ObjectiveImpl> listObjectives;
	private List<EventImpl> listEvents;
	private List<TransactionImpl> listTransactions;
	private Account account;
	
	// costruttore
	public ConsoleControllerImpl(LoginControllerImpl cl) {
		this.controllerLogin = cl;
		this.account = new AccountImpl(controllerLogin.getUserName());
		this.services = new ServicesImpl(this.account);
		this.listEvents = new ArrayList<EventImpl>();
		this.listObjectives = new ArrayList<ObjectiveImpl>();
		this.listTransactions = new ArrayList<TransactionImpl>();
	}
	
	/**
	 * @return la lista di tutte le transazioni
	 */
	@Override
	public List<TransactionImpl> getAllTransactions() {
		// stream dei servizi
		//Stream<TransactionImpl> servicesTransactions = services
		// creo uno stream degli obbiettivi
		
		// concateno gli stream per avere una lista completa
		return listTransactions;
	}
	
	/**
	 * @return la lista degli eventi inseriti dall'utente
	 */
	@Override
	public List<EventImpl> getEvent() {
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
