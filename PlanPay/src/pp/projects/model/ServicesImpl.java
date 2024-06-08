package pp.projects.model;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServicesImpl extends AbstractOperations implements Services {
	
	private List<Transaction> transactionList;

	public ServicesImpl(Account c) {
		this.transactionList = new ArrayList<>();
		super.accountRef = c;
	}

	// void creaTransazione(	//LocalDate date, String name, double amount, String type) > richiamata da 'deposita' o 'preleva'
	
	@Override
	public void deposit(double amount) {
		//operazione sul conto(+)
		this.accountRef.addBalance(amount);
		//istanzio nuova transazione
		
		// crea transazione fa questo:
		Transaction transaction = new Transaction(/*parametri di info per la transazione*/ "Servizio");
		//aggiungo la transazione alla lista
		this.transactionList.add(transaction);
	}

	@Override
	public boolean withdraw(double amount) {
		//operazione sul conto(-)
		boolean bOccured = this.accountRef.subBalance(amount);
		if(bOccured) {
			//istanzio nuova transazione
			Transaction transaction = new Transaction(/*parametri di info per la transazione*/ "Servizio");
			//aggiungo la transazione alla lista
			this.transactionList.add(transaction);
		}
		return bOccured;
	}

	@Override
	public List<Transaction> getList() {
		return this.transactionList;
	}
}
