package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	protected void doDeposit(double amount) {
		if(accountRef.subBalance(amount))
			savedBalance += amount;
	}
	
	@Override
	protected boolean doWithdraw(double amount) {
		if (isTargetMet() && savedBalance >= amount) {
			savedBalance -= amount;
			accountRef.addBalance(amount);
			return true;
		} 
		else
			return false;
	}
	
	@Override
	protected String getTransactionType() {
		return "Obbiettivo";
	}
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years) {
		//metodo per il calcolo del risparmio per questo obbiettivo
		//data inflazione annua, flusso giornaliero  di risparmio, interesse annuo
		//e durata del periodo di risparmio
		//TODO formula da rivedere
		return monthlySaving*12*years*savedBalance*Math.pow((1+interestRate-inflationRate), years);
	}
	
	@Override
	public Double getBalance() {
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
}
