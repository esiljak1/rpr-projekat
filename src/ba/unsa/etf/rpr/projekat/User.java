package ba.unsa.etf.rpr.projekat;

public class User extends Person{
    private int id;
    private String uni;

    public User(String firstname, String lastname, int age, Gender gender) {
        super(firstname, lastname, age, gender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }
}
