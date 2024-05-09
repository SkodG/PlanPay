package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationServiceImpl implements AuthenticationService{
	
	/* Campi della classe */
	private static final String FILE_NAME = "credentials.txt";
	private BufferedReader reader;
	private BufferedWriter writer;
    private Map<String, String> credentials;

	
	public AuthenticationServiceImpl() {
		this.credentials = new HashMap<>();
		// Alla creazione di un nuovo elemento autenticazione ricarico sempre i dati.
		// In tal modo se sono stati modificati ricarico sempre quelli corretti.
		loadCredential();
	}
	
	/**
	 *  metodo usato per caricare le credenziali da un file
	 */
	@Override
	public void loadCredential() {
		try {
			this.reader = new BufferedReader(new FileReader(FILE_NAME));
			
			String input = this.reader.readLine();
			if (input != null) {
				String[] arrayCredentials = input.split(" ");
				credentials.put(arrayCredentials[0], arrayCredentials[1]);
			}
		} catch (FileNotFoundException e) {
            // Se il file non esiste, lo creeremo in seguito
            System.out.println("Creazione del file '" + FILE_NAME + "'.");
        } catch (IOException e) {
            System.out.print("Problemi all'apertura del file: " + e);
        }
	}
	
	/**
	 * ritorna un booleano per sapere se le credenziali sono valide
	 */
	@Override
	public boolean valideAuthenticate(String utente, String password) {
		// Controlla che la chiave sia contenuta in "credentials".
		// In caso positivo prende la password da utente e la confronta con la password fornita come argomento.
		return credentials.containsKey(utente) && credentials.get(utente).equals(password);
	}
	
	/**
	 * ritorna un booleano per sapere se l'utente è stato registrato. Il metodo viene usato anche per la registrazione dello stesso.
	 */
	@Override
	public boolean registration(String utente, String password) {
		if(credentials.containsKey(utente)) {
			return false;
		}
		credentials.put(utente, password);
		saveCredential();
		return true;
	}
	
	/**
	 * metodo per salvare le credenziali su un file. Richiamato ad ogni nuova registrazione.
	 */
	@Override
	public void saveCredential() {
		try {
			this.writer = new BufferedWriter(new FileWriter(FILE_NAME));
			
			//itero su ogni coppia chiave-valore (o "entry") nella mappa credentials. 
			for (Map.Entry<String, String> entry : credentials.entrySet()) {
				writer.write(entry.getKey() + " " + entry.getValue());
				writer.newLine();
			}
		} catch (IOException e) {
            System.out.print("Problemi al salvataggio del file: " + e);
        }		
	}	
}
