package pp.projects.model;

import java.util.List;
import java.util.ArrayList;

public class ServicesImpl extends AbstractOperations implements Services {
	
	private List<Transaction> transactionList;
	
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
	public boolean withdraw(double amount) {
		//operazione sul conto(-)
		boolean bOccured = this.accountRef.subBalance(amount);
		if(bOccured) {
			//istanzio nuova transazione
			TransactionImpl transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Services");
			//aggiungo la transazione alla lista
			this.transactionList.add(transaction);
		}
		return bOccured;
	}

	@Override
	public List<Transaction> getList() {
		return this.transactionList;
	}
	

	//TODO 
	// boolean getbNegative();
}
