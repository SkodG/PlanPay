package pp.projects.model;

import java.util.List;

public abstract class AbstractOperations {
	
	protected List<TransactionImpl> transactionList;
	
	protected Account accountRef;
	
	public abstract void deposit(double amount);
	
	public abstract boolean withdraw(double amount);
	
	public List<TransactionImpl> getList(){
		return transactionList;
	}
}