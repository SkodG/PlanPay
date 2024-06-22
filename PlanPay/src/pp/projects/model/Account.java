package pp.projects.model;

public interface Account {

	public String getName();
	
	public Double getBalance();
	
	public void setBalance(Double balance);
	
	public void addBalance(double amount);
	
	public boolean subBalance(double amount);
}
