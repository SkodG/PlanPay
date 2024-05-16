package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginImpl implements Login{
	
	/* Campi della classe */
	private BufferedReader reader;
	private BufferedWriter writer;
    private Map<String, UserCredentials> credentials;
    private String accountName;
    
	private InputStream inputStream; 
    

	
	public LoginImpl() {
		this.credentials = new HashMap<>();
		this.accountName = "";
		this.inputStream = this.getClass().getResourceAsStream("/resource/credentials.txt");
		this.writer = null;
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
			this.reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String input;
			while ((input = reader.readLine()) != null) {
				String[] arrayCredentials = input.split(" ");
				credentials.put(arrayCredentials[1], new UserCredentials(arrayCredentials[2], arrayCredentials[0]));
			}
		} catch (FileNotFoundException e) {
            // Se il file non esiste, lo creeremo in seguito
			System.out.print("File non trovato: " + e);
        } catch (IOException e) {
            System.out.print("Problemi all'apertura del file: " + e);
        } finally {
        	// chiudo il reader se è stato aperto con successo
        	if (reader != null) {
        		try {
        			reader.close();
        		}catch (IOException e) {
        			System.out.println("Errore durante la chiusura del reader: " + e.getMessage());
        		}
        	}
        }
	}
	
	/**
	 * ritorna un booleano per sapere se le credenziali sono valide
	 */
	@Override
	public boolean valideAuthenticate(String utente, String password) {
		// Controlla che la chiave sia contenuta in "credentials".
		// In caso positivo prende la password da utente e la confronta con la password fornita come argomento.
		if (credentials.containsKey(utente)) {
			UserCredentials cred = credentials.get(utente);
            // Verifica se la password è corretta
            if (cred != null && cred.getPassword().equals(password)) {
                this.accountName = cred.getUserName();
                return true;
            }
		}
		return false;
	}
	
	/**
	 * ritorna un booleano per sapere se l'utente è stato registrato. Il metodo viene usato anche per la registrazione dello stesso.
	 */
	@Override
	public boolean registration(String utente, String password, String nomeUser) {
		if(credentials.containsKey(utente)) {
			return false;
		}
		credentials.put(utente, new UserCredentials(password, nomeUser));
		saveCredential(utente, password, nomeUser);
		return true;
	}
	
	/**
	 * metodo per salvare le credenziali su un file. Richiamato ad ogni nuova registrazione.
	 */
	@Override
	public void saveCredential(String utente, String password, String nomeUer) {
		// Percorso relativo al file nel progetto
        String relativePath = "src/resource/credentials.txt";
        File file = new File(relativePath);

        // Mi assicuro che la directory esista
        file.getParentFile().mkdirs();
		try {
			this.writer = new BufferedWriter(new FileWriter(file, true));
			
			writer.write(nomeUer + " " + utente + " " + password);
			writer.newLine();
		} catch (IOException e) {
            System.out.print("Problemi al salvataggio del file: " + e);
        } finally {
        	// Chiusura del writer, se è stato aperto con successo
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Errore durante la chiusura del writer: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }		
	}	
	
	public String getAccountName() {
		return this.accountName;
	}
}
