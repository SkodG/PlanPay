package pp.projects.model;

public class TransactionImpl {
	// Definizione dei campi
	private String tipo; // deposito o prelievo
	private String sorgente;
	
	// Costruttore per inizializzare la transazione
	public TransactionImpl(String t, String s) {
		this.tipo = t;
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
}
