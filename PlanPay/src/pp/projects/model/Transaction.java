package pp.projects.model;

public interface Transaction {
	/**
	 * aggiunge il servizio alla classe transazioni
	 * 
	 * @param s = servizio (pagamento/accredito)
	 * @throws IllegalArgumentException = restituisce l'eccezione quando viene passato un argomento inappropriato
	 */
	void addToTransaction(ServicesImpl s);
}
