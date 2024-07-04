package pp.projects.model;

public class AccountImpl implements Account{
	
	private String name;
	private Double balance;
	
	public AccountImpl(String name) {
		this.name = name;
		this.balance = 0.0;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Double getBalance() {
		return Math.floor(balance*100) / 100;
	}

	@Override
	public void setBalance(Double balance) {
		this.balance = balance;		
	}

	@Override
	public void addBalance(double amount) {
		this.balance += amount;		
	}

	// restituisce true se è stata prelevata la somma
	// tenendo conto che non si può prelevare più del saldo corrente
	@Override 
	public boolean subBalance(double amount) {
		boolean legalOp = false;
		if(this.balance - amount >= 0.0) {
			this.balance -= amount;
			legalOp = true;
		}
		return legalOp;
	}
}
