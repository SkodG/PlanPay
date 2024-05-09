package pp.projects.model;

/*
 * interfaccia per identificare l'utente
 */

public interface AuthenticationService {
	
	void loadCredential();			
	
	boolean valideAuthenticate(String u, String p);		
	
	boolean registration(String u, String p);		
	
	void saveCredential();			
}
