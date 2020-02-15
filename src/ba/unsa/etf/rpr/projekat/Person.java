package ba.unsa.etf.rpr.projekat;

public class Person {
    private String firstname, lastname;
    private int age;
    private Gender gender;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person(int id, String firstname, String lastname, int age, Gender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.id = id;
    }

    public Person(String firstname, String lastname, int age, Gender gender) {
        if(age <= 0) throw new IllegalArgumentException("Age must be positive!!");
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age <= 0) throw new IllegalArgumentException("Age must be positive!!");
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
