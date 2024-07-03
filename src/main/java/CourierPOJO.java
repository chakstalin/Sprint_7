public class CourierPOJO {

    public CourierPOJO(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;

    }

    public CourierPOJO() {

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String login;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    private  String firstName;
}
