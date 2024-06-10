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

    public void deposit(double amount) {
        doDeposit(amount);
        transactionList.add(new Transaction(LocalDate.now(), getTransactionType() + " Deposito", amount));
    }

    public boolean withdraw(double amount) {
        boolean success = doWithdraw(amount);
        if (success) {
            transactionList.add(new Transaction(LocalDate.now(), getTransactionType() + " Prelievo", amount));
        }
        return success;
    }

    protected abstract void doDeposit(double amount);
    protected abstract boolean doWithdraw(double amount);
    protected abstract String getTransactionType();

    public List<Transaction> getList() {
        return transactionList;
    }
}