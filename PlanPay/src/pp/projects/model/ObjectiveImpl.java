package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectiveImpl extends AbstractOperations implements Objective, Data {
	
	private String name;
	private String description;
	private double savedBalance;
	private double savingTarget;
	private LocalDate date; //type?
	//TODO aggiungere l'importo double per le funzione get e set(inizializzato a 0)
	
	public ObjectiveImpl(Account c, String n, String desc, double savingTarget) {
		transactionList = new ArrayList<>();
		super.accountRef = c;
		this.name = n;
		this.description = desc;
		this.savedBalance = 0.0;
		this.savingTarget = savingTarget;
		this.date = LocalDate.now(); // conviene modificare date per generarla 
		//al momento  dell'istanziazione senza prenderla dagli argomenti del costruttore
	}
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years) {
		//metodo per il calcolo del risparmio per questo obbiettivo
		//data inflazione annua, flusso giornaliero  di risparmio, interesse annuo
		//e durata del periodo di risparmio
		//TODO formula da rivedere
		return monthlySaving*12*years*savedBalance*Math.pow((1+interestRate-inflationRate), years);
	}
	
	public Double getBalance() {
		return this.savedBalance;
	}
	
	public double getSavingTarget() {
		return savingTarget;
	}
	
	public void setSavingTarget(double newTarget) {
		this.savingTarget = newTarget;
	}
	/**
	 * 
	 * @return true se il risparmio è uguale o superiore al valore fissato come obbiettivo,
	 *  altrimenti false
	 */
	public boolean isTargetMet() {
		boolean ret = false;
		if(savedBalance >= savingTarget)
			ret = true;		
		return ret;
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
	

}
