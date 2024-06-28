package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import pp.projects.controller.LoginControllerImpl;
import pp.projects.model.AuthenticationException;
import pp.projects.model.RegistrationException;
import pp.projects.model.UserCredentials;

public class LoginTest {
    private LoginControllerImpl loginController;
    private String tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        loginController = new LoginControllerImpl();
        tempFilePath = "src/resource/test_credentials.txt";
        loginController.getLoginModel().setPath(tempFilePath);
    }

    // signup success
    @Test
    public void testSuccessfulSignup() throws RegistrationException {
        boolean isRegistered = loginController.signupButtonClick("newuser", "newpassword", "newuser");
        assertTrue(isRegistered);
    }
    
    @Test
    public void testSaveCredential() throws IOException {
        loginController.getLoginModel().saveCredential("newuser", "newpassword", "newuser");

        // Verifico che il file non sia vuoto.
        File file = new File(tempFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        // Verifico il contenuto del file.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean bFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.equals("newuser newuser newpassword")) {
                	bFound = true;
                    break;
                }
            }
            assertTrue(bFound);
        }
    }
    
    @Test
    public void testLoadCredential() throws FileNotFoundException, IOException {
        loginController.getLoginModel().saveCredential("existinguser", "password123", "existinguser");
        
        loginController.getLoginModel().loadCredential();

        Map<String, UserCredentials> credentials = loginController.getLoginModel().getCredential();        
        assertTrue(credentials.containsKey("existinguser"));
        assertEquals("password123", credentials.get("existinguser").getPassword());
        assertEquals("existinguser", credentials.get("existinguser").getUserName());
    }

    // success login
    @Test
    public void testSuccessfulLogin() throws AuthenticationException {
        try {
            loginController.signupButtonClick("testuser", "password123", "Test User");
        } catch (RegistrationException e) {
        }

        boolean isAuthenticated = loginController.loginButtonClick("testuser", "password123");
        assertTrue(isAuthenticated);
    }
    
    @Test
    public void testGetAccountName() {
        // Verifico che il nome dell'account sia inizialmente vuoto
        assertEquals("", loginController.getUserName());

        try {
            loginController.signupButtonClick("testuser", "password123", "testuser");
            loginController.loginButtonClick("testuser", "password123");
        } catch (RegistrationException | AuthenticationException e) {
           
        }

        // Verifico che il nome dell'account sia impostato correttamente
        assertEquals("testuser", loginController.getUserName());
        
        //TODO Come gestisco il Nome?
    }

    @Test
    public void testException() {
		
		try {
			loginController.signupButtonClick("existinguser", "password123", "existinguser");
		} catch(Exception ex) {
			
		}
		
		/*** Test per RegistrationException ***/
		// Tento di registrare lo stesso utente
		assertThrows(RegistrationException.class, () -> loginController.signupButtonClick("existinguser", "newpassword", "existinguser"));
		// Tento di registrare un utente con credenziali vuote
		assertThrows(IllegalArgumentException.class, () -> loginController.signupButtonClick("", "", ""));
		
		
		/*** Test per AuthenticationException ***/
		// Login con credenziali errate
		assertThrows(AuthenticationException.class, () -> loginController.loginButtonClick("existinguser", "wrongpassword"));
		assertThrows(AuthenticationException.class, () -> loginController.loginButtonClick("wronguser", "password123"));
		assertThrows(AuthenticationException.class, () -> loginController.loginButtonClick("wronguser", "wrongpassword"));
		
		// Login con credenziali vuote
		assertThrows(IllegalArgumentException.class, () -> loginController.loginButtonClick("", ""));
		
	}
}
