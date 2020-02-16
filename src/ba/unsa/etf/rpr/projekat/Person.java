package ba.unsa.etf.rpr.projekat;

public interface Person {

    public int getId();

    public void setId(int id);

    public String getFirstname();

    public void setFirstname(String firstname);

    public String getLastname();

    public void setLastname(String lastname);

    public int getAge();

    public void setAge(int age);

    public Gender getGender();

    public void setGender(Gender gender);
}
