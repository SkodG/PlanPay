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
		return this.balance;
	}

	@Override
	public void setBalance(Double balance) {
		this.balance = balance;		
	}

	@Override
	public void addBalance(double amount) {
		this.balance += amount;		
	}

	@Override
	public void subBalance(double amount) {
		this.balance -= amount;		
	}


	
}
