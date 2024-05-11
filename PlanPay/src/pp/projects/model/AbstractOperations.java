package pp.projects.model;

import java.util.List;

public abstract class AbstractOperations {
	
	protected List<Transaction> transactionList;
	
	protected Account accountRef;
	
	public abstract void deposit(double amount);
	
	public abstract void withdraw(double amount);
	
	public List<Transaction> getList(){
		return transactionList;
	}
}