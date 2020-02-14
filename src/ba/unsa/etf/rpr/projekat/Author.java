package ba.unsa.etf.rpr.projekat;

public class Author extends Person {
    private String university;

    public Author(String firstname, String lastname, int age, Gender gender, String university) {
        super(firstname, lastname, age, gender);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
