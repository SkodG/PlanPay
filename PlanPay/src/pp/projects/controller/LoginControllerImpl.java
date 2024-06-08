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
	
	@Override
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
				return false;
			}
		}else {
			return false;
		}
	}
	
	@Override
	public void newSignupButtonClick() {
        // Rendi la vista di signup visibile
        signupView.setVisible(true);
        loginView.setVisible(false);
	}
	
	@Override
	public boolean signupButtonClick(String user, String passw, String name) {
		boolean isRegistred = false;
		
		if(!user.trim().equals("") && 
		   !passw.trim().equals("") && 
		   !name.trim().equals("")) {
			  // richiamo la funzione per sapere se i dati sono corretti
			  isRegistred = loginModel.registration(user, passw, name);
			  if(isRegistred) {
				  return true;
			  }else {
				  return false;
			  }
		}else {
			return false;
		}
	}
	
	@Override
	public String getUserName() {
		return this.userName;
	}
	
	@Override
	public ConsolleView getConsolleView() {
		return this.consolleView;
	}
	
}
