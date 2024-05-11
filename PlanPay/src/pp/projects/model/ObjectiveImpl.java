package pp.projects.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveImpl extends AbstractOperations implements Objective, Data {
	
	private String name;
	private String description;
	private String date; //type?
	
	public ObjectiveImpl(Account c, String n, String desc, String date) {
		transactionList = new ArrayList<>();
		super.accountRef = c;
		this.name = n;
		this.description = desc;
		this.date = date;
	}
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years) {
		//TODO metodo per il calcolo del risparmio per questo obbiettivo
		//data inflazione, flusso giornaliero  di risparmio, interesse annuo
		//e periodo temporale
		return 0.0;
	}
	
	@Override
	public void deposit(double amount) {
		//operazione sul conto(-)
		this.accountRef.subBalance(amount);
		//istanzio nuova transazione
		Transaction transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Objective");
		//aggiungo la transazione alla lista
		transactionList.add(transaction);
	}

	@Override
	public void withdraw(double amount) {
		//operazione sul conto(-)
		this.accountRef.addBalance(amount);
		//istanzio nuova transazione
		TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Objective");
		//aggiungo la transazione alla lista
		transactionList.add(transaction);
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
	public String getDate() {
		return this.date;
	}
}
