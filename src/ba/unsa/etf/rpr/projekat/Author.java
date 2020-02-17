package ba.unsa.etf.rpr.projekat;

import java.util.Objects;

public class Author implements Person {
    private String firstname, lastname;
    private int age;
    private Gender gender;
    private int id;
    private String university;

    public Author(String firstname, String lastname, int age, Gender gender, String university) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.university = university;
        this.id = 0;
    }

    public Author(int id, String firstname, String lastname, int age, Gender gender, String university) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
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
        return getFirstname() + " " + getLastname() +
               " " + university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(getFirstname(), author.getFirstname()) && Objects.equals(getLastname(), author.getLastname()) && Objects.equals(getAge(), author.getAge());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
