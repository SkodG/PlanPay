package pp.projects.model;

import java.util.List;
import java.util.ArrayList;

public class ServicesImpl extends AbstractOperations implements Services {
	
	private List<TransactionImpl> transactionList;
	
	// bNegative = false;
	
	public ServicesImpl(Account c) {
		 //super.transactionList = new ArrayList<>();
		this.transactionList = new ArrayList<>();
		super.accountRef = c;
	}

	@Override
	public void deposit(double amount) {
		//operazione sul conto(+)
		this.accountRef.addBalance(amount);
		//istanzio nuova transazione
		TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Services");
		//aggiungo la transazione alla lista
		//super.transactionList.add(transaction);
		this.transactionList.add(transaction);
	}

	@Override
	public void withdraw(double amount) {
		//operazione sul conto(-)
		
		//TODO bNegative = (questo sotto che è boolean)
		this.accountRef.subBalance(amount);
		//istanzio nuova transazione
		TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Services");
		//aggiungo la transazione alla lista
		//super.transactionList.add(transaction);
		this.transactionList.add(transaction);
	}

	@Override
	public List<Transaction> getList() {
		return super.transactionList;
	}
	

	//TODO 
	// boolean getbNegative();
}
