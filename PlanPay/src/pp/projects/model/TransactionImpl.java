package pp.projects.model;

import java.time.LocalDate;

public class TransactionImpl implements Data, Transaction {
	// Definizione dei campi
	private String tipo; // deposito o prelievo
	private String sorgente;
	private Double amount;	// TODO da settare il valore in base all'operazione fatta (presa quindi da services o da objective)
	
	// Costruttore per inizializzare la transazione
	public TransactionImpl(String s) {
		this.tipo = "";
		this.sorgente = s;
	}
	
	// Getter e Setter per i campi
	/**
	 * 
	 * @return tipo (mi dice se vado ad incrementare o diminuire il conto)
	 */
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
	/**
	 * 
	 * @return sorgente (mi dice da dove la transazione è stata effettuata)
	 */
    public String getSorgente() {
        return sorgente;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getAmount() {
		return this.amount;
	}
}
