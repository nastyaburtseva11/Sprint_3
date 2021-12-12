package model;

public class CourierLogin {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
