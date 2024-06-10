package pp.projects.model;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServicesImpl extends AbstractOperations {
	

	public ServicesImpl(Account account) {
		super(account);
	}

	// void creaTransazione(	//LocalDate date, String name, double amount, String type) > richiamata da 'deposita' o 'preleva'

	@Override
	protected void doDeposit(double amount) {
		//operazione sul conto(+)
		accountRef.addBalance(amount);		
	}
		
	@Override
	protected boolean doWithdraw(double amount) {
		return accountRef.subBalance(amount);
	}

	@Override
	protected String getTransactionType() {
		return "Servizio";
	}


}
