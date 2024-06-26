package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOperations {
	
	
    protected List<Transaction> transactionList;
    protected Account accountRef;

    public AbstractOperations(Account accountRef) {
        this.accountRef = accountRef;
        this.transactionList = new ArrayList<>();
    }

    public boolean deposit(double amount, String desc) /*throws IllegalInputException */{
    	//if(amount < 0.0)
    		//throw new IllegalInputException("Valore negativo non valido!");
        boolean success = doDeposit(amount);
        if(success) {
        	transactionList.add(new Transaction(LocalDate.now(), getTransactionType() + " Deposito "+desc, amount));
        }
        return success;
        
        
    }

    public boolean withdraw(double amount, String desc) /*throws IllegalInputException */{
    	//if(amount < 0.0)
    		//throw new IllegalInputException("Valore negativo non valido!");
        boolean success = doWithdraw(amount);
        if (success) {
            transactionList.add(new Transaction(LocalDate.now(), getTransactionType() + " Prelievo "+desc, amount));
        }
        return success;
    }

    protected abstract boolean doDeposit(double amount);
    protected abstract boolean doWithdraw(double amount);
    protected abstract String getTransactionType();

    public List<Transaction> getList() {
        return transactionList;
    }
    
    abstract public String nome();
}