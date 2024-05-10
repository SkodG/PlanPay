package pp.projects.model;

/*
 * interfaccia per identificare l'utente
 */

public interface Login {
	
	void loadCredential();			
	
	boolean valideAuthenticate(String u, String p);		
	
	boolean registration(String u, String p, String n);		
	
	void saveCredential(String u, String p, String n);			
}
