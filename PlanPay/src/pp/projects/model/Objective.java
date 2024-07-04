package pp.projects.model;

import java.util.List;

public interface Objective extends Data {
	/**
	 * 
	 * @param targetAmount = la cifra da raggiungere
	 * @param frequency = la frequenza mensile di versamento dei risparmi
	 * @param years = numero  di anni per raggiungere la cifra targetAmount
	 * @param months = numero di mesi per raggiungere la cifra targetAmount
	 * @param isBalanceAccounted indica se si deve tenere conto della somma già depositata nell'obbiettivo
	 * @return la somma da versare con frequenza = frequency, per raggiungere la cifra targetAmount
	 * @throws IllegalInputException 
	 */
	public double savingForecast(double targetAmount, double frequency, int years, int months, boolean isBalanceAccounted) throws IllegalInputException;
	/**
	 * 
	 * @return true se il risparmio è uguale o superiore al valore fissato come obbiettivo,
	 *  altrimenti false
	 */
	public boolean isTargetMet();
	
	public double getBalance();
	public double getSavingTarget();	
	public void setSavingTarget(double newTarget);	
	
    public List<Transaction> getList();
    public boolean deposit(double amount, String desc);
    public boolean withdraw(double amount, String desc);
}

