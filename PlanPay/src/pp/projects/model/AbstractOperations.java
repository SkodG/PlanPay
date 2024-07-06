package pp.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOperations {
	
	
    protected List<TransactionImpl> transactionList;
    protected Account accountRef;

    public AbstractOperations(Account accountRef) {
        this.accountRef = accountRef;
        this.transactionList = new ArrayList<>();
    }

    public boolean deposit(double amount, String desc) {
        boolean success = doDeposit(amount);
        if(success) {
        	transactionList.add(new TransactionImpl(LocalDate.now(), " Deposito "+ getTransactionType() + desc, amount));
        }
        return success;
        
        
    }

    public boolean withdraw(double amount, String desc) {
        boolean success = doWithdraw(amount);
        if (success) {
            transactionList.add(new TransactionImpl(LocalDate.now(), " Prelievo "+ getTransactionType() + desc, amount));
        }
        return success;
    }

    protected abstract boolean doDeposit(double amount);
    protected abstract boolean doWithdraw(double amount);
    protected abstract String getTransactionType();

    public List<TransactionImpl> getList() {
        return transactionList;
    }
    
    abstract public String nome();
}