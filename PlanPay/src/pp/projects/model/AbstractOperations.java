package pp.projects.model;

import java.time.LocalDate;

public abstract class AbstractOperations {
	
	
    protected Account accountRef;

    public AbstractOperations(Account accountRef) {
        this.accountRef = accountRef;
    }

    /**
     * Richiede di effettuare un deposito ad Account tramite doWithDraw(amount)
     * Se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean deposit(double amount, String desc) {
        boolean success = doDeposit(amount);
        if(success) {
        	accountRef.addTransaction(new TransactionImpl(LocalDate.now(), " Deposito "+ getTransactionType() + desc, amount));
        }
        return success;    
    }
    /**
     * Richiede di effettuare un prelievo ad Account tramite doWithDraw(amount)
     * Se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean withdraw(double amount, String desc) {
        boolean success = doWithdraw(amount);
        if (success) {
        	accountRef.addTransaction(new TransactionImpl(LocalDate.now(), " Prelievo "+ getTransactionType() + desc, amount));
        }
        return success;
    }

    protected abstract boolean doDeposit(double amount);
    protected abstract boolean doWithdraw(double amount);
    /**
     * 
     * @return stringa di informazioni sul tipo di Operazione (Servizio o Obbiettivo)
     */
    protected abstract String getTransactionType();
    public abstract String nome();
}