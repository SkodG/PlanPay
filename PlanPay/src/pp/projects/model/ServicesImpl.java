package pp.projects.model;


public class ServicesImpl extends AbstractOperations {
	
	

	public ServicesImpl(Account account) {
		super(account);
	}

	@Override
	protected boolean doDeposit(double amount) {
		//operazione sul conto(+)
		accountRef.addBalance(amount);
		return true;
	}
		
	@Override
	protected boolean doWithdraw(double amount) {
		return accountRef.subBalance(amount);
	}

	@Override
	protected String getTransactionType() {
		return "Servizio";
	}

	@Override
	public String nome() {
		return "";
	}

}