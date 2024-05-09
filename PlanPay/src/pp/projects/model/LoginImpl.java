package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginImpl implements Login{
	
	/* Campi della classe */
	private static final String FILE_NAME = "credenziali.txt";
	private BufferedReader reader;
	private BufferedWriter writer;
    private Map<String, String> credentials;
    private String accountName;
    
	private String tempDir;		// percorso di cartella temporanea;
	private String filePath;

	
	public LoginImpl() {
		this.credentials = new HashMap<>();
		this.accountName = "";
		this.tempDir = System.getProperty("java.io.tmpdir");
		this.filePath = tempDir + FILE_NAME;
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
			this.reader = new BufferedReader(new FileReader(filePath));
			
			String input = this.reader.readLine();
			if (input != null) {
				String[] arrayCredentials = input.split(" ");
				// arrayCredentials[0] è il nome account
				accountName = arrayCredentials[0];
				credentials.put(arrayCredentials[1], arrayCredentials[2]);
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
		if(credentials.containsKey(utente) || !createFile()) {
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
			this.writer = new BufferedWriter(new FileWriter(filePath));
			
			//itero su ogni coppia chiave-valore (o "entry") nella mappa credentials. 
			for (Map.Entry<String, String> entry : credentials.entrySet()) {
				writer.write(entry.getKey() + " " + entry.getValue());
				writer.newLine();
				System.out.println("SCritta riga in " + filePath);
			}
		} catch (IOException e) {
            System.out.print("Problemi al salvataggio del file: " + e);
        }		
	}	
	
	public boolean createFile() {	
		// creo un nuovo file
		File file = new File(filePath);
		
		try {
			// creazione del file
			if (file.createNewFile()) {
				System.out.println("Il file è stato creato con successo nella cartella: " + filePath);
            }
		}catch(IOException e) {
            System.out.println("Si è verificato un errore durante la creazione del file.");
            e.printStackTrace();
            return false;
		}
		return true;
		
	}
	
	public String getAccountName() {
		return this.accountName;
	}
}
