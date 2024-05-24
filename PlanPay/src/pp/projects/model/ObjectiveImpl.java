package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectiveImpl extends AbstractOperations implements Objective, Data {
	
	private String name;
	private String description;
	private double savedBalance;
	private LocalDate date; //type?
	private int id;
	//TODO aggiungere l'importo double per le funzione get e set(inizializzato a 0)
	
	public ObjectiveImpl(Account c, int id, String n, String desc) {
		transactionList = new ArrayList<>();
		super.accountRef = c;
		this.name = n;
		this.id = id;
		this.description = desc;
		this.savedBalance = 0.0;
		this.date = date; // conviene modificare date per generarla 
		//al momento  dell'istanziazione senza prenderla dagli argomenti del costruttore
	}
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years) {
		//metodo per il calcolo del risparmio per questo obbiettivo
		//data inflazione annua, flusso giornaliero  di risparmio, interesse annuo
		//e durata del periodo di risparmio
		
		return monthlySaving*12*years*savedBalance*Math.pow((1+interestRate-inflationRate), years);
	}
	
	@Override
	public void deposit(double amount) {
		//operazione sul conto(-)
		this.accountRef.subBalance(amount);
		//istanzio nuova transazione
		TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Objective");
		//aggiungo la transazione alla lista
		transactionList.add(transaction);
		//se la transazione è avvenuta posso modificare il bilancio(+)
		savedBalance += amount;
	}

	@Override
	public boolean withdraw(double amount) {
		boolean bOccured = false;
		//controllo che sia presente la somma sufficiente
		if(savedBalance >= amount) {
			bOccured = true;
			//operazione sul conto(-)
			this.accountRef.addBalance(amount);
			//istanzio nuova transazione
			TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Objective");
			//aggiungo la transazione alla lista
			transactionList.add(transaction);
			//se la transazione è avvenuta posso modificare il bilancio(-)
			savedBalance -= amount;
		}
		return bOccured;
	}
	
	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String n) {	
		this.name = n;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String d) {
		this.description  = d;
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}
	
	public int getId() {
		return this.id;
	}
}
