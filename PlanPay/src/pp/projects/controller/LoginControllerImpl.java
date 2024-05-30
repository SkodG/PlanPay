package pp.projects.controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import pp.projects.model.LoginImpl;
import pp.projects.view.ConsolleView;
import pp.projects.view.LoginView;
import pp.projects.view.SignupView;

public class LoginControllerImpl implements LoginController{
	private LoginImpl loginModel;
	private LoginView loginView;
	private SignupView signupView;
	private String userName;
	private ConsolleView consolleView;
	private ConsoleControllerImpl controller;
	
	public LoginControllerImpl() throws IOException {
		this.loginModel = new LoginImpl();
		this.loginView = new LoginView(this);
		this.signupView = new SignupView(this);
		this.userName = "";
		this.controller = new ConsoleControllerImpl(this);
		this.consolleView = new ConsolleView(controller, controller.getAccount());
	}
	
	public boolean loginButtonClick(String user, String passw) {
		boolean isAuthenticated = false;
		
		// controllo che i dati siano stati inseriti
		if(!user.trim().equals("") && 
		   !passw.trim().equals("")) {
			// richiamo la funzione per sapere se i dati sono corretti
			isAuthenticated = loginModel.valideAuthenticate(user, passw);
			if(isAuthenticated) {
				this.userName = loginModel.getAccountName();
				consolleView.setVisible(true);
				return true;
			}else {
				// Autenticazione fallita, mostra un messaggio di errore
				JOptionPane.showMessageDialog(null, "Credenziali non valide. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Credenziali mancanti. Riprovare.", "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public void newSignupButtonClick() {
        // Rendi la vista di signup visibile
        signupView.setVisible(true);
        loginView.setVisible(false);
	}
	
	public void signupButtonClick(String user, String passw, String name) {
		boolean isRegistred = false;
		
		if(!user.trim().equals("") && 
		   !passw.trim().equals("") && 
		   !name.trim().equals("")) {
			  // richiamo la funzione per sapere se i dati sono corretti
			  isRegistred = loginModel.registration(user, passw, name);
			  if(isRegistred) {
				  JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Info", JOptionPane.INFORMATION_MESSAGE);
				  signupView.setVisible(false);
			  }else {
				  // Autenticazione fallita, mostra un messaggio di errore
				  JOptionPane.showMessageDialog(null, "Utente già registrato!", "Errore", JOptionPane.ERROR_MESSAGE);
			  }
		}else {
			JOptionPane.showMessageDialog(null, "Credenziali mancanti. Riprovare.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loginButtonClickToSignup() {
        // Rendi la vista di signup visibile
        signupView.setVisible(false);
        loginView.setVisible(true);
	}
	
	public String getUserName() {
		return this.userName;
	}
	
}
