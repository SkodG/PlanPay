package pp.projects.model;

import java.time.LocalDate;

public class ObjectiveImpl extends AbstractOperations implements Objective, Data {
	
	
	private String name;
	private String description;
	private double savedBalance;
	private double savingTarget;
	private LocalDate date; //type?
	
    public ObjectiveImpl(Account accountRef, String name, String description, double savingTarget) {
        super(accountRef);
        this.name = name;
        this.description = description;
        this.savedBalance = 0.0;
        this.savingTarget = savingTarget;
        this.date = LocalDate.now();
    }
    
	@Override
	protected boolean doDeposit(double amount) {
		boolean result = false;
		if(accountRef.subBalance(amount)) {
			savedBalance += amount;
			result = true;
		}
		return result;
			
	}
	
	@Override
	protected boolean doWithdraw(double amount) {
		boolean result = false;
		if (isTargetMet() && savedBalance >= amount) {
			savedBalance -= amount;
			accountRef.addBalance(amount);
			result = true;
		} 
		return result;
	}
	
	@Override
	protected String getTransactionType() {
		return "Obbiettivo "+ getDescription();
	}
	
	
	@Override
	public double getBalance() {
		return this.savedBalance;
	}
	@Override
	public double getSavingTarget() {
		return savingTarget;
	}
	@Override
	public void setSavingTarget(double newTarget) {
		this.savingTarget = newTarget;
	}
	
	/**
	 * 
	 * @return true se il risparmio è uguale o superiore al valore fissato come obbiettivo,
	 *  altrimenti false
	 */
	public boolean isTargetMet() {
		if(savedBalance >= savingTarget)
			return true;		
		else
			return false;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String n) {	
		this.name = n;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String d) {
		this.description  = d;
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}

	@Override
	public String nome() {
		return this.getName();
	}

	@Override
	public double savingForecast(double targetAmount, double frequency, int years, int months, boolean isBalanceAccounted)
			throws IllegalInputException {
		int time = years + months/12;
		double result = 0.0;
		if(frequency > time*12)
			throw new IllegalInputException("Frequenza non corretta! Inserire un valore inferiore al periodo temporale totale");
		System.out.println("tempo "+ time);
		if(time*12 % frequency > 0)
			frequency = Math.floor(time*12/frequency);
		else
			frequency = frequency*12;
		System.out.println("arrotondo frequenza a "+ frequency);
		if(isBalanceAccounted)
			result = (targetAmount - this.getBalance())/frequency;
		else
			result = (targetAmount)/frequency;
		return result;
	}
}
