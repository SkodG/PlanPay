package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import pp.projects.controller.LoginControllerImpl;
import pp.projects.model.AuthenticationException;
import pp.projects.model.RegistrationException;

public class LoginTest {
    private LoginControllerImpl loginController;

    //@Before
    public void setUp() throws IOException {
        loginController = new LoginControllerImpl();
    }

    @Test
    public void testSuccessfulLogin() throws AuthenticationException {
        // Prima, registra un utente per assicurarti che esista
        try {
            loginController.signupButtonClick("testuser", "password123", "Test User");
        } catch (RegistrationException e) {
            // Ignora se l'utente è già registrato
        }

        // Esegui il login con le credenziali corrette
        boolean isAuthenticated = loginController.loginButtonClick("testuser", "password123");
        assertTrue(isAuthenticated);
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginWithWrongCredentials() throws AuthenticationException {
        // Esegui il login con credenziali errate
        loginController.loginButtonClick("wronguser", "wrongpassword");
    }

    @Test
    public void testSuccessfulSignup() throws RegistrationException {
        // Esegui la registrazione di un nuovo utente
        boolean isRegistered = loginController.signupButtonClick("newuser", "newpassword", "New User");
        assertTrue(isRegistered);
    }

    @Test(expected = RegistrationException.class)
    public void testSignupWithExistingUser() throws RegistrationException {
        // Registra un utente
        loginController.signupButtonClick("existinguser", "password123", "Existing User");

        // Tenta di registrare lo stesso utente
        loginController.signupButtonClick("existinguser", "newpassword", "Existing User");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignupWithEmptyFields() throws RegistrationException {
        // Tenta di registrare un utente con campi vuoti
        loginController.signupButtonClick("", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginWithEmptyFields() throws AuthenticationException {
        // Tenta di eseguire il login con campi vuoti
        loginController.loginButtonClick("", "");
    }
}
