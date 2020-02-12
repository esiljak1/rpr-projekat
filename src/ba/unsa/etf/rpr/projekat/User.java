package ba.unsa.etf.rpr.projekat;

public class User extends Person{
    private int id;
    private String username, password, email;

    public User(String firstname, String lastname, int age, Gender gender) {
        super(firstname, lastname, age, gender);
    }

    public User(int id, String firstname, String lastname, int age, Gender gender, String username, String password, String email) {
        super(firstname, lastname, age, gender);
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
