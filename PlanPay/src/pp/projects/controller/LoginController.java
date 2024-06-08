package pp.projects.controller;

import pp.projects.view.ConsolleView;

public interface LoginController {
	
	boolean loginButtonClick(String user, String passw);
	void newSignupButtonClick();
	boolean signupButtonClick(String user, String passw, String name);
	String getUserName();
	ConsolleView getConsolleView();
}
