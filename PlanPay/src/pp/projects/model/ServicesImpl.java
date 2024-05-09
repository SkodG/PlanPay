package pp.projects.model;

import java.util.ArrayList;
import java.util.List;

public class ServicesImpl extends AbstractOperations implements Services {
	
	
	public ServicesImpl(Account c) {
		 super.transactionList = new ArrayList<>();
		 super.accountRef = c;
	}

	@Override
	public void deposit(double amount) {
		//operazione sul conto(+)
		this.accountRef.addBalance(amount);
		//istanzio nuova transazione
		Transaction transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Services");
		//aggiungo la transazione alla lista
		super.transactionList.add(transaction);
	}

	@Override
	public void withdraw(double amount) {
		//operazione sul conto(-)
		this.accountRef.subBalance(amount);
		//istanzio nuova transazione
		Transaction transaction = new TransactionImpl(/*parametri di info per la transazione*/ "Services");
		//aggiungo la transazione alla lista
		super.transactionList.add(transaction);
	}

	@Override
	public List<Transaction> getList() {
		return super.transactionList;
	}

}
