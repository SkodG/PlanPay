package pp.projects.model;

public interface Objective {
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
	public double getBalance();
	public double getSavingTarget();	
	public void setSavingTarget(double newTarget);
	
	
}

