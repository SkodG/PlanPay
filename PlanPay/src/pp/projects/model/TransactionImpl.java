package pp.projects.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionImpl implements Transaction{
	
	private List<ServicesImpl> transactionsList;
	
	public TransactionImpl() {
		this.transactionsList = new ArrayList<>();
	}
	
	@Override
	public void addToTransaction(ServicesImpl s) {
		transactionsList.add(s);
	}

}
