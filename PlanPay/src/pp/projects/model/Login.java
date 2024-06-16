package pp.projects.model;

/*
 * interfaccia per identificare l'utente
 */

public interface Login {
	
	/**
	 *  Metodo usato per caricare le credenziali da un file.
	 */
	void loadCredential();			
	
	/**
	 * 
	 * @param u
	 * @param p
	 * @return 	di un booleano per sapere se le credenziali sono valide.
	 */
	boolean valideAuthenticate(String u, String p);		
	
	/**
	 * 
	 * @param u
	 * @param p
	 * @param n
	 * @returndi un booleano per sapere se l'utente è stato registrato. Il metodo viene usato anche per la registrazione dello stesso.
	 */
	boolean registration(String u, String p, String n);		
	
	/**
	 * Metodo usato per salvare le credenziali su un file. Richiamato ad ogni nuova registrazione.
	 * 
	 * @param u
	 * @param p
	 * @param n
	 */
	void saveCredential(String u, String p, String n);			
}
