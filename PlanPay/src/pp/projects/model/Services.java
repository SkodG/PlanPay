package pp.projects.model;


public class Services extends AbstractOperations {
	
	

	public Services(Account account) {
		super(account);
	}
	/**
	 * effettua la richiesta di deposito all'Account
	 */
	@Override
	protected boolean doDeposit(double amount) {
		accountRef.addBalance(amount);
		return true;
	}
	/**
	 * effettua la richiesta di prelievo all'Account
	 */
	@Override
	protected boolean doWithdraw(double amount) {
		return accountRef.subBalance(amount);
	}

	@Override
	protected String getTransactionType() {
		return "Servizio ";
	}

	@Override
	public String nome() {
		return "";
	}

}