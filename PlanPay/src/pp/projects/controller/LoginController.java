package pp.projects.controller;

import pp.projects.model.AuthenticationException;
import pp.projects.model.RegistrationException;
import pp.projects.view.ConsolleView;

public interface LoginController {
	
	boolean loginButtonClick(String user, String passw) throws IllegalArgumentException, AuthenticationException;
	void newSignupButtonClick();
	boolean signupButtonClick(String user, String passw, String name) throws IllegalArgumentException, RegistrationException;
	String getUserName();
	ConsolleView getConsolleView();
}
