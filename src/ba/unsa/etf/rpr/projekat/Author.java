package ba.unsa.etf.rpr.projekat;

import java.util.Objects;

public class Author extends Person {
    private String university;

    public Author(String firstname, String lastname, int age, Gender gender, String university) {
        super(firstname, lastname, age, gender);
        this.university = university;
    }

    public Author(int id, String firstname, String lastname, int age, Gender gender, String university) {
        super(id, firstname, lastname, age, gender);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return super.getFirstname() + " " + super.getLastname() +
               " " + university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(super.getFirstname(), author.getFirstname()) && Objects.equals(super.getLastname(), author.getLastname()) && Objects.equals(super.getAge(), author.getAge());
    }
}
