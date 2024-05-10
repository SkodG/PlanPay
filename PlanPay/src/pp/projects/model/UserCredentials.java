package pp.projects.model;

// Classe per contenere 2 valori (nome utente e password)
public class UserCredentials {
	private String userName;
    private String password;

    public UserCredentials(String password, String userName) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
