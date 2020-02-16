package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class User implements Person{
    private SimpleStringProperty firstname, lastname;
    private SimpleIntegerProperty age;
    private SimpleObjectProperty<Gender> gender;
    private int id;
    private SimpleStringProperty username, password, email;
    private String image;

    public User(String firstname, String lastname, int age, Gender gender) {
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleObjectProperty<>(gender);
    }
    public User(){
        firstname = new SimpleStringProperty();
        lastname = new SimpleStringProperty();
        age = new SimpleIntegerProperty();
        gender = new SimpleObjectProperty<>();
        id = 0;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        email = new SimpleStringProperty();
        image = new String();
    }

    public User(int id, String firstname, String lastname, int age, Gender gender, String username, String password, String email, String image) {
        this.id = id;
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleObjectProperty<>(gender);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.image = image;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return firstname.get();
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    @Override
    public String getLastname() {
        return lastname.get();
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    @Override
    public int getAge() {
        return age.get();
    }

    @Override
    public void setAge(int age) {
        this.age.set(age);
    }

    @Override
    public Gender getGender() {
        return gender.get();
    }

    @Override
    public void setGender(Gender gender) {
        this.gender.set(gender);
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public SimpleObjectProperty<Gender> genderProperty() {
        return gender;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
}
